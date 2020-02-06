package com.example.samuelseptiano.employeeselfservice.Model;

import com.google.gson.annotations.SerializedName;

public class ModuleModel {
    private String id;
    @SerializedName("modulecode")
    private String moduleCode;
    @SerializedName("moduledesc")
    private String moduleName;
    @SerializedName("icon")
    private String icon;
    @SerializedName("modulestatus")
    private String moduleStatus;

    public ModuleModel(String id, String moduleCode, String moduleName, String icon, String moduleStatus) {
        this.id = id;
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.icon = icon;
        this.moduleStatus = moduleStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(String moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    public ModuleModel() {
    }

}
