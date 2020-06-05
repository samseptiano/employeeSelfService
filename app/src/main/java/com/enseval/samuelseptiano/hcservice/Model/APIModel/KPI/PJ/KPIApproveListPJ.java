package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Samuel Septiano on 21,May,2019
 */
public class KPIApproveListPJ {
    private String id;
    @SerializedName("empName")
    private String empName;
    @SerializedName("empNiK")
    private String NIK;
    @SerializedName("status1")
    private String status1;
    @SerializedName("status2")
    private String status2;
    @SerializedName("status")
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
    @SerializedName("locationCode")
    private String locationCode;
    @SerializedName("locationName")
    private String locationName;
    @SerializedName("paid")
    private String paid;
    @SerializedName("pkid")
    private String pkid;
    @SerializedName("bobotTotal")
    private String bobotTotal;
    @SerializedName("jobTitleAtasan1")
    private String jobTitleAtasan1;
    @SerializedName("jobTitleAtasan2")
    private String jobTitleAtasan2;
    @SerializedName("nomorAtasan1")
    private String nomorAtasan1;
    @SerializedName("nomorAtasan2")
    private String nomorAtasan2;
    @SerializedName("fotoAtasan1")
    private String fotoAtasan1;
    @SerializedName("fotoAtasan2")
    private String fotoAtasan2;
    @SerializedName("orgName")
    private String orgName;
    private List<String> message;
    private List<String> time;
    @SerializedName("fotoKaryawan")
    private String empPhoto;

    @SerializedName("mulaiKontrak")
    private String mulaiKontrak;
    @SerializedName("selesaiKontrak")
    private String selesaiKontrak;

    @SerializedName("checkDate")
    private String checkDate;
    @SerializedName("checkByNIK")
    private String checkByNIK;

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckByNIK() {
        return checkByNIK;
    }

    public void setCheckByNIK(String checkByNIK) {
        this.checkByNIK = checkByNIK;
    }

    public String getMulaiKontrak() {
        return mulaiKontrak;
    }

    public void setMulaiKontrak(String mulaiKontrak) {
        this.mulaiKontrak = mulaiKontrak;
    }

    public String getSelesaiKontrak() {
        return selesaiKontrak;
    }

    public void setSelesaiKontrak(String selesaiKontrak) {
        this.selesaiKontrak = selesaiKontrak;
    }

    public KPIApproveListPJ() {
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

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getBobotTotal() {
        return bobotTotal;
    }

    public void setBobotTotal(String bobotTotal) {
        this.bobotTotal = bobotTotal;
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

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }
}
