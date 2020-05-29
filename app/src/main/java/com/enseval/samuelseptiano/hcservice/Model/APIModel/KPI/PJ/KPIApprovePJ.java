package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

/**
 * Created by Samuel Septiano on 02,September,2019
 */
public class KPIApprovePJ {
    private String empNIK;
    private String transID;

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public KPIApprovePJ() {
    }

    public KPIApprovePJ(String empNIK, String transID) {
        this.empNIK = empNIK;
        this.transID = transID;
    }
}
