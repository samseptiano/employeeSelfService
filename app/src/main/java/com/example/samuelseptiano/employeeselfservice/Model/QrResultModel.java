package com.example.samuelseptiano.employeeselfservice.Model;

public class QrResultModel {
    private String eventType;
    private String eventId;

    public QrResultModel(String eventType, String eventId) {
        this.eventType = eventType;
        eventId = eventId;
    }

    public QrResultModel() {
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        eventId = eventId;
    }
}
