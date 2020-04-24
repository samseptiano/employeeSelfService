package com.enseval.samuelseptiano.hcservice.Model.APIModel.EventUser;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventUserResponse {
    public List<EventUser> getEventUsers() {
        return eventUsers;
    }

    public void setEventUsers(List<EventUser> eventUsers) {
        this.eventUsers = eventUsers;
    }

    @SerializedName("evtps")
    private List<EventUser> eventUsers;
}
