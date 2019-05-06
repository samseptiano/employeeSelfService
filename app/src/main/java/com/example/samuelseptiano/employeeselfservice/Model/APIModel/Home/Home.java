package com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home;

import com.example.samuelseptiano.employeeselfservice.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

public class Home extends BaseResponse {

    @SerializedName("eventID")
    private String eventId;
    @SerializedName("eventName")
    private String eventName;
    @SerializedName("eventType")
    private String eventType;
    @SerializedName("externalEventCode")
    private String externalEventCode;
    @SerializedName("eventDesc")
    private String eventDesc;
    @SerializedName("eventImage")
    private String eventImage;
    @SerializedName("fgHasPasscodeYN")
    private String fGHasPasscodeYN;
    @SerializedName("fgAllSurveyDoneYN")
    private String fGAllSurveyDoneYN;
    @SerializedName("passcode")
    private String passcode;
    @SerializedName("fgHasSurveyYN")
    private String fGHasSurveyYN;
    @SerializedName("surveyID")
    private String surveyId;
    @SerializedName("fgSurveyDoneYN")
    private String fGSurveyDoneYN;

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

    public String getfGAllSurveyDoneYN() {
        return fGAllSurveyDoneYN;
    }

    public void setfGAllSurveyDoneYN(String fGAllSurveyDoneYN) {
        this.fGAllSurveyDoneYN = fGAllSurveyDoneYN;
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

    public Home(String eventId, String eventName, String eventType, String externalEventCode, String eventDesc, String eventImage, String fGHasPasscodeYN, String fGAllSurveyDoneYN, String passcode, String fGHasSurveyYN, String surveyId, String fGSurveyDoneYN) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.externalEventCode = externalEventCode;
        this.eventDesc = eventDesc;
        this.eventImage = eventImage;
        this.fGHasPasscodeYN = fGHasPasscodeYN;
        this.fGAllSurveyDoneYN = fGAllSurveyDoneYN;
        this.passcode = passcode;
        this.fGHasSurveyYN = fGHasSurveyYN;
        this.surveyId = surveyId;
        this.fGSurveyDoneYN = fGSurveyDoneYN;
    }

    public Home() {
    }
}
