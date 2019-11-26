package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

public class KPIHint {
    @SerializedName("kpiGradeCode")
    private String kpiGradeCode;
    @SerializedName("kpiGradeID")
    private String kpiGradeID;
    @SerializedName("kpiGradeName")
    private String kpiGradeName;
    @SerializedName("kpiID")
    private String kpiID;

    public KPIHint() {
    }

    public String getKpiGradeCode() {
        return kpiGradeCode;
    }

    public void setKpiGradeCode(String kpiGradeCode) {
        this.kpiGradeCode = kpiGradeCode;
    }

    public String getKpiGradeID() {
        return kpiGradeID;
    }

    public void setKpiGradeID(String kpiGradeID) {
        this.kpiGradeID = kpiGradeID;
    }

    public String getKpiGradeName() {
        return kpiGradeName;
    }

    public void setKpiGradeName(String kpiGradeName) {
        this.kpiGradeName = kpiGradeName;
    }

    public String getKpiID() {
        return kpiID;
    }

    public void setKpiID(String kpiID) {
        this.kpiID = kpiID;
    }
}
