package com.example.samuelseptiano.employeeselfservice.Controller;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;


import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from User.class
    public void clearAll() {

        realm.beginTransaction();
        RealmResults results = realm.where(UserRealmModel.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    //find all objects in the User.class
    public RealmResults<UserRealmModel> getBooks() {

        return realm.where(UserRealmModel.class).findAll();
    }

    //query a single item with the given id
    public UserRealmModel getBook(String id) {

        return realm.where(UserRealmModel.class).equalTo("id", id).findFirst();
    }

    //check if User.class is empty
    public boolean hasUsers() {

        return !realm.where(UserRealmModel.class).findAll().isEmpty();
    }

    //query example
    public RealmResults<UserRealmModel> queryedUserss() {

        return realm.where(UserRealmModel.class)
                .contains("userId", "userid")
                .or()
                .contains("email", "email")
                .findAll();

    }

}
