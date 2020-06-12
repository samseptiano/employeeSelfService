package com.enseval.samuelseptiano.hcservice.Model;

public class QrResultModel {
    private String eventType;
    private String eventId;

    public QrResultModel(String eventType, String eventId) {
        this.eventType = eventType;
        this.eventId = eventId;
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
        this.eventId = eventId;
    }
}
