package com.enseval.samuelseptiano.hcservice.Model;

import java.util.List;

public class Questions {

    private long id;
    private String question, answer;
    private List<String>options;
    private int checkedId = -1;
    private int correctOption;
    private boolean isAnswered;
    private String QuestionType;
    private String choice;

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String questionType) {
        QuestionType = questionType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + getId() +
                ", question='" + getQuestion() + '\'' +
                ", checkedId=" + getCheckedId() +
                ", correctOption=" + getCorrectOption() +
                '}';
    }



}
