package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.DevPlanHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIAnswer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KPIAnswerListPK {

    public KPIAnswerListPK() {
    }


    @SerializedName("STRENGTH")
    private String STRENGTH;
    @SerializedName("PAID")
    private String PAID;
    @SerializedName("PKID")
    private String PKID;
    @SerializedName("STATUS")
    private String STATUS;
    @SerializedName("NIKBAWAHAN")
    private String NIKBAWAHAN;
    @SerializedName("lTransDetail")
    private List<KPIAnswerPJ> kpiAnswerList;


    @SerializedName("CATATAN")
    private String CATATAN;

    public String getCATATAN() {
        return CATATAN;
    }

    public void setCATATAN(String CATATAN) {
        this.CATATAN = CATATAN;
    }

    public String getUpdUser() {
        return UpdUser;
    }

    public void setUpdUser(String updUser) {
        UpdUser = updUser;
    }

    @SerializedName("UPDUSER")
    private String UpdUser;

    @SerializedName("lDevPlanHeader")
    private List<DevPlanHeader> devPlanHeaderList;

    public String getSTRENGTH() {
        return STRENGTH;
    }

    public void setSTRENGTH(String STRENGTH) {
        this.STRENGTH = STRENGTH;
    }

    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    public String getPKID() {
        return PKID;
    }

    public void setPKID(String PKID) {
        this.PKID = PKID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getNIKBAWAHAN() {
        return NIKBAWAHAN;
    }

    public void setNIKBAWAHAN(String NIKBAWAHAN) {
        this.NIKBAWAHAN = NIKBAWAHAN;
    }

    public List<KPIAnswerPJ> getKpiAnswerList() {
        return kpiAnswerList;
    }

    public void setKpiAnswerList(List<KPIAnswerPJ> kpiAnswerList) {
        this.kpiAnswerList = kpiAnswerList;
    }

    public List<DevPlanHeader> getDevPlanHeaderList() {
        return devPlanHeaderList;
    }

    public void setDevPlanHeaderList(List<DevPlanHeader> devPlanHeaderList) {
        this.devPlanHeaderList = devPlanHeaderList;
    }
}
