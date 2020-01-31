package com.example.samuelseptiano.employeeselfservice.Model.RealmModel;

import io.realm.RealmObject;

public class ModuleRealmModel extends RealmObject {
    private String id;
    private String moduleCode;
    private String moduleName;
    private String icon;

    public ModuleRealmModel(String id, String moduleCode, String moduleName, String icon) {
        this.id = id;
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public ModuleRealmModel() {
    }

}
