package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class fileUpload {
    @SerializedName("extension")
    private String extension;

    @SerializedName("fileName")
    private String fileName;

    @SerializedName("image")
    private MultipartBody.Part image;

    public fileUpload() {
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartBody.Part getImage() {
        return image;
    }

    public void setImage(MultipartBody.Part image) {
        this.image = image;
    }
}
