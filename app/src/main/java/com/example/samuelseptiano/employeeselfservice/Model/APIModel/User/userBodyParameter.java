package com.example.samuelseptiano.employeeselfservice.Model.APIModel.User;

import com.google.gson.annotations.SerializedName;

public class userBodyParameter {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public userBodyParameter(String userId) {
        this.userId = userId;
    }

    @SerializedName("userId")
    private String userId;
}
