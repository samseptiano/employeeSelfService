package com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionAnswerSurvey {

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public int getQuestionSeq() {
        return questionSeq;
    }

    public void setQuestionSeq(int questionSeq) {
        this.questionSeq = questionSeq;
    }

    public List<QuestionMultipleChoice> getSurveyAnswers() {
        return surveyAnswers;
    }

    public void setSurveyAnswers(List<QuestionMultipleChoice> surveyAnswers) {
        this.surveyAnswers = surveyAnswers;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
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

    public QuestionAnswerSurvey(String question, String questionCategory, String questiontype, int questionSeq, List<QuestionMultipleChoice> surveyAnswers, String surveyID, String surveyQuestionID, String fGActiveYN, String lastUpdate, String lastUpdateBy) {
        this.question = question;
        this.questionCategory = questionCategory;
        this.questiontype = questiontype;
        this.questionSeq = questionSeq;
        this.surveyAnswers = surveyAnswers;
        this.surveyID = surveyID;
        this.surveyQuestionID = surveyQuestionID;
        this.fGActiveYN = fGActiveYN;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public QuestionAnswerSurvey() {
    }


    @SerializedName("question")
    private String question;
    @SerializedName("questionCategory")
    private String questionCategory;
    @SerializedName("questionType")
    private String questiontype;
    @SerializedName("questionSeq")
    private int questionSeq;
    @SerializedName("surveyAnswers")
    private List <QuestionMultipleChoice> surveyAnswers;
    @SerializedName("surveyID")
    private String surveyID;
    @SerializedName("surveyQuestionID")
    private String surveyQuestionID;
    @SerializedName("fgActiveYN")
    private String fGActiveYN;
    @SerializedName("lastUpdate")
    private String lastUpdate;
    @SerializedName("lastUpdateBy")
    private String lastUpdateBy;

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int checkedId) {
        this.checkedId = checkedId;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    private int checkedId = -1;
    private int correctOption;
    private boolean isAnswered;
    private String choice;

}
