package com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionAnswerResponse {

    public List<QuestionAnswerSurvey> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswerSurvey> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public QuestionAnswerResponse(List<QuestionAnswerSurvey> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public QuestionAnswerResponse() {
    }

    @SerializedName("questionAnswers")
    private List <QuestionAnswerSurvey> questionAnswers;

}
