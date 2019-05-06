package com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey;

import com.google.gson.annotations.SerializedName;

public class UserAnswer {
    @SerializedName("EventId")
    private String EventId;
    @SerializedName("EmpNIK")
    private String EmpNIK;
    @SerializedName("SurveyID")
    private String SurveyID;
    @SerializedName("QuestionID")
    private String QuestionID;
    @SerializedName("sessionId")
    private String SessionId;
    @SerializedName("AnswerID")
    private String AnswerID;
    @SerializedName("AnswerEssay")
    private String AnswerEssay;
    @SerializedName("LastUpdateBy")
    private String LastUpdateBy;

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEmpNIK() {
        return EmpNIK;
    }

    public void setEmpNIK(String empNIK) {
        EmpNIK = empNIK;
    }

    public String getSurveyID() {
        return SurveyID;
    }

    public void setSurveyID(String surveyID) {
        SurveyID = surveyID;
    }

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(String answerID) {
        AnswerID = answerID;
    }

    public String getAnswerEssay() {
        return AnswerEssay;
    }

    public void setAnswerEssay(String answerEssay) {
        AnswerEssay = answerEssay;
    }

    public String getLastUpdateBy() {
        return LastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        LastUpdateBy = lastUpdateBy;
    }

    public UserAnswer(String eventId, String empNIK, String surveyID, String questionID, String sessionId, String answerID, String answerEssay, String lastUpdateBy) {
        EventId = eventId;
        EmpNIK = empNIK;
        SurveyID = surveyID;
        QuestionID = questionID;
        SessionId = sessionId;
        AnswerID = answerID;
        AnswerEssay = answerEssay;
        LastUpdateBy = lastUpdateBy;
    }

    public UserAnswer() {
    }


}
