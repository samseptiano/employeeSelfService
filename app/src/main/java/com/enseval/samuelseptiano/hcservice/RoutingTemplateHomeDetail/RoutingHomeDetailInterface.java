package com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail;

import android.content.Context;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.UserList;

import java.util.List;

public interface RoutingHomeDetailInterface {
    void routingHomeDetail(String choice, Context context, String idEvent);

    void routingKPI(String choice, Context context, UserList userList, String type, String tahun);

    void routingHome(String choice, Context context);

    void routingChat(String roomName, String friendName, String friendNik, String semester, String tahun, String kualitatif, String kuantitatif, Context context, List<String> KPIItems, String friendBranchName, String friendDept,String friendJobTitle, String foto, String from);
}