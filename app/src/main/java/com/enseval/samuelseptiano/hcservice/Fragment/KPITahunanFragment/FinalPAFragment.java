package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.FinalPAFragments.DistribusiNormalFragment;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;


public class FinalPAFragment extends Fragment {

    ImageButton btnDistribusiNormal;
    View rootView;

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
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
        rootView =  inflater.inflate(R.layout.fragment_final_pa, container, false);

        btnDistribusiNormal = rootView.findViewById(R.id.imgBtnDistribusiNormal);
        btnDistribusiNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new DistribusiNormalFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("DistribusiNormalFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "Distribusi Normal Area", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


}
