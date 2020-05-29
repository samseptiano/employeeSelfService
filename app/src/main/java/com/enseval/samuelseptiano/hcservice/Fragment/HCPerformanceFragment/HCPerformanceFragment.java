package com.enseval.samuelseptiano.hcservice.Fragment.HCPerformanceFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.IconHomeAdapter;
import com.enseval.samuelseptiano.hcservice.Fragment.ChatInboxFragment;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceReviewFragment.PerformanceReviewFragment;
import com.enseval.samuelseptiano.hcservice.Helper.ModuleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.IconModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.ModuleRealmModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.ArrayList;
import java.util.List;


public class HCPerformanceFragment extends Fragment implements IconHomeAdapter.EventListener {

    View rootView;
    RecyclerView rvHcPerformance;
    IconHomeAdapter iconHomeAdapter;
    List<IconModel> iconModels = new ArrayList<>();

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
        rootView =  inflater.inflate(R.layout.fragment_hcperformance, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("HC Performance");


        ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getContext());
        ArrayList<ModuleRealmModel> mdl;
        mdl = moduleRealmHelper.findAllArticle();
        //========== icons HOME =================
        iconModels=new ArrayList<>();
        rvHcPerformance = rootView.findViewById(R.id.rv_icon_hc_performance);
        rvHcPerformance.setLayoutManager(new GridLayoutManager(getContext(), 3));
        for(int i=0;i<mdl.size();i++) {
            if(mdl.get(i).getModuleCode().equals("PFRV") || mdl.get(i).getModuleCode().equals("INNVT")
            || mdl.get(i).getModuleCode().equals("HCSV")) {
                IconModel im = new IconModel();
                im.setIconTitle(mdl.get(i).getModuleName());
                im.setIconCode(mdl.get(i).getModuleCode());
                im.setIconStatus(mdl.get(i).getModuleStatus());
                String name = mdl.get(i).getIcon();
                im.setIconGuideline(mdl.get(i).getModuleGuideline());
                int id = getResources().getIdentifier(name, "drawable", getContext().getPackageName());
                im.setIconImage(id);
                iconModels.add(im);
            }
        }
        iconHomeAdapter = new IconHomeAdapter(iconModels,getContext(), ConnectivityReceiver.isConnected(), getActivity(), mdl,this);
        rvHcPerformance.setAdapter(iconHomeAdapter);


        return rootView;
    }

    @Override
    public void callAction(String moduleCode) {
        switch(moduleCode) {
            case "PFRV":
                fr = new PerformanceReviewFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                fr.setArguments(bundle);
                fm = ((FragmentActivity)getContext()).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("PerformanceReviewFragment");
                ft.commit();
                Toast.makeText(getContext(),"Performance Review Area",Toast.LENGTH_SHORT).show();
                break;
            case "INNVT":
                break;
            case "HCSV":
                break;
            default:
                // code block
        }
    }
}
