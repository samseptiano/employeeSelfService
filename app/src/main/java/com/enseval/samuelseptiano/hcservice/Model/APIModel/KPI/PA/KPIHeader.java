package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.AP.APFileUploadModel;
import com.enseval.samuelseptiano.hcservice.Model.FileUploadAPModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class KPIHeader implements Serializable {

    public String getPaId() {
        return paId;
    }

    public void setPaId(String paId) {
        this.paId = paId;
    }

    @SerializedName("emailAtasan1")
    private String emailAtasan1;

    @SerializedName("emailAtasan2")
    private String emailAtasan2;

    @SerializedName("nomorAtasan1")
    private String nomorAtasan1;

    @SerializedName("nomorAtasan2")
    private String nomorAtasan2;

    public String getNomorAtasan1() {
        return nomorAtasan1;
    }

    public void setNomorAtasan1(String nomorAtasan1) {
        this.nomorAtasan1 = nomorAtasan1;
    }

    public String getNomorAtasan2() {
        return nomorAtasan2;
    }

    public void setNomorAtasan2(String nomorAtasan2) {
        this.nomorAtasan2 = nomorAtasan2;
    }

    public String getEmailAtasan1() {
        return emailAtasan1;
    }

    public void setEmailAtasan1(String emailAtasan1) {
        this.emailAtasan1 = emailAtasan1;
    }

    public String getEmailAtasan2() {
        return emailAtasan2;
    }

    public void setEmailAtasan2(String emailAtasan2) {
        this.emailAtasan2 = emailAtasan2;
    }

    @SerializedName("namaAtasan3")
    private String namaAtasan3;

    @SerializedName("nikAtasan3")
    private String nikAtasan3;

    @SerializedName("emailAtasan3")
    private String emailAtasan3;

    @SerializedName("star")
    private String star;

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getNamaAtasan3() {
        return namaAtasan3;
    }

    public void setNamaAtasan3(String namaAtasan3) {
        this.namaAtasan3 = namaAtasan3;
    }

    public String getNikAtasan3() {
        return nikAtasan3;
    }

    public void setNikAtasan3(String nikAtasan3) {
        this.nikAtasan3 = nikAtasan3;
    }

    public String getEmailAtasan3() {
        return emailAtasan3;
    }

    public void setEmailAtasan3(String emailAtasan3) {
        this.emailAtasan3 = emailAtasan3;
    }

    @SerializedName("paId")
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
    @SerializedName("fotoAtasan1")
    private String fotoAtasanLangsung;
    @SerializedName("namaAtasan2")
    private String atasanTakLangsung;
    @SerializedName("nikAtasan2")
    private String NIKAtasanTakLangsung;
    @SerializedName("fotoAtasan2")
    private String fotoAtasanTakLangsung;
    private String fotoBawahan;
    private String bawahanLangsung;
    private String empName;
    @SerializedName("empNIK")
    private String NIK;

    @SerializedName("jobTitleAtasan1")
    private String jobTitleAtasanLangsung;
    @SerializedName("jobTitleAtasan2")
    private String jobTitleAtasanTakLangsung;

    public String getJobTitleAtasanLangsung() {
        return jobTitleAtasanLangsung;
    }

    public void setJobTitleAtasanLangsung(String jobTitleAtasanLangsung) {
        this.jobTitleAtasanLangsung = jobTitleAtasanLangsung;
    }

    public String getJobTitleAtasanTakLangsung() {
        return jobTitleAtasanTakLangsung;
    }

    public void setJobTitleAtasanTakLangsung(String jobTitleAtasanTakLangsung) {
        this.jobTitleAtasanTakLangsung = jobTitleAtasanTakLangsung;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @SerializedName("orgName")
    private String orgName;
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


    public List<APFileUploadModel> getApFileUploadModel() {
        return apFileUploadModel;
    }

    public void setApFileUploadModel(List<APFileUploadModel> apFileUploadModel) {
        this.apFileUploadModel = apFileUploadModel;
    }

    @SerializedName("pA_AP")
    private List<APFileUploadModel> apFileUploadModel;

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    @SerializedName("empEmail")
    private String empEmail;

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
