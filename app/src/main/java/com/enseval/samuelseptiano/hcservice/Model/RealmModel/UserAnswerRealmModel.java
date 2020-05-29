package com.enseval.samuelseptiano.hcservice.Model.RealmModel;


import io.realm.RealmObject;

public class UserAnswerRealmModel extends RealmObject {


    private String id;
    private String EventId;
    private String EmpNIK;
    private String SurveyID;
    private String QuestionID;
    private String AnswerID;
    private String AnswerEssay;
    private String LastUpdateBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public UserAnswerRealmModel(String id, String eventId, String empNIK, String surveyID, String questionID, String answerID, String answerEssay, String lastUpdateBy) {
        this.id = id;
        EventId = eventId;
        EmpNIK = empNIK;
        SurveyID = surveyID;
        QuestionID = questionID;
        AnswerID = answerID;
        AnswerEssay = answerEssay;
        LastUpdateBy = lastUpdateBy;
    }

    public UserAnswerRealmModel() {
    }
}
