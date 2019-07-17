package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

public class KPIApproveList {
    private String id;
    private String empName;
    private String NIK;
    private String status1;
    private String status2;
    private String dept;
    private String NIKAtasan1;
    private String NIKAtasan2;
    private String jobTitle;

    public KPIApproveList() {
    }

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

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNIKAtasan1() {
        return NIKAtasan1;
    }

    public void setNIKAtasan1(String NIKAtasan1) {
        this.NIKAtasan1 = NIKAtasan1;
    }

    public String getNIKAtasan2() {
        return NIKAtasan2;
    }

    public void setNIKAtasan2(String NIKAtasan2) {
        this.NIKAtasan2 = NIKAtasan2;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
