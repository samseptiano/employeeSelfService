package com.example.samuelseptiano.employeeselfservice.Activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Application.MyApplication;
import com.example.samuelseptiano.employeeselfservice.Fragment.ChatFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.EventsFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.HomeFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment.KPIApproveListFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment.KPIFillFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment.KPIKuantitatifFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIApproveListTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIApproveTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIFillTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIKuantitatifTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.NotificationFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.ProfileFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.QuizFragment.QuizFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.RequestTemplate.HomeRequestFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.EventAbsentUserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.EventSessionRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserAnswerRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.User;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.userBodyParameter;
import com.example.samuelseptiano.employeeselfservice.Model.QrResultModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.Session.SessionManagement;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    // Session Manager Class
    SessionManagement session;
    LinearLayout lnProgressBar;
    List<String> imgCaptureList = new ArrayList<>();
    int userRes = 0;
    int flag;
    UserList userList;

    //qr code scanner object
    private IntentIntegrator qrScan;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int WRITE_EXT_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXT_REQUEST_CODE);
        }

        lnProgressBar = findViewById(R.id.linlaHeaderProgressMain);

        // Manually checking internet connection
        checkConnection();

        // Session class instance
        session = new SessionManagement(getApplicationContext());

        try {

            //initiallization realm
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder().build();
            Realm.setDefaultConfiguration(config);
        }
        catch (Exception e){
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(config);
        }

        UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());

        ///if session ended
        if(session.checkLogin().equals("logout")){
            finish();
        }
        else if(session.isLoggedIn() == true) {
            lnProgressBar.setVisibility(View.VISIBLE);
            //if connection exist
            if(checkConnection()){

                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                String RToken = usr.get(0).getToken();
                String id = usr.get(0).getUserId();
                String pwd = usr.get(0).getPassword();

                //jika token di realm masih ada
                if(RToken.length()>0){
                    ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);


                    apiService2.getUserDetail(new userBodyParameter(id,pwd), "Bearer " + RToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .unsubscribeOn(Schedulers.io())
                            .subscribe(new Observer<Response<User>>() {


                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Response<User> userRess) {
                                    userRes = userRess.code();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    lnProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete() {
                                    if(userRes == 401){
                                        lnProgressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        session.logoutUser();
                                        finish();
                                        Toast.makeText(getApplicationContext(), userRes+": Token Expired", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(userRes == 0){
                                        lnProgressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        session.logoutUser();
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Token Null", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        fr = new HomeFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                                        fr.setArguments(bundle);
                                        fm = getSupportFragmentManager();
                                        ft = fm.beginTransaction();
                                        ft.replace(R.id.fragment_frame, fr);
                                        ft.detach(fr);
                                        ft.attach(fr);
                                        ft.addToBackStack("fragmentHome");
                                        ft.commit();
                                        Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_SHORT).show();
                                        inflateTabLayoutFragment();
                                        lnProgressBar.setVisibility(View.GONE);

                                    }
                                }
                            });



//                    Call<User> call = apiService2.getUserDetail(new userBodyParameter(id,pwd), "Bearer " + RToken);
//
//                    call.enqueue(new Callback<User>() {
//                        @Override
//                        public void onResponse(Call<User> call, Response<User> response) {
//
//                            if(response.code() == 401){
//                                lnProgressBar.setVisibility(View.GONE);
//                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                                session.logoutUser();
//                                finish();
//                                Toast.makeText(getApplicationContext(), "Token Expired", Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                fr = new HomeFragment();
//                                Bundle bundle = new Bundle();
//                                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//                                fr.setArguments(bundle);
//                                fm = getSupportFragmentManager();
//                                ft = fm.beginTransaction();
//                                ft.replace(R.id.fragment_frame, fr);
//                                ft.detach(fr);
//                                ft.attach(fr);
//                                ft.addToBackStack("fragmentHome");
//                                ft.commit();
//                                Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_SHORT).show();
//                                inflateTabLayoutFragment();
//                                lnProgressBar.setVisibility(View.GONE);
//
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<User> call, Throwable t) {
//                            // Log error here since request failed
////                            fr = new OfflineFragment();
////                            fm = getSupportFragmentManager();
////                            ft = fm.beginTransaction();
////                            ft.replace(R.id.fragment_frame, fr);
////                            ft.detach(fr);
////                            ft.attach(fr);
////                            ft.addToBackStack("fragmentHome");
////                            ft.commit();
//                            lnProgressBar.setVisibility(View.GONE);
//                            Toast.makeText(getApplicationContext(), "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });


                    }
                    else{
                        //get user data from session
                        HashMap<String, String> user = session.getUserDetails();
                        // name
                        String nik = user.get(SessionManagement.KEY_NIK);
                        userRealmHelper.deleteData(nik);
                    lnProgressBar.setVisibility(View.GONE);
                    session.logoutUser();
                        finish();
                    }

            }
            else{
                fr = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                fr.setArguments(bundle);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.detach(fr);
                ft.attach(fr);
                ft.addToBackStack("fragmentHome");
                ft.commit();
                Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_SHORT).show();
                inflateTabLayoutFragment();
                lnProgressBar.setVisibility(View.GONE);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                    try {
                    super.onBackPressed();
                    }
                    catch(Exception e){
                        fr = new HomeFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                        fr.setArguments(bundle);
                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_frame, fr);
                        ft.detach(fr);
                        ft.attach(fr);
                        ft.addToBackStack("fragmentHome");
                        ft.commit();
                        Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_SHORT).show();
                    }
                  break;
            case R.id.setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.logout:
                try{
                UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());
                userRealmHelper.deleteAllData();
                HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getApplicationContext());
                homeRealmHelper.deleteAllData();
                EventAbsentUserRealmHelper eventAbsentUserRealmHelper = new EventAbsentUserRealmHelper(getApplicationContext());
                eventAbsentUserRealmHelper.deleteAllData();
                EventSessionRealmHelper eventSessionRealmHelper = new EventSessionRealmHelper(getApplicationContext());
                eventSessionRealmHelper.deleteAllData();
                UserAnswerRealmHelper userAnswerRealmHelper = new UserAnswerRealmHelper(getApplicationContext());
                userAnswerRealmHelper.deleteAllData();
//            UserEventRealmHelper userEventRealmHelper = new UserEventRealmHelper(getApplicationContext());
//            userRealmHelper.deleteAllData();
                session.logoutUser();
                finish();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"No Connect To Internet",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        //showToast(isConnected);
        return  isConnected;
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
    protected void onPause(){
        super.onPause();
    }
    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }

    //===================Inflate Fragment=============================================
    public void inflateTabLayoutFragment(){
        frr = new TabLayoutFragment();
        fmm = getSupportFragmentManager();
        ftt = fmm.beginTransaction();
        ftt.add(R.id.fragment_tablayout, frr);
        ftt.commit();
    }

    public void inflateHomeFragment(boolean Connstate){
            //inflateTabLayoutFragment();
            fr = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("ConnState", Connstate);
            fr.setArguments(bundle);
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.fragment_frame, fr);
            ft.detach(fr);
            ft.attach(fr);
            ft.addToBackStack("fragmentHome");
            ft.commit();
            Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_SHORT).show();
    }

    public void inflateQRFragment(boolean Connstate){
        //inflateTabLayoutFragment();
        qrScan = new IntentIntegrator(this);
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setPrompt("Scan QR for search Event");
        qrScan.setBeepEnabled(true);
        qrScan.setCameraId(0);
        qrScan.setBarcodeImageEnabled(true);
        qrScan.setCaptureActivity(CaptureActivity.class);
        qrScan.initiateScan();
    }

    public void inflateProfileFragment(boolean Connstate){
        //inflateTabLayoutFragment();
        fr = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ConnState", Connstate);
        fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        //to hide it, call the method again
//        InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
//        if(imm.){
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//        }
        Toast.makeText(getApplicationContext(), "Profile Area", Toast.LENGTH_SHORT).show();
    }

    public void inflateNotificationFragment(boolean Connstate){
        //inflateTabLayoutFragment();
        fr = new NotificationFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ConnState", Connstate);
        fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(), "Notification Area", Toast.LENGTH_SHORT).show();
    }


    public void inflateEventFragment(boolean Connstate){
        //inflateTabLayoutFragment();
        fr = new EventsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ConnState", Connstate);
        fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(),"History Area",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Fragment  f = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);

            if (f instanceof HomeFragment) {
                //super.onBackPressed();
                finish();
            }
            if (f instanceof ProfileFragment) {
                //super.onBackPressed();
                finish();
            }
            if (f instanceof EventsFragment) {
                //super.onBackPressed();
                finish();
            }
            if (f instanceof NotificationFragment) {
                super.onBackPressed();
                finish();
            } else if (f instanceof HomeDetailFragment) {
                try {
                        super.onBackPressed();
                        defineActionbar();

                }
                catch (Exception e){
                    fr = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle);
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.commit();
                    Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_SHORT).show();
                }
            } else if (f instanceof HomeRequestFragment) {
                getSupportFragmentManager().popBackStack();
                defineActionbar();
            } else if (f instanceof QuizFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIKuantitatifTahunanFragment) {
                //imgCaptureList = new ArrayList<>();
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIApproveTahunanFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIFillTahunanFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIApproveListTahunanFragment) {
                KPIKuantitatifTahunanFragment kp = new KPIKuantitatifTahunanFragment();
                kp.resetQuestion();
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIKuantitatifFragment) {
                //imgCaptureList = new ArrayList<>();
                //getSupportFragmentManager().popBackStack();

                FragmentManager fm = getSupportFragmentManager();

                for (int i = fm.getBackStackEntryCount() - 1; i > 0; i--) {
                    if (!fm.getBackStackEntryAt(i).getName().equalsIgnoreCase("KPIApproveListFragment")) {
                        fm.popBackStack();
                    }
//                    else
//                    {
//                        //break;
//                    }
                }


            }

            else if (f instanceof KPIFillFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIApproveListFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof HomeCategoryFragment) {
                getSupportFragmentManager().popBackStack();
                defineActionbar();
            }
            else if (f instanceof ChatFragment) {
                getSupportFragmentManager().popBackStack();
                defineActionbar();
            }
        }

    public void defineActionbar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data)
                {
                    //lnProgressBar.setVisibility(View.VISIBLE);

                    super.onActivityResult(requestCode,resultCode,data);
                    if (requestCode == 20 && resultCode == Activity.RESULT_OK)
                    {
                        try {
                            //Bitmap photo = (Bitmap) data.getExtras().get("data");

                            Uri selectedImage = data.getData();
                            String[] filePathColumn = { MediaStore.Images.Media.DATA };

                            // Get the cursor
                            Cursor cursor = getApplication().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String imgDecodableString = cursor.getString(columnIndex);
                            cursor.close();

                            imgCaptureList.add(imgDecodableString);
                            lnProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), imgDecodableString +" "+Integer.toString(imgCaptureList.size()), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            lnProgressBar.setVisibility(View.GONE);
                        }

                    }
                    else{

                        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                        if (result != null){
                            if (result.getContents() == null){
                                lnProgressBar.setVisibility(View.GONE);
                                //Toast.makeText(getApplicationContext(), "QR Not found", Toast.LENGTH_SHORT).show();
                            }else{
                                // jika qrcode berisi data
                                try{
                                    try{

                                        //=============== FORMAT QR YANG BISA DI SCAN {"eventId":"5","eventType":"Training"} ===================================//

                                        Gson g = new Gson();
                                        QrResultModel results = g.fromJson(result.getContents(), QrResultModel.class);

                                        if(results.getEventType().equals("Training")){
                                            try {
                                                HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getApplicationContext());
                                                HomeRealmModel homie = homeRealmHelper.findArticle(results.getEventId());

                                                if (homie.getEventType().equals(results.getEventType())) {
                                                    fr = new HomeDetailFragment();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                                                    bundle.putString("id",results.getEventId());
                                                    fr.setArguments(bundle);
                                                    fm = getSupportFragmentManager();
                                                    ft = fm.beginTransaction();
                                                    ft.replace(R.id.fragment_frame, fr);
                                                    ft.addToBackStack("HomeDetailFragment");
                                                    ft.commit();
                                                    Toast.makeText(getApplicationContext(),"Training Area",Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "No Training Found", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            catch (Exception e){
                                                Toast.makeText(getApplicationContext(),"No Training Found",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else if(results.getEventType().equals("Request")){
                                            HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getApplicationContext());
                                            HomeRealmModel homie = homeRealmHelper.findArticle(results.getEventId());
                                            try {
                                                if (homie.getEventType().equals(results.getEventType())) {
                                                    fr = new HomeRequestFragment();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                                                    bundle.putString("id", results.getEventId());
                                                    fr.setArguments(bundle);
                                                    fm = getSupportFragmentManager();
                                                    ft = fm.beginTransaction();
                                                    ft.replace(R.id.fragment_frame, fr);
                                                    ft.addToBackStack("HomeRequestFragment");
                                                    ft.commit();
                                                    Toast.makeText(getApplicationContext(), "Training Area", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "No Request Found", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            catch (Exception e){
                                                Toast.makeText(getApplicationContext(), "No Request Found", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                        else if(results.getEventType().equals("PA")){

                                        }
                                        else if(results.getEventType().equals("PK")){

                                            UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());
                                            List<UserRealmModel>usr = userRealmHelper.findAllArticle();
                                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                            flag = 0;
                                            apiService.getuserListPJ(usr.get(0).getEmpNIK(),"Bearer "+usr.get(0).getToken())
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .unsubscribeOn(Schedulers.io())
                                                    .subscribe(new Observer<List<UserList>>() {


                                                        @Override
                                                        public void onSubscribe(Disposable d) {

                                                        }

                                                        @Override
                                                        public void onNext(List<UserList> userLists) {
                                                            for(int i=0;i<userLists.size();i++){
                                                                if(userLists.get(i).getEmpNiK().equals(results.getEventId())){
                                                                    flag = 1;
                                                                    userList = new UserList();
                                                                    userList.setEmpNiK(userLists.get(i).getEmpNiK());
                                                                    userList.setOrgName("");
                                                                    userList.setJobTitleName(userLists.get(i).getJobTitleName());
                                                                    userList.setEmpName(userLists.get(i).getEmpName());
                                                                    userList.setNIKAtasanTakLangsung(userLists.get(i).getNIKAtasanTakLangsung());
                                                                    userList.setNIKAtasanLangsung(userLists.get(i).getNIKAtasanLangsung());
                                                                    userList.setNamaAtasanLangsung(userLists.get(i).getNamaAtasanLangsung());
                                                                    userList.setNamaAtasanTakLangsung(userLists.get(i).getNamaAtasanTakLangsung());

                                                                    userList.setDateStart(userLists.get(i).getDateStart().split(" ")[0]);
                                                                    userList.setDateEnd(userLists.get(i).getDateEnd().split(" ")[0]);
                                                                    userList.setFotoAtasanLangsung(userLists.get(i).getFotoAtasanLangsung());
                                                                    userList.setFotoAtasanTakLangsung(userLists.get(i).getFotoAtasanTakLangsung());
                                                                    userList.setPositionName(userLists.get(i).getPositionName());
                                                                    userList.setStatus(userLists.get(i).getStatus());
                                                                    userList.setStatus1(userLists.get(i).getStatus1());
                                                                    userList.setStatus2(userLists.get(i).getStatus2());
                                                                    userList.setFotoBawahan(userLists.get(i).getEmpPhoto());

                                                                    break;
                                                                }
                                                            }

                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                                                        }

                                                        @Override
                                                        public void onComplete() {
                                                            if(flag == 1){
                                                                fr = new KPIKuantitatifFragment();

                                                                Bundle bundle6 = new Bundle();
                                                                bundle6.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                                                                bundle6.putSerializable("id", userList);
                                                                bundle6.putString("KPIType","Approve");
                                                                bundle6.putString("semester","1");
                                                                // bundle4.putString("jenisKPI","Kuantitatif");
                                                                fr.setArguments(bundle6);

                                                                fm = getSupportFragmentManager();
                                                                ft = fm.beginTransaction();
                                                                ft.replace(R.id.fragment_frame, fr);
                                                                ft.addToBackStack("KPIKuantitatifFragment");
                                                                ft.commit();

                                                                frr = new TabLayoutFragment();
                                                                fmm = getSupportFragmentManager();
                                                                ftt = fmm.beginTransaction();
                                                                ft.replace(R.id.fragment_tablayout, frr);
                                                                ftt.remove(frr);
                                                                ftt.commit();

                                                                Toast.makeText(getApplicationContext(), "PA Area", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else if(flag == 0){
                                                                Toast.makeText(getApplicationContext(),"No Employee Found", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });

                                        }
                                        lnProgressBar.setVisibility(View.GONE);
                                    }
                                    catch (Exception e){
                                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
//                                          Toast.makeText(getApplicationContext()," Result Not Found",Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "An Error Occurred While Scanning", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            super.onActivityResult(requestCode, resultCode, data);
                        }


                    }
                }

}
