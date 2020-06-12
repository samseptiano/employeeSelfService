package com.enseval.samuelseptiano.hcservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.enseval.samuelseptiano.hcservice.Application.MyApplication;
import com.enseval.samuelseptiano.hcservice.MyService;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.Session.SessionManagement;

import java.util.Iterator;
import java.util.Set;


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
        Bundle extras = getIntent().getExtras();
//        if(extras == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(extras == null) {
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        i.putExtra("ISFROMNOTIFICATION", "N");
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                    else {
//                        Set<String> ks = extras.keySet();
//                        Iterator<String> iterator = ks.iterator();
//                        while (iterator.hasNext()) {
//                            String aa = iterator.next();
//                            Toast.makeText(getApplicationContext(),aa,Toast.LENGTH_LONG).show();
//
//                        }
////                            Log.d("KEY", aa);
                            if (extras.keySet().contains("from") && extras.keySet().contains("google.message_id")) {
                                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                        i.putExtra("ISFROMNOTIFICATION", "Y");
                                        startActivity(i);
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                        finish();
                            }
                            else{
                                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                i.putExtra("ISFROMNOTIFICATION", "N");
                                startActivity(i);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }
                        }
                }
            }, 5000);
//        }
//        else {
//
//            Set<String> ks = extras.keySet();
//            Iterator<String> iterator = ks.iterator();
//            while (iterator.hasNext()) {
//                String aa = iterator.next();
//                Log.d("KEY", aa);
//                if (aa.contains("google")) {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                            i.putExtra("ISFROMNOTIFICATION", "Y");
//                            startActivity(i);
//                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                            finish();
//                        }
//                    }, 5000);
//                    break;
//                }
//            }
//
//
//        }

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
