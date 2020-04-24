package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA;

import java.util.List;

public class KPIUserAnswerList {
    private String empNIK;
    private String semester;
    private String status1; //status atasan 1
    private String status2; //status atasan 2
    private String dept;
    private String KPIType;
    private String nilai, nilaiAkhir;
    private List <KPIQuestions> kpiUserAnswerList1; //semester 1
    private List <KPIQuestions> kpiUserAnswerList2; //semester 2
    private String kelebihan, kekurangan, rencanaTindaklanjut;

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

    public String getKPIType() {
        return KPIType;
    }

    public void setKPIType(String KPIType) {
        this.KPIType = KPIType;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getNilaiAkhir() {
        return nilaiAkhir;
    }

    public void setNilaiAkhir(String nilaiAkhir) {
        this.nilaiAkhir = nilaiAkhir;
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

    public KPIUserAnswerList() {
    }


}
