package com.example.samuelseptiano.employeeselfservice.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EventAbsentUserRealmModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class EventAbsentUserRealmHelper {

    private static final String TAG = "EventAbsentHelper";


    private Realm realm;
    private RealmResults<EventAbsentUserRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public EventAbsentUserRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(EventAbsentUser ea) {
        EventAbsentUserRealmModel eventAbsentUserRealmModel = new EventAbsentUserRealmModel();
        eventAbsentUserRealmModel.setId(UUID.randomUUID().toString());
        eventAbsentUserRealmModel.setEaid(ea.getEaid());
        eventAbsentUserRealmModel.setEventName(ea.getEventName());
        eventAbsentUserRealmModel.setEmpNIK(ea.getEmpNIK());
        eventAbsentUserRealmModel.setEventDate(ea.getEventDate());
        eventAbsentUserRealmModel.setEventID(ea.getEventID());
        eventAbsentUserRealmModel.setEventType(ea.getEventType());

        realm.beginTransaction();
        realm.copyToRealm(eventAbsentUserRealmModel);
        realm.commitTransaction();


        showLog("Added ; " + ea.getEventID());
        //showToast(ea.getEventName() + " berhasil disimpan.");
    }


    /**
     * method mencari semua article
     */
    public ArrayList<EventAbsentUserRealmModel> findAllArticle() {
        ArrayList<EventAbsentUserRealmModel> data = new ArrayList<>();


        realmResult = realm.where(EventAbsentUserRealmModel.class).distinct("eventName").findAll();
        //realmResult.sort("id", Sort.ASCENDING);
        realmResult.sort("eaid", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, eaid, eventName, eventDate,  empNIK, eventId, eventType;
                id = realmResult.get(i).getId();
                eventName = realmResult.get(i).getEventName();
                eventId = realmResult.get(i).getEventID();
                eventDate = realmResult.get(i).getEventDate();
                empNIK = realmResult.get(i).getEmpNIK();
                eaid = realmResult.get(i).getEaid();
                eventType = realmResult.get(i).getEventType();

                data.add(new EventAbsentUserRealmModel( id,eaid, empNIK, eventDate, eventId, eventName, eventType));

                //showToast(eventName);
            }


        } else {
            showLog("Size : 0");
            showToast("Database Empty!");
        }

        return data;
    }


    public EventAbsentUserRealmModel findArticle(String idd) {
        EventAbsentUserRealmModel data= new EventAbsentUserRealmModel();
        realmResult = realm.where(EventAbsentUserRealmModel.class).equalTo("eventId", idd).findAll();
        realmResult.sort("eventDate", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();
            String newsId,newsName,newsDesc,newsImage,newsDate,newsCategory,newsAuthor;
            for (int i = 0; i < realmResult.size(); i++) {

                String id, eaid, eventName, eventDate,  empNIK, eventId, eventType;
                id = realmResult.get(i).getId();
                eventName = realmResult.get(i).getEventName();
                eventId = realmResult.get(i).getEventID();
                eventDate = realmResult.get(i).getEventDate();
                empNIK = realmResult.get(i).getEmpNIK();
                eaid = realmResult.get(i).getEaid();
                eventType = realmResult.get(i).getEventType();

                data = new EventAbsentUserRealmModel( id,eaid, empNIK, eventDate, eventId, eventName,eventType);
            }

        } else {
            showLog("Size : 0");
            //showToast("NULL");
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
    public void updateArticle(int id, EventAbsentUser hm) {
        realm.beginTransaction();
        EventAbsentUserRealmModel home = realm.where(EventAbsentUserRealmModel.class).equalTo("eventID", id).findFirst();

        home.setEventName(hm.getEventName());
        home.setEventID(hm.getEventID());
        home.setEventDate(hm.getEventDate());
        home.setEaid(hm.getEaid());
        home.setEmpNIK(home.getEmpNIK());
        home.setEventType(home.getEventType());
        realm.commitTransaction();
        showLog("Updated : " + hm.getEventID());

        //showToast(hm.getEventId() + " berhasil diupdate.");
    }


    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteData(String id) {
        RealmResults<EventAbsentUserRealmModel> dataDesults = realm.where(EventAbsentUserRealmModel.class).equalTo("eventID", id).findAll();
        realm.beginTransaction();
        //dataDesults.remove(0);
        //dataDesults.removeLast();
        //dataDesults.clear();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus data berhasil.");
    }


    public void deleteAllData() {
        RealmResults<EventAbsentUserRealmModel> dataDesults = realm.where(EventAbsentUserRealmModel.class).findAll();
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
