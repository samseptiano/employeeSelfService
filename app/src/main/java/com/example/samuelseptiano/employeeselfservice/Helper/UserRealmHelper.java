package com.example.samuelseptiano.employeeselfservice.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.User;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class UserRealmHelper {
    private static final String TAG = "UserRealmHelper";


    private Realm realm;
    private RealmResults<UserRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public UserRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(User usr, String token, String userQR) {
        UserRealmModel user = new UserRealmModel();
        user.setId(UUID.randomUUID().toString());
        user.setUserId(usr.getUserId());
        user.setUsername(usr.getUsername());
        user.setEmpEmail(usr.getEmpEmail());
        user.setPassword(usr.getPassword());
        user.setEmpNIK(usr.getEmpNIK());
        user.setfGActiveYN(usr.getfGActiveYN());
        user.setLastUpdate(user.getLastUpdate());
        user.setLastUpdateBy(usr.getLastUpdateBy());
        user.setToken(token);
        user.setUserQR(userQR);

        user.setEmpName(usr.getEmpName());
        user.setCompanyName(usr.getCompanyName());
        user.setCompanyCode(usr.getCompanyCode());
        user.setDept(usr.getDept());
        user.setJobTitleCode(usr.getJobTitleCode());
        user.setJobTitleName(usr.getJobTitleName());
        user.setLocationName(usr.getLocationName());

        user.setPositionId(usr.getPositionId());
        user.setIsPKAccessed(usr.getIsPKAccessed());
        user.setNikAtasanLangsung(usr.getNikAtasanLangsung());
        user.setNamaAtasanLangsung(usr.getNamaAtasanLangsung());
        user.setPosAtasanLangsung(usr.getPosAtasanLangsung());

        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();

        showLog("Added ; " + usr.getUserId());
        //showToast(usr.getUsername() + " berhasil disimpan.");
    }


    /**
     * method mencari semua article
     */
    public ArrayList<UserRealmModel> findAllArticle() {
        ArrayList<UserRealmModel> data = new ArrayList<>();


        realmResult = realm.where(UserRealmModel.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, userId, lastUpdateBy, fGActiveYN, lastUpdate, username, empEmail, password, empNIK, token, userQR, lastChangePassword, empName, dept, companyName,companyCode,locationName,locationCode,jobTitleName,jobTitleCode, positionId,isPKAccessed, nikAtasan, namaAtasan, posAtasan;
                id = realmResult.get(i).getId();
                userId = realmResult.get(i).getUserId();
                username = realmResult.get(i).getUsername();
                empEmail = realmResult.get(i).getEmpEmail();
                password = realmResult.get(i).getPassword();
                fGActiveYN = realmResult.get(i).getfGActiveYN();
                lastUpdate = realmResult.get(i).getLastUpdate();
                lastUpdateBy = realmResult.get(i).getLastUpdateBy();
                lastChangePassword = realmResult.get(i).getLastChangePassword();
                empNIK = realmResult.get(i).getEmpNIK();
                token = realmResult.get(i).getToken();
                userQR = realmResult.get(i).getUserQR();

                empName = realmResult.get(i).getEmpName();
                dept = realmResult.get(i).getDept();
                locationName = realmResult.get(i).getLocationName();
                locationCode = realmResult.get(i).getLocationCode();
                companyName = realmResult.get(i).getCompanyName();
                companyCode = realmResult.get(i).getCompanyCode();
                jobTitleName = realmResult.get(i).getJobTitleName();
                jobTitleCode = realmResult.get(i).getJobTitleCode();
                positionId = realmResult.get(i).getPositionId();
                isPKAccessed = realmResult.get(i).getIsPKAccessed();
                nikAtasan = realmResult.get(i).getNikAtasanLangsung();
                namaAtasan = realmResult.get(i).getNamaAtasanLangsung();
                posAtasan = realmResult.get(i).getPosAtasanLangsung();


                data.add(new UserRealmModel( id,  userId,  lastUpdateBy,  fGActiveYN,  lastUpdate,  lastChangePassword,  username,  empEmail,  password,  empNIK,  token,  userQR,  empName,  dept,  jobTitleName,  jobTitleCode,  companyName,  companyCode,  locationName,  locationCode,positionId, isPKAccessed,nikAtasan,namaAtasan,posAtasan));

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
    public void updateArticle(int id, User usr, String token, String userQR) {
        realm.beginTransaction();
        UserRealmModel user = realm.where(UserRealmModel.class).equalTo("userId", id).findFirst();
        user.setEmpEmail(usr.getEmpEmail());
        user.setPassword(usr.getPassword());
        user.setEmpNIK(usr.getEmpNIK());
        user.setToken(token);
        user.setUserQR(userQR);

        user.setEmpName(usr.getEmpName());
        user.setCompanyName(usr.getCompanyName());
        user.setCompanyCode(usr.getCompanyCode());
        user.setDept(usr.getDept());
        user.setJobTitleCode(usr.getJobTitleCode());
        user.setJobTitleName(usr.getJobTitleName());
        user.setLocationName(usr.getLocationName());
        user.setLocationCode(usr.getLocationCode());
        user.setPositionId(usr.getPositionId());
        user.setIsPKAccessed(usr.getIsPKAccessed());
        user.setNikAtasanLangsung(usr.getNikAtasanLangsung());
        user.setNamaAtasanLangsung(usr.getNamaAtasanLangsung());
        user.setPosAtasanLangsung(usr.getPosAtasanLangsung());

        realm.commitTransaction();
        showLog("Updated : " + usr.getUserId());

        //showToast(usr.getUserId() + " berhasil diupdate.");
    }


    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteData(String id) {
        RealmResults<UserRealmModel> dataDesults = realm.where(UserRealmModel.class).equalTo("userId", id).findAll();
        realm.beginTransaction();
        //dataDesults.remove(0);
        //dataDesults.removeLast();
        //dataDesults.clear();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus data berhasil.");
    }


    public void deleteAllData() {
        RealmResults<UserRealmModel> dataDesults = realm.where(UserRealmModel.class).findAll();
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
