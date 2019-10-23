package com.example.samuelseptiano.employeeselfservice.Model;

import com.google.gson.annotations.SerializedName;

public class  ImageUploadModel {
    @SerializedName("fileName")
    private String filepath;
    @SerializedName("fileStream")
    private String imgString;
    @SerializedName("fileType")
    private String imgType;
    @SerializedName("empNIK")
    private String empNIK;
    @SerializedName("kpino")
    private String kpiNO;
    @SerializedName("transid")
    private String transID;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getKpiNO() {
        return kpiNO;
    }

    public void setKpiNO(String kpiNO) {
        this.kpiNO = kpiNO;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public ImageUploadModel(String filepath, String imgString, String imgType, String empNIK, String kpiNO, String transID) {
        this.filepath = filepath;
        this.imgString = imgString;
        this.imgType = imgType;
        this.empNIK = empNIK;
        this.kpiNO = kpiNO;
        this.transID = transID;
    }

    public ImageUploadModel() {
    }

}
