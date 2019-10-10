package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ;

/**
 * Created by Samuel Septiano on 21,May,2019
 */
public class KPIApproveListPJ {
    private String id;
    private String empName;
    private String NIK;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private  String status;
    private String status1;
    private String status2;
    private String dept;
        private String empPhoto;
    private String positionName;
    private String NIKAtasan;
    private String NIKAtasan2;
    private String namaAtasan;
    private String namaAtasan2;
    private String fotoAtasan;
    private String fotoAtasan2;
    private String tanggalMasaKontrakAwal;
    private String tanggalMasaKontrakAkhir;
    private String jobTitle;

    public KPIApproveListPJ(String id, String empName, String NIK, String status1, String status2, String dept, String NIKAtasan, String NIKAtasan2, String namaAtasan, String namaAtasan2, String fotoAtasan, String fotoAtasan2, String tanggalMasaKontrakAwal, String tanggalMasaKontrakAkhir, String jobTitle) {
        this.id = id;
        this.empName = empName;
        this.NIK = NIK;
        this.status1 = status1;
        this.status2 = status2;
        this.dept = dept;
        this.NIKAtasan = NIKAtasan;
        this.NIKAtasan2 = NIKAtasan2;
        this.namaAtasan = namaAtasan;
        this.namaAtasan2 = namaAtasan2;
        this.fotoAtasan = fotoAtasan;
        this.fotoAtasan2 = fotoAtasan2;
        this.tanggalMasaKontrakAwal = tanggalMasaKontrakAwal;
        this.tanggalMasaKontrakAkhir = tanggalMasaKontrakAkhir;
        this.jobTitle = jobTitle;
    }


    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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

    public String getNamaAtasan() {
        return namaAtasan;
    }

    public void setNamaAtasan(String namaAtasan) {
        this.namaAtasan = namaAtasan;
    }

    public String getNamaAtasan2() {
        return namaAtasan2;
    }

    public void setNamaAtasan2(String namaAtasan2) {
        this.namaAtasan2 = namaAtasan2;
    }

    public String getFotoAtasan() {
        return fotoAtasan;
    }

    public void setFotoAtasan(String fotoAtasan) {
        this.fotoAtasan = fotoAtasan;
    }

    public String getFotoAtasan2() {
        return fotoAtasan2;
    }

    public void setFotoAtasan2(String fotoAtasan2) {
        this.fotoAtasan2 = fotoAtasan2;
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
