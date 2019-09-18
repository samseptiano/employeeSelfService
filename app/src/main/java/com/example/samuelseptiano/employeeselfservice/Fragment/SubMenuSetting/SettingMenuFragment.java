package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuSetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.samuelseptiano.employeeselfservice.R;


public class SettingMenuFragment extends Fragment {
    View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setting_menu, container, false);


        return rootView;
    }


}
