package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHint;

import java.util.List;

public class SetupPositionDetailModel implements Comparable{
    private String id;
    private String templateId;
    private String templateJobTitle;
    private String templateOrganisasi;
    private String paId;
    private String KPIDesc;
    private String bobot;
    private String semester;
    private String KPIType;
    private String KPIperspective;
    private List<KPIHint> kpiHintList;
    private boolean isChecked=false;


    public int getCr() {
        return cr;
    }

    public void setCr(int cr) {
        this.cr = cr;
    }

    private int cr;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateJobTitle() {
        return templateJobTitle;
    }

    public void setTemplateJobTitle(String templateJobTitle) {
        this.templateJobTitle = templateJobTitle;
    }

    public String getTemplateOrganisasi() {
        return templateOrganisasi;
    }

    public void setTemplateOrganisasi(String templateOrganisasi) {
        this.templateOrganisasi = templateOrganisasi;
    }

    public String getPaId() {
        return paId;
    }

    public void setPaId(String paId) {
        this.paId = paId;
    }

    public String getKPIDesc() {
        return KPIDesc;
    }

    public void setKPIDesc(String KPIDesc) {
        this.KPIDesc = KPIDesc;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getKPIType() {
        return KPIType;
    }

    public void setKPIType(String KPIType) {
        this.KPIType = KPIType;
    }

    public String getKPIperspective() {
        return KPIperspective;
    }

    public void setKPIperspective(String KPIperspective) {
        this.KPIperspective = KPIperspective;
    }

    public List<KPIHint> getKpiHintList() {
        return kpiHintList;
    }

    public void setKpiHintList(List<KPIHint> kpiHintList) {
        this.kpiHintList = kpiHintList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public SetupPositionDetailModel() {
    }

    @Override
    public int compareTo(Object o) {
        int compareage=Integer.parseInt(((SetupPositionDetailModel)o).getSemester());
        /* For Ascending order*/
        return Integer.parseInt(this.getSemester())-compareage;
    }
}
