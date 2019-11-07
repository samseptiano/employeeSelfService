package com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.adapter;

import android.content.Context;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.TotalFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.interfaces.UpdateableFragmentListener;
import com.example.samuelseptiano.employeeselfservice.Model.FilterPAModel;

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

    public TabsAdapter(FragmentManager fm, Context context, String titleTotal, String titleApprove, String titleNotApprove) {
        super(fm);
        mContext = context;
        this.titleTotal = titleTotal;
        this.titleApprove = titleApprove;
        this.titleNotApprove = titleNotApprove;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TotalFragment("Total").newInstance("Total");
            case 1:
                return new TotalFragment("Approve").newInstance("Approve");
            case 2:
                return new TotalFragment("Not Approve").newInstance("Not Approve");

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
//        if (object instanceof UpdateableFragmentListener) {
            //sent to FirstFragment and SecondFragment
            ((UpdateableFragmentListener) object).update(mGeneralString);
//        }
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
                return titleTotal;
            case 1:
                return titleApprove;
            case 2:
                return titleNotApprove;
            default:
                return "FragmentPA";
        }
    }
}
