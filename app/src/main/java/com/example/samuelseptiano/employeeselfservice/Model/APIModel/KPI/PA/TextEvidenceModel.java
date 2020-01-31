package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

public class TextEvidenceModel {
    @SerializedName("actual")
    private String actual;
    @SerializedName("target")
    private String target;
    @SerializedName("evidence")
    private String evidence;
    @SerializedName("paId")
    private String PAID;
    @SerializedName("kpiId")
    private String KPIID;
    @SerializedName("compId")
    private String COMPID;
    @SerializedName("semester")
    private String SEMESTER;

    public TextEvidenceModel() {
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    public String getKPIID() {
        return KPIID;
    }

    public void setKPIID(String KPIID) {
        this.KPIID = KPIID;
    }

    public String getCOMPID() {
        return COMPID;
    }

    public void setCOMPID(String COMPID) {
        this.COMPID = COMPID;
    }

    public String getSEMESTER() {
        return SEMESTER;
    }

    public void setSEMESTER(String SEMESTER) {
        this.SEMESTER = SEMESTER;
    }
}
