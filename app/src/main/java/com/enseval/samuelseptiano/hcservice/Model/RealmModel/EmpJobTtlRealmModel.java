package com.enseval.samuelseptiano.hcservice.Model.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EmpJobTtlRealmModel extends RealmObject {
    @PrimaryKey
    private String id;
    private String jobttlcode;
    private String jobttlname;

    public EmpJobTtlRealmModel() {
    }

    public EmpJobTtlRealmModel(String id, String jobttlcode, String jobttlname) {
        this.id = id;
        this.jobttlcode = jobttlcode;
        this.jobttlname = jobttlname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobttlcode() {
        return jobttlcode;
    }

    public void setJobttlcode(String jobttlcode) {
        this.jobttlcode = jobttlcode;
    }

    public String getJobttlname() {
        return jobttlname;
    }

    public void setJobttlname(String jobttlname) {
        this.jobttlname = jobttlname;
    }
}
