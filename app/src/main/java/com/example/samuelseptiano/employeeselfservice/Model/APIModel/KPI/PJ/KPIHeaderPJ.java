package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Samuel Septiano on 24,May,2019
 */
public class KPIHeaderPJ {
    private String tahun;
    private String semester;
    @SerializedName("nilai")
    private String nilai;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    @SerializedName("transID")
    private String transId;
    @SerializedName("star")
    private String star;

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    @SerializedName("scoreName")
    private String scoreName;

    @SerializedName("statusName")
    private String status;

    public String getStatusFG() {
        return statusFG;
    }

    public void setStatusFG(String statusFG) {
        this.statusFG = statusFG;
    }

    @SerializedName("status")
    private String statusFG;

    public String getFotoBawahan() {
        return fotoBawahan;
    }

    public void setFotoBawahan(String fotoBawahan) {
        this.fotoBawahan = fotoBawahan;
    }

    private String fotoBawahan;

    private String formulaKualitatif; //string rumus
    private String formulaKuantitatif; //string rumus
    private String atasanLangsung;
    private String NIKatasanLangsung;
    private String fotoAtasanLangsung;
    private String atasanTakLangsung;
    private String NIKatasanTakLansung;
    private String fotoAtasanTakLangsung;
    private String bawahanLangsung;
    private String empName;
    private String NIK;
    private String dept;
    private String positionName;
    private String jobTitle;
    private String idHHeader;
    @SerializedName("pK_ViewTransDetail")
    private List<KPIQuestionsPJ> kpiQuestionsList;
    @SerializedName("aprStatus1")
    private String status1;
    @SerializedName("aprStatus2")
    private String status2;
    private String periodeAwal;
    private String periodeAkhir;
    private String LocationName;

    public String getIdHHeader() {
        return idHHeader;
    }

    public void setIdHHeader(String idHHeader) {
        this.idHHeader = idHHeader;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
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

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
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

    public String getNIKatasanLangsung() {
        return NIKatasanLangsung;
    }

    public void setNIKatasanLangsung(String NIKatasanLangsung) {
        this.NIKatasanLangsung = NIKatasanLangsung;
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

    public String getNIKatasanTakLansung() {
        return NIKatasanTakLansung;
    }

    public void setNIKatasanTakLansung(String NIKatasanTakLansung) {
        this.NIKatasanTakLansung = NIKatasanTakLansung;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public KPIHeaderPJ(String tahun, String semester, String nilai, String status, String formulaKualitatif, String formulaKuantitatif, String atasanLangsung, String NIKatasanLangsung, String fotoAtasanLangsung, String atasanTakLangsung, String NIKatasanTakLansung, String fotoAtasanTakLangsung, String bawahanLangsung, String empName, String NIK, String dept, String jobTitle, List<KPIQuestionsPJ> kpiQuestionsList, String status1, String status2, String periodeAwal, String periodeAkhir, String locationName) {
        this.tahun = tahun;
        this.semester = semester;
        this.nilai = nilai;
        this.status = status;
        this.formulaKualitatif = formulaKualitatif;
        this.formulaKuantitatif = formulaKuantitatif;
        this.atasanLangsung = atasanLangsung;
        this.NIKatasanLangsung = NIKatasanLangsung;
        this.fotoAtasanLangsung = fotoAtasanLangsung;
        this.atasanTakLangsung = atasanTakLangsung;
        this.NIKatasanTakLansung = NIKatasanTakLansung;
        this.fotoAtasanTakLangsung = fotoAtasanTakLangsung;
        this.bawahanLangsung = bawahanLangsung;
        this.empName = empName;
        this.NIK = NIK;
        this.dept = dept;
        this.jobTitle = jobTitle;
        this.kpiQuestionsList = kpiQuestionsList;
        this.status1 = status1;
        this.status2 = status2;
        this.periodeAwal = periodeAwal;
        this.periodeAkhir = periodeAkhir;
        LocationName = locationName;
    }

    public KPIHeaderPJ() {
    }
}
