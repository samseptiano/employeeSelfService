package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

import java.util.List;

public class KPIUserAnswerList {
    private String empNIK;
    private String semester;
    private String status;
    private String dept;
    private String KPIType;
    private List <KPIQuestions> kpiUserAnswerList;
    private List <String> imgCapture;

    //kualititatif
    private String kelebihan;
    private String kekurangan;
    private String rencanaTindaklanjut;

    //kuantitatif
    private String pendukung;
    private String penghambat;
    private String catatanLain;

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

    public List<KPIQuestions> getKpiUserAnswerList() {
        return kpiUserAnswerList;
    }

    public void setKpiUserAnswerList(List<KPIQuestions> kpiUserAnswerList) {
        this.kpiUserAnswerList = kpiUserAnswerList;
    }

    public List<String> getImgCapture() {
        return imgCapture;
    }

    public void setImgCapture(List<String> imgCapture) {
        this.imgCapture = imgCapture;
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

    public String getPendukung() {
        return pendukung;
    }

    public void setPendukung(String pendukung) {
        this.pendukung = pendukung;
    }

    public String getPenghambat() {
        return penghambat;
    }

    public void setPenghambat(String penghambat) {
        this.penghambat = penghambat;
    }

    public String getCatatanLain() {
        return catatanLain;
    }

    public void setCatatanLain(String catatanLain) {
        this.catatanLain = catatanLain;
    }

    public KPIUserAnswerList(String empNIK, String semester, String status, String dept, String KPIType, List<KPIQuestions> kpiUserAnswerList, List<String> imgCapture, String kelebihan, String kekurangan, String rencanaTindaklanjut, String pendukung, String penghambat, String catatanLain) {
        this.empNIK = empNIK;
        this.semester = semester;
        this.status = status;
        this.dept = dept;
        this.KPIType = KPIType;
        this.kpiUserAnswerList = kpiUserAnswerList;
        this.imgCapture = imgCapture;
        this.kelebihan = kelebihan;
        this.kekurangan = kekurangan;
        this.rencanaTindaklanjut = rencanaTindaklanjut;
        this.pendukung = pendukung;
        this.penghambat = penghambat;
        this.catatanLain = catatanLain;
    }

    public KPIUserAnswerList() {
    }
}
