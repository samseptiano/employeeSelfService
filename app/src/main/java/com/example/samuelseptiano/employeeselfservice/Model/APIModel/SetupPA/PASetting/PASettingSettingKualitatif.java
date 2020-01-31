package com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting;

import com.google.gson.annotations.SerializedName;

public class PASettingSettingKualitatif {
    @SerializedName("tempcompid")
    private String tempcompid;
    @SerializedName("joblvlgroupcode")
    private String joblvlgroupcode;

    public String getTempcompid() {
        return tempcompid;
    }

    public void setTempcompid(String tempcompid) {
        this.tempcompid = tempcompid;
    }

    public String getJoblvlgroupcode() {
        return joblvlgroupcode;
    }

    public void setJoblvlgroupcode(String joblvlgroupcode) {
        this.joblvlgroupcode = joblvlgroupcode;
    }

    public PASettingSettingKualitatif() {
    }
}
