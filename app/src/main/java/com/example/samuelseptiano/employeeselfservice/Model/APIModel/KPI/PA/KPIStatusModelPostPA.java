package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

public class KPIStatusModelPostPA {
    @SerializedName("PAID")
    private String paid;
    @SerializedName("PASTATUS")
    private String paStatus;

    public KPIStatusModelPostPA() {
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPaStatus() {
        return paStatus;
    }

    public void setPaStatus(String paStatus) {
        this.paStatus = paStatus;
    }
}
