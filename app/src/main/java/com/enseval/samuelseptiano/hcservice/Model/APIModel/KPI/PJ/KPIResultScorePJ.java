package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

public class KPIResultScorePJ {
    @SerializedName("kpi")
    private String KPIDesc;
    @SerializedName("cp")
    private String ratingScrore;
    @SerializedName("bobot")
    private String bobot;
    @SerializedName("hasil")
    private String hasil;
    @SerializedName("kpitype")
    private String kpitype;
    @SerializedName("resultstatus")
    private String resultstatus;

    public String getResultstatus() {
        return resultstatus;
    }

    public void setResultstatus(String resultstatus) {
        this.resultstatus = resultstatus;
    }

    public String getKpitype() {
        return kpitype;
    }

    public void setKpitype(String kpitype) {
        this.kpitype = kpitype;
    }

    public KPIResultScorePJ() {
    }

    public String getKPIDesc() {
        return KPIDesc;
    }

    public void setKPIDesc(String KPIDesc) {
        this.KPIDesc = KPIDesc;
    }

    public String getRatingScrore() {
        return ratingScrore;
    }

    public void setRatingScrore(String ratingScrore) {
        this.ratingScrore = ratingScrore;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }
}
