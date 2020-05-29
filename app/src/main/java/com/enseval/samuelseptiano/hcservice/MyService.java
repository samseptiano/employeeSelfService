package com.enseval.samuelseptiano.hcservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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
    public MyService() {
        UserRealmHelper userRealmHelper = new UserRealmHelper(MyApplication.getContext());
        usr = userRealmHelper.findAllArticle();

        EmpJobTitleRealmHelper empJobTitleRealmHelper = new EmpJobTitleRealmHelper(MyApplication.getContext());
        empJobTitleRealmHelper.deleteAllData();
        EmpOrgRealmHelper empOrgRealmHelper = new EmpOrgRealmHelper(MyApplication.getContext());
        empOrgRealmHelper.deleteAllData();


        finalEmpJobTitleRealmHelper = empJobTitleRealmHelper;
        finalEmpOrgRealmHelper = empOrgRealmHelper;
    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId){
//        onTaskRemoved(intent);
//        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
//        Toast.makeText(this, "Start Command!", Toast.LENGTH_LONG).show();
//
//        try{
//            apiService.getEmpOrg(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)), "Bearer " + usr.get(0).getToken())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .unsubscribeOn(Schedulers.io())
//                    .subscribe(new Observer<List<EmpOrgModel>>() {
//
//
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(List<EmpOrgModel> userRes) {
//                            empOrgModelList = userRes;
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            //tvErrorLogin.setText("Error: Something went wrong happened :(");
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            for (int i = 0; i < empOrgModelList.size(); i++) {
//                                finalEmpOrgRealmHelper.addArticle(empOrgModelList.get(i));
//                            }
////
//                            apiService.getEmpJobTtl(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)), "Bearer " + usr.get(0).getToken())
//                                    .subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .unsubscribeOn(Schedulers.io())
//                                    .subscribe(new Observer<List<EmpJobTtlModel>>() {
//
//
//                                        @Override
//                                        public void onSubscribe(Disposable d) {
//
//                                        }
//
//                                        @Override
//                                        public void onNext(List<EmpJobTtlModel> userRes) {
//                                            empJobTtlModelList = userRes;
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//                                            // tvErrorLogin.setText("Error: Something went wrong happened :(");
//                                        }
//
//                                        @Override
//                                        public void onComplete() {
//                                            //  pdd.dismiss();
//                                            for (int i = 0; i < empJobTtlModelList.size(); i++) {
//                                                finalEmpJobTitleRealmHelper.addArticle(empJobTtlModelList.get(i));
//                                            }
//
//                                            onDestroy();
//                                            //onTaskRemoved(intent);
//                                            Toast.makeText(getApplicationContext(), "Completed.. Service stopped", Toast.LENGTH_LONG).show();
//
//                                        }
//                                    });
//                        }
//                    });
//        }catch (Exception e){}
//        return START_STICKY;
//    }
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

}