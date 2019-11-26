package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

public class KPIAnswer {
    @SerializedName("PAID")
    private String PAID;
    @SerializedName("CP")
    private String CP ;
    @SerializedName("SEMESTER")
    private String SEMESTER;
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

    public KPIAnswer() {
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

    public String getSEMESTER() {
        return SEMESTER;
    }

    public void setSEMESTER(String SEMESTER) {
        this.SEMESTER = SEMESTER;
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
