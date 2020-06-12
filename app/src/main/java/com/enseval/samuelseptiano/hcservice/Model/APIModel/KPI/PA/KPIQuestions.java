package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class KPIQuestions implements Serializable {
    //@SerializedName("id")
    private String idKPI;
    @SerializedName("kpiName")
    private String KPIDesc;
    @SerializedName("pA_ViewTransGrades")
    private List<KPIHint> hint;
    @SerializedName("bobot")
    private String bobot;

    private String CR;

    @SerializedName("cp")
    private String CP;

    @SerializedName("kpiType")
    private String KPIcategory="";

    @SerializedName("kpiPerspektif")
    private String perspective="";

    @SerializedName("semester")
    private String semester="";

    @SerializedName("tahun")
    private String tahun="";

    @SerializedName("paId")
    private String paId;
    @SerializedName("id")
    private String kpiId;

    public String getMessageNotification() {
        return messageNotification;
    }

    public void setMessageNotification(String messageNotification) {
        this.messageNotification = messageNotification;
    }

    private String messageNotification;

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getPaId() {
        return paId;
    }

    public void setPaId(String paId) {
        this.paId = paId;
    }

    public String getKpiId() {
        return kpiId;
    }

    public void setKpiId(String kpiId) {
        this.kpiId = kpiId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public KPIQuestions() {
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

    public List<KPIHint> getHint() {
        return hint;
    }

    public void setHint(List<KPIHint> hint) {
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
    private List<ImageUploadModelPA> photoCapture;
    private String pendukung="";
    private String penghambat="";
    private String catatanLain="";
    private String evidence="";

    private String target="";
    private String actual="";

    public boolean isNotif() {
        return notif;
    }

    public void setNotif(boolean notif) {
        this.notif = notif;
    }

    private boolean notif=false;


    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

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
    private boolean isAnsweredTarget;
    private boolean isAnsweredActual;



    public Boolean getAnsweredTarget() {
        return isAnsweredTarget;
    }

    public void setAnsweredTarget(Boolean answeredTarget) {
        isAnsweredTarget = answeredTarget;
    }

    public Boolean getAnsweredActual() {
        return isAnsweredActual;
    }

    public void setAnsweredActual(Boolean answeredActual) {
        isAnsweredActual = answeredActual;
    }

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

    public List<ImageUploadModelPA> getPhotoCapture() {
        return photoCapture;
    }

    public void setPhotoCapture(List<ImageUploadModelPA> photoCapture) {
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
