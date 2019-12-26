package com.example.samuelseptiano.employeeselfservice.Model;

import com.google.gson.annotations.SerializedName;

public class VersionModel {
    @SerializedName("id")
    private String id;
    @SerializedName("version")
    private String version;
    @SerializedName("releaseDate")
    private String releaseDate;

    public VersionModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
