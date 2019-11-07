package com.example.samuelseptiano.employeeselfservice.Model;

public class FilterPAModel {
    private String tahun;
    private String direktorat;
    private String site;

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

    public FilterPAModel() {
    }

    public FilterPAModel(String tahun, String direktorat, String site) {
        this.tahun = tahun;
        this.direktorat = direktorat;
        this.site = site;
    }
}
