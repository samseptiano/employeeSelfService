package com.enseval.samuelseptiano.hcservice.Model.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventAbsentUserRealmModel extends RealmObject {


    @PrimaryKey
    private String id;
    private String eaid;
    private String empNIK;
    private String eventDate;
    private String eventID;
    private String eventName;
    private String eventType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEaid() {
        return eaid;
    }

    public void setEaid(String eaid) {
        this.eaid = eaid;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public EventAbsentUserRealmModel(String id, String eaid, String empNIK, String eventDate, String eventID, String eventName, String eventType) {
        this.id = id;
        this.eaid = eaid;
        this.empNIK = empNIK;
        this.eventDate = eventDate;
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventType = eventType;
    }

    public EventAbsentUserRealmModel() {
    }
}
