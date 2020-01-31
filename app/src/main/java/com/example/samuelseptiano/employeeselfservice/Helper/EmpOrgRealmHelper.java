package com.example.samuelseptiano.employeeselfservice.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpJobTtlModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpOrgModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EmpJobTtlRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EmpOrgRealmModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class EmpOrgRealmHelper {

    private static final String TAG = "EmpOrgRealmHelper";


    private Realm realm;
    private RealmResults<EmpOrgRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public EmpOrgRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(EmpOrgModel cm) {
        EmpOrgRealmModel empOrgRealmModel = new EmpOrgRealmModel();
        empOrgRealmModel.setId(UUID.randomUUID().toString());
        empOrgRealmModel.setOrgCode(cm.getOrgCode());
        empOrgRealmModel.setOrgName(cm.getOrgName());

        realm.beginTransaction();
        realm.copyToRealm(empOrgRealmModel);
        realm.commitTransaction();
        showLog("Added ; " + cm.getOrgCode());
        //showToast(usr.getUsername() + " berhasil disimpan.");
    }

    /**
     * method mencari semua article
     */
    public ArrayList<EmpOrgRealmModel> findAllArticle() {
        ArrayList<EmpOrgRealmModel> data = new ArrayList<>();


        realmResult = realm.where(EmpOrgRealmModel.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
//            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, orgCode,orgName;
                id = realmResult.get(i).getId();
                orgCode = realmResult.get(i).getOrgCode();
                orgName = realmResult.get(i).getOrgName();

                data.add(new EmpOrgRealmModel(id, orgCode,orgName));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Empty!");
        }

        return data;
    }




    public void deleteAllData() {
        RealmResults<EmpOrgRealmModel> dataDesults = realm.where(EmpOrgRealmModel.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();
        //showToast("Hapus Semua data berhasil.");
    }

    private void showLog(String s) {
        Log.d(TAG, s);

    }

    private void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
