package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting;

import com.google.gson.annotations.SerializedName;

public class PkImportModel {
    @SerializedName("tempcompid")
    private String tempCompID;
    @SerializedName("kpitype")
    private String kpiType;
    @SerializedName("periode")
    private String periode;

    public String getEmpnik() {
        return empnik;
    }

    public void setEmpnik(String empnik) {
        this.empnik = empnik;
    }

    @SerializedName("empnik")
    private String empnik;
    public PkImportModel() {
    }

    public String getTempCompID() {
        return tempCompID;
    }

    public void setTempCompID(String tempCompID) {
        this.tempCompID = tempCompID;
    }

    public String getKpiType() {
        return kpiType;
    }

    public void setKpiType(String kpiType) {
        this.kpiType = kpiType;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
}
