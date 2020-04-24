package com.enseval.samuelseptiano.hcservice.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey.UserAnswer;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserAnswerRealmModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class UserAnswerRealmHelper {

    private static final String TAG = "UserAnswerRealmHelper";


    private Realm realm;
    private RealmResults<UserAnswerRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public UserAnswerRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addArticle(UserAnswer ua) {
        UserAnswerRealmModel userAnswer = new UserAnswerRealmModel();
        userAnswer.setId(UUID.randomUUID().toString());
        userAnswer.setEventId(ua.getEventId());
        userAnswer.setAnswerEssay(ua.getAnswerEssay());
        userAnswer.setAnswerID(ua.getAnswerID());
        userAnswer.setEmpNIK(ua.getEmpNIK());
        userAnswer.setQuestionID(ua.getQuestionID());
        userAnswer.setSurveyID(ua.getSurveyID());
        userAnswer.setLastUpdateBy(ua.getLastUpdateBy());


        realm.beginTransaction();
        realm.copyToRealm(userAnswer);
        realm.commitTransaction();


        showLog("Added ; " + ua.getEventId());
        showToast( ua.getEventId() + " berhasil disimpan.");
    }


    /**
     * method mencari semua article
     */
    public ArrayList<UserAnswerRealmModel> findAllArticle() {
        ArrayList<UserAnswerRealmModel> data = new ArrayList<>();


        realmResult = realm.where(UserAnswerRealmModel.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id, eventId, answerEssay, answerId,  questionId, lastUpdateBy,  empNIK, surveyId;
                id = realmResult.get(i).getId();
                eventId = realmResult.get(i).getEventId();
                answerEssay = realmResult.get(i).getAnswerEssay();
                answerId = realmResult.get(i).getAnswerID();
                questionId = realmResult.get(i).getQuestionID();
                lastUpdateBy = realmResult.get(i).getLastUpdateBy();
                empNIK = realmResult.get(i).getEmpNIK();
                surveyId  = realmResult.get(i).getSurveyID();


                data.add(new UserAnswerRealmModel(id,eventId,empNIK,surveyId, questionId, answerId, answerEssay,lastUpdateBy));
            }


        } else {
            showLog("Size : 0");
            showToast("User Answer Empty!");
        }

        return data;
    }


    public UserAnswerRealmModel findArticle(String idd) {
        UserAnswerRealmModel data= new UserAnswerRealmModel();
        realmResult = realm.where(UserAnswerRealmModel.class).equalTo("id", idd).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();
            String newsId,newsName,newsDesc,newsImage,newsDate,newsCategory,newsAuthor;
            for (int i = 0; i < realmResult.size(); i++) {

                String id, eventId, answerEssay, answerId,  questionId, lastUpdateBy,  empNIK, surveyId;
                id = realmResult.get(i).getId();
                eventId = realmResult.get(i).getEventId();
                answerEssay = realmResult.get(i).getAnswerEssay();
                answerId = realmResult.get(i).getAnswerID();
                questionId = realmResult.get(i).getQuestionID();
                lastUpdateBy = realmResult.get(i).getLastUpdateBy();
                empNIK = realmResult.get(i).getEmpNIK();
                surveyId  = realmResult.get(i).getSurveyID();

                data = new UserAnswerRealmModel(id,eventId,empNIK,surveyId, questionId, answerId, answerEssay,lastUpdateBy);
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
    public void updateArticle(int id, UserAnswer ua) {
        realm.beginTransaction();
        UserAnswerRealmModel userAnswer = realm.where(UserAnswerRealmModel.class).equalTo("id", id).findFirst();

        userAnswer.setEventId(ua.getEventId());
        userAnswer.setAnswerEssay(ua.getAnswerEssay());
        userAnswer.setAnswerID(ua.getAnswerID());
        userAnswer.setEmpNIK(ua.getEmpNIK());
        userAnswer.setQuestionID(ua.getQuestionID());
        userAnswer.setSurveyID(ua.getSurveyID());
        userAnswer.setLastUpdateBy(ua.getLastUpdateBy());

        realm.commitTransaction();
        showLog("Updated : " + ua.getEventId());

        //showToast(hm.getEventId() + " berhasil diupdate.");
    }


    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteData(String id) {
        RealmResults<UserAnswerRealmModel> dataDesults = realm.where(UserAnswerRealmModel.class).equalTo("id", id).findAll();
        realm.beginTransaction();
        //dataDesults.remove(0);
        //dataDesults.removeLast();
        //dataDesults.clear();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus data berhasil.");
    }


    public void deleteAllData() {
        RealmResults<UserAnswerRealmModel> dataDesults = realm.where(UserAnswerRealmModel.class).findAll();
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
