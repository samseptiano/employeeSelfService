package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI;

import com.google.gson.annotations.SerializedName;

public class EmpJobLvlModel {
    @SerializedName("joblvlcode")
    private String joblvlcode;
    @SerializedName("joblvlgrpid")
    private String joblvlgrpid;
    @SerializedName("joblvllevel")
    private String joblvllevel;

    public String getJoblvlcode() {
        return joblvlcode;
    }

    public void setJoblvlcode(String joblvlcode) {
        this.joblvlcode = joblvlcode;
    }

    public String getJoblvlgrpid() {
        return joblvlgrpid;
    }

    public void setJoblvlgrpid(String joblvlgrpid) {
        this.joblvlgrpid = joblvlgrpid;
    }

    public String getJoblvllevel() {
        return joblvllevel;
    }

    public void setJoblvllevel(String joblvllevel) {
        this.joblvllevel = joblvllevel;
    }

    public EmpJobLvlModel() {
    }
}
