package com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey;

import com.google.gson.annotations.SerializedName;

public class QuestionMultipleChoice {


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSurveyAnswerID() {
        return surveyAnswerID;
    }

    public void setSurveyAnswerID(String surveyAnswerID) {
        this.surveyAnswerID = surveyAnswerID;
    }

    public String getSurveyQuestionID() {
        return surveyQuestionID;
    }

    public void setSurveyQuestionID(String surveyQuestionID) {
        this.surveyQuestionID = surveyQuestionID;
    }

    public String getfGActiveYN() {
        return fGActiveYN;
    }

    public void setfGActiveYN(String fGActiveYN) {
        this.fGActiveYN = fGActiveYN;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public QuestionMultipleChoice() {
    }

    public QuestionMultipleChoice(String answer, String surveyAnswerID, String surveyQuestionID, String fGActiveYN, String lastUpdate, String lastUpdateBy) {
        this.answer = answer;
        this.surveyAnswerID = surveyAnswerID;
        this.surveyQuestionID = surveyQuestionID;
        this.fGActiveYN = fGActiveYN;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    @SerializedName("answer")
    private String answer;
    @SerializedName("surveyAnswerID")
    private String surveyAnswerID;
    @SerializedName("surveyQuestionID")
    private String surveyQuestionID;
    @SerializedName("fgActiveYN")
    private String fGActiveYN;
    @SerializedName("lastUpdate")
    private String lastUpdate;
    @SerializedName("lastUpdateBy")
    private String lastUpdateBy;


}
