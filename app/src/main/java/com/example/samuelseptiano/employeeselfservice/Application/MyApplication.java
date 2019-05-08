package com.example.samuelseptiano.employeeselfservice.Application;

import android.content.Context;

import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.google.gson.annotations.SerializedName;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class MyApplication extends android.app.Application {
    private static MyApplication mInstance;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mContext = getApplicationContext();

        //kode konfigurasi Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder(). deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

    }

    public static Context getContext(){
        return mContext;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    private class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            //Mengambil schema
            RealmSchema schema = realm.getSchema();
            RealmSchema schema2 = realm.getSchema();
            RealmSchema schema3 = realm.getSchema();
            RealmSchema schema4 = realm.getSchema();
            RealmSchema schema5 = realm.getSchema();
            RealmSchema schema6 = realm.getSchema();


            //membuat schema baru jika versi 0
            if (oldVersion == 0) {
                schema.create("User")
                        .addField("id", String.class)
                        .addField("userID", String.class)
                        .addField("username", String.class)
                        .addField("empEmail", String.class)
                        .addField("password", String.class)
                        .addField("empNIK", String.class)
                        .addField("lastChangePassword", String.class)
                        .addField("lastUpdate", String.class)
                        .addField("lastUpdateBy", String.class)
                        .addField("fgActiveYN", String.class)
                        .addField("token", String.class)
                        .addField("userQR", String.class);

                schema2.create("Event")
                        .addField("id", String.class)
                        .addField("eventID", String.class)
                        .addField("eventName", String.class)
                        .addField("eventType", String.class)
                        .addField("externalEventCode", String.class)
                        .addField("eventDesc", String.class)
                        .addField("eventImage", String.class)
                        .addField("fgHasPasscodeYN", String.class)
                        .addField("passcode", String.class)
                        .addField("fgHasSurveyYN", String.class)
                        .addField("surveyID", String.class)
//                        .addField("fgActiveYN", String.class)
//                        .addField("lastUpdate", String.class)
//                        .addField("lastUpdateBy", String.class)
                        .addField("fgSurveyDoneYN", String.class)
                        .addField("fgAbsenYN", String.class);

                schema3.create("UserEvents")
                        .addField("id", String.class)
                        .addField("newsId", String.class)
                        .addField("dateJoin", String.class)
                        .addField("newsName", String.class)
                        .addField("newsDesc", String.class)
                        .addField("newsPoster", String.class)
                        .addField("status", String.class)
                        .addField("userId", String.class);

                schema4.create("SurveyQuestion")
                        .addField("id", String.class)
                        .addField("eventId", String.class)
                        .addField("surveyId", String.class)
                        .addField("empNIK", String.class)
                        .addField("answerId", String.class)
                        .addField("questionId", String.class)
                        .addField("answerEssay", String.class)
                        .addField("lastUpdateBy", String.class);

                schema5.create("AbsentHistory")
                        .addField("id", String.class)
                        .addField("eaid", String.class)
                        .addField("empNIK", String.class)
                        .addField("eventDate", String.class)
                        .addField("eventID", String.class)
                        .addField("eventName", String.class)
                        .addField("eventType", String.class);

                schema6.create("EventSession")
                        .addField("id", String.class)
                        .addField("esid", String.class)
                        .addField("eventId", String.class)
                        .addField("eventType", String.class)
                        .addField("instructorId", String.class)
                        .addField("sessionId", String.class)
                        .addField("sessionName", String.class)
                        .addField("sessionPlace", String.class)
                        .addField("instructorName", String.class)
                        .addField("sessionDateStart", String.class)
                        .addField("sessionDateEnd", String.class)
                        .addField("surveyId", String.class)
                        .addField("fileType", String.class)
                        .addField("fileData", String.class);

                oldVersion++;
            }

        }
    }
}
