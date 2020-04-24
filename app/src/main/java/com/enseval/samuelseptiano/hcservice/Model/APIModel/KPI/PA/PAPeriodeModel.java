package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

public class PAPeriodeModel {
    @SerializedName("id")
    private String id;
    @SerializedName("tahun")
    private String tahun;
    @SerializedName("semester")
    private String semester;

    public String getFgDefaultYN() {
        return fgDefaultYN;
    }

    public void setFgDefaultYN(String fgDefaultYN) {
        this.fgDefaultYN = fgDefaultYN;
    }

    @SerializedName("fgdefaultyn")
    private String fgDefaultYN;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public PAPeriodeModel() {
    }
}
