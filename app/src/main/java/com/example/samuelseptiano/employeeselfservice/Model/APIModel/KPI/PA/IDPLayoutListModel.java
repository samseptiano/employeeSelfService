package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class IDPLayoutListModel implements Serializable {
    private String idpTitle;
    private List<View>lnIdpDetail;

    public List<LinearLayout> getLnExpanded() {
        return lnExpanded;
    }

    public void setLnExpanded(List<LinearLayout> lnExpanded) {
        this.lnExpanded = lnExpanded;
    }

    private List<LinearLayout>lnExpanded;
    public List<ImageButton> getImgExpand() {
        return imgExpand;
    }

    public void setImgExpand(List<ImageButton> imgExpand) {
        this.imgExpand = imgExpand;
    }

    private  List<ImageButton> imgExpand;
    private LinearLayout LinLaIdpDetail;
    private List<CheckBox>cbIdpDetail;
    private List<TextView>tvIdpTitleDetail;
    private List<TextView>tvIdpNumber;
    private List<EditText>edtDevelopmentActivities;
    private List<EditText>edtKPI;
    private List<EditText>edtDueDate;
    private List<EditText>edtMentors;
    private List<EditText>edtAchievementRecommendation;

    public List<EditText> getEdtDevelopmentActivities() {
        return edtDevelopmentActivities;
    }

    public void setEdtDevelopmentActivities(List<EditText> edtDevelopmentActivities) {
        this.edtDevelopmentActivities = edtDevelopmentActivities;
    }

    public List<EditText> getEdtKPI() {
        return edtKPI;
    }

    public void setEdtKPI(List<EditText> edtKPI) {
        this.edtKPI = edtKPI;
    }

    public List<EditText> getEdtDueDate() {
        return edtDueDate;
    }

    public void setEdtDueDate(List<EditText> edtDueDate) {
        this.edtDueDate = edtDueDate;
    }

    public List<EditText> getEdtMentors() {
        return edtMentors;
    }

    public void setEdtMentors(List<EditText> edtMentors) {
        this.edtMentors = edtMentors;
    }

    public List<EditText> getEdtAchievementRecommendation() {
        return edtAchievementRecommendation;
    }

    public void setEdtAchievementRecommendation(List<EditText> edtAchievementRecommendation) {
        this.edtAchievementRecommendation = edtAchievementRecommendation;
    }

    public LinearLayout getLinLaIdpDetail() {
        return LinLaIdpDetail;
    }

    public void setLinLaIdpDetail(LinearLayout linLaIdpDetail) {
        LinLaIdpDetail = linLaIdpDetail;
    }



    public List<CheckBox> getCbIdpDetail() {
        return cbIdpDetail;
    }

    public void setCbIdpDetail(List<CheckBox> cbIdpDetail) {
        this.cbIdpDetail = cbIdpDetail;
    }

    public List<TextView> getTvIdpTitleDetail() {
        return tvIdpTitleDetail;
    }

    public void setTvIdpTitleDetail(List<TextView> tvIdpTitleDetail) {
        this.tvIdpTitleDetail = tvIdpTitleDetail;
    }

    public List<TextView> getTvIdpNumber() {
        return tvIdpNumber;
    }

    public void setTvIdpNumber(List<TextView> tvIdpNumber) {
        this.tvIdpNumber = tvIdpNumber;
    }

    public String getIdpTitle() {
        return idpTitle;
    }

    public void setIdpTitle(String idpTitle) {
        this.idpTitle = idpTitle;
    }

    public List<View> getLnIdpDetail() {
        return lnIdpDetail;
    }

    public void setLnIdpDetail(List<View> lnIdpDetail) {
        this.lnIdpDetail = lnIdpDetail;
    }

    public IDPLayoutListModel() {
    }
}
