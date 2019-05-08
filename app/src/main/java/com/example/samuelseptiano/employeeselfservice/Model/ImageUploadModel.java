package com.example.samuelseptiano.employeeselfservice.Model;

public class ImageUploadModel {
    private String filepath;
    private String imgString;

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

    public ImageUploadModel(String filepath, String imgString) {
        this.filepath = filepath;
        this.imgString = imgString;
    }
}
