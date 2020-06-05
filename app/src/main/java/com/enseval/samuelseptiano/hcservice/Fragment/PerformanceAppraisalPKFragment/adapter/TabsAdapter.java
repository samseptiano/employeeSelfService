package com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment.TotalFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment.interfaces.UpdateableFragmentListener;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIApproveListPJ;
import com.enseval.samuelseptiano.hcservice.Model.FilterPAModel;
import com.enseval.samuelseptiano.hcservice.Model.FilterPJModel;
import com.enseval.samuelseptiano.hcservice.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierg on 20/05/16.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    public static final int TOTAL_TABS = 3;
    public FilterPJModel mGeneralString;
    public Context mContext;
    public String tahun,empType;

    private String titleTotal;
    private String titleApprove;
    private String titleNotApprove;

    private List<KPIApproveListPJ> kpiApproveLists = new ArrayList<>();
    private List<KPIApproveListPJ> kpiApproveListsApprove = new ArrayList<>();
    private List<KPIApproveListPJ> kpiApproveListsNotApprove = new ArrayList<>();

    public TabsAdapter(FragmentManager fm, Context context, String titleTotal, String titleApprove, String titleNotApprove, List<KPIApproveListPJ> kpiApproveLists, List<KPIApproveListPJ> kpiApproveListsApprove, List<KPIApproveListPJ> kpiApproveListsNotApprove, String tahun, String empType) {
        super(fm);
        mContext = context;
        this.titleTotal = titleTotal;
        this.titleApprove = titleApprove;
        this.titleNotApprove = titleNotApprove;
        this.kpiApproveLists = kpiApproveLists;
        this.kpiApproveListsApprove = kpiApproveListsApprove;
        this.kpiApproveListsNotApprove = kpiApproveListsNotApprove;
        this.tahun = tahun;
        this.empType = empType;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("empType", empType);
                return new TotalFragment("Total",kpiApproveLists, mContext.getResources().getDrawable(R.drawable.complete),empType).newInstance("Total",kpiApproveLists,mContext.getResources().getDrawable(R.drawable.complete),empType);
            case 1:
                 bundle = new Bundle();
                bundle.putString("empType", empType);
                return new TotalFragment("Approved",kpiApproveListsApprove,mContext.getResources().getDrawable(R.drawable.complete),empType).newInstance("Approve",kpiApproveListsApprove,mContext.getResources().getDrawable(R.drawable.no_results_found),empType);
            case 2:
                 bundle = new Bundle();
                bundle.putString("empType", empType);
                return new TotalFragment("Not Approved",kpiApproveListsNotApprove,mContext.getResources().getDrawable(R.drawable.approved),empType).newInstance("Not Approve",kpiApproveListsNotApprove,mContext.getResources().getDrawable(R.drawable.approved),empType);

        }
        return null;
    }

    //received from ManagerFragment
    public void update(FilterPJModel filterPJModel) {
        mGeneralString = filterPJModel;
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
                bundle.putString("empType", empType);

                return titleTotal;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("operan", (Serializable) mGeneralString);
                bundle2.putString("tahun", tahun);
                bundle2.putString("empType", empType);

                return titleApprove;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("operan", (Serializable) mGeneralString);
                bundle3.putString("tahun", tahun);
                bundle3.putString("empType", empType);

                return titleNotApprove;
            default:
                return "FragmentPA";
        }
    }
}
