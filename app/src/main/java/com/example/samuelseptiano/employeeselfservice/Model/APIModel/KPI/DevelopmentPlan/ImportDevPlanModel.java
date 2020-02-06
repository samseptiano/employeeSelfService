package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan;

import com.google.gson.annotations.SerializedName;

public class ImportDevPlanModel {
    @SerializedName("PERIODE")
    private String periode;
    @SerializedName("PAID")
    private String paid;

    public ImportDevPlanModel() {
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}
