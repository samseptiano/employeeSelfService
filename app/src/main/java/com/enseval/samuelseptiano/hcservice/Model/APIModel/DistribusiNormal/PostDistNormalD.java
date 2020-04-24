package com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal;

import com.google.gson.annotations.SerializedName;

public class PostDistNormalD {
    @SerializedName("dnid")
    private String dnID;
    @SerializedName("empnik")
    private String empNIK;
    @SerializedName("dnadj")
    private String dnAdj;
    @SerializedName("alasanadj")
    private String alasanAdjustment;
    @SerializedName("upduser")
    private String updUser;

    public String getDnID() {
        return dnID;
    }

    public void setDnID(String dnID) {
        this.dnID = dnID;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getDnAdj() {
        return dnAdj;
    }

    public void setDnAdj(String dnAdj) {
        this.dnAdj = dnAdj;
    }

    public String getAlasanAdjustment() {
        return alasanAdjustment;
    }

    public void setAlasanAdjustment(String alasanAdjustment) {
        this.alasanAdjustment = alasanAdjustment;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public PostDistNormalD() {
    }


}
