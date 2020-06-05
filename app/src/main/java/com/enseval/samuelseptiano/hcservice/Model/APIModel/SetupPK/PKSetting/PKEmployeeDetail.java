package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHint;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PKEmployeeDetail {
    @SerializedName("bobot")
    private String bobot;
       @SerializedName("kpiid")
    private String kpiID;

    public String getKategoriCode() {
        return kategoriCode;
    }

    public void setKategoriCode(String kategoriCode) {
        this.kategoriCode = kategoriCode;
    }

    @SerializedName("kategoriCode")
    private String kategoriCode;


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

    @SerializedName("pkid")
    private String pkid;
    @SerializedName("semester")
    private String semester;
    @SerializedName("pktransgrade")
    List<KPIHint> hintList;

    public String getEmpnik() {
        return empnik;
    }

    public void setEmpnik(String empnik) {
        this.empnik = empnik;
    }

    @SerializedName("empnik")
    private String empnik;

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

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public PKEmployeeDetail() {
    }
}
