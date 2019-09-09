package com.example.samuelseptiano.employeeselfservice.Model.APIModel.User;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Samuel Septiano on 31,July,2019
 */
public class UserList implements Serializable {
    @SerializedName("empId")
    public String empId;
    @SerializedName("empNiK")
    public String empNiK ;
    @SerializedName("empName")
    public String empName;
    //public string Name ;
    @SerializedName("positionCode")
    public String positionCode ;
    @SerializedName("positionName")
    public String positionName ;
    @SerializedName("jobTitleCode")
    public String jobTitleCode ;
    @SerializedName("jobTitleName")
    public String jobTitleName ;
    @SerializedName("locationCode")
    public String locationCode ;
    @SerializedName("locationName")
    public String locationName ;
    @SerializedName("companyName")
    public String companyName ;
    @SerializedName("companyCode")
    public String companyCode ;
    @SerializedName("orgName")
    public String orgName ;
    @SerializedName("orgCode")
    public String orgCode ;
    @SerializedName("dateEnd")
    public String dateEnd ;
    @SerializedName("dateStart")
    public String dateStart;
    @SerializedName("joinDate")
    public String joinDate ;
    @SerializedName("positionId")
    public String positionId ;
    @SerializedName("signDate")
    public String signDate ;
    @SerializedName("bulanJatuhTempo")
    public String bulanJatuhTempo ;
    @SerializedName("empType")
    public String empType ;
    @SerializedName("posAtasan1")
    public String posAtasanLangsung;
    @SerializedName("posAtasan2")
    public String posAtasanTakLangsung;
    @SerializedName("nikAtasan1")
    public String NIKAtasanLangsung;
    @SerializedName("nikAtasan2")
    public String NIKAtasanTakLangsung;

    @SerializedName("namaAtasan1")
    public String namaAtasanLangsung;
    @SerializedName("namaAtasan2")
    public String namaAtasanTakLangsung;

    @SerializedName("fotoAtasan1")
    public String fotoAtasanLangsung;
    @SerializedName("fotoAtasan2")
    public String fotoAtasanTakLangsung;

    @SerializedName("fotoKaryawan")
    public String empPhoto;

    @SerializedName("status")
    public String status;

    public String status1;

    public String status2;

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

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }



    public UserList(String empId, String empNiK, String empName, String positionCode, String positionName, String jobTitleCode, String jobTitleName, String locationCode, String locationName, String companyName, String companyCode, String orgName, String orgCode, String dateEnd, String dateStart, String joinDate, String positionId, String signDate, String bulanJatuhTempo, String empType, String posAtasanLangsung, String posAtasanTakLangsung, String NIKAtasanLangsung, String NIKAtasanTakLangsung, String namaAtasanLangsung, String namaAtasanTakLangsung, String fotoAtasanLangsung, String fotoAtasanTakLangsung) {
        this.empId = empId;
        this.empNiK = empNiK;
        this.empName = empName;
        this.positionCode = positionCode;
        this.positionName = positionName;
        this.jobTitleCode = jobTitleCode;
        this.jobTitleName = jobTitleName;
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.orgName = orgName;
        this.orgCode = orgCode;
        this.dateEnd = dateEnd;
        this.dateStart = dateStart;
        this.joinDate = joinDate;
        this.positionId = positionId;
        this.signDate = signDate;
        this.bulanJatuhTempo = bulanJatuhTempo;
        this.empType = empType;
        this.posAtasanLangsung = posAtasanLangsung;
        this.posAtasanTakLangsung = posAtasanTakLangsung;
        this.NIKAtasanLangsung = NIKAtasanLangsung;
        this.NIKAtasanTakLangsung = NIKAtasanTakLangsung;
        this.namaAtasanLangsung = namaAtasanLangsung;
        this.namaAtasanTakLangsung = namaAtasanTakLangsung;
        this.fotoAtasanLangsung = fotoAtasanLangsung;
        this.fotoAtasanTakLangsung = fotoAtasanTakLangsung;
    }

    public String getFotoAtasanLangsung() {
        return fotoAtasanLangsung;
    }

    public void setFotoAtasanLangsung(String fotoAtasanLangsung) {
        this.fotoAtasanLangsung = fotoAtasanLangsung;
    }

    public String getFotoAtasanTakLangsung() {
        return fotoAtasanTakLangsung;
    }

    public void setFotoAtasanTakLangsung(String fotoAtasanTakLangsung) {
        this.fotoAtasanTakLangsung = fotoAtasanTakLangsung;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpNiK() {
        return empNiK;
    }

    public void setEmpNiK(String empNiK) {
        this.empNiK = empNiK;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getJobTitleCode() {
        return jobTitleCode;
    }

    public void setJobTitleCode(String jobTitleCode) {
        this.jobTitleCode = jobTitleCode;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getBulanJatuhTempo() {
        return bulanJatuhTempo;
    }

    public void setBulanJatuhTempo(String bulanJatuhTempo) {
        this.bulanJatuhTempo = bulanJatuhTempo;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getPosAtasanLangsung() {
        return posAtasanLangsung;
    }

    public void setPosAtasanLangsung(String posAtasanLangsung) {
        this.posAtasanLangsung = posAtasanLangsung;
    }

    public String getPosAtasanTakLangsung() {
        return posAtasanTakLangsung;
    }

    public void setPosAtasanTakLangsung(String posAtasanTakLangsung) {
        this.posAtasanTakLangsung = posAtasanTakLangsung;
    }

    public String getNIKAtasanLangsung() {
        return NIKAtasanLangsung;
    }

    public void setNIKAtasanLangsung(String NIKAtasanLangsung) {
        this.NIKAtasanLangsung = NIKAtasanLangsung;
    }

    public String getNIKAtasanTakLangsung() {
        return NIKAtasanTakLangsung;
    }

    public void setNIKAtasanTakLangsung(String NIKAtasanTakLangsung) {
        this.NIKAtasanTakLangsung = NIKAtasanTakLangsung;
    }

    public String getNamaAtasanLangsung() {
        return namaAtasanLangsung;
    }

    public void setNamaAtasanLangsung(String namaAtasanLangsung) {
        this.namaAtasanLangsung = namaAtasanLangsung;
    }

    public String getNamaAtasanTakLangsung() {
        return namaAtasanTakLangsung;
    }

    public void setNamaAtasanTakLangsung(String namaAtasanTakLangsung) {
        this.namaAtasanTakLangsung = namaAtasanTakLangsung;
    }

    public UserList() {
    }


}
