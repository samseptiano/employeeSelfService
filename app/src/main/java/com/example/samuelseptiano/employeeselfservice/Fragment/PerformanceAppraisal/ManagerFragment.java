package com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.adapter.TabsAdapter;
import com.example.samuelseptiano.employeeselfservice.Model.FilterPAModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.android.material.tabs.TabLayout;

public class ManagerFragment extends Fragment {

    private ViewPager mViewPager;
    private TabsAdapter mAdapter;
    public String fragTitle;

    String titleTotal = "Total(30)";
    String titleApprove = "Approved(10)";
    String titleNotApprove = "Not Approve(20)";


    public ManagerFragment() {
        // Required empty public constructor
    }

    public static ManagerFragment newInstance() {
        ManagerFragment fragment = new ManagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("My team Performance");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("My Team Performance");

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.fragment_manager_tab_bar_tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_manager_tab_bar_pager);

        mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove);

        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    public void update(FilterPAModel filterPAModel){
        mAdapter.update(filterPAModel);
    }

}
