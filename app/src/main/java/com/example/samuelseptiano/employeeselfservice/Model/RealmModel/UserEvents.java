package com.example.samuelseptiano.employeeselfservice.Model.RealmModel;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class UserEvents extends RealmObject {
    public HomeRealmModel getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(HomeRealmModel userEvents) {
        this.userEvents = userEvents;
    }

    @SerializedName("newsTable")
    private HomeRealmModel userEvents;



    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    @SerializedName("transUserEvents")
    private String dateJoin;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    @SerializedName("status")
    private String status;

    @SerializedName("id_user")
    private String id_user;
}
