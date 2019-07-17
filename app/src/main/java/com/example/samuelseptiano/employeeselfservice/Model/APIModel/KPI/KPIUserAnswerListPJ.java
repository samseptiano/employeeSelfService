package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

import java.util.List;

public class KPIUserAnswerListPJ {
    private String empNIK;
    private String semester;
    private String status1; //status atasan langsung
    private String status2; //status atasan taklangsung
    private String dept;
    private String skorAkhir;
    private String skorRating;
    private String KPIType;
    private List <KPIQuestionsPJ> kpiUserAnswerList;

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

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

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

    public KPIUserAnswerListPJ() {
    }
}
