package com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class TabLayoutFragment extends Fragment {

    View rootView;
    BottomNavigationView navigation;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        navigation = (BottomNavigationView) rootView.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return rootView;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ((MainActivity)getActivity()).inflateHomeFragment(ConnectivityReceiver.isConnected());
                    return true;
                case R.id.navigation_profile:
                    ((MainActivity)getActivity()).inflateProfileFragment(ConnectivityReceiver.isConnected());
                    return true;
                case R.id.navigation_event:
                    ((MainActivity)getActivity()).inflateEventFragment(ConnectivityReceiver.isConnected());
                    return true;
                case R.id.navigation_qr:
                    ((MainActivity)getActivity()).inflateQRFragment(ConnectivityReceiver.isConnected());
                    return true;
//
            }
            return false;
        }
    };

}
