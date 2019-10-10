package com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail;

import android.content.Context;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;

public interface RoutingHomeDetailInterface {
    void routingHomeDetail(String choice, Context context, String idEvent);
    void routingKPI(String choice, Context context, UserList userList, String type);
    void routingHome(String choice, Context context);
    void routingChat(String roomName,String friendName,String friendNik, Context context);
}
