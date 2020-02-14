package com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal;

import java.util.List;

public class PerhitunganPAEMPModel {

    private int starDN;
    private int starAdj;
    private String empName;
    private String empJobTitle;
    private String empPhoto;
    private String empEmail;
    private String empNIK;
    private String empBranchName;
    private String score;
    private String nilaiDN;
    private String nilaiAdj;
    private String alasanAdj;
    private String alasanReject;
    private List<ApprDNModell> apprDNModellList;

    public int getStarDN() {
        return starDN;
    }

    public void setStarDN(int starDN) {
        this.starDN = starDN;
    }

    public int getStarAdj() {
        return starAdj;
    }

    public void setStarAdj(int starAdj) {
        this.starAdj = starAdj;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getEmpBranchName() {
        return empBranchName;
    }

    public void setEmpBranchName(String empBranchName) {
        this.empBranchName = empBranchName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNilaiDN() {
        return nilaiDN;
    }

    public void setNilaiDN(String nilaiDN) {
        this.nilaiDN = nilaiDN;
    }

    public String getNilaiAdj() {
        return nilaiAdj;
    }

    public void setNilaiAdj(String nilaiAdj) {
        this.nilaiAdj = nilaiAdj;
    }

    public String getAlasanAdj() {
        return alasanAdj;
    }

    public void setAlasanAdj(String alasanAdj) {
        this.alasanAdj = alasanAdj;
    }

    public String getAlasanReject() {
        return alasanReject;
    }

    public void setAlasanReject(String alasanReject) {
        this.alasanReject = alasanReject;
    }

    public List<ApprDNModell> getApprDNModellList() {
        return apprDNModellList;
    }

    public void setApprDNModellList(List<ApprDNModell> apprDNModellList) {
        this.apprDNModellList = apprDNModellList;
    }

    public PerhitunganPAEMPModel() {
    }
}
