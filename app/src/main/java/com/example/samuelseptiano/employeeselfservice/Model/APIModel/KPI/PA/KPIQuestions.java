package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;

import java.util.List;

public class KPIQuestions {
    private String idKPI;
    private String KPIDesc;
    private List<String> hint;
    private String bobot;
    private String CR;
    private String compRate;
    private String KPIcategory;
    private String perspective;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    private String semester;

    public KPIQuestions(String idKPI, String KPIDesc, List<String> hint, String bobot, String CR, String compRate, String KPIcategory, String perspective) {
        this.idKPI = idKPI;
        this.KPIDesc = KPIDesc;
        this.hint = hint;
        this.bobot = bobot;
        this.CR = CR;
        this.compRate = compRate;
        this.KPIcategory = KPIcategory;
        this.perspective = perspective;
    }

    public KPIQuestions() {
    }

    public String getCompRate() {
        return compRate;
    }

    public void setCompRate(String compRate) {
        this.compRate = compRate;
    }

    public String getCR() {
        return CR;
    }

    public void setCR(String CR) {
        this.CR = CR;
    }

    public String getPerspective() {
        return perspective;
    }

    public void setPerspective(String perspective) {
        this.perspective = perspective;
    }


    public String getIdKPI() {
        return idKPI;
    }

    public void setIdKPI(String idKPI) {
        this.idKPI = idKPI;
    }

    public String getKPIDesc() {
        return KPIDesc;
    }

    public void setKPIDesc(String KPIDesc) {
        this.KPIDesc = KPIDesc;
    }

    public List<String> getHint() {
        return hint;
    }

    public void setHint(List<String> hint) {
        this.hint = hint;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getKPIcategory() {
        return KPIcategory;
    }

    public void setKPIcategory(String KPIcategory) {
        this.KPIcategory = KPIcategory;
    }


    private int checkedId = -1;
    private boolean isAnswered;
    private List<ImageUploadModel> photoCapture;
    private String pendukung="";
    private String penghambat="";
    private String catatanLain="";
    private String evidence="";

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    private boolean isAnsweredCatatan;
    private boolean isAnsweredPendukung;
    private boolean isAnsweredPenghambat;
    private boolean isAnsweredEvidence;


    public Boolean getAnsweredEvidence() {
        return isAnsweredEvidence;
    }

    public void setAnsweredEvidence(Boolean answeredEvidence) {
        isAnsweredEvidence = answeredEvidence;
    }


    public Boolean getAnsweredCatatan() {
        return isAnsweredCatatan;
    }

    public void setAnsweredCatatan(Boolean answeredCatatan) {
        isAnsweredCatatan = answeredCatatan;
    }

    public Boolean getAnsweredPendukung() {
        return isAnsweredPendukung;
    }

    public void setAnsweredPendukung(Boolean answeredPendukung) {
        isAnsweredPendukung = answeredPendukung;
    }

    public Boolean getAnsweredPenghambat() {
        return isAnsweredPenghambat;
    }

    public void setAnsweredPenghambat(Boolean answeredPenghambat) {
        isAnsweredPenghambat = answeredPenghambat;
    }

    public List<ImageUploadModel> getPhotoCapture() {
        return photoCapture;
    }

    public void setPhotoCapture(List<ImageUploadModel> photoCapture) {
        this.photoCapture = photoCapture;
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

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int checkedId) {
        this.checkedId = checkedId;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
