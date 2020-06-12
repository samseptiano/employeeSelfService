package com.enseval.samuelseptiano.hcservice.Model.APIModel.User;

import com.google.gson.annotations.SerializedName;

public class userBodyParameter {
    @SerializedName("userId")
    private String userId;

    @SerializedName("password")
    private String password;

    @SerializedName("firebaseToken")
    private String firebaseToken;

    public userBodyParameter(String userId, String password, String firebaseToken) {
        this.userId = userId;
        this.password = password;
        this.firebaseToken = firebaseToken;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
