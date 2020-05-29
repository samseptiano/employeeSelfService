package com.enseval.samuelseptiano.hcservice.Fragment.PerformanceReviewFragment;

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
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.DevelopmentPlanFragment;
import com.enseval.samuelseptiano.hcservice.Helper.ModuleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.UserList;
import com.enseval.samuelseptiano.hcservice.Model.IconModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.ModuleRealmModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.ArrayList;
import java.util.List;


public class PerformanceReviewFragment extends Fragment implements IconHomeAdapter.EventListener {

    View rootView;
    RecyclerView rvPerformanceReview;
    IconHomeAdapter iconHomeAdapter;
    List<IconModel> iconModels = new ArrayList<>();
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Performance Review");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            //setConnState(bundle.getBoolean("ConnState"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_performance_review, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Performance Review");


        ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getContext());
        ArrayList<ModuleRealmModel> mdl;
        mdl = moduleRealmHelper.findAllArticle();
        //========== icons HOME =================
        iconModels=new ArrayList<>();
        rvPerformanceReview = rootView.findViewById(R.id.rv_icon_performance_review);
        rvPerformanceReview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        for(int i=0;i<mdl.size();i++) {
            if(mdl.get(i).getModuleCode().equals("PRVAPPR") ||mdl.get(i).getModuleCode().equals("PTNRVW")) {
                IconModel im = new IconModel();
                im.setIconTitle(mdl.get(i).getModuleName());
                im.setIconCode(mdl.get(i).getModuleCode());
                im.setIconStatus(mdl.get(i).getModuleStatus());
                im.setIconGuideline(mdl.get(i).getModuleGuideline());
                String name = mdl.get(i).getIcon();
                int id = getResources().getIdentifier(name, "drawable", getContext().getPackageName());
                im.setIconImage(id);
                iconModels.add(im);
            }
        }
        iconHomeAdapter = new IconHomeAdapter(iconModels,getContext(), ConnectivityReceiver.isConnected(), getActivity(), mdl,this);
        rvPerformanceReview.setAdapter(iconHomeAdapter);

        return rootView;
    }

    @Override
    public void callAction(String moduleCode) {
        UserList userList = new UserList();
        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
        switch(moduleCode) {
            case "PRVAPPR":
                if(ConnectivityReceiver.isConnected()) {
                    routingHomeDetailInterface.routingKPI("Penilaian", getContext(), userList, "","");
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PTNRVW":
                break;
            case "HCCHT":
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new ChatInboxFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("ChatInboxFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "HC Chat", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            break;
//            case "PFRV-IDP":
//                if(ConnectivityReceiver.isConnected()) {
//                    // code block
//                    fr = new DevelopmentPlanFragment();
//                    Bundle bundle3 = new Bundle();
//                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//                    fr.setArguments(bundle3);
//                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
//                    ft = fm.beginTransaction();
//                    ft.replace(R.id.fragment_frame, fr);
//                    ft.addToBackStack("DevelopmentPlanFragment");
//                    ft.commit();
//                    Toast.makeText(getContext(), "Development Plan Area", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
//                }
//                break;
            default:
                // code block
        }
    }
}
