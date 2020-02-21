package com.enseval.samuelseptiano.hcservice.Model;

import java.io.Serializable;

public class FilterPJModel implements Serializable {
    private String tahun;
    private String jobTitle;
    private String periode;
    private String direktorat;
    private String site;


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getDirektorat() {
        return direktorat;
    }

    public void setDirektorat(String direktorat) {
        this.direktorat = direktorat;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public FilterPJModel() {
    }

    public FilterPJModel(String tahun, String direktorat, String site) {
        this.tahun = tahun;
        this.direktorat = direktorat;
        this.site = site;
    }
}
