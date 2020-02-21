package com.enseval.samuelseptiano.hcservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.enseval.samuelseptiano.hcservice.Application.MyApplication;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.Session.SessionManagement;


public class SplashActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_splash);
        SessionManagement session = new SessionManagement(getApplicationContext());

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
//        ConfigRealmHelper configRealmHelper = new ConfigRealmHelper(getApplicationContext());



//        session.createUpdateVer(versionName);
        //session.clearUpdate();


        new Handler().postDelayed(new Runnable() {
//
//
            @Override
            public void run() {
                // This method will be executed once the timer is over
//                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//                Call<List<VersionModel>> call = apiService.getVersion(new UserLogin("mario","secret"));
//                call.enqueue(new Callback<List<VersionModel>>() {
//                    @Override
//                    public void onResponse(Call<List<VersionModel>>call, Response<List<VersionModel>> response) {
//                        if(versionName.equals(response.body().get(0).getVersion())){
                            Intent i = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(i);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
//                        }
//                        else {
//                            Intent i = new Intent(SplashActivity.this, UpdateActivity.class);
//                            startActivity(i);
//                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                            finish();
//                        }
//
//                    }
//                    @Override
//                    public void onFailure(Call<List<VersionModel>>call, Throwable t) {
//                        // Log error here since request failed
//                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        }, 5000);


    }

    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        //showToast(isConnected);
        return  isConnected;
    }

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

}
