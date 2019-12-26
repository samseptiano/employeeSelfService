package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.SetupPA;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;


public class SetupPAFragment extends Fragment {

    ImageButton imgBtnKuantitatif,imgBtnKualitatif;
    View rootView;
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
        rootView = inflater.inflate(R.layout.fragment_setup_pa, container, false);
        imgBtnKuantitatif = rootView.findViewById(R.id.imgBtnSetupKuantitatif);
        imgBtnKualitatif = rootView.findViewById(R.id.imgBtnSetupKualitatif);

        imgBtnKuantitatif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new SetupPAKuantitatifDBFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("SetupPAKuantitatifDBFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "Setup PA Kuantitatif Dashboard", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }            }
        });
        return rootView;
    }


}
