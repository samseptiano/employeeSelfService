package com.enseval.samuelseptiano.hcservice.Model.APIModel.AP;

import com.google.gson.annotations.SerializedName;

public class APFileUploadModel {
    @SerializedName("apid")
    private String APID;
    @SerializedName("paid")
    private String PAID;
    @SerializedName("folder")
    private String folder;
    @SerializedName("filename")
    private String fileName;
    @SerializedName("extension")
    private String extension;
    @SerializedName("upduser")
    private String updUser;

    public APFileUploadModel() {
    }

    public String getAPID() {
        return APID;
    }

    public void setAPID(String APID) {
        this.APID = APID;
    }

    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }
}
