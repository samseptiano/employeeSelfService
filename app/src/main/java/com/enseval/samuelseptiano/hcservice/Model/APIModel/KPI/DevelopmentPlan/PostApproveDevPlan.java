package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.DevelopmentPlan;

import com.google.gson.annotations.SerializedName;

public class PostApproveDevPlan {
    @SerializedName("PAID")
    private String PAID;
    @SerializedName("APPRSEQ")
    private String APPRSEQ;
    @SerializedName("EMPNIK")
    private String EMPNIK;

    public PostApproveDevPlan() {
    }

    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    public String getAPPRSEQ() {
        return APPRSEQ;
    }

    public void setAPPRSEQ(String APPRSEQ) {
        this.APPRSEQ = APPRSEQ;
    }

    public String getEMPNIK() {
        return EMPNIK;
    }

    public void setEMPNIK(String EMPNIK) {
        this.EMPNIK = EMPNIK;
    }
}
