package com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment;

import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIApproveListAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIApproveListAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIApproveListPJ;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class KPIApproveListFragment extends Fragment {

    View rootView;
    RecyclerView recyclerViewKPIHint;
    KPIApproveListAdapter kpiAdapter;
    List<KPIApproveListPJ> KPIList;
    Button btnFillKpi;
    Fragment fr;
    LinearLayout lnMyTeam,lnMyTeamArea,lnDasboard,lnDashboardArea;
    ImageView imgExpandTeam, imgExpandDashboard;
    PieChartView pieChartView,pieChartView2;
    FloatingActionButton mFloatingActionButton;
    TextView tvChart1,tvChart2;

    List<SliceValue> pieData = new ArrayList<>();
    List< SliceValue> pieData2 = new ArrayList<>();

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
        lnDasboard = rootView.findViewById(R.id.lnMyDashboard);
        lnDashboardArea = rootView.findViewById(R.id.lnMyDashboardArea);
        lnMyTeam = rootView.findViewById(R.id.lnMyTeam);
        lnMyTeamArea = rootView.findViewById(R.id.lnMyTeamArea);
        imgExpandTeam = rootView.findViewById(R.id.imgExpandTeam);
        imgExpandDashboard = rootView.findViewById(R.id.imgExpandDashboard);
        tvChart1 = rootView.findViewById(R.id.tvChart1);
        tvChart2 = rootView.findViewById(R.id.tvChart2);



        pieChartView = rootView.findViewById(R.id.chart);
        pieChartView2 = rootView.findViewById(R.id.chart2);


        //Chart1
        //======================================
        tvChart1.setText("Diagram 1");
        if(pieData.size()<1) {

            pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
            pieData.add(new SliceValue(75, Color.GREEN).setLabel("Q2: $4"));
            pieData.add(new SliceValue(10, Color.RED).setLabel("Q3: $18"));
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartView.setPieChartData(pieChartData);

        //======================================

        //Chart2
        //======================================
        tvChart2.setText("Diagram 2");
        if(pieData2.size()<1) {

            pieData2.add(new SliceValue(15, Color.CYAN).setLabel("Q1: $10"));
            pieData2.add(new SliceValue(25, Color.MAGENTA).setLabel("Q2: $4"));
            pieData2.add(new SliceValue(60, Color.YELLOW).setLabel("Q3: $18"));
        }
        PieChartData pieChartData2 = new PieChartData(pieData2);
        pieChartData2.setHasLabels(true);
        pieChartView2.setPieChartData(pieChartData2);

        //======================================

        lnMyTeamArea.setVisibility(View.GONE);
        imgExpandDashboard.setImageResource(R.drawable.expand_up);


        lnMyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgExpandTeam.setImageResource(R.drawable.expand_up);
                lnMyTeamArea.setVisibility(View.VISIBLE);
                lnDashboardArea.setVisibility(View.GONE);
                imgExpandDashboard.setImageResource(R.drawable.expand_down);
            }
        });

        lnDasboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgExpandTeam.setImageResource(R.drawable.expand_down);
                lnMyTeamArea.setVisibility(View.GONE);
                lnDashboardArea.setVisibility(View.VISIBLE);
                imgExpandDashboard.setImageResource(R.drawable.expand_up);
            }
        });


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
                Toast.makeText(getContext(),"Pengisian KPI Mandiri", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("PA Approvee List");
        setHasOptionsMenu(true);

        KPIList = new ArrayList<KPIApproveListPJ>();
        for(int i=0;i<20;i++){
            KPIApproveListPJ ka = new KPIApproveListPJ();
            ka.setDept("Internal  Audit");
            ka.setEmpName("Samuel Septiano Winata");
            ka.setTanggalMasaKontrakAwal("20-05-2011");
            ka.setTanggalMasaKontrakAkhir("20-06-2019");
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
        kpiAdapter = new KPIApproveListAdapter(KPIList, getContext(), getActivity());
        recyclerViewKPIHint.setAdapter(kpiAdapter);

        recyclerViewKPIHint.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !mFloatingActionButton.isShown())
                    //mFloatingActionButton.show();
                mFloatingActionButton.hide();
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
