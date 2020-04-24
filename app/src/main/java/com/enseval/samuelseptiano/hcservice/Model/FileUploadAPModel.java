package com.enseval.samuelseptiano.hcservice.Model;

import com.google.gson.annotations.SerializedName;

public class FileUploadAPModel {
    @SerializedName("filename")
    private String filename;
    @SerializedName("fileString")
    private String imgString;
    @SerializedName("paId")
    private String paId;
    @SerializedName("fileExt")
    private String fileExt;

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

    public String getPaId() {
        return paId;
    }

    public void setPaId(String paId) {
        this.paId = paId;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public FileUploadAPModel() {
    }

}
