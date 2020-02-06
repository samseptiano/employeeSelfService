package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samuel Septiano on 23,August,2019
 */
public class KPIDashboard {

    @SerializedName("sudah")
    private String totalStatusApproved;
    @SerializedName("total")
    private String total;
    @SerializedName("belum")
    private String totalStatusNotApproved;

    public KPIDashboard() {
    }

    public String getTotalStatusApproved() {
        return totalStatusApproved;
    }

    public void setTotalStatusApproved(String totalStatusApproved) {
        this.totalStatusApproved = totalStatusApproved;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalStatusNotApproved() {
        return totalStatusNotApproved;
    }

    public void setTotalStatusNotApproved(String totalStatusNotApproved) {
        this.totalStatusNotApproved = totalStatusNotApproved;
    }
}
