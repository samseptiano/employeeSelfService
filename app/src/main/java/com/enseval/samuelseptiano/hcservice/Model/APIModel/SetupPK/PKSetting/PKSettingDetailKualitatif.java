package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting;

import com.google.gson.annotations.SerializedName;

public class PKSettingDetailKualitatif implements Comparable {
    @SerializedName("bobot")
    private String bobot;
    @SerializedName("compid")
    private String compID;
    @SerializedName("cr")
    private String cr;
    @SerializedName("semester")
    private String semester;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private boolean checked;

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getCompID() {
        return compID;
    }

    public void setCompID(String compID) {
        this.compID = compID;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public PKSettingDetailKualitatif() {
    }

    @Override
    public int compareTo(Object o) {
        int compareage=Integer.parseInt(((PKSettingDetailKualitatif)o).getCompID());
        /* For Ascending order*/
        return Integer.parseInt(this.getCompID())-compareage;
    }
}
