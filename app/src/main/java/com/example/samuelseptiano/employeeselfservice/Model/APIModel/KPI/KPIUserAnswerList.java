package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

import java.util.List;

public class KPIUserAnswerList {
    private String empNIK;
    private String semester;
    private String status;
    private String dept;
    private String KPIType;
    private List <KPIQuestions> kpiUserAnswerList1;
    private List <KPIQuestions> kpiUserAnswerList2;
    private String kelebihan, kekurangan, rencanaTindaklanjut;

    public KPIUserAnswerList() {
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

    public List<KPIQuestions> getKpiUserAnswerList1() {
        return kpiUserAnswerList1;
    }

    public void setKpiUserAnswerList1(List<KPIQuestions> kpiUserAnswerList1) {
        this.kpiUserAnswerList1 = kpiUserAnswerList1;
    }

    public List<KPIQuestions> getKpiUserAnswerList2() {
        return kpiUserAnswerList2;
    }

    public void setKpiUserAnswerList2(List<KPIQuestions> kpiUserAnswerList2) {
        this.kpiUserAnswerList2 = kpiUserAnswerList2;
    }

    public String getKelebihan() {
        return kelebihan;
    }

    public void setKelebihan(String kelebihan) {
        this.kelebihan = kelebihan;
    }

    public String getKekurangan() {
        return kekurangan;
    }

    public void setKekurangan(String kekurangan) {
        this.kekurangan = kekurangan;
    }

    public String getRencanaTindaklanjut() {
        return rencanaTindaklanjut;
    }

    public void setRencanaTindaklanjut(String rencanaTindaklanjut) {
        this.rencanaTindaklanjut = rencanaTindaklanjut;
    }

    public KPIUserAnswerList(String empNIK, String semester, String status, String dept, String kpiType, List<KPIQuestions> kpiUserAnswerList1, List<KPIQuestions> kpiUserAnswerList2, String kelebihan, String kekurangan, String rencanaTindaklanjut) {
        this.empNIK = empNIK;
        this.semester = semester;
        this.status = status;
        this.dept = dept;
        KPIType = kpiType;
        this.kpiUserAnswerList1 = kpiUserAnswerList1;
        this.kpiUserAnswerList2 = kpiUserAnswerList2;
        this.kelebihan = kelebihan;
        this.kekurangan = kekurangan;
        this.rencanaTindaklanjut = rencanaTindaklanjut;
    }
}
