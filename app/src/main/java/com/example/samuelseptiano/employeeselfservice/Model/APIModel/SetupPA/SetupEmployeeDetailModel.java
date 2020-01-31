package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;

import java.util.List;

public class SetupEmployeeDetailModel implements Comparable {
    private String id;
    private String empNIK;
    private String empJobTitle;
    private String empPhoto;
    private String paId;
    private String KPIDesc;
    private String bobot;
    private String semester;
    private String KPIType;
    private String KPIperspective;
    private List<KPIHint> kpiHintList;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked=false;

    public SetupEmployeeDetailModel() {
    }

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

    @Override
    public int compareTo(Object o) {
        int compareage=Integer.parseInt(((SetupEmployeeDetailModel)o).getSemester());
        /* For Ascending order*/
        return Integer.parseInt(this.getSemester())-compareage;
    }
}
