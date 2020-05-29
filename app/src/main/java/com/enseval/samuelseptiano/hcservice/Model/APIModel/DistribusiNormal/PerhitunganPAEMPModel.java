package com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PerhitunganPAEMPModel {

    @SerializedName("dnid")
    private String DNID;
    @SerializedName("empname")
    private String EMPNAME;
    @SerializedName("empnik")
    private String EMPNIK;
    @SerializedName("jobttlname")
    private String JOBTTLNAME;
    @SerializedName("orgname")
    private String ORGNAME;
    @SerializedName("dnscore")
    private String DNSCORE;
    @SerializedName("dnnilai")
    private String DNNILAI;
    @SerializedName("dnnilaiadjustment")
    private String DNNILAIADJUSTMENT;
    @SerializedName("dnnilaifinal")
    private String DNNILAIFINAL;
    @SerializedName("angkanilai")
    private String ANGKANILAI;
    @SerializedName("angkanilaiadjustment")
    private String ANGKANILAIADJUSTMENT;
    @SerializedName("angkanilaifinal")
    private String ANGKANILAIFINAL;
    @SerializedName("dnalasanrevisi")
    private String ALASANREVISI;

    public String getALASANREVISI() {
        return ALASANREVISI;
    }

    public void setALASANREVISI(String ALASANREVISI) {
        this.ALASANREVISI = ALASANREVISI;
    }


    public String getANGKANILAI() {
        return ANGKANILAI;
    }

    public void setANGKANILAI(String ANGKANILAI) {
        this.ANGKANILAI = ANGKANILAI;
    }

    public String getANGKANILAIADJUSTMENT() {
        return ANGKANILAIADJUSTMENT;
    }

    public void setANGKANILAIADJUSTMENT(String ANGKANILAIADJUSTMENT) {
        this.ANGKANILAIADJUSTMENT = ANGKANILAIADJUSTMENT;
    }

    public String getANGKANILAIFINAL() {
        return ANGKANILAIFINAL;
    }

    public void setANGKANILAIFINAL(String ANGKANILAIFINAL) {
        this.ANGKANILAIFINAL = ANGKANILAIFINAL;
    }

    public String getDNID() {
        return DNID;
    }

    public void setDNID(String DNID) {
        this.DNID = DNID;
    }

    public String getEMPNAME() {
        return EMPNAME;
    }

    public void setEMPNAME(String EMPNAME) {
        this.EMPNAME = EMPNAME;
    }

    public String getEMPNIK() {
        return EMPNIK;
    }

    public void setEMPNIK(String EMPNIK) {
        this.EMPNIK = EMPNIK;
    }

    public String getJOBTTLNAME() {
        return JOBTTLNAME;
    }

    public void setJOBTTLNAME(String JOBTTLNAME) {
        this.JOBTTLNAME = JOBTTLNAME;
    }

    public String getORGNAME() {
        return ORGNAME;
    }

    public void setORGNAME(String ORGNAME) {
        this.ORGNAME = ORGNAME;
    }

    public String getDNSCORE() {
        return DNSCORE;
    }

    public void setDNSCORE(String DNSCORE) {
        this.DNSCORE = DNSCORE;
    }

    public String getDNNILAI() {
        return DNNILAI;
    }

    public void setDNNILAI(String DNNILAI) {
        this.DNNILAI = DNNILAI;
    }

    public String getDNNILAIADJUSTMENT() {
        return DNNILAIADJUSTMENT;
    }

    public void setDNNILAIADJUSTMENT(String DNNILAIADJUSTMENT) {
        this.DNNILAIADJUSTMENT = DNNILAIADJUSTMENT;
    }

    public String getDNNILAIFINAL() {
        return DNNILAIFINAL;
    }

    public void setDNNILAIFINAL(String DNNILAIFINAL) {
        this.DNNILAIFINAL = DNNILAIFINAL;
    }

    public PerhitunganPAEMPModel() {
    }
}
