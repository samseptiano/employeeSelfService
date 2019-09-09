package com.example.samuelseptiano.employeeselfservice.Model.APIModel.User;
import com.google.gson.annotations.SerializedName;

public class User {


    public User() {
    }

    @SerializedName("userID")
    private String userId;
    @SerializedName("lastUpdateBy")
    private String lastUpdateBy;
    @SerializedName("fgActiveYN")
    private String fGActiveYN;
    @SerializedName("lastUpdate")
    private String lastUpdate;
    @SerializedName("lastChangePassword")
    private String lastChangePassword;
    @SerializedName("empUsername")
    private String username;
    @SerializedName("empEmail")
    private String empEmail;
    @SerializedName("password")
    private String password;
    @SerializedName("empNiK")
    private String empNIK;
    @SerializedName("empName")
    private String empName;
    @SerializedName("orgGroupName")
    private String dept;
    @SerializedName("jobTitleName")
    private String jobTitleName;
    @SerializedName("jobTitleCode")
    private String jobTitleCode;
    @SerializedName("companyName")
    private String companyName;
    @SerializedName("companyCode")
    private String companyCode;
    @SerializedName("locationName")
    private String locationName;
    @SerializedName("locationCode")
    private String locationCode;
    @SerializedName("positionId")
    private String positionId;
    @SerializedName("isPKAccessed")
    private String isPKAccessed;
    @SerializedName("NIKAtasanLangsung")
    private String nikAtasanLangsung;
    @SerializedName("namaAtasanLangsung")
    private String namaAtasanLangsung;
    @SerializedName("posAtasanLangsung")
    private String posAtasanLangsung;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getfGActiveYN() {
        return fGActiveYN;
    }

    public void setfGActiveYN(String fGActiveYN) {
        this.fGActiveYN = fGActiveYN;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastChangePassword() {
        return lastChangePassword;
    }

    public void setLastChangePassword(String lastChangePassword) {
        this.lastChangePassword = lastChangePassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getJobTitleCode() {
        return jobTitleCode;
    }

    public void setJobTitleCode(String jobTitleCode) {
        this.jobTitleCode = jobTitleCode;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getIsPKAccessed() {
        return isPKAccessed;
    }

    public void setIsPKAccessed(String isPKAccessed) {
        this.isPKAccessed = isPKAccessed;
    }

    public String getNikAtasanLangsung() {
        return nikAtasanLangsung;
    }

    public void setNikAtasanLangsung(String nikAtasanLangsung) {
        this.nikAtasanLangsung = nikAtasanLangsung;
    }

    public String getNamaAtasanLangsung() {
        return namaAtasanLangsung;
    }

    public void setNamaAtasanLangsung(String namaAtasanLangsung) {
        this.namaAtasanLangsung = namaAtasanLangsung;
    }

    public String getPosAtasanLangsung() {
        return posAtasanLangsung;
    }

    public void setPosAtasanLangsung(String posAtasanLangsung) {
        this.posAtasanLangsung = posAtasanLangsung;
    }

    public User(String userId, String lastUpdateBy, String fGActiveYN, String lastUpdate, String lastChangePassword, String username, String empEmail, String password, String empNIK, String empName, String dept, String jobTitleName, String jobTitleCode, String companyName, String companyCode, String locationName, String locationCode, String positionId, String isPKAccessed, String nikAtasanLangsung, String namaAtasanLangsung, String posAtasanLangsung) {
        this.userId = userId;
        this.lastUpdateBy = lastUpdateBy;
        this.fGActiveYN = fGActiveYN;
        this.lastUpdate = lastUpdate;
        this.lastChangePassword = lastChangePassword;
        this.username = username;
        this.empEmail = empEmail;
        this.password = password;
        this.empNIK = empNIK;
        this.empName = empName;
        this.dept = dept;
        this.jobTitleName = jobTitleName;
        this.jobTitleCode = jobTitleCode;
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.locationName = locationName;
        this.locationCode = locationCode;
        this.positionId = positionId;
        this.isPKAccessed = isPKAccessed;
        this.nikAtasanLangsung = nikAtasanLangsung;
        this.namaAtasanLangsung = namaAtasanLangsung;
        this.posAtasanLangsung = posAtasanLangsung;
    }
}
