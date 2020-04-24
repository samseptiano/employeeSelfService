package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI;

import com.google.gson.annotations.SerializedName;

public class EmpOrgModel {
    @SerializedName("orgcode")
    private String orgCode;
    @SerializedName("orgname")
    private String orgName;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public EmpOrgModel() {
    }
}
