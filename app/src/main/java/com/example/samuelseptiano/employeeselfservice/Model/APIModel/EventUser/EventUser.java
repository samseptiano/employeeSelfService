package com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventUser;

import com.google.gson.annotations.SerializedName;

public class EventUser {
    @SerializedName("epid")
    private int EPID;
    @SerializedName("empNIK")
    private String empNIK;
    @SerializedName("eventID")
    private String eventID;
    @SerializedName("fgActiveYN")
    private String fGActiveYN;
    @SerializedName("lastUpdate")
    private String lastUpdate;
    @SerializedName("lastUpdateBy")
    private String lastUpdateBy;

    public int getEPID() {
        return EPID;
    }

    public void setEPID(int EPID) {
        this.EPID = EPID;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getfGActiveYN() {
        return fGActiveYN;
    }

    public void setfGActiveYN(String fGActiveYN) {
        this.fGActiveYN = fGActiveYN;
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

    public EventUser(int EPID, String empNIK, String eventID, String fGActiveYN, String lastUpdate, String lastUpdateBy) {
        this.EPID = EPID;
        this.empNIK = empNIK;
        this.eventID = eventID;
        this.fGActiveYN = fGActiveYN;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public EventUser() {
    }
}
