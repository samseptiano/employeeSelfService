package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DevPlanHeader implements Serializable {
    @SerializedName("devid")
    private String DEVID;
    @SerializedName("paid")
    private String PAID;
    @SerializedName("compid")
    private String COMPID;
    @SerializedName("compname")
    private String COMPNAME;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    private Boolean isChecked = false;

    public String getCOMPNAME() {
        return COMPNAME;
    }

    public void setCOMPNAME(String COMPNAME) {
        this.COMPNAME = COMPNAME;
    }


    @SerializedName("devPlanDetail")
    private List<DevPlanDetail> devPlanDetail;

    public String getDEVID() {
        return DEVID;
    }

    public void setDEVID(String DEVID) {
        this.DEVID = DEVID;
    }

    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    public String getCOMPID() {
        return COMPID;
    }

    public void setCOMPID(String COMPID) {
        this.COMPID = COMPID;
    }

    public List<DevPlanDetail> getDevPlanDetail() {
        return devPlanDetail;
    }

    public void setDevPlanDetail(List<DevPlanDetail> devPlanDetail) {
        this.devPlanDetail = devPlanDetail;
    }

    public DevPlanHeader() {
    }
}
