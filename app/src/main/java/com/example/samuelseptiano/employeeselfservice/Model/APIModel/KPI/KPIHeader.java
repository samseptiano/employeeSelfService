package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

import java.util.List;

public class KPIHeader {
    private String tahun;
    private String semester;
    private String bobot;
    private String status;
    private String atasanLangsung;
    private String bawahanLangsung;
    private String empName;
    private String NIK;
    private String dept;
    private List <KPIQuestions> kpiQuestionsList;

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAtasanLangsung() {
        return atasanLangsung;
    }

    public void setAtasanLangsung(String atasanLangsung) {
        this.atasanLangsung = atasanLangsung;
    }

    public String getBawahanLangsung() {
        return bawahanLangsung;
    }

    public void setBawahanLangsung(String bawahanLangsung) {
        this.bawahanLangsung = bawahanLangsung;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public List<KPIQuestions> getKpiQuestionsList() {
        return kpiQuestionsList;
    }

    public void setKpiQuestionsList(List<KPIQuestions> kpiQuestionsList) {
        this.kpiQuestionsList = kpiQuestionsList;
    }

    public KPIHeader(String tahun, String semester, String bobot, String status, String atasanLangsung, String bawahanLangsung, String empName, String NIK, String dept, List<KPIQuestions> kpiQuestionsList) {
        this.tahun = tahun;
        this.semester = semester;
        this.bobot = bobot;
        this.status = status;
        this.atasanLangsung = atasanLangsung;
        this.bawahanLangsung = bawahanLangsung;
        this.empName = empName;
        this.NIK = NIK;
        this.dept = dept;
        this.kpiQuestionsList = kpiQuestionsList;
    }

    public KPIHeader() {
    }

}
