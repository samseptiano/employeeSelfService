package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.TotalFragment;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

public class KPIStatusTahunanFragment extends Fragment {

    View rootview;
    ImageButton imgBtnOpen;

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Status PA");
        rootview =  inflater.inflate(R.layout.fragment_kpi_status_tahunan, container, false);

        imgBtnOpen = rootview.findViewById(R.id.imgBtnOpen);
        imgBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr = new TotalFragment("");
                Bundle bundle3 = new Bundle();
                bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle3.putString("types", "STATUS");
                bundle3.putString("jenis", "OPEN");
                fr.setArguments(bundle3);
                fr.setArguments(bundle3);
                fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("KPIApproveListTahunanFragment");
                ft.commit();
//                Toast.makeText(getContext(), "Approvee KPI Area", Toast.LENGTH_SHORT).show();
            }
        });



        return rootview;
    }

}
