package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MKualitatif {
    @SerializedName("tempcompid")
    private String TEMPCOMPID;
    @SerializedName("compid")
    private String COMPID;
    @SerializedName("bobot")
    private String BOBOT;
    @SerializedName("semester")
    private String SEMESTER;
    @SerializedName("compname")
    private String KPINAME;
    @SerializedName("compdeskripsi")
    private String KPIPERSPECTIVECODE;
    @SerializedName("cr")
    private String CR;
    @SerializedName("pA_ViewTransGrades")
    private List<KPIHint> pA_ViewTransGrades;

    public String getTEMPCOMPID() {
        return TEMPCOMPID;
    }

    public void setTEMPCOMPID(String TEMPCOMPID) {
        this.TEMPCOMPID = TEMPCOMPID;
    }

    public String getCOMPID() {
        return COMPID;
    }

    public void setCOMPID(String COMPID) {
        this.COMPID = COMPID;
    }

    public String getBOBOT() {
        return BOBOT;
    }

    public void setBOBOT(String BOBOT) {
        this.BOBOT = BOBOT;
    }

    public String getSEMESTER() {
        return SEMESTER;
    }

    public void setSEMESTER(String SEMESTER) {
        this.SEMESTER = SEMESTER;
    }

    public String getKPINAME() {
        return KPINAME;
    }

    public void setKPINAME(String KPINAME) {
        this.KPINAME = KPINAME;
    }

    public String getKPIPERSPECTIVECODE() {
        return KPIPERSPECTIVECODE;
    }

    public void setKPIPERSPECTIVECODE(String KPIPERSPECTIVECODE) {
        this.KPIPERSPECTIVECODE = KPIPERSPECTIVECODE;
    }

    public String getCR() {
        return CR;
    }

    public void setCR(String CR) {
        this.CR = CR;
    }

    public List<KPIHint> getpA_ViewTransGrades() {
        return pA_ViewTransGrades;
    }

    public void setpA_ViewTransGrades(List<KPIHint> pA_ViewTransGrades) {
        this.pA_ViewTransGrades = pA_ViewTransGrades;
    }

    public MKualitatif() {
    }

}
