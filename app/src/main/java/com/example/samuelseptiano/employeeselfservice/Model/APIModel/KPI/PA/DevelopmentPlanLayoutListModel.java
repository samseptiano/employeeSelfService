package com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class DevelopmentPlanLayoutListModel implements Serializable {
    private String empNIK;
    private String empName;
    private List<View> lnDevPlan;
    private LinearLayout linLaDevPlan;
    private List<CheckBox>cbDevPlan;
    private List<TextView>tvDevPlan;
    private List<Button>btnDevPlan;

    public DevelopmentPlanLayoutListModel() {
    }

    public String getEmpNIK() {
        return empNIK;
    }

    public void setEmpNIK(String empNIK) {
        this.empNIK = empNIK;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<View> getLnDevPlan() {
        return lnDevPlan;
    }

    public void setLnDevPlan(List<View> lnDevPlan) {
        this.lnDevPlan = lnDevPlan;
    }

    public LinearLayout getLinLaDevPlan() {
        return linLaDevPlan;
    }

    public void setLinLaDevPlan(LinearLayout linLaDevPlan) {
        this.linLaDevPlan = linLaDevPlan;
    }

    public List<CheckBox> getCbDevPlan() {
        return cbDevPlan;
    }

    public void setCbDevPlan(List<CheckBox> cbDevPlan) {
        this.cbDevPlan = cbDevPlan;
    }

    public List<TextView> getTvDevPlan() {
        return tvDevPlan;
    }

    public void setTvDevPlan(List<TextView> tvDevPlan) {
        this.tvDevPlan = tvDevPlan;
    }

    public List<Button> getBtnDevPlan() {
        return btnDevPlan;
    }

    public void setBtnDevPlan(List<Button> btnDevPlan) {
        this.btnDevPlan = btnDevPlan;
    }
}
