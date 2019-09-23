package com.example.samuelseptiano.employeeselfservice.Model;

import com.google.gson.annotations.SerializedName;

public class  ImageUploadModel {
    @SerializedName("fileName")
    private String filepath;
    @SerializedName("fileStream")
    private String imgString;
    @SerializedName("fileType")
    private String imgType;

    public ImageUploadModel(String filepath, String imgString, String imgType) {
        this.filepath = filepath;
        this.imgString = imgString;
        this.imgType = imgType;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
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

    public ImageUploadModel() {
    }

}
