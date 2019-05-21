package com.example.samuelseptiano.employeeselfservice.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Application.MyApplication;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.User;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.userBodyParameter;
import com.example.samuelseptiano.employeeselfservice.Model.TokenAuthModel.UserLogin;
import com.example.samuelseptiano.employeeselfservice.Model.TokenAuthModel.UserLoginResponse;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.Session.SessionManagement;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.WHITE;

public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    Button btnLogin;
    Button btnSignup;
    EditText edtNIK;
    EditText edtPassword;
    SessionManagement session;
    String token="TOKEN";
    ImageView imgLogo;
    Realm realm;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());

        // Session Manager
        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_SHORT).show();



        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        edtNIK = (EditText) findViewById(R.id.edtNIK);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgLogo = findViewById(R.id.iconLogo);
        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);



        checkConnection();

        //if connection exist
        if(ConnectivityReceiver.isConnected()){

            //generate new token=====================
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserLoginResponse> call = apiService.getToken(new UserLogin("mario","secret"));
            call.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse>call, Response<UserLoginResponse> response) {
                    if(response.code() == 400){
                        Toast.makeText(getApplicationContext(), "Error 400: Bad Request", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() == 401){
                        Toast.makeText(getApplicationContext(), "Error 401: Not Authorized", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() == 200){
                        token = response.body().getToken();
                        //=========================================================
                        btnLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(edtNIK.getText().length()<1 || edtPassword.getText().length()<1) {
                                    Toast.makeText(getApplicationContext(), "NIK or Password Still Empty!!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    linlaHeaderProgress.setVisibility(View.VISIBLE);
                                    imgLogo.setVisibility(View.GONE);
                                    ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);

                                    String userName = edtNIK.getText().toString();
                                    String password = edtPassword.getText().toString();
                                    Call<User> call = apiService2.getUserDetail(new userBodyParameter(userName), "Bearer " + token);
                                    call.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            User user = response.body();
                                            try{
                                                String usernamee = user.getUsername();
                                                if (userName.equals(usernamee) && password.equals(user.getPassword().replaceAll(" ", ""))) {
                                                    String userQR = generateQR(user.getEmpNIK());
//
//                                                    int width =300;
//                                                    int height = 300;
//                                                    int smallestDimension = width < height ? width : height;

//                                                    Map<EncodeHintType, ErrorCorrectionLevel> hintMap =new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//                                                    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//                                                    String userQR = CreateQRCode(user.getEmpNIK(), "UTF-8", hintMap, smallestDimension, smallestDimension);

                                                    userRealmHelper.addArticle(user, token, userQR);
                                                    session.createLoginSession(userName, password.replaceAll(" ", ""));
                                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                    linlaHeaderProgress.setVisibility(View.GONE);
                                                    imgLogo.setVisibility(View.VISIBLE);

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Password Not Correct!", Toast.LENGTH_SHORT).show();
                                                    linlaHeaderProgress.setVisibility(View.GONE);
                                                    imgLogo.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            catch(Exception e){
                                                Toast.makeText(getApplicationContext(), "Username Not Registered!", Toast.LENGTH_SHORT).show();
                                                linlaHeaderProgress.setVisibility(View.GONE);
                                                imgLogo.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            // Log error here since request failed
                                            Log.e(TAG, t.toString());
                                            Toast.makeText(getApplicationContext(), "Error: Something went wrong happened :(", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                        });
                    }
                    //=========================================================
                }

                @Override
                public void onFailure(Call<UserLoginResponse>call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    Toast.makeText(getApplicationContext(),"Error: Something went wrong happened :(",Toast.LENGTH_SHORT).show();
                }
            });
            //=================================================

        }
        else{
            showToast(ConnectivityReceiver.isConnected());
        }



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(LoginActivity.this,MainActivity.class);
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private String generateQR(String content){
        String temp="";
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 550, 550);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : WHITE);
                }
            }

            //Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.ic_dashboard_black_24dp);

            ByteArrayOutputStream baos = new  ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);

        } catch (WriterException e) {
            temp = e.getMessage();
            e.printStackTrace();
        }
        return temp;
    }


    public  String CreateQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth){
        String temp = "";

        try {
            //generating qr code in bitmatrix type
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            //converting bitmatrix to bitmap

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    //pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                    pixels[offset + x] = matrix.get(x, y) ?
                            ResourcesCompat.getColor(getResources(),R.color.black_overlay,null) :WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            //setting bitmap to image view

            Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.ic_dashboard_black_24dp);


            ByteArrayOutputStream baos = new  ByteArrayOutputStream();
            overlay.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);


            //imageViewBitmap.setImageBitmap(mergeBitmaps(overlay,bitmap));

        }catch (Exception er){
            Log.e("QrGenerate",er.getMessage());
        }

        return temp;
    }



    public Bitmap mergeBitmaps(Bitmap overlay, Bitmap bitmap) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bitmap, new Matrix(), null);

        int centreX = (canvasWidth  - overlay.getWidth()) /2;
        int centreY = (canvasHeight - overlay.getHeight()) /2 ;
        canvas.drawBitmap(overlay, centreX, centreY, null);

        return combined;
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showToast(isConnected);
    }
    // Showing the status in Toast
    private void showToast(boolean isConnected) {
        String message;
        if (isConnected) {
            message = "Good! Connected to Internet";
        } else {
            message = "Sorry! Not connected to internet";
        }
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }
}
