package com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIImageUploadAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.ViewPagerAdapter;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.List;


public class KPIFillFragment extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    Boolean captureDone = false;
    RecyclerView recyclerView;
    KPIImageUploadAdapter adapter;
    Activity activity;
    int isClick=0;

    public List<ImageUploadModel> imgCaptureList = new ArrayList<>();

    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            isClick = bundle.getInt("tabPosition");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kpifill, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("KPI");

        //photoProfile = (CircleImageView) rootView.findViewById(R.id.empPhoto);

        viewPager = (ViewPager) rootView.findViewById(R.id.KPIViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.KPITabs);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home_black_24dp);

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

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String nik = usr.get(0).getEmpNIK();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment fr;


        //buat API KPI by parameter NIK dengan output:
                                                    // kualitatif semester 1,
                                                    // kualitatif semester 2,
                                                    // kuantitatif semester 1,
                                                    // kuantitatif semester 2

        fr = new KPIKuantitatifFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putBoolean("ConnState", ConnectivityReceiver.isConnected());
        bundle4.putString("id", "");
        bundle4.putString("KPIType","");
        bundle4.putString("semester","1");
        fr.setArguments(bundle4);
        adapter.addFragment(fr, "Semester 1");


//        fr = new KPIKuantitatifFragment();
//        Bundle bundle5 = new Bundle();
//        bundle5.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//        bundle5.putString("id", "");
//        bundle5.putString("KPIType","");
//        bundle5.putString("semester","2");
//        fr.setArguments(bundle5);
//        adapter.addFragment(fr, "Semester 2");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
    }

}
