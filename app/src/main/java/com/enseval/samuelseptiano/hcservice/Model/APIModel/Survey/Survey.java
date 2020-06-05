package com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey;

import com.google.gson.annotations.SerializedName;

public class Survey {
    @SerializedName("surveyBobot")
    private String surveyBobot;
    @SerializedName("surveyDateStart")
    private String surveyDateStart;
    @SerializedName("surveyDateEnd")
    private String surveyDateEnd;
    @SerializedName("surveyID")
    private String surveyID;
    @SerializedName("surveyName")
    private String surveyName;

    public Survey(String surveyBobot, String surveyDateStart, String surveyDateEnd, String surveyID, String surveyName) {
        this.surveyBobot = surveyBobot;
        this.surveyDateStart = surveyDateStart;
        this.surveyDateEnd = surveyDateEnd;
        this.surveyID = surveyID;
        this.surveyName = surveyName;
    }

    public Survey() {
    }

    public String getSurveyBobot() {
        return surveyBobot;
    }

    public void setSurveyBobot(String surveyBobot) {
        this.surveyBobot = surveyBobot;
    }

    public String getSurveyDateStart() {
        return surveyDateStart;
    }

    public void setSurveyDateStart(String surveyDateStart) {
        this.surveyDateStart = surveyDateStart;
    }

    public String getSurveyDateEnd() {
        return surveyDateEnd;
    }

    public void setSurveyDateEnd(String surveyDateEnd) {
        this.surveyDateEnd = surveyDateEnd;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }
}
