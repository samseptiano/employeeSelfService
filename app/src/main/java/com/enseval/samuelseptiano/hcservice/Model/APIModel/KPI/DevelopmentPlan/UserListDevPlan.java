package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.DevelopmentPlan;

import com.google.gson.annotations.SerializedName;

public class UserListDevPlan {
    private String id;
    @SerializedName("empName")
    private String empName;
    @SerializedName("empNiK")
    private String NIK;
    @SerializedName("department")
    private String dept;
    @SerializedName("namaAtasan1")
    private String namaAtasan1;
    @SerializedName("namaAtasan2")
    private String namaAtasan2;
    @SerializedName("nikAtasan1")
    private String NIKAtasan1;
    @SerializedName("nikAtasan2")
    private String NIKAtasan2;
    @SerializedName("jobTitleName")
    private String jobTitle;
    @SerializedName("empType")
    private String jobStatus;
    @SerializedName("positionName")
    private String BranchName;
    @SerializedName("nilai")
    private String score;
    @SerializedName("star")
    private String star;
    @SerializedName("dateStart")
    private String dateStart;
    @SerializedName("dateEnd")
    private String dateEnd;
    @SerializedName("jobTitleAtasan1")
    private String jobTitleAtasan1;
    @SerializedName("jobTitleAtasan2")
    private String jobTitleAtasan2;
    @SerializedName("fotoAtasan1")
    private String fotoAtasan1;
    @SerializedName("fotoAtasan2")
    private String fotoAtasan2;
    @SerializedName("orgName")
    private String orgName;
    @SerializedName("fotoKaryawan")
    private String empPhoto;
    @SerializedName("fgCanApproveYN")
    private String FgCanApproveYN;
    @SerializedName("fgHasApproveYN")
    private String FgHasApproveYN;
    @SerializedName("apprseq")
    private String APPRSEQ;

    @SerializedName("emailAtasan1")
    private String emailAtasan1;
    @SerializedName("emailAtasan2")
    private String emailAtasan2;

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

    public UserListDevPlan() {
    }

    public UserListDevPlan(String id, String empName, String NIK, String dept, String namaAtasan1, String namaAtasan2, String NIKAtasan1, String NIKAtasan2, String jobTitle, String jobStatus, String branchName, String score, String star, String dateStart, String dateEnd, String jobTitleAtasan1, String jobTitleAtasan2, String fotoAtasan1, String fotoAtasan2, String orgName, String empPhoto, String fgCanApproveYN, String fgHasApproveYN, String APPRSEQ) {
        this.id = id;
        this.empName = empName;
        this.NIK = NIK;
        this.dept = dept;
        this.namaAtasan1 = namaAtasan1;
        this.namaAtasan2 = namaAtasan2;
        this.NIKAtasan1 = NIKAtasan1;
        this.NIKAtasan2 = NIKAtasan2;
        this.jobTitle = jobTitle;
        this.jobStatus = jobStatus;
        BranchName = branchName;
        this.score = score;
        this.star = star;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.jobTitleAtasan1 = jobTitleAtasan1;
        this.jobTitleAtasan2 = jobTitleAtasan2;
        this.fotoAtasan1 = fotoAtasan1;
        this.fotoAtasan2 = fotoAtasan2;
        this.orgName = orgName;
        this.empPhoto = empPhoto;
        FgCanApproveYN = fgCanApproveYN;
        FgHasApproveYN = fgHasApproveYN;
        this.APPRSEQ = APPRSEQ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNamaAtasan1() {
        return namaAtasan1;
    }

    public void setNamaAtasan1(String namaAtasan1) {
        this.namaAtasan1 = namaAtasan1;
    }

    public String getNamaAtasan2() {
        return namaAtasan2;
    }

    public void setNamaAtasan2(String namaAtasan2) {
        this.namaAtasan2 = namaAtasan2;
    }

    public String getNIKAtasan1() {
        return NIKAtasan1;
    }

    public void setNIKAtasan1(String NIKAtasan1) {
        this.NIKAtasan1 = NIKAtasan1;
    }

    public String getNIKAtasan2() {
        return NIKAtasan2;
    }

    public void setNIKAtasan2(String NIKAtasan2) {
        this.NIKAtasan2 = NIKAtasan2;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getJobTitleAtasan1() {
        return jobTitleAtasan1;
    }

    public void setJobTitleAtasan1(String jobTitleAtasan1) {
        this.jobTitleAtasan1 = jobTitleAtasan1;
    }

    public String getJobTitleAtasan2() {
        return jobTitleAtasan2;
    }

    public void setJobTitleAtasan2(String jobTitleAtasan2) {
        this.jobTitleAtasan2 = jobTitleAtasan2;
    }

    public String getFotoAtasan1() {
        return fotoAtasan1;
    }

    public void setFotoAtasan1(String fotoAtasan1) {
        this.fotoAtasan1 = fotoAtasan1;
    }

    public String getFotoAtasan2() {
        return fotoAtasan2;
    }

    public void setFotoAtasan2(String fotoAtasan2) {
        this.fotoAtasan2 = fotoAtasan2;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getFgCanApproveYN() {
        return FgCanApproveYN;
    }

    public void setFgCanApproveYN(String fgCanApproveYN) {
        FgCanApproveYN = fgCanApproveYN;
    }

    public String getFgHasApproveYN() {
        return FgHasApproveYN;
    }

    public void setFgHasApproveYN(String fgHasApproveYN) {
        FgHasApproveYN = fgHasApproveYN;
    }

    public String getAPPRSEQ() {
        return APPRSEQ;
    }

    public void setAPPRSEQ(String APPRSEQ) {
        this.APPRSEQ = APPRSEQ;
    }
}
