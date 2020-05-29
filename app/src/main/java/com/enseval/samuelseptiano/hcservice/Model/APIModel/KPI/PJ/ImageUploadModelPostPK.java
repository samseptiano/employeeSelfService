package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

public class ImageUploadModelPostPK {
    @SerializedName("FILENAME")
    private String filename;
    @SerializedName("FILESTRING")
    private String imgString;
    @SerializedName("KPIID")
    private String kpiId;
    @SerializedName("COMPID")
    private String compId;
    @SerializedName("PKID")
    private String pkId;
    @SerializedName("FILEEXT")
    private String fileExt;
    @SerializedName("SEMESTER")
    private String semester;
    @SerializedName("EMPNIK")
    private String empNIK;

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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public ImageUploadModelPostPK() {
    }


}