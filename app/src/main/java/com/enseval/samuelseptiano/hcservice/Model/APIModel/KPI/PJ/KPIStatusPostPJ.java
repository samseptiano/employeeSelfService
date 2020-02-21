package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samuel Septiano on 03,September,2019
 */
public class KPIStatusPostPJ {

    @SerializedName("STATUS")
    private String status;

    @SerializedName("APREMPNIK")
    private String empNik;

    @SerializedName("TRANSID")
    private String transId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpNik() {
        return empNik;
    }

    public void setEmpNik(String empNik) {
        this.empNik = empNik;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public KPIStatusPostPJ() {
    }

}
