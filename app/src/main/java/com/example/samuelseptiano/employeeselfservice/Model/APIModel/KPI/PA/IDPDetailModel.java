package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

public class IDPDetailModel {
    private String id;
    private String idpId;
    private String paTransId;
    private String empNIK;
    private String developmentActivity;
    private String kpi;
    private String dueDate;
    private String mentor;
    private String achievementReccomendation;

    public IDPDetailModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdpId() {
        return idpId;
    }

    public void setIdpId(String idpId) {
        this.idpId = idpId;
    }

    public String getPaTransId() {
        return paTransId;
    }

    public void setPaTransId(String paTransId) {
        this.paTransId = paTransId;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getDevelopmentActivity() {
        return developmentActivity;
    }

    public void setDevelopmentActivity(String developmentActivity) {
        this.developmentActivity = developmentActivity;
    }

    public String getKpi() {
        return kpi;
    }

    public void setKpi(String kpi) {
        this.kpi = kpi;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getAchievementReccomendation() {
        return achievementReccomendation;
    }

    public void setAchievementReccomendation(String achievementReccomendation) {
        this.achievementReccomendation = achievementReccomendation;
    }
}
