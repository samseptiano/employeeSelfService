package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

public class DevPlanDetail {
    @SerializedName("devid")
    private String DEVID;
    @SerializedName("devplanmethodid")
    private String DEVPLANMETHODID;
    @SerializedName("devplanactivities")
    private String DEVPLANACTIVITIES;
    @SerializedName("devplankpi")
    private String DEVPLANKPI;
    @SerializedName("devplanduedate")
    private String DEVPLANDUEDATE;
    @SerializedName("devplanmentor")
    private String DEVPLANMENTOR;
    @SerializedName("devplanachievement")
    private String DEVPLANACHIEVEMENT;
    @SerializedName("devplanstatus")
    private String DEVPLANSTATUS;


    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    @SerializedName("paid")
    private String PAID;
    public String getDevplanMethodDesk() {
        return devplanMethodDesk;
    }

    public void setDevplanMethodDesk(String devplanMethodDesk) {
        this.devplanMethodDesk = devplanMethodDesk;
    }

    @SerializedName("devplanmethoddesk")
    private String devplanMethodDesk;

    public boolean isCheckedCb() {
        return isCheckedCb;
    }

    public void setCheckedCb(boolean checkedCb) {
        isCheckedCb = checkedCb;
    }

    private boolean isCheckedCb;

    public String getDEVID() {
        return DEVID;
    }

    public void setDEVID(String DEVID) {
        this.DEVID = DEVID;
    }

    public String getDEVPLANMETHODID() {
        return DEVPLANMETHODID;
    }

    public void setDEVPLANMETHODID(String DEVPLANMETHODID) {
        this.DEVPLANMETHODID = DEVPLANMETHODID;
    }

    public String getDEVPLANACTIVITIES() {
        return DEVPLANACTIVITIES;
    }

    public void setDEVPLANACTIVITIES(String DEVPLANACTIVITIES) {
        this.DEVPLANACTIVITIES = DEVPLANACTIVITIES;
    }

    public String getDEVPLANKPI() {
        return DEVPLANKPI;
    }

    public void setDEVPLANKPI(String DEVPLANKPI) {
        this.DEVPLANKPI = DEVPLANKPI;
    }

    public String getDEVPLANDUEDATE() {
        return DEVPLANDUEDATE;
    }

    public void setDEVPLANDUEDATE(String DEVPLANDUEDATE) {
        this.DEVPLANDUEDATE = DEVPLANDUEDATE;
    }

    public String getDEVPLANMENTOR() {
        return DEVPLANMENTOR;
    }

    public void setDEVPLANMENTOR(String DEVPLANMENTOR) {
        this.DEVPLANMENTOR = DEVPLANMENTOR;
    }

    public String getDEVPLANACHIEVEMENT() {
        return DEVPLANACHIEVEMENT;
    }

    public void setDEVPLANACHIEVEMENT(String DEVPLANACHIEVEMENT) {
        this.DEVPLANACHIEVEMENT = DEVPLANACHIEVEMENT;
    }

    public String getDEVPLANSTATUS() {
        return DEVPLANSTATUS;
    }

    public void setDEVPLANSTATUS(String DEVPLANSTATUS) {
        this.DEVPLANSTATUS = DEVPLANSTATUS;
    }

    public DevPlanDetail() {
    }
}
