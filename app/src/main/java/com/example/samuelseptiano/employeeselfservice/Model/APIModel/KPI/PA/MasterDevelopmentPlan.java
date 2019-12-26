package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

public class MasterDevelopmentPlan {
    @SerializedName("devplanmethodid")
    private String devPlanId;
    @SerializedName("devplanmethoddesk")
    private String devplanDesc;

    public MasterDevelopmentPlan() {
    }

    public String getDevPlanId() {
        return devPlanId;
    }

    public void setDevPlanId(String devPlanId) {
        this.devPlanId = devPlanId;
    }

    public String getDevplanDesc() {
        return devplanDesc;
    }

    public void setDevplanDesc(String devplanDesc) {
        this.devplanDesc = devplanDesc;
    }
}
