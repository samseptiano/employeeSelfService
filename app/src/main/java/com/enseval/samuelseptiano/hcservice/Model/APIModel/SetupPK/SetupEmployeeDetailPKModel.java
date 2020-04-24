package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHint;

import java.util.List;

public class SetupEmployeeDetailPKModel implements Comparable {
    private String id;
    private String empNIK;
    private String empJobTitle;
    private String empPhoto;
    private String empType;
    private String periodeAwal;
    private String periodeAkhir;
    private String pkId;
    private String KPIDesc;
    private String bobot;
    private String KPIType;
    private String KPIperspective;
    private List<KPIHint> kpiHintList;
    private boolean isChecked=false;

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

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getEmpJobTitle() {
        return empJobTitle;
    }

    public void setEmpJobTitle(String empJobTitle) {
        this.empJobTitle = empJobTitle;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
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

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
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

    public SetupEmployeeDetailPKModel() {
    }

    @Override
    public int compareTo(Object o) {
        int compareage=Integer.parseInt(((SetupEmployeeDetailPKModel)o).getId());
        /* For Ascending order*/
        return Integer.parseInt(this.getId())-compareage;
    }
}
