package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

public class KPIApproveList {
    private String id;
    private String empName;
    private String NIK;
    private String status;
    private String dept;
    private String NIKAtasan;
    private String jobTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNIKAtasan() {
        return NIKAtasan;
    }

    public void setNIKAtasan(String NIKAtasan) {
        this.NIKAtasan = NIKAtasan;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public KPIApproveList() {
    }

    public KPIApproveList(String id, String empName, String NIK, String status, String dept, String NIKAtasan, String jobTitle) {
        this.id = id;
        this.empName = empName;
        this.NIK = NIK;
        this.status = status;
        this.dept = dept;
        this.NIKAtasan = NIKAtasan;
        this.jobTitle = jobTitle;
    }
}
