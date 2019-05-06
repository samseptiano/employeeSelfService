package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samuelseptiano.employeeselfservice.Carrousel.ViewPagerCarouselView;
import com.example.samuelseptiano.employeeselfservice.R;


public class HomeHeaderFragment extends Fragment {

    ViewPagerCarouselView viewPagerCarouselView;
    View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home_header, container, false);

        long carouselSlideInterval = 3000; // 3 SECONDS
        int [] imageResourceIds = {R.drawable.car6, R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4, R.drawable.car5, R.drawable.car6, R.drawable.car1}; // car6 on 0, and car1 on last for circular scroll purpose

        viewPagerCarouselView = (ViewPagerCarouselView) rootView.findViewById(R.id.carousel_view);
        viewPagerCarouselView.setData(getFragmentManager(), imageResourceIds, carouselSlideInterval);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPagerCarouselView.onDestroy();
    }

}
