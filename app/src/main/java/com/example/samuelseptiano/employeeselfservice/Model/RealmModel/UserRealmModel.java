package com.example.samuelseptiano.employeeselfservice.Model.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserRealmModel extends RealmObject {

    @PrimaryKey
    private String id;
    private String userId;
    private String lastUpdateBy;
    private String fGActiveYN;
    private String lastUpdate;
    private String lastChangePassword;
    private String username;
    private String empEmail;
    private String password;
    private String empNIK;
    private String token;
    private String userQR;
    private String empName;
    private String dept;
    private String jobTitleName;
    private String jobTitleCode;
    private String companyName;
    private String companyCode;
    private String locationName;
    private String locationCode;
    private String  positionId;
    private String  isPKAccessed;
    private String  nikAtasanLangsung;
    private String  namaAtasanLangsung;
    private String  posAtasanLangsung;
    private String  nikAtasanTakLangsung;
    private String  namaAtasanTakLangsung;
    private String  posAtasanTakLangsung;
    private String  branchName;
    private String jobTitleAtasan1;
    private String jobTitleAtasan2;
    private String orgName;
    private String orgCode;
    private String  fotoAtasanLangsung;
    private String  fotoAtasanTakLangsung;
    private String empPhoto;
    private String orgNameAtasan1;
    private String orgNameAtasan2;
    private String privilege;
    private String emailAtasan1;
    private String emailAtasan2;

    public UserRealmModel(String id, String userId, String lastUpdateBy, String fGActiveYN, String lastUpdate, String lastChangePassword, String username, String empEmail, String password, String empNIK, String token, String userQR, String empName, String dept, String jobTitleName, String jobTitleCode, String companyName, String companyCode, String locationName, String locationCode, String positionId, String isPKAccessed, String nikAtasanLangsung, String namaAtasanLangsung, String posAtasanLangsung, String nikAtasanTakLangsung, String namaAtasanTakLangsung, String posAtasanTakLangsung, String branchName, String jobTitleAtasan1, String jobTitleAtasan2, String orgName, String orgCode, String fotoAtasanLangsung, String fotoAtasanTakLangsung, String empPhoto, String orgNameAtasan1, String orgNameAtasan2, String privilege, String emailAtasan1, String emailAtasan2) {
        this.id = id;
        this.userId = userId;
        this.lastUpdateBy = lastUpdateBy;
        this.fGActiveYN = fGActiveYN;
        this.lastUpdate = lastUpdate;
        this.lastChangePassword = lastChangePassword;
        this.username = username;
        this.empEmail = empEmail;
        this.password = password;
        this.empNIK = empNIK;
        this.token = token;
        this.userQR = userQR;
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
        this.nikAtasanTakLangsung = nikAtasanTakLangsung;
        this.namaAtasanTakLangsung = namaAtasanTakLangsung;
        this.posAtasanTakLangsung = posAtasanTakLangsung;
        this.branchName = branchName;
        this.jobTitleAtasan1 = jobTitleAtasan1;
        this.jobTitleAtasan2 = jobTitleAtasan2;
        this.orgName = orgName;
        this.orgCode = orgCode;
        this.fotoAtasanLangsung = fotoAtasanLangsung;
        this.fotoAtasanTakLangsung = fotoAtasanTakLangsung;
        this.empPhoto = empPhoto;
        this.orgNameAtasan1 = orgNameAtasan1;
        this.orgNameAtasan2 = orgNameAtasan2;
        this.privilege = privilege;
        this.emailAtasan1 = emailAtasan1;
        this.emailAtasan2 = emailAtasan2;
    }

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

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserQR() {
        return userQR;
    }

    public void setUserQR(String userQR) {
        this.userQR = userQR;
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

    public String getNikAtasanTakLangsung() {
        return nikAtasanTakLangsung;
    }

    public void setNikAtasanTakLangsung(String nikAtasanTakLangsung) {
        this.nikAtasanTakLangsung = nikAtasanTakLangsung;
    }

    public String getNamaAtasanTakLangsung() {
        return namaAtasanTakLangsung;
    }

    public void setNamaAtasanTakLangsung(String namaAtasanTakLangsung) {
        this.namaAtasanTakLangsung = namaAtasanTakLangsung;
    }

    public String getPosAtasanTakLangsung() {
        return posAtasanTakLangsung;
    }

    public void setPosAtasanTakLangsung(String posAtasanTakLangsung) {
        this.posAtasanTakLangsung = posAtasanTakLangsung;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getOrgNameAtasan1() {
        return orgNameAtasan1;
    }

    public void setOrgNameAtasan1(String orgNameAtasan1) {
        this.orgNameAtasan1 = orgNameAtasan1;
    }

    public String getOrgNameAtasan2() {
        return orgNameAtasan2;
    }

    public void setOrgNameAtasan2(String orgNameAtasan2) {
        this.orgNameAtasan2 = orgNameAtasan2;
    }

    public UserRealmModel() {
    }

}
