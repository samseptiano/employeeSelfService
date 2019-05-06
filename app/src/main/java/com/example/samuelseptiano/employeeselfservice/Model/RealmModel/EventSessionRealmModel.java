package com.example.samuelseptiano.employeeselfservice.Model.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventSessionRealmModel extends RealmObject {

    @PrimaryKey
    private  String id;
    private String esid;
    private String EventID;
    private String eventType;
    private String instructorID;
    private String sessionID;
    private String sessionName;
    private String sessionPlace;
    private String instructorName;
    private String surveyID;
    private String sessionDateStart;
    private String sessionDateEnd;
    private String fileData;
    private String fileType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEsid() {
        return esid;
    }

    public void setEsid(String esid) {
        this.esid = esid;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionPlace() {
        return sessionPlace;
    }

    public void setSessionPlace(String sessionPlace) {
        this.sessionPlace = sessionPlace;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public String getSessionDateStart() {
        return sessionDateStart;
    }

    public void setSessionDateStart(String sessionDateStart) {
        this.sessionDateStart = sessionDateStart;
    }

    public String getSessionDateEnd() {
        return sessionDateEnd;
    }

    public void setSessionDateEnd(String sessionDateEnd) {
        this.sessionDateEnd = sessionDateEnd;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public EventSessionRealmModel(String id, String esid, String eventID, String eventType, String instructorID, String sessionID, String sessionName, String sessionPlace, String instructorName, String surveyID, String sessionDateStart, String sessionDateEnd, String fileData, String fileType) {
        this.id = id;
        this.esid = esid;
        EventID = eventID;
        this.eventType = eventType;
        this.instructorID = instructorID;
        this.sessionID = sessionID;
        this.sessionName = sessionName;
        this.sessionPlace = sessionPlace;
        this.instructorName = instructorName;
        this.surveyID = surveyID;
        this.sessionDateStart = sessionDateStart;
        this.sessionDateEnd = sessionDateEnd;
        this.fileData = fileData;
        this.fileType = fileType;
    }

    public EventSessionRealmModel() {
    }
}
