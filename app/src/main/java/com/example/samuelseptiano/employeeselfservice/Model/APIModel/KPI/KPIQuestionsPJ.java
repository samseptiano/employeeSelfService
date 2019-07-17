package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI;

import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;

import java.util.List;

/**
 * Created by Samuel Septiano on 24,May,2019
 */
public class KPIQuestionsPJ {

        private String idKPI;
        private String KPIDesc;
        private List<String> hint;
        private String bobot;
        private String CR;
        private String KPIcategory;
        private String perspective;

    public KPIQuestionsPJ(String idKPI, String KPIDesc, List<String> hint, String bobot, String CR, String KPIcategory, String perspective) {
        this.idKPI = idKPI;
        this.KPIDesc = KPIDesc;
        this.hint = hint;
        this.bobot = bobot;
        this.CR = CR;
        this.KPIcategory = KPIcategory;
        this.perspective = perspective;
    }

    public KPIQuestionsPJ() {
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


        public String getIdKPI() {
            return idKPI;
        }

        public void setIdKPI(String idKPI) {
            this.idKPI = idKPI;
        }

        public String getKPIDesc() {
            return KPIDesc;
        }

        public void setKPIDesc(String KPIDesc) {
            this.KPIDesc = KPIDesc;
        }

        public List<String> getHint() {
            return hint;
        }

        public void setHint(List<String> hint) {
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
        private List<ImageUploadModel> photoCapture;
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


