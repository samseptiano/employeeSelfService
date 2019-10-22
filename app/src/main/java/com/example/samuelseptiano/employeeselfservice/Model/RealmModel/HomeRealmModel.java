package com.example.samuelseptiano.employeeselfservice.Model.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class HomeRealmModel extends RealmObject {

    public HomeRealmModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getExternalEventCode() {
        return externalEventCode;
    }

    public void setExternalEventCode(String externalEventCode) {
        this.externalEventCode = externalEventCode;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getfGHasPasscodeYN() {
        return fGHasPasscodeYN;
    }

    public void setfGHasPasscodeYN(String fGHasPasscodeYN) {
        this.fGHasPasscodeYN = fGHasPasscodeYN;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getfGHasSurveyYN() {
        return fGHasSurveyYN;
    }

    public void setfGHasSurveyYN(String fGHasSurveyYN) {
        this.fGHasSurveyYN = fGHasSurveyYN;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getfGSurveyDoneYN() {
        return fGSurveyDoneYN;
    }

    public void setfGSurveyDoneYN(String fGSurveyDoneYN) {
        this.fGSurveyDoneYN = fGSurveyDoneYN;
    }


    @PrimaryKey
    private String id;
    private String eventId;
    private String eventName;
    private String eventType;
    private String externalEventCode;
    private String eventDesc;
    private String eventImage;
    private String fGHasPasscodeYN;
    private String passcode;
    private String fGHasSurveyYN;
    private String surveyId;
    private String fGSurveyDoneYN;

    public String getfGAbsenYN() {
        return fGAbsenYN;
    }

    public void setfGAbsenYN(String fGAbsenYN) {
        this.fGAbsenYN = fGAbsenYN;
    }

    private String fGAbsenYN;

    public HomeRealmModel(String id, String eventId, String eventName, String eventType, String externalEventCode, String eventDesc, String eventImage, String fGHasPasscodeYN, String passcode, String fGHasSurveyYN, String surveyId, String fGSurveyDoneYN, String fGAbsenYN) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.externalEventCode = externalEventCode;
        this.eventDesc = eventDesc;
        this.eventImage = eventImage;
        this.fGHasPasscodeYN = fGHasPasscodeYN;
        this.passcode = passcode;
        this.fGHasSurveyYN = fGHasSurveyYN;
        this.surveyId = surveyId;
        this.fGSurveyDoneYN = fGSurveyDoneYN;
        this.fGAbsenYN = fGAbsenYN;
    }
}
