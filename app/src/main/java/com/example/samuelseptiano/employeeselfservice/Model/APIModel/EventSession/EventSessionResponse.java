package com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventSessionResponse {
    public EventSessionResponse(List<EventSession> eventSessions) {
        this.eventSessions = eventSessions;
    }

    public List<EventSession> getEventSessions() {
        return eventSessions;
    }

    public void setEventSessions(List<EventSession> eventSessions) {
        this.eventSessions = eventSessions;
    }

    public EventSessionResponse() {
    }

    @SerializedName("evtps")
    private List<EventSession> eventSessions;
}
