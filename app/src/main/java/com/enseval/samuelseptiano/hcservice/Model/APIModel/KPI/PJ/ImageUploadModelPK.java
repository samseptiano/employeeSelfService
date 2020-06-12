package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

public class ImageUploadModelPK {
    @SerializedName("filename")
    private String filename;
    @SerializedName("fileString")
    private String imgString;
    @SerializedName("kpiId")
    private String kpiId;
    @SerializedName("compId")
    private String compId;
    @SerializedName("pkId")
    private String pkId;
    @SerializedName("fileExt")
    private String fileExt;

    public ImageUploadModelPK() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    public String getKpiId() {
        return kpiId;
    }

    public void setKpiId(String kpiId) {
        this.kpiId = kpiId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }
}