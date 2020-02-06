package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PASettingHeader implements Serializable {
    @SerializedName("tempkpiname")
    private String tempKPIName;
    @SerializedName("tempkpiid")
    private String tempKPIID;
    @SerializedName("statusdeployyn")
    private String statusDeployYN;
    @SerializedName("pA_TempKPIDetails")
    private List<PASettingDetail> paSettingDetails;
    @SerializedName("pA_TempKPISettings")
    private List<PASettingSetting> paSettingSettings;

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    @SerializedName("bobot")
    private String bobot;

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    @SerializedName("empnik")
    private String empNIK;

    @SerializedName("tempyear")
    private String year;

    private boolean isChecked;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public PASettingHeader() {
    }

    public String getTempKPIName() {
        return tempKPIName;
    }

    public void setTempKPIName(String tempKPIName) {
        this.tempKPIName = tempKPIName;
    }

    public String getTempKPIID() {
        return tempKPIID;
    }

    public void setTempKPIID(String tempKPIID) {
        this.tempKPIID = tempKPIID;
    }

    public String getStatusDeployYN() {
        return statusDeployYN;
    }

    public void setStatusDeployYN(String statusDeployYN) {
        this.statusDeployYN = statusDeployYN;
    }

    public List<PASettingDetail> getPaSettingDetails() {
        return paSettingDetails;
    }

    public void setPaSettingDetails(List<PASettingDetail> paSettingDetails) {
        this.paSettingDetails = paSettingDetails;
    }

    public List<PASettingSetting> getPaSettingSettings() {
        return paSettingSettings;
    }

    public void setPaSettingSettings(List<PASettingSetting> paSettingSettings) {
        this.paSettingSettings = paSettingSettings;
    }
}
