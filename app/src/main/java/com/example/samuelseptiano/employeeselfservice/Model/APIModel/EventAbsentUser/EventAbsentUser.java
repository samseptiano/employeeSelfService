package com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser;

import com.google.gson.annotations.SerializedName;

public class EventAbsentUser {
    @SerializedName("EAID")
    private String eaid;
    @SerializedName("empNIK")
    private String empNIK;
    @SerializedName("eventDate")
    private String eventDate;
    @SerializedName("eventType")
    private String eventType;
    @SerializedName("eventID")
    private String EventID;
    @SerializedName("eventName")
    private String EventName;
    @SerializedName("fgActiveYN")
    private String fgActiveYN;
    @SerializedName("lastUpdate")
    private String lastUpdate;
    @SerializedName("lastUpdateBy")
    private String lastUpdateBy;
    @SerializedName("statusHadirYN")
    private String statusHadirYN;

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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getFgActiveYN() {
        return fgActiveYN;
    }

    public void setFgActiveYN(String fgActiveYN) {
        this.fgActiveYN = fgActiveYN;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getStatusHadirYN() {
        return statusHadirYN;
    }

    public void setStatusHadirYN(String statusHadirYN) {
        this.statusHadirYN = statusHadirYN;
    }

    public EventAbsentUser(String eaid, String empNIK, String eventDate, String eventType, String eventID, String eventName, String fgActiveYN, String lastUpdate, String lastUpdateBy, String statusHadirYN) {
        this.eaid = eaid;
        this.empNIK = empNIK;
        this.eventDate = eventDate;
        this.eventType = eventType;
        EventID = eventID;
        EventName = eventName;
        this.fgActiveYN = fgActiveYN;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.statusHadirYN = statusHadirYN;
    }

    public EventAbsentUser() {
    }
}
