package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHint;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Samuel Septiano on 24,May,2019
 */
public class KPIQuestionsPJ {
        private String idHeader;
    @SerializedName("transid")
    private String transId;

    public String getKpiNo() {
        return kpiNo;
    }

    public void setKpiNo(String kpiNo) {
        this.kpiNo = kpiNo;
    }

    @SerializedName("kpino")
    private String kpiNo;
    @SerializedName("kpiDesc")
    private String KPIDesc;
    @SerializedName("pK_ViewTransGrades")
    private List<KPIHint> hint;
    @SerializedName("bobot")
    private String bobot;
    @SerializedName("nilai")
    private String nilai;
    @SerializedName("cr")
    private String CR;
    @SerializedName("gradescore")
    private String compRate;
    @SerializedName("kpIcategory")
    private String KPIcategory;
        private String perspective;

    public KPIQuestionsPJ(String transId, String KPIDesc, List<KPIHint> hint, String bobot, String CR, String compRate, String KPIcategory, String perspective) {
        this.transId = transId;
        this.KPIDesc = KPIDesc;
        this.hint = hint;
        this.bobot = bobot;
        this.CR = CR;
        this.compRate = compRate;
        this.KPIcategory = KPIcategory;
        this.perspective = perspective;
    }

    public KPIQuestionsPJ() {
    }


    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }



    public String getIdHeader() {
        return idHeader;
    }

    public void setIdHeader(String idHeader) {
        this.idHeader = idHeader;
    }

    public String getCompRate() {
        return compRate;
    }

    public void setCompRate(String compRate) {
        this.compRate = compRate;
    }

    public String getCR() {
        return CR;
    }

    public void setCR(String CR) {
        this.CR = CR;
    }

    public String getPerspective() {
            return perspective;
        }

        public void setPerspective(String perspective) {
            this.perspective = perspective;
        }


        public String getTransId() {
            return transId;
        }

        public void setTransId(String transId) {
            this.transId = transId;
        }

        public String getKPIDesc() {
            return KPIDesc;
        }

        public void setKPIDesc(String KPIDesc) {
            this.KPIDesc = KPIDesc;
        }

        public List<KPIHint> getHint() {
            return hint;
        }

        public void setHint(List<KPIHint> hint) {
            this.hint = hint;
        }

        public String getBobot() {
            return bobot;
        }

        public void setBobot(String bobot) {
            this.bobot = bobot;
        }

        public String getKPIcategory() {
            return KPIcategory;
        }

        public void setKPIcategory(String KPIcategory) {
            this.KPIcategory = KPIcategory;
        }


        private int checkedId = -1;
        private boolean isAnswered;
    @SerializedName("pK_FileEvidences")
        private List<ImageUploadModel> photoCapture;
    @SerializedName("evidence")
    private String evidence="";

    public boolean isAnswerenEvidence() {
        return isAnswerenEvidence;
    }

    public void setAnswerenEvidence(boolean answerenEvidence) {
        isAnswerenEvidence = answerenEvidence;
    }

    private boolean isAnswerenEvidence;

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }




        public List<ImageUploadModel> getPhotoCapture() {
            return photoCapture;
        }

        public void setPhotoCapture(List<ImageUploadModel> photoCapture) {
            this.photoCapture = photoCapture;
        }



        public int getCheckedId() {
            return checkedId;
        }

        public void setCheckedId(int checkedId) {
            this.checkedId = checkedId;
        }

        public boolean isAnswered() {
            return isAnswered;
        }

        public void setAnswered(boolean answered) {
            isAnswered = answered;
        }
    }


