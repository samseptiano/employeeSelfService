package com.enseval.samuelseptiano.hcservice.Model;

import java.io.Serializable;

public class FilterPJModel implements Serializable {
    private String periodeAwal;
    private String periodeAkhir;
    private String direktorat;
    private String site;

    public FilterPJModel(String periodeAwal, String periodeAkhir, String direktorat, String site) {
        this.periodeAwal = periodeAwal;
        this.periodeAkhir = periodeAkhir;
        this.direktorat = direktorat;
        this.site = site;
    }


    public String getPeriodeAwal() {
        return periodeAwal;
    }

    public void setPeriodeAwal(String periodeAwal) {
        this.periodeAwal = periodeAwal;
    }

    public String getPeriodeAkhir() {
        return periodeAkhir;
    }

    public void setPeriodeAkhir(String periodeAkhir) {
        this.periodeAkhir = periodeAkhir;
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
}
