package com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ;

import com.enseval.samuelseptiano.hcservice.Model.ImageUploadModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Samuel Septiano on 02,September,2019
 */
public class KPIQuestionsPostPJ {
    @SerializedName("kpiCategory")
    private String kpiCategory;
    @SerializedName("KPINO")
    private String KPINO;
    @SerializedName("Evidence")
    private String Evidence;
    @SerializedName("GRADESCORE")
    private String GradeScore;
    @SerializedName("pK_FileEvidences")
    List<ImageUploadModel> fileEvidence;

    public String getKpiCategory() {
        return kpiCategory;
    }

    public void setKpiCategory(String kpiCategory) {
        this.kpiCategory = kpiCategory;
    }

    public String getKPINO() {
        return KPINO;
    }

    public void setKPINO(String KPINO) {
        this.KPINO = KPINO;
    }

    public String getEvidence() {
        return Evidence;
    }

    public void setEvidence(String evidence) {
        Evidence = evidence;
    }

    public String getGradeScore() {
        return GradeScore;
    }

    public void setGradeScore(String gradeScore) {
        GradeScore = gradeScore;
    }

    public List<ImageUploadModel> getFileEvidence() {
        return fileEvidence;
    }

    public void setFileEvidence(List<ImageUploadModel> fileEvidence) {
        this.fileEvidence = fileEvidence;
    }

    public KPIQuestionsPostPJ() {
    }
}
