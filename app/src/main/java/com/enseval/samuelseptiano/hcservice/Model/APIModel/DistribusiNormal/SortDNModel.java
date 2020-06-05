package com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal;

public class SortDNModel {
    private String sortBy;
    private String order;

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    private int ordinal;

    public SortDNModel() {
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
