package com.enseval.samuelseptiano.hcservice.Helper;

import android.content.Context;
import android.util.Log;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.Home.Home;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.HomeRealmModel;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class HomeRealmHelper {

    private static final String TAG = "HomeRealmHelper";


    private Realm realm;
    private RealmResults<HomeRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public HomeRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }

    public HomeRealmHelper() {
        realm = Realm.getInstance(Realm.getDefaultConfiguration()); }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(Home hm) {
        try {
            HomeRealmModel home = new HomeRealmModel();
            home.setId(hm.getEventId());
            home.setEventId(hm.getEventId());
            home.setEventName(hm.getEventName());
            home.setEventDesc(hm.getEventDesc());
            home.setExternalEventCode(hm.getExternalEventCode());
            home.setEventType(hm.getEventType());
            home.setfGSurveyDoneYN(home.getfGSurveyDoneYN());
            home.setfGHasSurveyYN(hm.getfGHasSurveyYN());
            home.setfGHasPasscodeYN(hm.getfGHasPasscodeYN());
            home.setPasscode(hm.getPasscode());
            home.setSurveyId(hm.getSurveyId());
            home.setEventImage(hm.getEventImage());
            home.setfGAbsenYN(hm.getfGAbsenYN());

            realm.beginTransaction();
            realm.copyToRealm(home);
            realm.commitTransaction();


            showLog("Added ; " + hm.getEventId());
        }
        catch (Exception e){
            showLog("Duplicate ; " + hm.getEventId());

        }
        //showToast(usr.getUsername() + " berhasil disimpan.");
    }


    /**
     * method mencari semua article
     */
    public ArrayList<HomeRealmModel> findAllArticle() {
        ArrayList<HomeRealmModel> data = new ArrayList<>();


        realmResult = realm.where(HomeRealmModel.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, eventId, eventName, eventType,  externalEventCode, eventDesc,  eventImage, fGHasPasscodeYN, passcode, fGHasSurveyYN, surveyId, fGSurveyDoneYN, fGAbsenYN;
                id = realmResult.get(i).getId();
                eventId = realmResult.get(i).getEventId();
                eventName = realmResult.get(i).getEventName();
                eventDesc = realmResult.get(i).getEventDesc();
                eventType = realmResult.get(i).getEventType();
                eventImage = realmResult.get(i).getEventImage();
                externalEventCode = realmResult.get(i).getExternalEventCode();
                fGHasPasscodeYN = realmResult.get(i).getfGHasPasscodeYN();
                passcode = realmResult.get(i).getPasscode();
                fGHasSurveyYN = realmResult.get(i).getfGHasSurveyYN();
                surveyId = realmResult.get(i).getSurveyId();
                fGSurveyDoneYN = realmResult.get(i).getfGSurveyDoneYN();
                fGAbsenYN = realmResult.get(i).getfGAbsenYN();

                data.add(new HomeRealmModel( id,eventId,eventName,eventType,externalEventCode,eventDesc,eventImage, fGHasPasscodeYN,passcode,fGHasSurveyYN, surveyId, fGSurveyDoneYN,fGAbsenYN));
            }


        } else {
            showLog("Size : 0");
//            showToast("No Training Found");
        }

        return data;
    }


    public HomeRealmModel findArticle(String idd) {
        HomeRealmModel data= new HomeRealmModel();
        realmResult = realm.where(HomeRealmModel.class).equalTo("eventId", idd).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();
            String newsId,newsName,newsDesc,newsImage,newsDate,newsCategory,newsAuthor;
            for (int i = 0; i < realmResult.size(); i++) {

                String id, eventId, eventName, eventType,  externalEventCode, eventDesc,  eventImage, fGHasPasscodeYN, passcode, fGHasSurveyYN, surveyId, fGSurveyDoneYN, fGAbsenYN;
                id = realmResult.get(i).getId();
                eventId = realmResult.get(i).getEventId();
                eventName = realmResult.get(i).getEventName();
                eventDesc = realmResult.get(i).getEventDesc();
                eventType = realmResult.get(i).getEventType();
                eventImage = realmResult.get(i).getEventImage();
                externalEventCode = realmResult.get(i).getExternalEventCode();
                fGHasPasscodeYN = realmResult.get(i).getfGHasPasscodeYN();
                passcode = realmResult.get(i).getPasscode();
                fGHasSurveyYN = realmResult.get(i).getfGHasSurveyYN();
                surveyId = realmResult.get(i).getSurveyId();
                fGSurveyDoneYN = realmResult.get(i).getfGSurveyDoneYN();
                fGAbsenYN = realmResult.get(i).getfGAbsenYN();

                data = new HomeRealmModel( id,eventId,eventName,eventType,externalEventCode,eventDesc,eventImage, fGHasPasscodeYN,passcode,fGHasSurveyYN, surveyId, fGSurveyDoneYN,fGAbsenYN);
            }

        } else {
            showLog("Size : 0");
            //showToast("NULL");
        }

        return data;
    }

    public  ArrayList<HomeRealmModel> findCategoryArticle(String cat) {
        ArrayList<HomeRealmModel> data = new ArrayList<>();
        realmResult = realm.where(HomeRealmModel.class).equalTo("eventType", cat).findAll();
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();
            String newsId,newsName,newsDesc,newsImage,newsDate,newsCategory,newsAuthor;
            for (int i = 0; i < realmResult.size(); i++) {

                String id, eventId, eventName, eventType,  externalEventCode, eventDesc,  eventImage, fGHasPasscodeYN, passcode, fGHasSurveyYN, surveyId, fGSurveyDoneYN,fgAbsenYN;
                id = realmResult.get(i).getId();
                eventId = realmResult.get(i).getEventId();
                eventName = realmResult.get(i).getEventName();
                eventDesc = realmResult.get(i).getEventDesc();
                eventType = realmResult.get(i).getEventType();
                eventImage = realmResult.get(i).getEventImage();
                externalEventCode = realmResult.get(i).getExternalEventCode();
                fGHasPasscodeYN = realmResult.get(i).getfGHasPasscodeYN();
                passcode = realmResult.get(i).getPasscode();
                fGHasSurveyYN = realmResult.get(i).getfGHasSurveyYN();
                surveyId = realmResult.get(i).getSurveyId();
                fGSurveyDoneYN = realmResult.get(i).getfGSurveyDoneYN();
                fgAbsenYN = realmResult.get(i).getfGAbsenYN();

                data.add(new HomeRealmModel( id,eventId,eventName,eventType,externalEventCode,eventDesc,eventImage, fGHasPasscodeYN,passcode,fGHasSurveyYN, surveyId, fGSurveyDoneYN,fgAbsenYN));

            }

        } else {
            showLog("Size : 0");
            showToast("NULL");
        }

        return data;
    }

    public  ArrayList<HomeRealmModel> findArticles(String query) {
        ArrayList<HomeRealmModel> data = new ArrayList<>();
        realmResult = realm.where(HomeRealmModel.class).contains("eventName",query,Case.INSENSITIVE).findAll();
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();
            String newsId,newsName,newsDesc,newsImage,newsDate,newsCategory,newsAuthor;
            for (int i = 0; i < realmResult.size(); i++) {

                String id, eventId, eventName, eventType,  externalEventCode, eventDesc,  eventImage, fGHasPasscodeYN, passcode, fGHasSurveyYN, surveyId, fGSurveyDoneYN,fgAbsenYN;
                id = realmResult.get(i).getId();
                eventId = realmResult.get(i).getEventId();
                eventName = realmResult.get(i).getEventName();
                eventDesc = realmResult.get(i).getEventDesc();
                eventType = realmResult.get(i).getEventType();
                eventImage = realmResult.get(i).getEventImage();
                externalEventCode = realmResult.get(i).getExternalEventCode();
                fGHasPasscodeYN = realmResult.get(i).getfGHasPasscodeYN();
                passcode = realmResult.get(i).getPasscode();
                fGHasSurveyYN = realmResult.get(i).getfGHasSurveyYN();
                surveyId = realmResult.get(i).getSurveyId();
                fGSurveyDoneYN = realmResult.get(i).getfGSurveyDoneYN();
                fgAbsenYN = realmResult.get(i).getfGAbsenYN();

                data.add(new HomeRealmModel( id,eventId,eventName,eventType,externalEventCode,eventDesc,eventImage, fGHasPasscodeYN,passcode,fGHasSurveyYN, surveyId, fGSurveyDoneYN,fgAbsenYN));

            }

        } else {
            data = null;
            showLog("Size : 0");
            showToast("Search not found");
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
    public void updateArticle(int id, Home hm) {
        realm.beginTransaction();
        HomeRealmModel home = realm.where(HomeRealmModel.class).equalTo("eventID", id).findFirst();

        home.setEventName(hm.getEventName());
        home.setEventDesc(hm.getEventDesc());
        home.setExternalEventCode(hm.getExternalEventCode());
        home.setEventType(hm.getEventType());
        home.setfGSurveyDoneYN(home.getfGSurveyDoneYN());
        home.setfGHasSurveyYN(hm.getfGHasSurveyYN());
        home.setfGHasPasscodeYN(hm.getfGHasPasscodeYN());
        home.setPasscode(hm.getPasscode());
        home.setSurveyId(hm.getSurveyId());
        home.setEventImage(hm.getEventImage());

        realm.commitTransaction();
        showLog("Updated : " + hm.getEventId());

        //showToast(hm.getEventId() + " berhasil diupdate.");
    }


    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteData(String id) {
        RealmResults<HomeRealmModel> dataDesults = realm.where(HomeRealmModel.class).equalTo("eventID", id).findAll();
        realm.beginTransaction();
        //dataDesults.remove(0);
        //dataDesults.removeLast();
        //dataDesults.clear();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus data berhasil.");
    }


    public void deleteAllData() {
        RealmResults<HomeRealmModel> dataDesults = realm.where(HomeRealmModel.class).findAll();
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
//        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
