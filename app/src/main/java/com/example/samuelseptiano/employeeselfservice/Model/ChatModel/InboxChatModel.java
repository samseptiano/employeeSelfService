package com.example.samuelseptiano.employeeselfservice.Model.ChatModel;

import java.util.List;

/**
 * Created by Samuel Septiano on 17,June,2019
 */
public class InboxChatModel {
    private String From;
    private List<String> kualitatif;
    private List<String> kuantitatif;
    private List<String> FromPhoto;
    private List<String> time;
    private List<String> Message;

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public List<String> getKualitatif() {
        return kualitatif;
    }

    public void setKualitatif(List<String> kualitatif) {
        this.kualitatif = kualitatif;
    }

    public List<String> getKuantitatif() {
        return kuantitatif;
    }

    public void setKuantitatif(List<String> kuantitatif) {
        this.kuantitatif = kuantitatif;
    }

    public List<String> getFromPhoto() {
        return FromPhoto;
    }

    public void setFromPhoto(List<String> fromPhoto) {
        FromPhoto = fromPhoto;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<String> getMessage() {
        return Message;
    }

    public void setMessage(List<String> message) {
        Message = message;
    }

    public InboxChatModel() {
    }
}
