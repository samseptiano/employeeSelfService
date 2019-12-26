package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class KPIHeader implements Serializable {

    @SerializedName("id")
    private String paId;
    @SerializedName("year")
    private String tahun;
    private String semester;
    private String bobot;
    @SerializedName("status")
    private String status;
    private String formulaKualitatif; //string rumus
    private String formulaKuantitatif; //string rumus
    @SerializedName("namaAtasan1")
    private String atasanLangsung;
    @SerializedName("nikAtasan1")
    private String NIKAtasanLangsung;
    private String fotoAtasanLangsung;
    @SerializedName("namaAtasan2")
    private String atasanTakLangsung;
    @SerializedName("nikAtasan2")
    private String NIKAtasanTakLangsung;
    private String fotoAtasanTakLangsung;
    private String fotoBawahan;
    private String bawahanLangsung;
    private String empName;
    @SerializedName("empNIK")
    private String NIK;
    private String dept;
    @SerializedName("pA_ViewTransDetail")
    private List<KPIQuestions> kpiQuestionsList;
    @SerializedName("pA_MDevPlan")
    private List<MasterDevelopmentPlan> masterDevelopmentPlanList;
    @SerializedName("pA_DevPlanH")
    private List<DevPlanHeader> devPlanHeaderList;
    @SerializedName("statusAtasan1")
    private String status1;
    @SerializedName("statusAtasan2")
    private String status2;
    private String company;
    private String LocationName;
    private String branchName;
    private String jobTitleName;

    public List<DevPlanHeader> getDevPlanHeaderList() {
        return devPlanHeaderList;
    }

    public void setDevPlanHeaderList(List<DevPlanHeader> devPlanHeaderList) {
        this.devPlanHeaderList = devPlanHeaderList;
    }


    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    @SerializedName("strength")
    private String strength;


    public List<MasterDevelopmentPlan> getMasterDevelopmentPlanList() {
        return masterDevelopmentPlanList;
    }

    public void setMasterDevelopmentPlanList(List<MasterDevelopmentPlan> masterDevelopmentPlanList) {
        this.masterDevelopmentPlanList = masterDevelopmentPlanList;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getFotoBawahan() {
        return fotoBawahan;
    }

    public void setFotoBawahan(String fotoBawahan) {
        this.fotoBawahan = fotoBawahan;
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

    public String getFormulaKualitatif() {
        return formulaKualitatif;
    }

    public void setFormulaKualitatif(String formulaKualitatif) {
        this.formulaKualitatif = formulaKualitatif;
    }

    public String getFormulaKuantitatif() {
        return formulaKuantitatif;
    }

    public void setFormulaKuantitatif(String formulaKuantitatif) {
        this.formulaKuantitatif = formulaKuantitatif;
    }

    public String getAtasanLangsung() {
        return atasanLangsung;
    }

    public void setAtasanLangsung(String atasanLangsung) {
        this.atasanLangsung = atasanLangsung;
    }

    public String getNIKAtasanLangsung() {
        return NIKAtasanLangsung;
    }

    public void setNIKAtasanLangsung(String NIKAtasanLangsung) {
        this.NIKAtasanLangsung = NIKAtasanLangsung;
    }

    public String getFotoAtasanLangsung() {
        return fotoAtasanLangsung;
    }

    public void setFotoAtasanLangsung(String fotoAtasanLangsung) {
        this.fotoAtasanLangsung = fotoAtasanLangsung;
    }

    public String getAtasanTakLangsung() {
        return atasanTakLangsung;
    }

    public void setAtasanTakLangsung(String atasanTakLangsung) {
        this.atasanTakLangsung = atasanTakLangsung;
    }

    public String getNIKAtasanTakLangsung() {
        return NIKAtasanTakLangsung;
    }

    public void setNIKAtasanTakLangsung(String NIKAtasanTakLangsung) {
        this.NIKAtasanTakLangsung = NIKAtasanTakLangsung;
    }

    public String getFotoAtasanTakLangsung() {
        return fotoAtasanTakLangsung;
    }

    public void setFotoAtasanTakLangsung(String fotoAtasanTakLangsung) {
        this.fotoAtasanTakLangsung = fotoAtasanTakLangsung;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public KPIHeader(String tahun, String semester, String bobot, String status, String formulaKualitatif, String formulaKuantitatif, String atasanLangsung, String NIKAtasanLangsung, String fotoAtasanLangsung, String atasanTakLangsung, String NIKAtasanTakLangsung, String fotoAtasanTakLangsung, String bawahanLangsung, String empName, String NIK, String dept, List<KPIQuestions> kpiQuestionsList, String status1, String status2, String company, String locationName) {
        this.tahun = tahun;
        this.semester = semester;
        this.bobot = bobot;
        this.status = status;
        this.formulaKualitatif = formulaKualitatif;
        this.formulaKuantitatif = formulaKuantitatif;
        this.atasanLangsung = atasanLangsung;
        this.NIKAtasanLangsung = NIKAtasanLangsung;
        this.fotoAtasanLangsung = fotoAtasanLangsung;
        this.atasanTakLangsung = atasanTakLangsung;
        this.NIKAtasanTakLangsung = NIKAtasanTakLangsung;
        this.fotoAtasanTakLangsung = fotoAtasanTakLangsung;
        this.bawahanLangsung = bawahanLangsung;
        this.empName = empName;
        this.NIK = NIK;
        this.dept = dept;
        this.kpiQuestionsList = kpiQuestionsList;
        this.status1 = status1;
        this.status2 = status2;
        this.company = company;
        LocationName = locationName;
    }

    public KPIHeader() {
    }




}
