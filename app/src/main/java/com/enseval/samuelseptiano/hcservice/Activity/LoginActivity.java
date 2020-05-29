package com.enseval.samuelseptiano.hcservice.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.PositionSetupPAAdapter;
import com.enseval.samuelseptiano.hcservice.Application.MyApplication;
import com.enseval.samuelseptiano.hcservice.BuildConfig;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.SetupPAKuantitatifDBFragment;
import com.enseval.samuelseptiano.hcservice.Helper.EmpJobTitleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EmpOrgRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EventAbsentUserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EventSessionRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.HomeRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserAnswerRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobTtlModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpOrgModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.MyService;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.ModuleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.User;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.userBodyParameter;
import com.enseval.samuelseptiano.hcservice.Model.TokenAuthModel.UserLogin;
import com.enseval.samuelseptiano.hcservice.Model.TokenAuthModel.UserLoginResponse;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.Session.SessionManagement;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.WHITE;

public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    Button btnLogin;
    TextInputEditText edtNIK;
    TextInputEditText edtPassword;
    SessionManagement session;
    String token="TOKEN";
    ImageView imgLogo;
    Realm realm;
    TextView tvErrorLogin;
    User user;
    ProgressDialog pdd;
    List<EmpJobTtlModel> empJobTtlModelList;
    List<EmpOrgModel> empOrgModelList;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());
        ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getApplicationContext());
        EmpOrgRealmHelper empOrgRealmHelper = new EmpOrgRealmHelper(getApplicationContext());
        EmpJobTitleRealmHelper empJobTitleRealmHelper = new EmpJobTitleRealmHelper(getApplicationContext());

        // Session Manager
        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_SHORT).show();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtNIK = (TextInputEditText) findViewById(R.id.edtNIK);
        edtPassword = (TextInputEditText) findViewById(R.id.edtPassword);
        imgLogo = findViewById(R.id.iconLogo);
        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        tvErrorLogin = findViewById(R.id.tvErrorLogin);

         userRealmHelper = new UserRealmHelper(getApplicationContext());
        userRealmHelper.deleteAllData();
        HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getApplicationContext());
        homeRealmHelper.deleteAllData();
        EventAbsentUserRealmHelper eventAbsentUserRealmHelper = new EventAbsentUserRealmHelper(getApplicationContext());
        eventAbsentUserRealmHelper.deleteAllData();
        EventSessionRealmHelper eventSessionRealmHelper = new EventSessionRealmHelper(getApplicationContext());
        eventSessionRealmHelper.deleteAllData();
        UserAnswerRealmHelper userAnswerRealmHelper = new UserAnswerRealmHelper(getApplicationContext());
        userAnswerRealmHelper.deleteAllData();
         empJobTitleRealmHelper = new EmpJobTitleRealmHelper(getApplicationContext());
        empJobTitleRealmHelper.deleteAllData();
         moduleRealmHelper = new ModuleRealmHelper(getApplicationContext());
        moduleRealmHelper.deleteAllData();
         empOrgRealmHelper = new EmpOrgRealmHelper(getApplicationContext());
        empOrgRealmHelper.deleteAllData();

        checkConnection();

        //if connection exist
        if(ConnectivityReceiver.isConnected()){



            //generate new token=====================
            ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
            Call<UserLoginResponse> call = apiService.getToken(new UserLogin(BuildConfig.USERID_TOKEN,BuildConfig.PASSWORD_TOKEN));
            UserRealmHelper finalUserRealmHelper = userRealmHelper;
            ModuleRealmHelper finalModuleRealmHelper = moduleRealmHelper;
            EmpJobTitleRealmHelper finalEmpJobTitleRealmHelper = empJobTitleRealmHelper;
            EmpOrgRealmHelper finalEmpOrgRealmHelper = empOrgRealmHelper;
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
                                    tvErrorLogin.setText("NIK or Password Still Empty!!");
                                    //Toast.makeText(getApplicationContext(), "NIK or Password Still Empty!!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    linlaHeaderProgress.setVisibility(View.VISIBLE);
                                    imgLogo.setVisibility(View.GONE);
                                    ApiInterface apiService2 = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);

                                    String userName = edtNIK.getText().toString();
                                    String password = edtPassword.getText().toString();

//                                    pdd = new ProgressDialog(LoginActivity.this);
//                                    pdd.setMessage("Sync data...");
//                                    pdd.setCancelable(false);
//                                    pdd.show();

                                    apiService.getUserDetail(new userBodyParameter(userName,password), "Bearer " + token)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .unsubscribeOn(Schedulers.io())
                                            .subscribe(new Observer<Response<User>>() {


                                                @Override
                                                public void onSubscribe(Disposable d) {

                                                }

                                                @Override
                                                public void onNext(Response<User> userRes) {
                                                    user = userRes.body();
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    tvErrorLogin.setText("Error: Something went wrong happened :(");
                                                    //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onComplete() {
//                                                    try{
                                                    try {
                                                        String usernamee = user.getEmpNIK();
//
                                                        if (usernamee.equals(null)) {
//                                                            pdd.dismiss();
                                                            tvErrorLogin.setText("Username or Password Not Correct!");
                                                            //Toast.makeText(getApplicationContext(), "Username or Password Not Correct!", Toast.LENGTH_SHORT).show();
                                                            linlaHeaderProgress.setVisibility(View.GONE);
                                                            imgLogo.setVisibility(View.VISIBLE);
                                                        } else {
                                                            String userQR = generateQR(user.getEmpNIK());
                                                            finalUserRealmHelper.addArticle(user, token, userQR);

                                                            for (int i = 0; i < user.getPrevilegeModules().size(); i++) {
                                                                finalModuleRealmHelper.addArticle(user.getPrevilegeModules().get(i));
                                                            }

                                                            //+++++++++++++++



                                                            UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());
                                                            ArrayList<UserRealmModel> usr;
                                                            usr = userRealmHelper.findAllArticle();

                                                            stopService(new Intent(LoginActivity.this, MyService.class));
                                                            startService(new Intent(LoginActivity.this, MyService.class));

                                                            //                                                            apiService.getEmpOrg(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)), "Bearer " + token)
//                                                                    .subscribeOn(Schedulers.io())
//                                                                    .observeOn(AndroidSchedulers.mainThread())
//                                                                    .unsubscribeOn(Schedulers.io())
//                                                                    .subscribe(new Observer<List<EmpOrgModel>>() {
//
//
//                                                                        @Override
//                                                                        public void onSubscribe(Disposable d) {
//
//                                                                        }
//
//                                                                        @Override
//                                                                        public void onNext(List<EmpOrgModel> userRes) {
//                                                                            empOrgModelList = userRes;
//
//                                                                        }
//
//                                                                        @Override
//                                                                        public void onError(Throwable e) {
//                                                                            tvErrorLogin.setText("Error: Something went wrong happened :(");
//                                                                        }
//
//                                                                        @Override
//                                                                        public void onComplete() {
//                                                                            for (int i = 0; i < empOrgModelList.size(); i++) {
//                                                                                finalEmpOrgRealmHelper.addArticle(empOrgModelList.get(i));
//                                                                            }
////
//                                                                            apiService.getEmpJobTtl(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)), "Bearer " + token)
//                                                                                    .subscribeOn(Schedulers.io())
//                                                                                    .observeOn(AndroidSchedulers.mainThread())
//                                                                                    .unsubscribeOn(Schedulers.io())
//                                                                                    .subscribe(new Observer<List<EmpJobTtlModel>>() {
//
//
//                                                                                        @Override
//                                                                                        public void onSubscribe(Disposable d) {
//
//                                                                                        }
//
//                                                                                        @Override
//                                                                                        public void onNext(List<EmpJobTtlModel> userRes) {
//                                                                                            empJobTtlModelList = userRes;
//                                                                                        }
//
//                                                                                        @Override
//                                                                                        public void onError(Throwable e) {
//                                                                                            tvErrorLogin.setText("Error: Something went wrong happened :(");
//                                                                                        }
//
//                                                                                        @Override
//                                                                                        public void onComplete() {
//                                                                                            pdd.dismiss();
//                                                                                            for (int i = 0; i < empJobTtlModelList.size(); i++) {
//                                                                                                finalEmpJobTitleRealmHelper.addArticle(empJobTtlModelList.get(i));
//                                                                                            }
//                                                                                            session.createLoginSession(userName, password.replaceAll(" ", ""));
//
//                                                                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                                                                            startActivity(i);
//                                                                                            finish();
//                                                                                            linlaHeaderProgress.setVisibility(View.GONE);
//                                                                                            imgLogo.setVisibility(View.VISIBLE);
//                                                                                        }
//                                                                                    });
//                                                                        }
//                                                                    });

//                                                            pdd.dismiss();
                                                            session.createLoginSession(userName, password.replaceAll(" ", ""));

                                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                            startActivity(i);
                                                            finish();
                                                            linlaHeaderProgress.setVisibility(View.GONE);
                                                            imgLogo.setVisibility(View.VISIBLE);


//                                                            ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
//                                                            Call<List<EmpOrgModel>> call2 = apiService.getEmpOrg(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
//                                                            call2.enqueue(new Callback<List<EmpOrgModel>>() {
//                                                                @Override
//                                                                public void onResponse(Call<List<EmpOrgModel>> call, Response<List<EmpOrgModel>> response) {
//                                                                    int statusCode = response.code();
//
//                                                                    List<EmpOrgModel> empOrgModelList = response.body();
//
//                                                                    for (int i = 0; i < empOrgModelList.size(); i++) {
//                                                                        empOrgRealmHelper.addArticle(empOrgModelList.get(i));
//                                                                    }
//
//                                                                    ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
//                                                                    Call<List<EmpJobTtlModel>> call3 = apiService.getEmpJobTtl(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
//                                                                    call3.enqueue(new Callback<List<EmpJobTtlModel>>() {
//                                                                        @Override
//                                                                        public void onResponse(Call<List<EmpJobTtlModel>> call, Response<List<EmpJobTtlModel>> response) {
//                                                                            int statusCode = response.code();
//                                                                            List<EmpJobTtlModel> empJobTtlModels = response.body();
//
//                                                                            for (int i = 0; i < empJobTtlModels.size(); i++) {
//                                                                                empJobTitleRealmHelper.addArticle(empJobTtlModels.get(i));
//                                                                            }
//
//                                                                            session.createLoginSession(userName, password.replaceAll(" ", ""));
//                                                                            pdd.dismiss();
//
//                                                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                                                            startActivity(i);
//                                                                            finish();
//                                                                            linlaHeaderProgress.setVisibility(View.GONE);
//                                                                            imgLogo.setVisibility(View.VISIBLE);
//                                                                        }
//                                                                        @Override
//                                                                        public void onFailure(Call<List<EmpJobTtlModel>> call, Throwable t) {
//                                                                            Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
//                                                                            pdd.dismiss();
//
//                                                                        }
//                                                                    });
//
//                                                                }
//                                                                @Override
//                                                                public void onFailure(Call<List<EmpOrgModel>> call, Throwable t) {
//                                                                    Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
////                                                                    pd.dismiss();
//
//                                                                }
//                                                            });
//

                                                            //+++++++++++++++




                                                        }
//                                                    }
//                                                    catch(Exception e){
//                                                        tvErrorLogin.setText(e.toString()+"Username Not Registered!");
                                                        //Toast.makeText(getApplicationContext(), "Username Not Registered!", Toast.LENGTH_SHORT).show();
                                                        linlaHeaderProgress.setVisibility(View.GONE);
                                                        imgLogo.setVisibility(View.VISIBLE);
//                                                    }
                                                    }
                                                    catch (Exception e){
                                                        //pdd.dismiss();
                                                        linlaHeaderProgress.setVisibility(View.GONE);
                                                        imgLogo.setVisibility(View.VISIBLE);
                                                        Toast.makeText(getApplicationContext(), "Username atau password salah", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });

//                                    Call<User> call = apiService2.getUserDetail(new userBodyParameter(userName,password), "Bearer " + token);
//                                    call.enqueue(new Callback<User>() {
//                                        @Override
//                                        public void onResponse(Call<User> call, Response<User> response) {
//                                            user = response.body();
//                                            try{
//                                                String usernamee = user.getEmpNIK();
////                                                if (userName.equals(usernamee) && password.equals(user.getPassword().replaceAll(" ", ""))) {
////                                                    String userQR = generateQR(user.getEmpNIK());
////                                                    userRealmHelper.addArticle(user, token, userQR);
////                                                    session.createLoginSession(userName, password.replaceAll(" ", ""));
////                                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
////                                                    startActivity(i);
////                                                    finish();
////                                                    linlaHeaderProgress.setVisibility(View.GONE);
////                                                    imgLogo.setVisibility(View.VISIBLE);
////
////                                                } else {
////                                                    Toast.makeText(getApplicationContext(), "Username or Password Not Correct!", Toast.LENGTH_SHORT).show();
////                                                    linlaHeaderProgress.setVisibility(View.GONE);
////                                                    imgLogo.setVisibility(View.VISIBLE);
////                                                }
//
//                                                if(usernamee.equals(null)){
//                                                    Toast.makeText(getApplicationContext(), "Username or Password Not Correct!", Toast.LENGTH_SHORT).show();
//                                                    linlaHeaderProgress.setVisibility(View.GONE);
//                                                    imgLogo.setVisibility(View.VISIBLE);
//                                                }
//                                                else{
//                                                   // Toast.makeText(getApplicationContext(), user.getEmpNIK(), Toast.LENGTH_LONG).show();
//                                                    String userQR = generateQR(user.getEmpNIK());
//                                                    userRealmHelper.addArticle(user, token, userQR);
//                                                    session.createLoginSession(userName, password.replaceAll(" ", ""));
//                                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                                    startActivity(i);
//                                                    finish();
//                                                    linlaHeaderProgress.setVisibility(View.GONE);
//                                                    imgLogo.setVisibility(View.VISIBLE);
//                                                }
//                                            }
//                                            catch(Exception e){
//                                                Toast.makeText(getApplicationContext(), "Username Not Registered!", Toast.LENGTH_SHORT).show();
//                                                linlaHeaderProgress.setVisibility(View.GONE);
//                                                imgLogo.setVisibility(View.VISIBLE);
//                                            }
//                                        }
//                                        @Override
//                                        public void onFailure(Call<User> call, Throwable t) {
//                                            // Log error here since request failed
//                                            Log.e(TAG, t.toString());
//                                            Toast.makeText(getApplicationContext(), "Error: Something went wrong happened :(", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });

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
                    tvErrorLogin.setText(t.toString());
                    Toast.makeText(getApplicationContext(), "Connection Failure", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"Error: Something went wrong happened :( "+t.toString(),Toast.LENGTH_SHORT).show();
                }
            });
            //=================================================

        }
        else{
            showToast(ConnectivityReceiver.isConnected());
        }




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


    private void showForgetPassword(){
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.forgot_password_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        EditText edtUsername = (EditText) dialog.findViewById(R.id.edtusername);
        Button btnSend = (Button) dialog.findViewById(R.id.btnSent);
        btnSend.setVisibility(View.GONE);

        String username = edtUsername.getText().toString();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
