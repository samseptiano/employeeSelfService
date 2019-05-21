package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.ViewPagerAdapter;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;


public class KPIApproveTahunanFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    String type="";
    String nik = "";

    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            type = bundle.getString("KPIType");
            nik = bundle.getString("id");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kpiapprove, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("KPI Tahunan");

        //photoProfile = (CircleImageView) rootView.findViewById(R.id.empPhoto);

        viewPager = (ViewPager) rootView.findViewById(R.id.KPIViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.KPITabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment fr;

        fr = new KPIKuantitatifTahunanFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putBoolean("ConnState", ConnectivityReceiver.isConnected());
        bundle4.putString("id", nik);
        bundle4.putString("KPIType",type);
        bundle4.putString("semester","1");
        fr.setArguments(bundle4);
        adapter.addFragment(fr, "Semester 1");

        fr = new KPIKuantitatifTahunanFragment();
        Bundle bundle5 = new Bundle();
        bundle5.putBoolean("ConnState", ConnectivityReceiver.isConnected());
        bundle5.putString("id", nik);
        bundle5.putString("KPIType",type);
        bundle5.putString("semester","2");
        fr.setArguments(bundle5);
        adapter.addFragment(fr, "Semester 2");




        viewPager.setAdapter(adapter);
    }

}
