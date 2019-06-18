package com.example.samuelseptiano.employeeselfservice.Model.ChatModel;

/**
 * Created by Samuel Septiano on 17,June,2019
 */
public class InboxChatModel {
    private String From, Message, To;
    int totalMessageNew;

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public int getTotalMessageNew() {
        return totalMessageNew;
    }

    public void setTotalMessageNew(int totalMessageNew) {
        this.totalMessageNew = totalMessageNew;
    }

    public InboxChatModel() {
    }

    public InboxChatModel(String from, String message, String to, int totalMessageNew) {
        From = from;
        Message = message;
        To = to;
        this.totalMessageNew = totalMessageNew;
    }
}
