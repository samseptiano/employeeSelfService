package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PASettingHeaderKualitatif implements Serializable {
    @SerializedName("tempcompname")
    private String tempCompName;
    @SerializedName("tempcompid")
    private String tempCompID;
    @SerializedName("tempcompsubyn")
    private String tempCompSubYN;

    public String getStatusDeployYN() {
        return statusDeployYN;
    }

    public void setStatusDeployYN(String statusDeployYN) {
        this.statusDeployYN = statusDeployYN;
    }

    @SerializedName("statusdeployyn")
    private String statusDeployYN;

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    @SerializedName("bobot")
    private String bobot;

    @SerializedName("pA_TempCompDetails")
    private List<PASettingDetailKualitatif> paSettingDetails;
    @SerializedName("pA_TempCompSettings")
    private List<PASettingSettingKualitatif> paSettingSettings;
    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    @SerializedName("empnik")
    private String empNIK;
    public String getTabDab() {
        return tabDab;
    }

    public void setTabDab(String tabDab) {
        this.tabDab = tabDab;
    }

    private String tabDab;

    public String getTempCompName() {
        return tempCompName;
    }

    public void setTempCompName(String tempCompName) {
        this.tempCompName = tempCompName;
    }

    public String getTempCompID() {
        return tempCompID;
    }

    public void setTempCompID(String tempCompID) {
        this.tempCompID = tempCompID;
    }

    public String getTempCompSubYN() {
        return tempCompSubYN;
    }

    public void setTempCompSubYN(String tempCompSubYN) {
        this.tempCompSubYN = tempCompSubYN;
    }

    public List<PASettingDetailKualitatif> getPaSettingDetails() {
        return paSettingDetails;
    }

    public void setPaSettingDetails(List<PASettingDetailKualitatif> paSettingDetails) {
        this.paSettingDetails = paSettingDetails;
    }

    public List<PASettingSettingKualitatif> getPaSettingSettings() {
        return paSettingSettings;
    }

    public void setPaSettingSettings(List<PASettingSettingKualitatif> paSettingSettings) {
        this.paSettingSettings = paSettingSettings;
    }

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

    public PASettingHeaderKualitatif() {
    }

}
