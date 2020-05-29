package com.enseval.samuelseptiano.hcservice.Model;

import com.google.gson.annotations.SerializedName;

public class WhatsappLog {
    @SerializedName("id")
    private String ID;
    @SerializedName("message")
    private String MESSAGE;
    @SerializedName("receivedno")
    private String RECEIVEDNO;
    @SerializedName("status")
    private String STATUS;
    @SerializedName("messagetime")
    private String MESSAGETIME;
    @SerializedName("messagesenttime")
    private String MESSAGESENTTIME;
    @SerializedName("descapi")
    private String DESCAPI;

    public WhatsappLog() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getRECEIVEDNO() {
        return RECEIVEDNO;
    }

    public void setRECEIVEDNO(String RECEIVEDNO) {
        this.RECEIVEDNO = RECEIVEDNO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getMESSAGETIME() {
        return MESSAGETIME;
    }

    public void setMESSAGETIME(String MESSAGETIME) {
        this.MESSAGETIME = MESSAGETIME;
    }

    public String getMESSAGESENTTIME() {
        return MESSAGESENTTIME;
    }

    public void setMESSAGESENTTIME(String MESSAGESENTTIME) {
        this.MESSAGESENTTIME = MESSAGESENTTIME;
    }

    public String getDESCAPI() {
        return DESCAPI;
    }

    public void setDESCAPI(String DESCAPI) {
        this.DESCAPI = DESCAPI;
    }
}
