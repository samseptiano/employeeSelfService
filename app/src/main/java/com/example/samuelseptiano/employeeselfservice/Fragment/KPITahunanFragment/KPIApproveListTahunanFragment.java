package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIApproveListAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment.KPIFillFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class KPIApproveListTahunanFragment extends Fragment {

    View rootView;
    RecyclerView recyclerViewKPIHint;
    KPIApproveListAdapterTahunan kpiAdapter;
    List<KPIApproveList> KPIList;
    Button btnFillKpi;
    LinearLayout lnBtnFill,lnShownBtnFill;
    Fragment fr;
    FloatingActionButton mFloatingActionButton;

    public Boolean getConnState() {
        return connState;
    }

    public void setConnState(Boolean connState) {
        this.connState = connState;
    }

    Boolean connState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kpi_approve_list, container, false);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floating_action_button);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()) {
                    fr = new KPIFillFragment();

                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingKPI("Fill PA", getContext(), "", "");

                }else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFloatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(),"Pengisian KPI Tahunan Mandiri", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("PA Tahunan Approvee List");
        setHasOptionsMenu(true);

        KPIList = new ArrayList<KPIApproveList>();
        for(int i=0;i<20;i++){
            KPIApproveList ka = new KPIApproveList();
            ka.setDept("Internal  Audit");
            ka.setEmpName("Samuel Septiano Winata");
            ka.setId(Integer.toString(i));
            ka.setNIK("181201101");
            ka.setJobTitle("Regional Distribution Manager");

            //=== parameter list bawahan berdasarkan nik akun tersebut ===
            ka.setNIKAtasan("181201101");
            //==================
            if(i%2==0){
                ka.setStatus("Not Approved");
            }
            else {
                ka.setStatus("Approved");
            }
            KPIList.add(ka);
        }

        recyclerViewKPIHint = (RecyclerView) rootView.findViewById(R.id.recyclerViewKPIApproveList);
        recyclerViewKPIHint.setLayoutManager(new LinearLayoutManager(getContext()));
        kpiAdapter = new KPIApproveListAdapterTahunan(KPIList, getContext(), getActivity());
        recyclerViewKPIHint.setAdapter(kpiAdapter);


        recyclerViewKPIHint.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !mFloatingActionButton.isShown())
                    mFloatingActionButton.show();
                else if(dy>0 && mFloatingActionButton.isShown())
                    mFloatingActionButton.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });




        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem mSearch = menu.findItem(R.id.mi_search);

        android.support.v7.widget.SearchView mSearchView = (android.support.v7.widget.SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s.toLowerCase(Locale.getDefault());
                try {
                    kpiAdapter.filter(text);
                    if(text.equals("")){
                        mFloatingActionButton.show();
                    }
                    else {
                        mFloatingActionButton.hide();
                    }
                } catch (Exception e) {

                }


                return true;

            }
        });
    }
}
