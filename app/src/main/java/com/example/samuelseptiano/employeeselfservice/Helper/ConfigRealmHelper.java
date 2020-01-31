package com.example.samuelseptiano.employeeselfservice.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Model.ConfigModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.ConfigRealmModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ConfigRealmHelper {

    private static final String TAG = "ConfigRealmHelper";


    private Realm realm;
    private RealmResults<ConfigRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public ConfigRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(ConfigModel cm) {
        ConfigRealmModel configRealmModel = new ConfigRealmModel();
       configRealmModel.setId(UUID.randomUUID().toString());
       configRealmModel.setUrl(cm.getUrl());

        realm.beginTransaction();
        realm.copyToRealm(configRealmModel);
        realm.commitTransaction();
        showLog("Added ; " + cm.getUrl());
        //showToast(usr.getUsername() + " berhasil disimpan.");
    }

    /**
     * method mencari semua article
     */
    public ArrayList<ConfigRealmModel> findAllArticle() {
        ArrayList<ConfigRealmModel> data = new ArrayList<>();


        realmResult = realm.where(ConfigRealmModel.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
//            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, url;
                id = realmResult.get(i).getId();
                url = realmResult.get(i).getUrl();
                data.add(new ConfigRealmModel(id, url));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Empty!");
        }

        return data;
    }




    public void deleteAllData() {
        RealmResults<ConfigRealmModel> dataDesults = realm.where(ConfigRealmModel.class).findAll();
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
