package com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.enseval.samuelseptiano.hcservice.Helper.ModuleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.ModuleRealmModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.ArrayList;


public class SetupPKFragment extends Fragment {

    ImageButton imgBtnKuantitatif,imgBtnKualitatif;
    View rootView;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    String empType="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setup_pk, container, false);
        imgBtnKuantitatif = rootView.findViewById(R.id.imgBtnSetupKuantitatif);
        imgBtnKualitatif = rootView.findViewById(R.id.imgBtnSetupKualitatif);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                empType = bundle.getString("empType");
            }
            catch (Exception e){

            }
        }
        actionBar.setTitle("Setup PA");

//        actionBar.setTitle("Setup "+empType);
        ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getContext());
        ArrayList<ModuleRealmModel> mdl;
        mdl = moduleRealmHelper.findAllArticle();

        for(int i=0;i<mdl.size();i++) {
            if(mdl.get(i).getModuleCode().equals("STPKLTTF")) {
                imgBtnKualitatif.setVisibility(View.VISIBLE);
            }
        }
        for(int i=0;i<mdl.size();i++) {
            if(mdl.get(i).getModuleCode().equals("STPKNTTF")) {
                imgBtnKuantitatif.setVisibility(View.VISIBLE);
            }
        }

        imgBtnKualitatif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new SetupPKKualitatifDBFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    bundle3.putString("empType", empType);
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("SetupPKKualitatifDBFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "Setup PK Kualitatif Dashboard", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBtnKuantitatif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new SetupPKKuantitatifDBFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    bundle3.putString("empType", empType);
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("SetupPKKuantitatifDBFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "Setup PK Kuantitatif Dashboard", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }


}
