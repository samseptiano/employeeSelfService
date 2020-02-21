package com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistNormalH {

    @SerializedName("empnik")
    private String EMPNIK ;
    @SerializedName("privilegecode")
    private String PRIVILEGECODE;
    @SerializedName("dnid")
    private String DNID ;
    @SerializedName("dnyear")
    private String DNYEAR ;
    @SerializedName("dngroupname")
    private String DNGROUPNAME ;
    @SerializedName("dnstatus")
    private String DNSTATUS ;
    @SerializedName("dnmean")
    private String DNMEAN;
    @SerializedName("dnstdev")
    private String DNSTDEV;
    @SerializedName("dnbak")
    private String DNBAK;
    @SerializedName("jumlahdnbak")
    private String JUMLAHDNBAK;
    @SerializedName("persendnbak")
    private String PERSENDNBAK;
    @SerializedName("jumlahdnbakadj")
    private String JUMLAHDNBAKADJ;
    @SerializedName("persendnbakadj")
    private String PERSENDNBAKADJ;
    @SerializedName("dnbaa")
    private String DNBAA;
    @SerializedName("jumlahdnbaa")
    private String JUMLAHDNBAA;
    @SerializedName("persendnbaa")
    private String PERSENDNBAA;
    @SerializedName("jumlahdnbaaadj")
    private String JUMLAHDNBAAADJ;
    @SerializedName("persendnbaaadj")
    private String PERSENDNBAAADJ;
    @SerializedName("dnbal")
    private String DNBAL;
    @SerializedName("jumlahdnbal")
    private String JUMLAHDNBAL;
    @SerializedName("persendnbal")
    private String PERSENDNBAL;
    @SerializedName("jumlahdnbaladj")
    private String JUMLAHDNBALADJ;
    @SerializedName("persendnbaladj")
    private String PERSENDNBALADJ;
    @SerializedName("dnbab")
    private String DNBAB;
    @SerializedName("jumlahdnbab")
    private String JUMLAHDNBAB;
    @SerializedName("persendnbab")
    private String PERSENDNBAB;
    @SerializedName("jumlahdnbabadj")
    private String JUMLAHDNBABADJ;
    @SerializedName("persendnbabadj")
    private String PERSENDNBABADJ;
    @SerializedName("dnbae")
    private String DNBAE;
    @SerializedName("jumlahdnbae")
    private String JUMLAHDNBAE;
    @SerializedName("persendnbae")
    private String PERSENDNBAE;
    @SerializedName("jumlahdnbaeadj")
    private String JUMLAHDNBAEADJ;
    @SerializedName("persendnbaeadj")
    private String PERSENDNBAEADJ;
    @SerializedName("nilaitotal")
    private String NILAITOTAL;
    @SerializedName("nilaiadjtotal")
    private String  NILAIADJTOTAL;
    @SerializedName("nilaifinaltotal")
    private String NILAIFINALTOTAL;

    @SerializedName("rangednk")
    private String RANGEDNK;
    @SerializedName("rangedna")
    private String RANGEDNA;
    @SerializedName("rangednl")
    private String RANGEDNL;
    @SerializedName("rangednb")
    private String RANGEDNB;
    @SerializedName("rangedne")
    private String RANGEDNE;

    @SerializedName("distNormalDList")
    private List<PerhitunganPAEMPModel> distNormalDList;
    @SerializedName("distNormalApprList")
    private List<ApprDNModell> distNormalApprList ;

    public String getEMPNIK() {
        return EMPNIK;
    }

    public void setEMPNIK(String EMPNIK) {
        this.EMPNIK = EMPNIK;
    }

    public String getPRIVILEGECODE() {
        return PRIVILEGECODE;
    }

    public void setPRIVILEGECODE(String PRIVILEGECODE) {
        this.PRIVILEGECODE = PRIVILEGECODE;
    }

    public String getDNID() {
        return DNID;
    }

    public void setDNID(String DNID) {
        this.DNID = DNID;
    }

    public String getDNYEAR() {
        return DNYEAR;
    }

    public void setDNYEAR(String DNYEAR) {
        this.DNYEAR = DNYEAR;
    }

    public String getDNGROUPNAME() {
        return DNGROUPNAME;
    }

    public void setDNGROUPNAME(String DNGROUPNAME) {
        this.DNGROUPNAME = DNGROUPNAME;
    }

    public String getDNSTATUS() {
        return DNSTATUS;
    }

    public void setDNSTATUS(String DNSTATUS) {
        this.DNSTATUS = DNSTATUS;
    }

    public String getDNMEAN() {
        return DNMEAN;
    }

    public void setDNMEAN(String DNMEAN) {
        this.DNMEAN = DNMEAN;
    }

    public String getDNSTDEV() {
        return DNSTDEV;
    }

    public void setDNSTDEV(String DNSTDEV) {
        this.DNSTDEV = DNSTDEV;
    }

    public String getDNBAK() {
        return DNBAK;
    }

    public void setDNBAK(String DNBAK) {
        this.DNBAK = DNBAK;
    }

    public String getJUMLAHDNBAK() {
        return JUMLAHDNBAK;
    }

    public void setJUMLAHDNBAK(String JUMLAHDNBAK) {
        this.JUMLAHDNBAK = JUMLAHDNBAK;
    }

    public String getPERSENDNBAK() {
        return PERSENDNBAK;
    }

    public void setPERSENDNBAK(String PERSENDNBAK) {
        this.PERSENDNBAK = PERSENDNBAK;
    }

    public String getJUMLAHDNBAKADJ() {
        return JUMLAHDNBAKADJ;
    }

    public void setJUMLAHDNBAKADJ(String JUMLAHDNBAKADJ) {
        this.JUMLAHDNBAKADJ = JUMLAHDNBAKADJ;
    }

    public String getPERSENDNBAKADJ() {
        return PERSENDNBAKADJ;
    }

    public void setPERSENDNBAKADJ(String PERSENDNBAKADJ) {
        this.PERSENDNBAKADJ = PERSENDNBAKADJ;
    }

    public String getDNBAA() {
        return DNBAA;
    }

    public void setDNBAA(String DNBAA) {
        this.DNBAA = DNBAA;
    }

    public String getJUMLAHDNBAA() {
        return JUMLAHDNBAA;
    }

    public void setJUMLAHDNBAA(String JUMLAHDNBAA) {
        this.JUMLAHDNBAA = JUMLAHDNBAA;
    }

    public String getPERSENDNBAA() {
        return PERSENDNBAA;
    }

    public void setPERSENDNBAA(String PERSENDNBAA) {
        this.PERSENDNBAA = PERSENDNBAA;
    }

    public String getJUMLAHDNBAAADJ() {
        return JUMLAHDNBAAADJ;
    }

    public void setJUMLAHDNBAAADJ(String JUMLAHDNBAAADJ) {
        this.JUMLAHDNBAAADJ = JUMLAHDNBAAADJ;
    }

    public String getPERSENDNBAAADJ() {
        return PERSENDNBAAADJ;
    }

    public void setPERSENDNBAAADJ(String PERSENDNBAAADJ) {
        this.PERSENDNBAAADJ = PERSENDNBAAADJ;
    }

    public String getDNBAL() {
        return DNBAL;
    }

    public void setDNBAL(String DNBAL) {
        this.DNBAL = DNBAL;
    }

    public String getJUMLAHDNBAL() {
        return JUMLAHDNBAL;
    }

    public void setJUMLAHDNBAL(String JUMLAHDNBAL) {
        this.JUMLAHDNBAL = JUMLAHDNBAL;
    }

    public String getPERSENDNBAL() {
        return PERSENDNBAL;
    }

    public void setPERSENDNBAL(String PERSENDNBAL) {
        this.PERSENDNBAL = PERSENDNBAL;
    }

    public String getJUMLAHDNBALADJ() {
        return JUMLAHDNBALADJ;
    }

    public void setJUMLAHDNBALADJ(String JUMLAHDNBALADJ) {
        this.JUMLAHDNBALADJ = JUMLAHDNBALADJ;
    }

    public String getPERSENDNBALADJ() {
        return PERSENDNBALADJ;
    }

    public void setPERSENDNBALADJ(String PERSENDNBALADJ) {
        this.PERSENDNBALADJ = PERSENDNBALADJ;
    }

    public String getDNBAB() {
        return DNBAB;
    }

    public void setDNBAB(String DNBAB) {
        this.DNBAB = DNBAB;
    }

    public String getJUMLAHDNBAB() {
        return JUMLAHDNBAB;
    }

    public void setJUMLAHDNBAB(String JUMLAHDNBAB) {
        this.JUMLAHDNBAB = JUMLAHDNBAB;
    }

    public String getPERSENDNBAB() {
        return PERSENDNBAB;
    }

    public void setPERSENDNBAB(String PERSENDNBAB) {
        this.PERSENDNBAB = PERSENDNBAB;
    }

    public String getJUMLAHDNBABADJ() {
        return JUMLAHDNBABADJ;
    }

    public void setJUMLAHDNBABADJ(String JUMLAHDNBABADJ) {
        this.JUMLAHDNBABADJ = JUMLAHDNBABADJ;
    }

    public String getPERSENDNBABADJ() {
        return PERSENDNBABADJ;
    }

    public void setPERSENDNBABADJ(String PERSENDNBABADJ) {
        this.PERSENDNBABADJ = PERSENDNBABADJ;
    }

    public String getDNBAE() {
        return DNBAE;
    }

    public void setDNBAE(String DNBAE) {
        this.DNBAE = DNBAE;
    }

    public String getJUMLAHDNBAE() {
        return JUMLAHDNBAE;
    }

    public void setJUMLAHDNBAE(String JUMLAHDNBAE) {
        this.JUMLAHDNBAE = JUMLAHDNBAE;
    }

    public String getPERSENDNBAE() {
        return PERSENDNBAE;
    }

    public void setPERSENDNBAE(String PERSENDNBAE) {
        this.PERSENDNBAE = PERSENDNBAE;
    }

    public String getJUMLAHDNBAEADJ() {
        return JUMLAHDNBAEADJ;
    }

    public void setJUMLAHDNBAEADJ(String JUMLAHDNBAEADJ) {
        this.JUMLAHDNBAEADJ = JUMLAHDNBAEADJ;
    }

    public String getPERSENDNBAEADJ() {
        return PERSENDNBAEADJ;
    }

    public void setPERSENDNBAEADJ(String PERSENDNBAEADJ) {
        this.PERSENDNBAEADJ = PERSENDNBAEADJ;
    }

    public String getNILAITOTAL() {
        return NILAITOTAL;
    }

    public void setNILAITOTAL(String NILAITOTAL) {
        this.NILAITOTAL = NILAITOTAL;
    }

    public String getNILAIADJTOTAL() {
        return NILAIADJTOTAL;
    }

    public void setNILAIADJTOTAL(String NILAIADJTOTAL) {
        this.NILAIADJTOTAL = NILAIADJTOTAL;
    }

    public String getNILAIFINALTOTAL() {
        return NILAIFINALTOTAL;
    }

    public void setNILAIFINALTOTAL(String NILAIFINALTOTAL) {
        this.NILAIFINALTOTAL = NILAIFINALTOTAL;
    }

    public String getRANGEDNK() {
        return RANGEDNK;
    }

    public void setRANGEDNK(String RANGEDNK) {
        this.RANGEDNK = RANGEDNK;
    }

    public String getRANGEDNA() {
        return RANGEDNA;
    }

    public void setRANGEDNA(String RANGEDNA) {
        this.RANGEDNA = RANGEDNA;
    }

    public String getRANGEDNL() {
        return RANGEDNL;
    }

    public void setRANGEDNL(String RANGEDNL) {
        this.RANGEDNL = RANGEDNL;
    }

    public String getRANGEDNB() {
        return RANGEDNB;
    }

    public void setRANGEDNB(String RANGEDNB) {
        this.RANGEDNB = RANGEDNB;
    }

    public String getRANGEDNE() {
        return RANGEDNE;
    }

    public void setRANGEDNE(String RANGEDNE) {
        this.RANGEDNE = RANGEDNE;
    }

    public List<PerhitunganPAEMPModel> getDistNormalDList() {
        return distNormalDList;
    }

    public void setDistNormalDList(List<PerhitunganPAEMPModel> distNormalDList) {
        this.distNormalDList = distNormalDList;
    }

    public List<ApprDNModell> getDistNormalApprList() {
        return distNormalApprList;
    }

    public void setDistNormalApprList(List<ApprDNModell> distNormalApprList) {
        this.distNormalApprList = distNormalApprList;
    }

    public DistNormalH() {
    }

}
