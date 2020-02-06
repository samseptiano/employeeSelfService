package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MKPI {
    @SerializedName("tempkpiid")
    private String TEMPKPIID;
    @SerializedName("kpiid")
    private String KPIID;
    @SerializedName("bobot")
    private String BOBOT;
    @SerializedName("semester")
    private String SEMESTER;
    @SerializedName("kpiname")
    private String KPINAME;
    @SerializedName("kpiperspectivecode")
    private String KPIPERSPECTIVECODE;
    @SerializedName("kpicr")
    private String KPICR;
    @SerializedName("kpiflagfocus")
    private String KPIFLAGFOCUS;
    @SerializedName("pA_ViewTransGrades")
    private List<KPIHint> pA_ViewTransGrades;

    public MKPI() {
    }

    public String getTEMPKPIID() {
        return TEMPKPIID;
    }

    public void setTEMPKPIID(String TEMPKPIID) {
        this.TEMPKPIID = TEMPKPIID;
    }

    public String getKPIID() {
        return KPIID;
    }

    public void setKPIID(String KPIID) {
        this.KPIID = KPIID;
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

    public String getKPICR() {
        return KPICR;
    }

    public void setKPICR(String KPICR) {
        this.KPICR = KPICR;
    }

    public String getKPIFLAGFOCUS() {
        return KPIFLAGFOCUS;
    }

    public void setKPIFLAGFOCUS(String KPIFLAGFOCUS) {
        this.KPIFLAGFOCUS = KPIFLAGFOCUS;
    }

    public List<KPIHint> getpA_ViewTransGrades() {
        return pA_ViewTransGrades;
    }

    public void setpA_ViewTransGrades(List<KPIHint> pA_ViewTransGrades) {
        this.pA_ViewTransGrades = pA_ViewTransGrades;
    }
}
