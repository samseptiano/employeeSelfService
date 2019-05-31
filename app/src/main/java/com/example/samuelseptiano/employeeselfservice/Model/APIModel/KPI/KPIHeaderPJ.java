package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

import java.util.List;

/**
 * Created by Samuel Septiano on 24,May,2019
 */
public class KPIHeaderPJ {
    private String tahun;
    private String semester;
    private String bobot;
    private String status;
    private String atasanLangsung;
    private String atasanTakLangsung;
    private String bawahanLangsung;
    private String empName;
    private String NIK;
    private String dept;
    private List<KPIQuestionsPJ> kpiQuestionsList;
    private String status1;
    private String status2;
    private String periodeAwal;
    private String periodeAkhir;

    private String LocationName;

    public KPIHeaderPJ(String tahun, String semester, String bobot, String status, String atasanLangsung, String atasanTakLangsung, String bawahanLangsung, String empName, String NIK, String dept, List<KPIQuestionsPJ> kpiQuestionsList, String status1, String status2, String periodeAwal, String periodeAkhir, String locationName) {
        this.tahun = tahun;
        this.semester = semester;
        this.bobot = bobot;
        this.status = status;
        this.atasanLangsung = atasanLangsung;
        this.atasanTakLangsung = atasanTakLangsung;
        this.bawahanLangsung = bawahanLangsung;
        this.empName = empName;
        this.NIK = NIK;
        this.dept = dept;
        this.kpiQuestionsList = kpiQuestionsList;
        this.status1 = status1;
        this.status2 = status2;
        this.periodeAwal = periodeAwal;
        this.periodeAkhir = periodeAkhir;
        LocationName = locationName;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getPeriodeAwal() {
        return periodeAwal;
    }

    public void setPeriodeAwal(String periodeAwal) {
        this.periodeAwal = periodeAwal;
    }

    public String getPeriodeAkhir() {
        return periodeAkhir;
    }

    public void setPeriodeAkhir(String periodeAkhir) {
        this.periodeAkhir = periodeAkhir;
    }

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

    public String getAtasanTakLangsung() {
        return atasanTakLangsung;
    }

    public void setAtasanTakLangsung(String atasanTakLangsung) {
        this.atasanTakLangsung = atasanTakLangsung;
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

    public List<KPIQuestionsPJ> getKpiQuestionsList() {
        return kpiQuestionsList;
    }

    public void setKpiQuestionsList(List<KPIQuestionsPJ> kpiQuestionsList) {
        this.kpiQuestionsList = kpiQuestionsList;
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

    public KPIHeaderPJ() {
    }
}
