package com.enseval.samuelseptiano.hcservice.Activity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.enseval.samuelseptiano.hcservice.Application.MyApplication;
import com.enseval.samuelseptiano.hcservice.Fragment.ChatFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.EventsFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.HCPerformanceFragment.HCPerformanceFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.HCRoadmapFragment.HCRoadmapFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.HomeFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.KPIApproveListFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.KPIFillFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.KPIKuantitatifFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK.EmployeeSetupDetailPKFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK.PositionSetupDetailPKFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK.SetupPKFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK.SetupPKKualitatifDBFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK.SetupPKKuantitatifDBFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.DevelopmentPlanDetailFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.DevelopmentPlanFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.DevelopmentPlanSubDetailFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.FinalPAFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.FinalPAFragments.DistribusiNormalFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.KPIApproveListTahunanFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.KPIApproveTahunanFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.KPIKuantitatifTahunanFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.KPIStatusTahunanFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.EmployeeSetupDetailPAFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.SetupPAFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.SetupPAKuantitatifDBFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.NotificationFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPAFragment.TotalFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceReviewFragment.PerformanceAppraisalFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.ProfileFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.QuizFragment.QuizFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.SubMenuHome.RequestTemplate.HomeRequestFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.TalentManagementFragment.TalentManagementFragment;
import com.enseval.samuelseptiano.hcservice.Helper.EmpJobTitleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EmpOrgRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EventAbsentUserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EventSessionRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.HomeRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.ModuleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserAnswerRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.User;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.UserList;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.userBodyParameter;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.HomeRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.TokenAuthModel.UserLogin;
import com.enseval.samuelseptiano.hcservice.MyService;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.Session.SessionManagement;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Fragment.HCAssessmentFragment.HCAssessmentFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.PositionSetupDetailPAFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.SetupPAKualitatifDBFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceReviewFragment.PerformanceReviewFragment;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHeader;
import com.enseval.samuelseptiano.hcservice.Model.QrResultModel;
import com.enseval.samuelseptiano.hcservice.Model.VersionModel;
import com.enseval.samuelseptiano.hcservice.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;


public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    // Session Manager Class
    SessionManagement session;
    LinearLayout lnProgressBar;
    List<String> imgCaptureList = new ArrayList<>();
    int userRes = 0;
    int flag;
//    UserList userList;

    String isFromNotification="N";
    ArrayList<UserRealmModel> usr;
    UserRealmHelper userRealmHelper;

    //qr code scanner object
    private IntentIntegrator qrScan;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int WRITE_EXT_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFromNotification="N";

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(1);


        Bundle extras = getIntent().getExtras();


        if(extras == null ) {

            isFromNotification= "N";
        } else {

            isFromNotification= extras.getString("ISFROMNOTIFICATION");
        }

        if(isFromNotification.equals("Y")){
            ProgressDialog pdd = new ProgressDialog(this);
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                @Override
                protected void onPreExecute() {
                    pdd.setMessage("Sync data...");
                    pdd.setCancelable(false);
                    pdd.setIndeterminate(false);
                    pdd.show();
                }

                @Override
                protected Void doInBackground(Void... arg0) {
                    try {
                        //Do something...
                        isFromNotification="Y";
                        stopService(new Intent(MainActivity.this, MyService.class));
                        startService(new Intent(MainActivity.this, MyService.class));

                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (pdd!=null) {
                        pdd.dismiss();
                    }
                }

            };
            task.execute((Void[])null);
        }


//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.skip_showcase,
//                null);
//
//        final AlertDialog dialog = builder.create();
//        dialog.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        dialog.setView(dialogLayout, 0, 0, 0, 0);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//        WindowManager.LayoutParams wlmp = dialog.getWindow()
//                .getAttributes();
//        wlmp.gravity = Gravity.BOTTOM;
//
////        Button btnCamera = (Button) dialogLayout.findViewById(R.id.button_Camera);
////        Button btnGallery = (Button) dialogLayout.findViewById(R.id.button_Gallery);
////        Button btnDismiss = (Button) dialogLayout.findViewById(R.id.btnCancelCamera);
//
//        builder.setView(dialogLayout);
//
//        dialog.show();

//        Handler handler = new Handler();
//
//        final Runnable r = new Runnable() {
//            public void run() {
//                stopService(new Intent(MainActivity.this, MyService.class));
//                handler.postDelayed(this, 6000);
//            }
//        };

//        handler.postDelayed(r, 6000);

        //startService(new Intent(getApplicationContext(), MyService.class));

        //startService(new Intent(getApplicationContext(), MyService.class));
       // stopService(new Intent(this, MyService.class));

        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //====================== CUSTOM APPBAR LAYOUT =============================================
       // ((AppCompatActivity)getA=ctivity()).getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTitle("");
//        LayoutInflater mInflater = LayoutInflater.from(getContext());
//        View mCustomView = mInflater.inflate(R.layout.custom_appbar, null);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(mCustomView);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("KPI Approve List");
        //====================== CUSTOM APPBAR LAYOUT ====================================================

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

        userRealmHelper = new UserRealmHelper(getApplicationContext());
        usr = userRealmHelper.findAllArticle();

        ///if session ended
        if(session.checkLogin().equals("logout")){
            stopService(new Intent(this, MyService.class));
            finish();
        }
        else if(session.isLoggedIn() == true) {
            lnProgressBar.setVisibility(View.VISIBLE);
            //if connection exist
            if(checkConnection()){

                String RToken = usr.get(0).getToken();
                String id = usr.get(0).getUserId();
                String pwd = usr.get(0).getPassword();

                //jika token di realm masih ada
                if(RToken.length()>0){
                    ApiInterface apiService2 = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);


                    apiService2.getUserDetail(new userBodyParameter(id,pwd, FirebaseInstanceId.getInstance().getToken()), "Bearer " + RToken)
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
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        checkVersion();
        //Toast.makeText(getApplicationContext(),"Fragment Attached",Toast.LENGTH_LONG).show();
//        if (fragment instanceof MyFragment) {
//            ((MyFragment) fragment).setListener(mMyFragmentListener);
//        }
    }


//    @Override
//    public void onStop() {
//        super.onStop();
//
//        //stopService(new Intent(this, MyService.class));
//    }

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
//            case R.id.guideline:
//                startActivity(new Intent(this, GuidelineActivity.class));
//                break;
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
                EmpJobTitleRealmHelper empJobTitleRealmHelper = new EmpJobTitleRealmHelper(getApplicationContext());
                    empJobTitleRealmHelper.deleteAllData();
                ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getApplicationContext());
                    moduleRealmHelper.deleteAllData();
                EmpOrgRealmHelper empOrgRealmHelper = new EmpOrgRealmHelper(getApplicationContext());
                    empOrgRealmHelper.deleteAllData();
//            UserEventRealmHelper userEventRealmHelper = new UserEventRealmHelper(getApplicationContext());
//            userRealmHelper.deleteAllData();
                    session.logoutUser();


                    finish();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
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

//        stopService(new Intent(this, MyService.class));
//        startService(new Intent(this, MyService.class));
        checkVersion();

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
            else if (f instanceof ProfileFragment) {
                //super.onBackPressed();
                finish();
            }
            else if (f instanceof EventsFragment) {
                //super.onBackPressed();
                finish();
            }
            else if (f instanceof NotificationFragment) {
                super.onBackPressed();
                finish();
            }
            else if (f instanceof HomeDetailFragment) {
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
            }
            else if (f instanceof HomeRequestFragment) {
                getSupportFragmentManager().popBackStack();
                defineActionbar();
            }
            else if (f instanceof PerformanceReviewFragment) {
                getSupportFragmentManager().popBackStack();
                defineActionbar();
            }
            else if (f instanceof HCPerformanceFragment) {
                getSupportFragmentManager().popBackStack();
                defineActionbar();
            }
            else if (f instanceof HCAssessmentFragment) {
                getSupportFragmentManager().popBackStack();
                defineActionbar();
            }
            else if (f instanceof QuizFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof EmployeeSetupDetailPAFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof PositionSetupDetailPAFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof SetupPAKualitatifDBFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof SetupPAKuantitatifDBFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof SetupPAFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof EmployeeSetupDetailPKFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof PositionSetupDetailPKFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof SetupPKKualitatifDBFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof SetupPKKuantitatifDBFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof SetupPKFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIKuantitatifTahunanFragment) {
                KPIHeader kh = new KPIHeader();
                kh = ((KPIKuantitatifTahunanFragment) f).getkpiHeader();
                if((kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK()) && kh.getStatus().equals("C"))){
                    getSupportFragmentManager().popBackStack();

                }
                else if((kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK()) && (kh.getStatus().equals("A")|| kh.getStatus().equals("C")))){
                    getSupportFragmentManager().popBackStack();
                }
                else if((kh.getNIK().equals(usr.get(0).getEmpNIK()) && (kh.getStatus().equals("S") || kh.getStatus().equals("A") || kh.getStatus().equals("C")))){
                    getSupportFragmentManager().popBackStack();
                }
                else if(!((KPIKuantitatifTahunanFragment) f).getdiUbah()){
                    getSupportFragmentManager().popBackStack();
                }
                else{
                    AlertDialog alertbox = new AlertDialog.Builder(this)
                            .setMessage("Apakah anda ingin menyimpan penilaian yang telah diisi sebelum keluar dari menu ini??")
                            .setPositiveButton("YA", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Context context = getApplicationContext();
                                    ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
                                    getSupportFragmentManager().popBackStack();
                                    //close();
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    getSupportFragmentManager().popBackStack();
                                }
                            })
                            .show();
                            alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.light_grey2));
                            alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
                }


                    //                if(((KPIKuantitatifTahunanFragment) f).getType().equals("Approve")) {
//                    if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {
//
//                        if(!((KPIKuantitatifTahunanFragment) f).getdiUbah()){
//                            getSupportFragmentManager().popBackStack();
//                        }
//                        else if (kh.getStatus().equals("A") || kh.getStatus().equals("C") && !((KPIKuantitatifTahunanFragment) f).getdiUbah()) {
//                            getSupportFragmentManager().popBackStack();
//
//                        } else {
//                            AlertDialog alertbox = new AlertDialog.Builder(this)
//                                    .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            Context context = getApplicationContext();
//                                            ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
//                                            getSupportFragmentManager().popBackStack();
//                                            //close();
//                                        }
//                                    })
//                                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            getSupportFragmentManager().popBackStack();
//                                        }
//                                    })
//                                    .show();
//                        }
//
//                    }else if (kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK())) {
//                        if (kh.getStatus().equals("C") && !((KPIKuantitatifTahunanFragment) f).getdiUbah()) {
//                            getSupportFragmentManager().popBackStack();
//
//                        } else {
//                            AlertDialog alertbox = new AlertDialog.Builder(this)
//                                    .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            Context context = getApplicationContext();
//                                            ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
//                                            getSupportFragmentManager().popBackStack();
//                                            //close();
//                                        }
//                                    })
//                                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            getSupportFragmentManager().popBackStack();
//                                        }
//                                    })
//                                    .show();
//
//                        }
//                    }
//                }
//                else{
//                    if(kh.getStatus().equals("S") || kh.getStatus().equals("A") || kh.getStatus().equals("C") && !((KPIKuantitatifTahunanFragment) f).getdiUbah()){
//
//                        getSupportFragmentManager().popBackStack();
//
//                    }
//                    else if(kh.getStatus().equals("O") && !((KPIKuantitatifTahunanFragment) f).getdiUbah()){
//                        getSupportFragmentManager().popBackStack();
//
//                    }
//                    else{
//                        AlertDialog alertbox = new AlertDialog.Builder(this )
//                                .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                    // do something when the button is clicked
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        Context context = getApplicationContext();
//                                        ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
//                                        getSupportFragmentManager().popBackStack();
//                                        //close();
//                                    }
//                                })
//                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                    // do something when the button is clicked
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        getSupportFragmentManager().popBackStack();
//                                    }
//                                })
//                                .show();
//                    }
//                }

                //======================================================================
//                AlertDialog alertbox = new AlertDialog.Builder(this)
//                        .setMessage("Anda Ingin Keluar?")
//                        .setPositiveButton("Simpan dan Keluar", new DialogInterface.OnClickListener() {
//
//                            // do something when the button is clicked
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                Context context = getApplicationContext();
//                                ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
//                                getSupportFragmentManager().popBackStack();
//                                //close();
//                            }
//                        })
//                        .setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
//
//                            // do something when the button is clicked
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                getSupportFragmentManager().popBackStack();
//
//                            }
//                        })
//                        .show();

            }
            else if (f instanceof PerformanceAppraisalFragment) {
                //imgCaptureList = new ArrayList<>();
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIApproveTahunanFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof DevelopmentPlanFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof HCRoadmapFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof TalentManagementFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof PerformanceReviewFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof DevelopmentPlanDetailFragment) {
                if(((DevelopmentPlanDetailFragment) f).hasApprove.equals("Y")){
                    getSupportFragmentManager().popBackStack();
                }
                else if(!((DevelopmentPlanDetailFragment) f).getUbah()) {
                    getSupportFragmentManager().popBackStack();
                }
                else{
                    AlertDialog alertbox = new AlertDialog.Builder(this)
                            .setMessage("Apakah anda ingin menyimpan IDP yang telah diisi sebelum keluar dari menu ini?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Context context = getApplicationContext();
                                    ((DevelopmentPlanDetailFragment) f).saveDevPlan(context);
                                    getSupportFragmentManager().popBackStack();
                                    //close();
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    getSupportFragmentManager().popBackStack();
                                }
                            }).show();

                    //2. now setup to change color of the button
                    alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.light_grey2));
                    alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
                }
            }
            else if (f instanceof DevelopmentPlanSubDetailFragment) {
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

//                FragmentManager fm = getSupportFragmentManager();
//                for (int i = fm.getBackStackEntryCount() - 1; i > 0; i--) {
//                    if (!fm.getBackStackEntryAt(i).getName().equalsIgnoreCase("KPIApproveListFragment")) {
//                        fm.popBackStack();
//                    }
////                    else
////                    {
////                        //break;
////                    }
//                }

                KPIHeaderPJ kh = new KPIHeaderPJ();
                kh = ((KPIKuantitatifFragment) f).getkpiHeader();
                if((kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK()) && kh.getStatus().equals("C"))){
                    getSupportFragmentManager().popBackStack();

                }
                else if((kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK()) && (kh.getStatus().equals("A")|| kh.getStatus().equals("C")))){
                    getSupportFragmentManager().popBackStack();
                }
                else if((kh.getNIK().equals(usr.get(0).getEmpNIK()) && (kh.getStatus().equals("S") || kh.getStatus().equals("A") || kh.getStatus().equals("C")))){
                    getSupportFragmentManager().popBackStack();
                }
                else if(!((KPIKuantitatifFragment) f).getdiUbah()){
                    getSupportFragmentManager().popBackStack();
                }
                else{
                    AlertDialog alertbox = new AlertDialog.Builder(this)
                            .setMessage("Apakah anda ingin menyimpan penilaian yang telah diisi sebelum keluar dari menu ini??")
                            .setPositiveButton("YA", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Context context = getApplicationContext();
                                    ((KPIKuantitatifFragment) f).SaveKPI(context);
                                    getSupportFragmentManager().popBackStack();
                                    //close();
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    getSupportFragmentManager().popBackStack();
                                }
                            })
                            .show();
                    alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.light_grey2));
                    alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
                }


                //                if(((KPIKuantitatifTahunanFragment) f).getType().equals("Approve")) {
//                    if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {
//
//                        if(!((KPIKuantitatifTahunanFragment) f).getdiUbah()){
//                            getSupportFragmentManager().popBackStack();
//                        }
//                        else if (kh.getStatus().equals("A") || kh.getStatus().equals("C") && !((KPIKuantitatifTahunanFragment) f).getdiUbah()) {
//                            getSupportFragmentManager().popBackStack();
//
//                        } else {
//                            AlertDialog alertbox = new AlertDialog.Builder(this)
//                                    .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            Context context = getApplicationContext();
//                                            ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
//                                            getSupportFragmentManager().popBackStack();
//                                            //close();
//                                        }
//                                    })
//                                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            getSupportFragmentManager().popBackStack();
//                                        }
//                                    })
//                                    .show();
//                        }
//
//                    }else if (kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK())) {
//                        if (kh.getStatus().equals("C") && !((KPIKuantitatifTahunanFragment) f).getdiUbah()) {
//                            getSupportFragmentManager().popBackStack();
//
//                        } else {
//                            AlertDialog alertbox = new AlertDialog.Builder(this)
//                                    .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            Context context = getApplicationContext();
//                                            ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
//                                            getSupportFragmentManager().popBackStack();
//                                            //close();
//                                        }
//                                    })
//                                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            getSupportFragmentManager().popBackStack();
//                                        }
//                                    })
//                                    .show();
//
//                        }
//                    }
//                }
//                else{
//                    if(kh.getStatus().equals("S") || kh.getStatus().equals("A") || kh.getStatus().equals("C") && !((KPIKuantitatifTahunanFragment) f).getdiUbah()){
//
//                        getSupportFragmentManager().popBackStack();
//
//                    }
//                    else if(kh.getStatus().equals("O") && !((KPIKuantitatifTahunanFragment) f).getdiUbah()){
//                        getSupportFragmentManager().popBackStack();
//
//                    }
//                    else{
//                        AlertDialog alertbox = new AlertDialog.Builder(this )
//                                .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                    // do something when the button is clicked
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        Context context = getApplicationContext();
//                                        ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
//                                        getSupportFragmentManager().popBackStack();
//                                        //close();
//                                    }
//                                })
//                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                    // do something when the button is clicked
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        getSupportFragmentManager().popBackStack();
//                                    }
//                                })
//                                .show();
//                    }
//                }

                //======================================================================
//                AlertDialog alertbox = new AlertDialog.Builder(this)
//                        .setMessage("Anda Ingin Keluar?")
//                        .setPositiveButton("Simpan dan Keluar", new DialogInterface.OnClickListener() {
//
//                            // do something when the button is clicked
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                Context context = getApplicationContext();
//                                ((KPIKuantitatifTahunanFragment) f).SaveKPI(context);
//                                getSupportFragmentManager().popBackStack();
//                                //close();
//                            }
//                        })
//                        .setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
//
//                            // do something when the button is clicked
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                getSupportFragmentManager().popBackStack();
//
//                            }
//                        })
//                        .show();


            }
            else if (f instanceof KPIFillFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof KPIStatusTahunanFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof TotalFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof FinalPAFragment) {
                getSupportFragmentManager().popBackStack();
            }
            else if (f instanceof DistribusiNormalFragment) {
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

//                                            UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());
//                                            List<UserRealmModel>usr = userRealmHelper.findAllArticle();
//                                            ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
//                                            flag = 0;
//                                            apiService.getuserListPJ(usr.get(0).getEmpNIK(),"Bearer "+usr.get(0).getToken())
//                                                    .subscribeOn(Schedulers.io())
//                                                    .observeOn(AndroidSchedulers.mainThread())
//                                                    .unsubscribeOn(Schedulers.io())
//                                                    .subscribe(new Observer<List<UserList>>() {
//
//
//                                                        @Override
//                                                        public void onSubscribe(Disposable d) {
//
//                                                        }
//
//                                                        @Override
//                                                        public void onNext(List<UserList> userLists) {
//                                                            for(int i=0;i<userLists.size();i++){
//                                                                if(userLists.get(i).getEmpNiK().equals(results.getEventId())){
//                                                                    flag = 1;
//                                                                    userList = new UserList();
//                                                                    userList.setEmpNiK(userLists.get(i).getEmpNiK());
//                                                                    userList.setOrgName("");
//                                                                    userList.setJobTitleName(userLists.get(i).getJobTitleName());
//                                                                    userList.setEmpName(userLists.get(i).getEmpName());
//                                                                    userList.setNIKAtasanTakLangsung(userLists.get(i).getNIKAtasanTakLangsung());
//                                                                    userList.setNIKAtasanLangsung(userLists.get(i).getNIKAtasanLangsung());
//                                                                    userList.setNamaAtasanLangsung(userLists.get(i).getNamaAtasanLangsung());
//                                                                    userList.setNamaAtasanTakLangsung(userLists.get(i).getNamaAtasanTakLangsung());
//
//                                                                    userList.setDateStart(userLists.get(i).getDateStart().split(" ")[0]);
//                                                                    userList.setDateEnd(userLists.get(i).getDateEnd().split(" ")[0]);
//                                                                    userList.setFotoAtasanLangsung(userLists.get(i).getFotoAtasanLangsung());
//                                                                    userList.setFotoAtasanTakLangsung(userLists.get(i).getFotoAtasanTakLangsung());
//                                                                    userList.setPositionName(userLists.get(i).getPositionName());
//                                                                    userList.setStatus(userLists.get(i).getStatus());
//                                                                    userList.setStatus1(userLists.get(i).getStatus1());
//                                                                    userList.setStatus2(userLists.get(i).getStatus2());
//                                                                    userList.setFotoBawahan(userLists.get(i).getEmpPhoto());
//
//                                                                    break;
//                                                                }
//                                                            }
//
//                                                        }
//
//                                                        @Override
//                                                        public void onError(Throwable e) {
//                                                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//                                                        }
//
//                                                        @Override
//                                                        public void onComplete() {
//                                                            if(flag == 1){
//                                                                fr = new KPIKuantitatifFragment();
//
//                                                                Bundle bundle6 = new Bundle();
//                                                                bundle6.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//                                                                bundle6.putSerializable("id", userList);
//                                                                bundle6.putString("KPIType","Approve");
//                                                                bundle6.putString("semester","1");
//                                                                // bundle4.putString("jenisKPI","Kuantitatif");
//                                                                fr.setArguments(bundle6);
//
//                                                                fm = getSupportFragmentManager();
//                                                                ft = fm.beginTransaction();
//                                                                ft.replace(R.id.fragment_frame, fr);
//                                                                ft.addToBackStack("KPIKuantitatifFragment");
//                                                                ft.commit();
//
//                                                                frr = new TabLayoutFragment();
//                                                                fmm = getSupportFragmentManager();
//                                                                ftt = fmm.beginTransaction();
//                                                                ft.replace(R.id.fragment_tablayout, frr);
//                                                                ftt.remove(frr);
//                                                                ftt.commit();
//
//                                                                Toast.makeText(getApplicationContext(), "PA Area", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                            else if(flag == 0){
//                                                                Toast.makeText(getApplicationContext(),"No Employee Found", Toast.LENGTH_LONG).show();
//                                                            }
//                                                        }
//                                                    });

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


    private void checkVersion(){
        MyApplication.getInstance().setConnectivityListener(this);
        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = pinfo.versionName;

        ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<List<VersionModel>> call = apiService.getVersion();
        call.enqueue(new Callback<List<VersionModel>>() {
            @Override
            public void onResponse(Call<List<VersionModel>>call, Response<List<VersionModel>> response) {
                try {
                    if (!versionName.equals(response.body().get(0).getVersion())) {
                        Bundle extras = new Bundle();
                        extras.putString("version",response.body().get(0).getVersion());
                        extras.putString("linkUpdate",response.body().get(0).getLinkUpdate());


                        Intent i = new Intent(MainActivity.this, UpdateActivity.class);
                        i.putExtras(extras);
                        startActivity(i);
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                }
                catch(Exception e){

                }
            }
            @Override
            public void onFailure(Call<List<VersionModel>>call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error Connection Found", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    public boolean isFirstTime()
//    {
//        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//        boolean ranBefore = preferences.getBoolean("RanBefore", false);
////        if (!ranBefore) {
////            // first time
////            SharedPreferences.Editor editor = preferences.edit();
////            editor.putBoolean("RanBefore", true);
////            editor.commit();
////        }
//        return !ranBefore;
//    }


    public void UpdateData(){
        ProgressDialog pdd = new ProgressDialog(this);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                pdd.setMessage("Sync data...");
                pdd.setCancelable(false);
                pdd.setIndeterminate(false);
                pdd.show();
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    //Do something...
                    stopService(new Intent(MainActivity.this, MyService.class));
                    startService(new Intent(MainActivity.this, MyService.class));

                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (pdd!=null) {
                    pdd.dismiss();
                }
            }

        };
        task.execute((Void[])null);
    }
}
