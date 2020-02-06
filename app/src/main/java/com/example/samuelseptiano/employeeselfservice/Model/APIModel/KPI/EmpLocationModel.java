package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

import com.google.gson.annotations.SerializedName;

public class EmpLocationModel {
    @SerializedName("locationcode")
    private String locationcode;

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    @SerializedName("locationname")
    private String locationname;

    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }

    public EmpLocationModel() {
    }
}
