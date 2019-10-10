package com.example.samuelseptiano.employeeselfservice.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSession;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EventSessionRealmModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class EventSessionRealmHelper {
    private static final String TAG = "EventSessionHelper";


    private Realm realm;
    private RealmResults<EventSessionRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public EventSessionRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(EventSession es) {
        EventSessionRealmModel eventSessionRealmModel = new EventSessionRealmModel();

        eventSessionRealmModel.setId(UUID.randomUUID().toString());
        eventSessionRealmModel.setEsid(es.getEsid());
        eventSessionRealmModel.setEventID(es.getEventID());
        eventSessionRealmModel.setInstructorID(es.getInstructorID());
        eventSessionRealmModel.setInstructorName(es.getInstructorName());
        eventSessionRealmModel.setSessionDateEnd(es.getSessionDateEnd());
        eventSessionRealmModel.setSessionDateStart(es.getSessionDateStart());
        eventSessionRealmModel.setSessionName(es.getSessionName());
        eventSessionRealmModel.setSessionPlace(es.getSessionPlace());
        eventSessionRealmModel.setSessionID(es.getSessionID());
        eventSessionRealmModel.setSurveyID(es.getSurveyID());
        eventSessionRealmModel.setFileData(es.getFileData());
        eventSessionRealmModel.setFileType(es.getFileType());

        realm.beginTransaction();
        realm.copyToRealm(eventSessionRealmModel);
        realm.commitTransaction();


        showLog("Added ; " + es.getEsid());
        //showToast(ea.getEventName() + " berhasil disimpan.");
    }


    /**
     * method mencari semua article
     */
    public ArrayList<EventSessionRealmModel> findAllArticle() {
        ArrayList<EventSessionRealmModel> data = new ArrayList<>();


        realmResult = realm.where(EventSessionRealmModel.class).findAll().sort("sessionName", Sort.ASCENDING);
        //realmResult.sort("id", Sort.ASCENDING);
        //realmResult.sort("sessionName", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, esid, eventID,instructorID, sessionID, sessionName, sessionPlace,instructorName,surveyID, sessionDateStart, sessionDateEnd, fileData, fileType, eventType;

                id = realmResult.get(i).getId();
                esid = realmResult.get(i).getEsid();
                eventID = realmResult.get(i).getEventID();
                instructorID = realmResult.get(i).getInstructorID();
                instructorName = realmResult.get(i).getInstructorName();
                sessionDateEnd = realmResult.get(i).getSessionDateEnd();
                sessionDateStart = realmResult.get(i).getSessionDateStart();
                sessionName = realmResult.get(i).getSessionName();
                sessionPlace = realmResult.get(i).getSessionPlace();
                sessionID = realmResult.get(i).getSessionID();
                surveyID = realmResult.get(i).getSurveyID();
                fileData = realmResult.get(i).getFileData();
                fileType = realmResult.get(i).getFileType();
                eventType = realmResult.get(i).getEventType();

                data.add(new EventSessionRealmModel( id, esid, eventID,eventType,instructorID, sessionID, sessionName, sessionPlace,instructorName, surveyID, sessionDateStart, sessionDateEnd, fileData, fileType));

                //showToast(eventName);
            }


        } else {
            showLog("Size : 0");
            showToast("Event Session Empty!");
        }

        return data;
    }


    public ArrayList<EventSessionRealmModel> findArticle(String idd) {
        ArrayList<EventSessionRealmModel> data = new ArrayList<>();
        realmResult = realm.where(EventSessionRealmModel.class).equalTo("EventID", idd).findAll();
        realmResult.sort("esid", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();
            String newsId,newsName,newsDesc,newsImage,newsDate,newsCategory,newsAuthor;
            for (int i = 0; i < realmResult.size(); i++) {

                String id, esid, eventID,eventType, instructorID, sessionID, sessionName, sessionPlace,instructorName,surveyID, sessionDateStart, sessionDateEnd, fileData,fileType;
                id = realmResult.get(i).getId();
                esid = realmResult.get(i).getEsid();
                eventID = realmResult.get(i).getEventID();
                instructorID = realmResult.get(i).getInstructorID();
                instructorName = realmResult.get(i).getInstructorName();
                sessionDateEnd = realmResult.get(i).getSessionDateEnd();
                sessionDateStart = realmResult.get(i).getSessionDateStart();
                sessionName = realmResult.get(i).getSessionName();
                sessionPlace = realmResult.get(i).getSessionPlace();
                sessionID = realmResult.get(i).getSessionID();
                surveyID = realmResult.get(i).getSurveyID();
                fileData = realmResult.get(i).getFileData();
                fileType = realmResult.get(i).getFileType();
                eventType = realmResult.get(i).getEventType();

                data.add(new EventSessionRealmModel( id, esid, eventID,eventType,instructorID, sessionID, sessionName, sessionPlace,instructorName, surveyID, sessionDateStart, sessionDateEnd,fileData,fileType));
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
    public void updateArticle(int id, EventSession es) {
        realm.beginTransaction();
        EventSessionRealmModel eventSession = realm.where(EventSessionRealmModel.class).equalTo("esid", id).findFirst();

        eventSession.setEsid(es.getEsid());
        eventSession.setEventID(es.getEventID());
        eventSession.setInstructorID(es.getInstructorID());
        eventSession.setInstructorName(es.getInstructorName());
        eventSession.setSessionDateEnd(es.getSessionDateEnd());
        eventSession.setSessionDateStart(es.getSessionDateStart());
        eventSession.setSessionName(es.getSessionName());
        eventSession.setSessionPlace(es.getSessionPlace());
        eventSession.setSessionID(es.getSessionID());
        eventSession.setSurveyID(es.getSurveyID());
        eventSession.setFileData(es.getFileData());
        eventSession.setFileType(es.getFileType());
        eventSession.setEventType(es.getEventType());

        realm.commitTransaction();
        showLog("Updated : " + es.getEventID());

        //showToast(hm.getEventId() + " berhasil diupdate.");
    }


    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteData(String id) {
        RealmResults<EventSessionRealmModel> dataDesults = realm.where(EventSessionRealmModel.class).equalTo("esid", id).findAll();
        realm.beginTransaction();
        //dataDesults.remove(0);
        //dataDesults.removeLast();
        //dataDesults.clear();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus data berhasil.");
    }


    public void deleteAllData() {
        RealmResults<EventSessionRealmModel> dataDesults = realm.where(EventSessionRealmModel.class).findAll();
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
