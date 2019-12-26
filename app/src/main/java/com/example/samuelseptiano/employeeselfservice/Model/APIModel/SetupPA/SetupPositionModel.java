package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA;

import com.google.gson.annotations.SerializedName;


public class SetupPositionModel {
    private String id;
    private String positionName;
    private String year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public SetupPositionModel() {
    }
}
