package com.enseval.samuelseptiano.hcservice.Model;

import com.google.gson.annotations.SerializedName;

public class DelImageUploadModel {
    @SerializedName("TRANSID")
    private String transID;
    @SerializedName("KPINO")
    private String kpiNo;
    @SerializedName("fileName")
    private String filepath;
    @SerializedName("fileStream")
    private String imgString;
    @SerializedName("fileType")
    private String imgType;

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getKpiNo() {
        return kpiNo;
    }

    public void setKpiNo(String kpiNo) {
        this.kpiNo = kpiNo;
    }

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

    public DelImageUploadModel() {
    }

}
