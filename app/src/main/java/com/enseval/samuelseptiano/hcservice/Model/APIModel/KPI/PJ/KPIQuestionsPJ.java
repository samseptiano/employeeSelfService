package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHint;
import com.enseval.samuelseptiano.hcservice.Model.ImageUploadModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Samuel Septiano on 24,May,2019
 */
public class KPIQuestionsPJ {
    private String idKPI;
    @SerializedName("kpiName")
    private String KPIDesc;
    @SerializedName("pK_ViewTransGrades")
    private List<KPIHintPJ> hint;
    @SerializedName("bobot")
    private String bobot;
    private String CR;
    @SerializedName("cp")
    private String CP;
    @SerializedName("kpiType")
    private String KPIcategory="";
    @SerializedName("kpiPerspektif")
    private String perspective="";
    @SerializedName("paId")
    private String paId;
    @SerializedName("pkId")
    private String pkId;
    @SerializedName("id")
    private String kpiId;

    //====================================================
    private String messageNotification;
    private int checkedId = -1;
    private boolean isAnswered;
    private List<ImageUploadModelPK> photoCapture;
    private String pendukung="";
    private String penghambat="";
    private String catatanLain="";
    private String evidence="";

    private boolean notif=false;


    private boolean isAnsweredCatatan;
    private boolean isAnsweredPendukung;
    private boolean isAnsweredPenghambat;
    private boolean isAnsweredEvidence;
    private boolean isAnsweredTarget;
    private boolean isAnsweredActual;

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

    public List<KPIHintPJ> getHint() {
        return hint;
    }

    public void setHint(List<KPIHintPJ> hint) {
        this.hint = hint;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getCR() {
        return CR;
    }

    public void setCR(String CR) {
        this.CR = CR;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getKPIcategory() {
        return KPIcategory;
    }

    public void setKPIcategory(String KPIcategory) {
        this.KPIcategory = KPIcategory;
    }

    public String getPerspective() {
        return perspective;
    }

    public void setPerspective(String perspective) {
        this.perspective = perspective;
    }

    public String getPaId() {
        return paId;
    }

    public void setPaId(String paId) {
        this.paId = paId;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getKpiId() {
        return kpiId;
    }

    public void setKpiId(String kpiId) {
        this.kpiId = kpiId;
    }

    public String getMessageNotification() {
        return messageNotification;
    }

    public void setMessageNotification(String messageNotification) {
        this.messageNotification = messageNotification;
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

    public List<ImageUploadModelPK> getPhotoCapture() {
        return photoCapture;
    }

    public void setPhotoCapture(List<ImageUploadModelPK> photoCapture) {
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

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public boolean isNotif() {
        return notif;
    }

    public void setNotif(boolean notif) {
        this.notif = notif;
    }

    public boolean isAnsweredCatatan() {
        return isAnsweredCatatan;
    }

    public void setAnsweredCatatan(boolean answeredCatatan) {
        isAnsweredCatatan = answeredCatatan;
    }

    public boolean isAnsweredPendukung() {
        return isAnsweredPendukung;
    }

    public void setAnsweredPendukung(boolean answeredPendukung) {
        isAnsweredPendukung = answeredPendukung;
    }

    public boolean isAnsweredPenghambat() {
        return isAnsweredPenghambat;
    }

    public void setAnsweredPenghambat(boolean answeredPenghambat) {
        isAnsweredPenghambat = answeredPenghambat;
    }

    public boolean isAnsweredEvidence() {
        return isAnsweredEvidence;
    }

    public void setAnsweredEvidence(boolean answeredEvidence) {
        isAnsweredEvidence = answeredEvidence;
    }

    public boolean isAnsweredTarget() {
        return isAnsweredTarget;
    }

    public void setAnsweredTarget(boolean answeredTarget) {
        isAnsweredTarget = answeredTarget;
    }

    public boolean isAnsweredActual() {
        return isAnsweredActual;
    }

    public void setAnsweredActual(boolean answeredActual) {
        isAnsweredActual = answeredActual;
    }
}


