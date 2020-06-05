package com.enseval.samuelseptiano.hcservice.Model;

public class IconModel {
    public String getIconTitle() {
        return iconTitle;
    }

    public void setIconTitle(String iconTitle) {
        this.iconTitle = iconTitle;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public int getIconImage() {
        return iconImage;
    }

    public void setIconImage(int iconImage) {
        this.iconImage = iconImage;
    }

    public String getIconStatus() {
        return iconStatus;
    }

    public void setIconStatus(String iconStatus) {
        this.iconStatus = iconStatus;
    }

    private String iconTitle;
    private String iconCode;
    private int iconImage;
    private String iconStatus;

    public String getIconGuideline() {
        return iconGuideline;
    }

    public void setIconGuideline(String iconGuideline) {
        this.iconGuideline = iconGuideline;
    }

    private String iconGuideline;

    public boolean isShowedCase() {
        return isShowedCase = false;
    }

    public void setShowedCase(boolean showedCase) {
        isShowedCase = showedCase;
    }

    private boolean isShowedCase;

    public IconModel() {
    }

}

