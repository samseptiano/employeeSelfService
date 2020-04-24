package com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal;

import com.google.gson.annotations.SerializedName;

public class ApprDNModell {
    @SerializedName("dnapprovalnama")
    private String apprName;
    @SerializedName("dnapprovalnik")
    private String apprNIK;
    @SerializedName("dnapprovalemail")
    private String apprEmail;
    @SerializedName("dnapprovalphoto")
    private String apprPhoto;
    @SerializedName("dnapprovaldate")
    private String apprDate;
    @SerializedName("dnapprovalstatus")
    private String apprStatus;
    @SerializedName("dnhasapproveyn")
    private String fgHasApproveYN;
    @SerializedName("dncanapproveyn")
    private String fgCanApproveYN;
    @SerializedName("dnapprovalseq")
    private String dnApprovalSeq;
    @SerializedName("dnid")
    private String DNID;

    public String getApprName() {
        return apprName;
    }

    public void setApprName(String apprName) {
        this.apprName = apprName;
    }

    public String getApprNIK() {
        return apprNIK;
    }

    public void setApprNIK(String apprNIK) {
        this.apprNIK = apprNIK;
    }

    public String getApprEmail() {
        return apprEmail;
    }

    public void setApprEmail(String apprEmail) {
        this.apprEmail = apprEmail;
    }

    public String getApprPhoto() {
        return apprPhoto;
    }

    public void setApprPhoto(String apprPhoto) {
        this.apprPhoto = apprPhoto;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getFgHasApproveYN() {
        return fgHasApproveYN;
    }

    public void setFgHasApproveYN(String fgHasApproveYN) {
        this.fgHasApproveYN = fgHasApproveYN;
    }

    public String getFgCanApproveYN() {
        return fgCanApproveYN;
    }

    public void setFgCanApproveYN(String fgCanApproveYN) {
        this.fgCanApproveYN = fgCanApproveYN;
    }

    public String getDnApprovalSeq() {
        return dnApprovalSeq;
    }

    public void setDnApprovalSeq(String dnApprovalSeq) {
        this.dnApprovalSeq = dnApprovalSeq;
    }

    public String getDNID() {
        return DNID;
    }

    public void setDNID(String DNID) {
        this.DNID = DNID;
    }

    public ApprDNModell() {
    }
}
