package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting;

import com.google.gson.annotations.SerializedName;

public class PKSettingSetting {
    @SerializedName("jobttlname")
    private String jobtitleName;
    @SerializedName("orgname")
    private String orgName;
    @SerializedName("tempjobttlcode")
    private String tempJobTitleCode;
    @SerializedName("temporgcode")
    private String tempOrgCode;

    public String getJobtitleName() {
        return jobtitleName;
    }

    public void setJobtitleName(String jobtitleName) {
        this.jobtitleName = jobtitleName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTempJobTitleCode() {
        return tempJobTitleCode;
    }

    public void setTempJobTitleCode(String tempJobTitleCode) {
        this.tempJobTitleCode = tempJobTitleCode;
    }

    public String getTempOrgCode() {
        return tempOrgCode;
    }

    public void setTempOrgCode(String tempOrgCode) {
        this.tempOrgCode = tempOrgCode;
    }

    public PKSettingSetting() {
    }
}
