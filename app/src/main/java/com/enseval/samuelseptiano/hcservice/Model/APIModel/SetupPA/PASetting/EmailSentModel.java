package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmailSentModel {

    @SerializedName("senderemail")
    public String SENDEREMAIL;
    @SerializedName("sendernik")
    public String SENDERNIK;
    @SerializedName("sendername")
    public String SENDERNAME;
    @SerializedName("receiveremail")
    public String RECEIVEREMAIL;
    @SerializedName("receivernik")
    public String RECEIVERNIK;
    @SerializedName("receivername")
    public String RECEIVERNAME;
    @SerializedName("subject")
    public String SUBJECT;
    @SerializedName("message")
    public String MESSAGE;
    @SerializedName("cc")
    public List<String> CC;
    @SerializedName("bcc")
    public List<String> BCC;
    @SerializedName("mailtype")
    public String MAILTYPE;


}
