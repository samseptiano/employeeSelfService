package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.os.Bundle;
import android.sax.RootElement;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.ViewPagerAdapter;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile.ProfileDataFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile.QRFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
//    private CircleImageView photoProfile;
//    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ArrayList<UserRealmModel> usr;
//        usr = userRealmHelper.findAllArticle();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container,    false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("Profile");

        //photoProfile = (CircleImageView) rootView.findViewById(R.id.empPhoto);

        viewPager = (ViewPager) rootView.findViewById(R.id.ProfileViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.ProfileTabs);
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

        fr = new ProfileDataFragment();
        adapter.addFragment(fr, "Profile");
        fr = new QRFragment();
        adapter.addFragment(fr, "Show MY QR");
        viewPager.setAdapter(adapter);
    }
    }

