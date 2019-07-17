package com.example.samuelseptiano.employeeselfservice.Model.ChatModel;

import java.util.List;

/**
 * Created by Samuel Septiano on 17,June,2019
 */
public class InboxChatModel {
    private String From;
    private List<String> time;
    private List<String> Message;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
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
