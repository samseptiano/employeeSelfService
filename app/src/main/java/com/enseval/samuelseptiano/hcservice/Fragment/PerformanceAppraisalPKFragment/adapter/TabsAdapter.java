package com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPAFragment.TotalFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPAFragment.interfaces.UpdateableFragmentListener;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.enseval.samuelseptiano.hcservice.Model.FilterPAModel;
import com.enseval.samuelseptiano.hcservice.R;

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
    public String tahun;

    private String titleTotal;
    private String titleApprove;
    private String titleNotApprove;

    private List<KPIApproveList> kpiApproveLists = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsApprove = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsNotApprove = new ArrayList<>();

    public TabsAdapter(FragmentManager fm, Context context, String titleTotal, String titleApprove, String titleNotApprove, List<KPIApproveList> kpiApproveLists, List<KPIApproveList> kpiApproveListsApprove, List<KPIApproveList> kpiApproveListsNotApprove, String tahun) {
        super(fm);
        mContext = context;
        this.titleTotal = titleTotal;
        this.titleApprove = titleApprove;
        this.titleNotApprove = titleNotApprove;
        this.kpiApproveLists = kpiApproveLists;
        this.kpiApproveListsApprove = kpiApproveListsApprove;
        this.kpiApproveListsNotApprove = kpiApproveListsNotApprove;
        this.tahun = tahun;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TotalFragment("Total",kpiApproveLists, mContext.getResources().getDrawable(R.drawable.complete)).newInstance("Total",kpiApproveLists,mContext.getResources().getDrawable(R.drawable.complete));
            case 1:
                return new TotalFragment("Approve",kpiApproveListsApprove,mContext.getResources().getDrawable(R.drawable.complete)).newInstance("Approve",kpiApproveListsApprove,mContext.getResources().getDrawable(R.drawable.no_results_found));
            case 2:
                return new TotalFragment("Not Approve",kpiApproveListsNotApprove,mContext.getResources().getDrawable(R.drawable.approved)).newInstance("Not Approve",kpiApproveListsNotApprove,mContext.getResources().getDrawable(R.drawable.approved));

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
                bundle.putString("tahun", tahun);

                return titleTotal;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("operan", (Serializable) mGeneralString);
                bundle2.putString("tahun", tahun);
                return titleApprove;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("operan", (Serializable) mGeneralString);
                bundle3.putString("tahun", tahun);
                return titleNotApprove;
            default:
                return "FragmentPA";
        }
    }
}
