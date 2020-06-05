package com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal;

import com.google.gson.annotations.SerializedName;

public class ProcessDNModel {

    @SerializedName("dnid")
    private String dnID;
    @SerializedName("periode")
    private String periode;
    @SerializedName("groupid")
    private String groupID;
    @SerializedName("groupidname")
    private String groupIDName;

    @SerializedName("upduser")
    private String updUser;

    public ProcessDNModel() {
    }

    public String getDnID() {
        return dnID;
    }

    public void setDnID(String dnID) {
        this.dnID = dnID;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupIDName() {
        return groupIDName;
    }

    public void setGroupIDName(String groupIDName) {
        this.groupIDName = groupIDName;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }
}
