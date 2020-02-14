package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.Activity.MainActivity;
import com.enseval.samuelseptiano.hcservice.Adapter.ViewPagerAdapter;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.UserList;
import com.enseval.samuelseptiano.hcservice.R;
import com.google.android.material.tabs.TabLayout;


public class KPIApproveTahunanFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    String type="";
    UserList userList;

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
            userList = (UserList) bundle.getSerializable("id");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kpiapprove_tahunan, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("KPI Tahunan");

        //photoProfile = (CircleImageView) rootView.findViewById(R.id.empPhoto);

//        viewPager = (ViewPager) rootView.findViewById(R.id.KPIViewPager);
//        setupViewPager(viewPager);
//
//        tabLayout = (TabLayout) rootView.findViewById(R.id.KPITabs);
//        tabLayout.setupWithViewPager(viewPager);
//
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        viewPager.setOffscreenPageLimit(2);
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });


        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment fr;

        fr = new KPIKuantitatifTahunanFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putBoolean("ConnState", ConnectivityReceiver.isConnected());
        bundle4.putSerializable("id", userList);
        bundle4.putString("KPIType",type);
        bundle4.putString("semester","1");
        fr.setArguments(bundle4);
        adapter.addFragment(fr, "Semester 1");

        fr = new KPIKuantitatifTahunanFragment();
        Bundle bundle5 = new Bundle();
        bundle5.putBoolean("ConnState", ConnectivityReceiver.isConnected());
        bundle5.putSerializable("id", userList);
        bundle5.putString("KPIType",type);
        bundle5.putString("semester","2");
        fr.setArguments(bundle5);
        adapter.addFragment(fr, "Semester 2");

        viewPager.setAdapter(adapter);
    }

}
