package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA;

import java.util.List;


public class SetupPositionKualitatifModel {
    private String id;
    private String positionName;
    private String year;
    private List<String> golongan;
    private String tabDab;
    private boolean isChecked;

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

    public List<String> getGolongan() {
        return golongan;
    }

    public void setGolongan(List<String> golongan) {
        this.golongan = golongan;
    }

    public String getTabDab() {
        return tabDab;
    }

    public void setTabDab(String tabDab) {
        this.tabDab = tabDab;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public SetupPositionKualitatifModel() {
    }
}
