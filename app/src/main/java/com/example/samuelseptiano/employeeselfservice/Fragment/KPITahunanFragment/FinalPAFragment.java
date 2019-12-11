package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samuelseptiano.employeeselfservice.R;


public class FinalPAFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        //actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("Final PA");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Final PA");
        return inflater.inflate(R.layout.fragment_final_pa, container, false);
    }


}
