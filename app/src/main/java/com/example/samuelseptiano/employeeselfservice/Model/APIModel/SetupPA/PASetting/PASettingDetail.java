package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting;

import com.google.gson.annotations.SerializedName;

public class PASettingDetail implements Comparable {
    @SerializedName("bobot")
    private String bobot;
    @SerializedName("kpiid")
    private String kpiID;
    @SerializedName("semester")
    private String semester;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getKpiID() {
        return kpiID;
    }

    public void setKpiID(String kpiID) {
        this.kpiID = kpiID;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public PASettingDetail() {
    }

    @Override
    public int compareTo(Object o) {
        int compareage=Integer.parseInt(((PASettingDetail)o).getSemester());
        /* For Ascending order*/
        return Integer.parseInt(this.getSemester())-compareage;
    }
}
