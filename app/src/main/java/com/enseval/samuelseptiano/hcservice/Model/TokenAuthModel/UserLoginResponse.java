package com.enseval.samuelseptiano.hcservice.Model.TokenAuthModel;

public class UserLoginResponse {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
