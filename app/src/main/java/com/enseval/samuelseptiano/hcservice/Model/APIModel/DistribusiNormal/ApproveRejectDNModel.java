package com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal;

import com.google.gson.annotations.SerializedName;

public class ApproveRejectDNModel {
    @SerializedName("dnid")
    private String DNID;
    @SerializedName("dnapprovalseq")
    private String DNAPPROVALSEQ;
    @SerializedName("empnik")
    private String EMPNIK;
    @SerializedName("upduser")
    private String UPDUSER;
    @SerializedName("alasanreject")
    private String ALASANREJECT;

    public ApproveRejectDNModel() {
    }

    public String getDNID() {
        return DNID;
    }

    public void setDNID(String DNID) {
        this.DNID = DNID;
    }

    public String getDNAPPROVALSEQ() {
        return DNAPPROVALSEQ;
    }

    public void setDNAPPROVALSEQ(String DNAPPROVALSEQ) {
        this.DNAPPROVALSEQ = DNAPPROVALSEQ;
    }

    public String getEMPNIK() {
        return EMPNIK;
    }

    public void setEMPNIK(String EMPNIK) {
        this.EMPNIK = EMPNIK;
    }

    public String getUPDUSER() {
        return UPDUSER;
    }

    public void setUPDUSER(String UPDUSER) {
        this.UPDUSER = UPDUSER;
    }

    public String getALASANREJECT() {
        return ALASANREJECT;
    }

    public void setALASANREJECT(String ALASANREJECT) {
        this.ALASANREJECT = ALASANREJECT;
    }
}
