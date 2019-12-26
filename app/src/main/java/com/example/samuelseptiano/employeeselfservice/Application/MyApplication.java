package com.example.samuelseptiano.employeeselfservice.Application;

import android.content.Context;

import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;

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

        config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new DataMigration())
                .build();
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

    public class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            //Mengambil schema
            RealmSchema schema = realm.getSchema();
            RealmSchema schema2 = realm.getSchema();
            RealmSchema schema3 = realm.getSchema();
            RealmSchema schema4 = realm.getSchema();
            RealmSchema schema5 = realm.getSchema();
            RealmSchema schema6 = realm.getSchema();
            RealmSchema schema7 = realm.getSchema();


            //membuat schema baru jika versi 0
            if (oldVersion == 0) {
                schema.create("User")
                        .addField("id", String.class)
                        .addField("userID", String.class)
                        .addField("username", String.class)
                        .addField("empPhoto", String.class)
                        .addField("empEmail", String.class)
                        .addField("password", String.class)
                        .addField("empNIK", String.class)
                        .addField("empName", String.class)
                        .addField("companyName", String.class)
                        .addField("companyCode", String.class)
                        .addField("locationName", String.class)
                        .addField("locationCode", String.class)
                        .addField("dept", String.class)
                        .addField("jobTitleName", String.class)
                        .addField("jobTitleCode", String.class)
                        .addField("lastChangePassword", String.class)
                        .addField("lastUpdate", String.class)
                        .addField("lastUpdateBy", String.class)
                        .addField("fgActiveYN", String.class)
                        .addField("positionId", String.class)
                        .addField("isPKAccessed", String.class)
                        .addField("nikAtasanLangsung", String.class)
                        .addField("namaAtasanLangsung", String.class)
                        .addField("posAtasanLangsung", String.class)
                        .addField("fotoAtasanTakLangsung", String.class)
                        .addField("jobTitleAtasan1", String.class)
                        .addField("nikAtasanTakLangsung", String.class)
                        .addField("orgNameAtasan1", String.class)
                        .addField("orgNameAtasan2", String.class)
                        .addField("namaAtasanTakLangsung", String.class)
                        .addField("posAtasanTakLangsung", String.class)
                        .addField("fotoAtasanTakLangsung", String.class)
                        .addField("jobTitleAtasan2", String.class)
                        .addField("orgName", String.class)
                        .addField("orgCode", String.class)
                        .addField("branchName", String.class)
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

                schema7.create("Config")
                        .addField("url", String.class);


                oldVersion++;
            }

        }
    }
}
