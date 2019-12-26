package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KPIAnswerList {

    public KPIAnswerList() {
    }


    @SerializedName("STRENGTH")
    private String STRENGTH;
    @SerializedName("PAID")
    private String PAID;
    @SerializedName("STATUS")
    private String STATUS;
    @SerializedName("NIKBAWAHAN")
    private String NIKBAWAHAN;
    @SerializedName("lTransDetail")
    private List<KPIAnswer> kpiAnswerList;

    public List<DevPlanHeader> getDevPlanHeaderList() {
        return devPlanHeaderList;
    }

    public void setDevPlanHeaderList(List<DevPlanHeader> devPlanHeaderList) {
        this.devPlanHeaderList = devPlanHeaderList;
    }

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

    public List<KPIAnswer> getKpiAnswerList() {
        return kpiAnswerList;
    }

    public void setKpiAnswerList(List<KPIAnswer> kpiAnswerList) {
        this.kpiAnswerList = kpiAnswerList;
    }
}
