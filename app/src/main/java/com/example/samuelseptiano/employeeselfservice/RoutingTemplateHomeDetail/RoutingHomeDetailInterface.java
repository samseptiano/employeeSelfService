package com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail;

import android.content.Context;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;

public interface RoutingHomeDetailInterface {
    void routingHomeDetail(String choice, Context context, String idEvent);
    void routingKPI(String choice, Context context, String empId, String type);
    void routingHome(String choice, Context context);
    void routingChat(String roomName,String friendName,String friendNik, Context context);
}
