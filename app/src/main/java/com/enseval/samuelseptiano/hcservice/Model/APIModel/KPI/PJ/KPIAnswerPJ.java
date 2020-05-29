package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

public class KPIAnswerPJ {
    @SerializedName("PKID")
    private String PKID;
    @SerializedName("PAID")
    private String PAID;
    @SerializedName("CP")
    private String CP ;
    @SerializedName("KPIID")
    private String KPIID ;
    @SerializedName("COMPID")
    private String COMPID;
    @SerializedName("EMPNIK")
    private String EMPNIK ;
    @SerializedName("EVIDENCES")
    private String EVIDENCES;
    @SerializedName("KPITYPE")
    private String KPITYPE;

    public KPIAnswerPJ() {
    }

    public String getPKID() {
        return PKID;
    }

    public void setPKID(String PKID) {
        this.PKID = PKID;
    }

    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getKPIID() {
        return KPIID;
    }

    public void setKPIID(String KPIID) {
        this.KPIID = KPIID;
    }

    public String getCOMPID() {
        return COMPID;
    }

    public void setCOMPID(String COMPID) {
        this.COMPID = COMPID;
    }

    public String getEMPNIK() {
        return EMPNIK;
    }

    public void setEMPNIK(String EMPNIK) {
        this.EMPNIK = EMPNIK;
    }

    public String getEVIDENCES() {
        return EVIDENCES;
    }

    public void setEVIDENCES(String EVIDENCES) {
        this.EVIDENCES = EVIDENCES;
    }

    public String getKPITYPE() {
        return KPITYPE;
    }

    public void setKPITYPE(String KPITYPE) {
        this.KPITYPE = KPITYPE;
    }
}
