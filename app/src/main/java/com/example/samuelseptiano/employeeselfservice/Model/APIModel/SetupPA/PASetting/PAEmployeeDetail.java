package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PAEmployeeDetail {
    @SerializedName("bobot")
    private String bobot;
    @SerializedName("kpiid")
    private String kpiID;

    public String getCompid() {
        return compid;
    }

    public void setCompid(String compid) {
        this.compid = compid;
    }

    @SerializedName("compid")
    private String compid;
    @SerializedName("kpiname")
    private String kpiname;

    public String getKpiname() {
        return kpiname;
    }

    public void setKpiname(String kpiname) {
        this.kpiname = kpiname;
    }

    @SerializedName("paid")
    private String paid;
    @SerializedName("semester")
    private String semester;
    @SerializedName("patransgrade")
    List<KPIHint> hintList;

    public List<KPIHint> getHintList() {
        return hintList;
    }

    public void setHintList(List<KPIHint> hintList) {
        this.hintList = hintList;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getKpiID() {
        return kpiID;
    }

    public void setKpiID(String kpiID) {
        this.kpiID = kpiID;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public PAEmployeeDetail() {
    }
}
