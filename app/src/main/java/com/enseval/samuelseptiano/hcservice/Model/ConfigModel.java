package com.enseval.samuelseptiano.hcservice.Model;

public class ConfigModel {
    private String id;
    private String url;

    public ConfigModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ConfigModel(String id, String url) {
        this.id = id;
        this.url = url;
    }

}
