package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samuel Septiano on 30,August,2019
 */
public class KPIHintPJ {

    public KPIHintPJ() {
    }

    @SerializedName("hint")
    private String hint;

    @SerializedName("kpino")
    private String kpino;

    @SerializedName("transid")
    private String transid;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getKpino() {
        return kpino;
    }

    public void setKpino(String kpino) {
        this.kpino = kpino;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }
}
