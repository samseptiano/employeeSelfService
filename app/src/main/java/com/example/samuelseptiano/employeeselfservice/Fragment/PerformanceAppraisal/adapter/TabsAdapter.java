package com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.adapter;

import android.content.Context;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.TotalFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.interfaces.UpdateableFragmentListener;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.FilterPAModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierg on 20/05/16.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    public static final int TOTAL_TABS = 3;
    public FilterPAModel mGeneralString;
    public Context mContext;

    private String titleTotal;
    private String titleApprove;
    private String titleNotApprove;

    private List<KPIApproveList> kpiApproveLists = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsApprove = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsNotApprove = new ArrayList<>();

    public TabsAdapter(FragmentManager fm, Context context, String titleTotal, String titleApprove, String titleNotApprove, List<KPIApproveList> kpiApproveLists, List<KPIApproveList> kpiApproveListsApprove, List<KPIApproveList> kpiApproveListsNotApprove) {
        super(fm);
        mContext = context;
        this.titleTotal = titleTotal;
        this.titleApprove = titleApprove;
        this.titleNotApprove = titleNotApprove;
        this.kpiApproveLists = kpiApproveLists;
        this.kpiApproveListsApprove = kpiApproveListsApprove;
        this.kpiApproveListsNotApprove = kpiApproveListsNotApprove;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TotalFragment("Total",kpiApproveLists).newInstance("Total",kpiApproveLists);
            case 1:
                return new TotalFragment("Approve",kpiApproveListsApprove).newInstance("Approve",kpiApproveListsApprove);
            case 2:
                return new TotalFragment("Not Approve",kpiApproveListsNotApprove).newInstance("Not Approve",kpiApproveListsNotApprove);

        }
        return null;
    }

    //received from ManagerFragment
    public void update(FilterPAModel filterPAModel) {
        mGeneralString = filterPAModel;
        //updated
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof UpdateableFragmentListener) {
            ((UpdateableFragmentListener) object).update(mGeneralString);
        }
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return TOTAL_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putSerializable("operan", (Serializable) mGeneralString);
                return titleTotal;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("operan", (Serializable) mGeneralString);
                return titleApprove;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("operan", (Serializable) mGeneralString);
                return titleNotApprove;
            default:
                return "FragmentPA";
        }
    }
}
