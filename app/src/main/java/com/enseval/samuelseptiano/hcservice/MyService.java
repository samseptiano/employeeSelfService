package com.enseval.samuelseptiano.hcservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Activity.LoginActivity;
import com.enseval.samuelseptiano.hcservice.Activity.MainActivity;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Application.MyApplication;
import com.enseval.samuelseptiano.hcservice.Helper.EmpJobTitleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EmpOrgRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.ModuleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserAnswerRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobTtlModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpOrgModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyService extends Service {

    List<EmpJobTtlModel> empJobTtlModelList;
    List<EmpOrgModel> empOrgModelList;
    ArrayList<UserRealmModel> usr;
    EmpJobTitleRealmHelper finalEmpJobTitleRealmHelper;
    EmpOrgRealmHelper finalEmpOrgRealmHelper;
    public static boolean isServiceRunning = false;

    public MyService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //onTaskRemoved(intent);
        if (intent != null/* && intent.getAction().equals(C.ACTION_START_SERVICE)*/) {
            runservice();
        }
        else stopMyService();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onCreate() {
        runservice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("servicedestroy", "servicedestroy");
        isServiceRunning = false;

        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    private void runservice(){
        try {
            UserRealmHelper userRealmHelper = new UserRealmHelper(MyApplication.getContext());
            usr = userRealmHelper.findAllArticle();

            EmpJobTitleRealmHelper empJobTitleRealmHelper = new EmpJobTitleRealmHelper(MyApplication.getContext());
            empJobTitleRealmHelper.deleteAllData();
            EmpOrgRealmHelper empOrgRealmHelper = new EmpOrgRealmHelper(MyApplication.getContext());
            empOrgRealmHelper.deleteAllData();

            finalEmpJobTitleRealmHelper = empJobTitleRealmHelper;
            finalEmpOrgRealmHelper = empOrgRealmHelper;

            //Toast.makeText(this, "Sync...", Toast.LENGTH_LONG).show();
            // Toast.makeText(getApplicationContext(),"This is a Service running in Background",Toast.LENGTH_LONG).show();
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);

            try{
                apiService.getEmpOrg(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)), "Bearer " + usr.get(0).getToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(new Observer<List<EmpOrgModel>>() {


                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<EmpOrgModel> userRes) {
                                empOrgModelList = userRes;

                            }

                            @Override
                            public void onError(Throwable e) {
                                //tvErrorLogin.setText("Error: Something went wrong happened :(");
                            }

                            @Override
                            public void onComplete() {
                                finalEmpOrgRealmHelper.deleteAllData();
                                for (int i = 0; i < empOrgModelList.size(); i++) {
                                    finalEmpOrgRealmHelper.addArticle(empOrgModelList.get(i));
                                }
//
                                apiService.getEmpJobTtl(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)), "Bearer " + usr.get(0).getToken())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .unsubscribeOn(Schedulers.io())
                                        .subscribe(new Observer<List<EmpJobTtlModel>>() {


                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(List<EmpJobTtlModel> userRes) {
                                                empJobTtlModelList = userRes;
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                // tvErrorLogin.setText("Error: Something went wrong happened :(");
                                            }

                                            @Override
                                            public void onComplete() {
                                                //  pdd.dismiss();
                                                Toast.makeText(getApplicationContext(), "Service keep run", Toast.LENGTH_LONG).show();

                                                finalEmpJobTitleRealmHelper.deleteAllData();

                                                for (int i = 0; i < empJobTtlModelList.size(); i++) {
                                                    finalEmpJobTitleRealmHelper.addArticle(empJobTtlModelList.get(i));
                                                }

                                                onDestroy();
                                                //Toast.makeText(getApplicationContext(), "Completed.. Service stopped", Toast.LENGTH_LONG).show();

                                            }
                                        });
                            }
                        });
            }catch (Exception e){}



        }catch (IllegalArgumentException e){

        }
    }
    void stopMyService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }
}