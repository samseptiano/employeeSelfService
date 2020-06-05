package com.enseval.samuelseptiano.hcservice.Model.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ConfigRealmModel extends RealmObject {

    @PrimaryKey
    private String id;
    private String url;

    public ConfigRealmModel() {
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

    public ConfigRealmModel(String id, String url) {
        this.id = id;
        this.url = url;
    }


}
