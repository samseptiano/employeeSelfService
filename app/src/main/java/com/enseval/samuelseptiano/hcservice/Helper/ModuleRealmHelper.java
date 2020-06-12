package com.enseval.samuelseptiano.hcservice.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Model.ModuleModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.ModuleRealmModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ModuleRealmHelper {
    private static final String TAG = "ModuleRealmHelper";


    private Realm realm;
    private RealmResults<ModuleRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public ModuleRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }

    public ModuleRealmHelper() {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(ModuleModel mdl) {
        ModuleRealmModel module = new ModuleRealmModel();
        module.setId(UUID.randomUUID().toString());
        module.setModuleCode(mdl.getModuleCode());
        module.setModuleName(mdl.getModuleName());
        module.setIcon(mdl.getIcon());
        module.setModuleStatus(mdl.getModuleStatus());
        module.setModuleGuideline(mdl.getModuleGuideline());

        realm.beginTransaction();
        realm.copyToRealm(module);
        realm.commitTransaction();

        showLog("Added ; " + mdl.getModuleCode());
        //showToast(usr.getUsername() + " berhasil disimpan.");
    }


    /**
     * method mencari semua article
     */
    public ArrayList<ModuleRealmModel> findAllArticle() {
        ArrayList<ModuleRealmModel> data = new ArrayList<>();

        realmResult = realm.where(ModuleRealmModel.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, moduleCode,modulename,icon,moduleStatus,moduleGuideline;
                id = realmResult.get(i).getId();
                moduleCode = realmResult.get(i).getModuleCode();
                modulename = realmResult.get(i).getModuleName();
                icon = realmResult.get(i).getIcon();
                moduleGuideline = realmResult.get(i).getModuleGuideline();

                moduleStatus = realmResult.get(i).getModuleStatus();
                data.add(new ModuleRealmModel( id,moduleCode,modulename,icon, moduleStatus,moduleGuideline));
                }


        } else {
            showLog("Size : 0");
            //showToast("Database Kosong!");
        }

        return data;
    }


    /**
     * method update article
     *
     * @param id
     * @param //title
     * @param //description
     */
    public void updateArticle(int id, ModuleModel mdl) {
        realm.beginTransaction();
        ModuleRealmModel module = realm.where(ModuleRealmModel.class).equalTo("id", id).findFirst();
        module.setModuleName(mdl.getModuleName());
        module.setModuleCode(mdl.getModuleCode());
        module.setIcon(mdl.getIcon());
        module.setModuleGuideline(mdl.getModuleGuideline());
        module.setModuleStatus(mdl.getModuleStatus());
        realm.commitTransaction();
        showLog("Updated : " + mdl.getModuleCode());

        //showToast(usr.getUserId() + " berhasil diupdate.");
    }


    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteData(String id) {
        RealmResults<ModuleRealmModel> dataDesults = realm.where(ModuleRealmModel.class).equalTo("id", id).findAll();
        realm.beginTransaction();
        //dataDesults.remove(0);
        //dataDesults.removeLast();
        //dataDesults.clear();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus data berhasil.");
    }


    public void deleteAllData() {
        RealmResults<ModuleRealmModel> dataDesults = realm.where(ModuleRealmModel.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus Semua data berhasil.");
    }


    /**
     * membuat log
     *
     * @param s
     */
    private void showLog(String s) {
        Log.d(TAG, s);

    }

    /**
     * Membuat Toast Informasi
     */
    private void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
