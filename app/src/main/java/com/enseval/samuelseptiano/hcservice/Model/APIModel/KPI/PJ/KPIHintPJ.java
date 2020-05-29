package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samuel Septiano on 30,August,2019
 */
public class KPIHintPJ {

    @SerializedName("kpiGradeCode")
    private String kpiGradeCode;
    @SerializedName("kpiGradeID")
    private String kpiGradeID;
    @SerializedName("kpiGradeName")
    private String kpiGradeName;
    @SerializedName("kpiID")
    private String kpiID;

    public KPIHintPJ() {
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
