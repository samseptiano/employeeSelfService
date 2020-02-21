package com.enseval.samuelseptiano.hcservice.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Model.RealmModel.EmpJobTtlRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobTtlModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class EmpJobTitleRealmHelper {

    private static final String TAG = "EmpJobTitleRealmHelper";


    private Realm realm;
    private RealmResults<EmpJobTtlRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public EmpJobTitleRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(EmpJobTtlModel cm) {
        EmpJobTtlRealmModel empJobTtlRealmModel = new EmpJobTtlRealmModel();
        empJobTtlRealmModel.setId(UUID.randomUUID().toString());
        empJobTtlRealmModel.setJobttlcode(cm.getJobttlcode());
        empJobTtlRealmModel.setJobttlname(cm.getJobttlcode());

        realm.beginTransaction();
        realm.copyToRealm(empJobTtlRealmModel);
        realm.commitTransaction();
        showLog("Added ; " + cm.getJobttlcode());
        //showToast(usr.getUsername() + " berhasil disimpan.");
    }

    /**
     * method mencari semua article
     */
    public ArrayList<EmpJobTtlRealmModel> findAllArticle() {
        ArrayList<EmpJobTtlRealmModel> data = new ArrayList<>();


        realmResult = realm.where(EmpJobTtlRealmModel.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
//            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, jobTtlCode,jobTtlName;
                id = realmResult.get(i).getId();
                jobTtlCode = realmResult.get(i).getJobttlcode();
                jobTtlName = realmResult.get(i).getJobttlname();

                data.add(new EmpJobTtlRealmModel(id, jobTtlCode,jobTtlName));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Empty!");
        }

        return data;
    }




    public void deleteAllData() {
        RealmResults<EmpJobTtlRealmModel> dataDesults = realm.where(EmpJobTtlRealmModel.class).findAll();
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
