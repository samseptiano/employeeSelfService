package com.enseval.samuelseptiano.hcservice.Fragment.HCAssessmentFragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.IconHomeAdapter;
import com.enseval.samuelseptiano.hcservice.Helper.ModuleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.IconModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.ModuleRealmModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.ArrayList;
import java.util.List;


public class HCAssessmentFragment extends Fragment implements IconHomeAdapter.EventListener {

    View rootView;
    List<IconModel> iconModels;
    IconHomeAdapter iconHomeAdapter;
    RecyclerView rvMBTI;
    String moduleName = "HCAS-";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_hcassessment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("HC Assessment");

        ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getContext());
        ArrayList<ModuleRealmModel> mdl;
        mdl = moduleRealmHelper.findAllArticle();

        iconModels=new ArrayList<>();
        rvMBTI = (RecyclerView) rootView.findViewById(R.id.rv_self_assessment_mbti);
        rvMBTI.setLayoutManager(new GridLayoutManager(getContext(), 3));
        for(int i=0;i<mdl.size();i++) {
            if(mdl.get(i).getModuleCode().contains("HCAS") && mdl.get(i).getModuleCode().split("-").length==2) {
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
        rvMBTI.setAdapter(iconHomeAdapter);

        return rootView;

    }

    @Override
    public void callAction(String moduleCode) {

    }
}
