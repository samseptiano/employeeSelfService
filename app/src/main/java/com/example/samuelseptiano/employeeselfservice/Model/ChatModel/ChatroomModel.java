package com.example.samuelseptiano.employeeselfservice.Model.ChatModel;

import java.util.List;

/**
 * Created by Samuel Septiano on 13,June,2019
 */
public class ChatroomModel {
    private String chatroomName;
    private List<ChatModel> lchat;

    public String getChatroomName() {
        return chatroomName;
    }

    public void setChatroomName(String chatroomName) {
        this.chatroomName = chatroomName;
    }

    public List<ChatModel> getLchat() {
        return lchat;
    }

    public void setLchat(List<ChatModel> lchat) {
        this.lchat = lchat;
    }

    public ChatroomModel(String chatroomName, List<ChatModel> lchat) {
        this.chatroomName = chatroomName;
        this.lchat = lchat;
    }

    public ChatroomModel() {
    }
}
