package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import java.util.List;

public class IDPModel {
    private String id;
    private String paTransId;
    private String empNIK;
    private String IDPTitle;
    private List<IDPDetailModel> idpDetailModelList;

    public IDPModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaTransId() {
        return paTransId;
    }

    public void setPaTransId(String paTransId) {
        this.paTransId = paTransId;
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getIDPTitle() {
        return IDPTitle;
    }

    public void setIDPTitle(String IDPTitle) {
        this.IDPTitle = IDPTitle;
    }

    public List<IDPDetailModel> getIdpDetailModelList() {
        return idpDetailModelList;
    }

    public void setIdpDetailModelList(List<IDPDetailModel> idpDetailModelList) {
        this.idpDetailModelList = idpDetailModelList;
    }
}
