package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI;

import com.google.gson.annotations.SerializedName;

public class EmpJobTtlModel {
    @SerializedName("jobttlcode")
    private String jobttlcode;
    @SerializedName("jobttlname")
    private String jobttlname;

    public String getJobttlname() {
        return jobttlname;
    }

    public void setJobttlname(String jobttlname) {
        this.jobttlname = jobttlname;
    }

    public String getJobttlcode() {
        return jobttlcode;
    }

    public void setJobttlcode(String jobttlcode) {
        this.jobttlcode = jobttlcode;
    }

    public EmpJobTtlModel() {
    }
}
