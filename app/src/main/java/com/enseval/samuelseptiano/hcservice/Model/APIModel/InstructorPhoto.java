package com.enseval.samuelseptiano.hcservice.Model.APIModel;

import com.google.gson.annotations.SerializedName;

public class InstructorPhoto {
    @SerializedName("id")
    private String id;
    @SerializedName("fileData")
    private String fileData;
    @SerializedName("fileType")
    private String fileType;

    public InstructorPhoto(String id, String fileType) {
        this.id = id;
        this.fileType = fileType;
    }

    public InstructorPhoto(String id, String fileData, String fileType) {
        this.id = id;
        this.fileData = fileData;
        this.fileType = fileType;
    }

    public InstructorPhoto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
