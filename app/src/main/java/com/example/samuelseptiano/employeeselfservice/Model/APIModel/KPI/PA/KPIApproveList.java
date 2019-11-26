package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KPIApproveList implements Serializable {
    private String id;
    @SerializedName("empName")
    private String empName;
    @SerializedName("empNiK")
    private String NIK;
    private String status1;
    private String status2;
    private String status;
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

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }
    @SerializedName("fotoKaryawan")
    private String empPhoto;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public KPIApproveList() {
    }


}
