package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KPIUserAnswerListPJ {

    @SerializedName("TRANSID")
    private String transID;

    public String getEmpNik() {
        return empNik;
    }

    public void setEmpNik(String empNik) {
        this.empNik = empNik;
    }

    @SerializedName("userempnik")
    private String empNik;

    @SerializedName("PK_TransDetails")
    private List <KPIQuestionsPostPJ> kpiUserAnswerList;


    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public List<KPIQuestionsPostPJ> getKpiUserAnswerList() {
        return kpiUserAnswerList;
    }

    public void setKpiUserAnswerList(List<KPIQuestionsPostPJ> kpiUserAnswerList) {
        this.kpiUserAnswerList = kpiUserAnswerList;
    }

    public KPIUserAnswerListPJ() {
    }
}
