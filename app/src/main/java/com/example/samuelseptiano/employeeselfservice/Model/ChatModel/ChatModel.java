package com.example.samuelseptiano.employeeselfservice.Model.ChatModel;

/**
 * Created by Samuel Septiano on 12,June,2019
 */
public class ChatModel {
    private String user;
    private String message;
    private String sentTime;
    private String status1,status2;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public ChatModel() {
    }

    public ChatModel(String user, String message, String sentTime, String status1, String status2) {
        this.user = user;
        this.message = message;
        this.sentTime = sentTime;
        this.status1 = status1;
        this.status2 = status2;
    }
}
