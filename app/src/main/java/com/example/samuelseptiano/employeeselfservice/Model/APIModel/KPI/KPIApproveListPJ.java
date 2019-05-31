package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

/**
 * Created by Samuel Septiano on 21,May,2019
 */
public class KPIApproveListPJ {
    private String id;
    private String empName;
    private String NIK;
    private String status;
    private String dept;
    private String NIKAtasan;
    private String NIKAtasan2;
    private String tanggalMasaKontrakAwal;
    private String tanggalMasaKontrakAkhir;
    private String jobTitle;

    public KPIApproveListPJ(String id, String empName, String NIK, String status, String dept, String NIKAtasan, String NIKAtasan2, String tanggalMasaKontrakAwal, String tanggalMasaKontrakAkhir, String jobTitle) {
        this.id = id;
        this.empName = empName;
        this.NIK = NIK;
        this.status = status;
        this.dept = dept;
        this.NIKAtasan = NIKAtasan;
        this.NIKAtasan2 = NIKAtasan2;
        this.tanggalMasaKontrakAwal = tanggalMasaKontrakAwal;
        this.tanggalMasaKontrakAkhir = tanggalMasaKontrakAkhir;
        this.jobTitle = jobTitle;
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

    public String getNIKAtasan2() {
        return NIKAtasan2;
    }

    public void setNIKAtasan2(String NIKAtasan2) {
        this.NIKAtasan2 = NIKAtasan2;
    }

    public String getTanggalMasaKontrakAwal() {
        return tanggalMasaKontrakAwal;
    }

    public void setTanggalMasaKontrakAwal(String tanggalMasaKontrakAwal) {
        this.tanggalMasaKontrakAwal = tanggalMasaKontrakAwal;
    }

    public String getTanggalMasaKontrakAkhir() {
        return tanggalMasaKontrakAkhir;
    }

    public void setTanggalMasaKontrakAkhir(String tanggalMasaKontrakAkhir) {
        this.tanggalMasaKontrakAkhir = tanggalMasaKontrakAkhir;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public KPIApproveListPJ() {
    }
}
