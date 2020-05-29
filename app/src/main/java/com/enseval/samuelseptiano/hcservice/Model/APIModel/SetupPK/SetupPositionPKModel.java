package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK;

import java.util.List;


public class SetupPositionPKModel {
    private String id;
    private String positionName;
    private String year;
    private List<String> jabatan;
    private List<String> Organisasi;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;

    public List<String> getJabatan() {
        return jabatan;
    }

    public void setJabatan(List<String> jabatan) {
        this.jabatan = jabatan;
    }

    public List<String> getOrganisasi() {
        return Organisasi;
    }

    public void setOrganisasi(List<String> organisasi) {
        Organisasi = organisasi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public SetupPositionPKModel() {
    }
}
