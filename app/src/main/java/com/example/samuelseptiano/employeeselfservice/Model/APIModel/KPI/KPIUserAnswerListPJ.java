package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

import java.util.List;

public class KPIUserAnswerListPJ {
    private String empNIK;
    private String semester;
    private String status;
    private String dept;
    private String skorAkhir;
    private String skorRating;
    private String KPIType;
    private List <KPIQuestionsPJ> kpiUserAnswerList;

    public List<KPIQuestions> getKpiUserAnswerListPJ() {
        return kpiUserAnswerListPJ;
    }

    public void setKpiUserAnswerListPJ(List<KPIQuestions> kpiUserAnswerListPJ) {
        this.kpiUserAnswerListPJ = kpiUserAnswerListPJ;
    }

    private List <KPIQuestions> kpiUserAnswerListPJ; //semester 1

    public String getSkorAkhir() {
        return skorAkhir;
    }

    public void setSkorAkhir(String skorAkhir) {
        this.skorAkhir = skorAkhir;
    }

    public String getSkorRating() {
        return skorRating;
    }

    public void setSkorRating(String skorRating) {
        this.skorRating = skorRating;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getKPIType() {
        return KPIType;
    }

    public void setKPIType(String KPIType) {
        this.KPIType = KPIType;
    }

    public List<KPIQuestionsPJ> getKpiUserAnswerList() {
        return kpiUserAnswerList;
    }

    public void setKpiUserAnswerList(List<KPIQuestionsPJ> kpiUserAnswerList) {
        this.kpiUserAnswerList = kpiUserAnswerList;
    }


    public KPIUserAnswerListPJ(String empNIK, String semester, String status, String dept, String KPIType, List<KPIQuestionsPJ> kpiUserAnswerList) {
        this.empNIK = empNIK;
        this.semester = semester;
        this.status = status;
        this.dept = dept;
        this.KPIType = KPIType;
        this.kpiUserAnswerList = kpiUserAnswerList;
    }

    public KPIUserAnswerListPJ() {
    }
}
