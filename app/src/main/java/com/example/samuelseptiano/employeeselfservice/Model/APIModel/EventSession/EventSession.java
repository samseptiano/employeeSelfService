package com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession;

import com.google.gson.annotations.SerializedName;

public class EventSession {
    @SerializedName("esid")
    private String esid;
    @SerializedName("eventID")
    private String EventID;
    @SerializedName("eventType")
    private String eventType;
    @SerializedName("instructorID")
    private String instructorID;
    @SerializedName("sessionID")
    private String sessionID;
    @SerializedName("sessionName")
    private String sessionName;
    @SerializedName("sessionPlace")
    private String sessionPlace;
    @SerializedName("instructorName")
    private String instructorName;
    @SerializedName("fgHasSurveyYN")
    private String fgHasSurveyYN;
    @SerializedName("fgSurveyDoneYN")
    private String fgSurveyDoneYN;
    @SerializedName("surveyID")
    private String surveyID;
    @SerializedName("surveyDoneDate")
    private String surveyDoneDate;
    @SerializedName("sessionDateStart")
    private String sessionDateStart;
    @SerializedName("sessionDateEnd")
    private String sessionDateEnd;
    @SerializedName("fileData")
    private String fileData;

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

    public String getFgHasSurveyYN() {
        return fgHasSurveyYN;
    }

    public void setFgHasSurveyYN(String fgHasSurveyYN) {
        this.fgHasSurveyYN = fgHasSurveyYN;
    }

    public String getFgSurveyDoneYN() {
        return fgSurveyDoneYN;
    }

    public void setFgSurveyDoneYN(String fgSurveyDoneYN) {
        this.fgSurveyDoneYN = fgSurveyDoneYN;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public String getSurveyDoneDate() {
        return surveyDoneDate;
    }

    public void setSurveyDoneDate(String surveyDoneDate) {
        this.surveyDoneDate = surveyDoneDate;
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

    public EventSession(String esid, String eventID, String eventType, String instructorID, String sessionID, String sessionName, String sessionPlace, String instructorName, String fgHasSurveyYN, String fgSurveyDoneYN, String surveyID, String surveyDoneDate, String sessionDateStart, String sessionDateEnd, String fileData, String fileType) {
        this.esid = esid;
        EventID = eventID;
        this.eventType = eventType;
        this.instructorID = instructorID;
        this.sessionID = sessionID;
        this.sessionName = sessionName;
        this.sessionPlace = sessionPlace;
        this.instructorName = instructorName;
        this.fgHasSurveyYN = fgHasSurveyYN;
        this.fgSurveyDoneYN = fgSurveyDoneYN;
        this.surveyID = surveyID;
        this.surveyDoneDate = surveyDoneDate;
        this.sessionDateStart = sessionDateStart;
        this.sessionDateEnd = sessionDateEnd;
        this.fileData = fileData;
        this.fileType = fileType;
    }

    @SerializedName("fileType")
    private String fileType;


    public EventSession() {
    }
}
