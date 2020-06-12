package com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting;

import com.google.gson.annotations.SerializedName;

public class KPISettingSettingResponse {

    @SerializedName("result")
    public String result;
    @SerializedName("namatemplate")
    public String namaTemplate;

    public KPISettingSettingResponse() {
    }

    public KPISettingSettingResponse(String result, String namaTemplate) {
        this.result = result;
        this.namaTemplate = namaTemplate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNamaTemplate() {
        return namaTemplate;
    }

    public void setNamaTemplate(String namaTemplate) {
        this.namaTemplate = namaTemplate;
    }
}
