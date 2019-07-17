package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static com.example.samuelseptiano.employeeselfservice.Application.MyApplication.getContext;


public class KPIApproveListTahunanFragment extends Fragment {

    View rootView;
    RecyclerView recyclerViewKPIHint;
    KPIApproveListAdapterTahunan kpiAdapter;
    List<KPIApproveList> KPIList;
    Button btnFillKpi, btnFillKpiPJ;
    LinearLayout lnBtnFill,lnShownBtnFill;
    LinearLayout lnMyKpi,lnMyKpiArea,lnMyTeam,lnMyTeamArea,lnDasboard,lnDashboardArea;
    ImageView imgExpandKPI,imgExpandTeam, imgExpandDashboard;
    PieChartView pieChartView,pieChartView2;
    TextView tvChart1,tvChart2, tvChartDesc1,tvChartDesc2;

    boolean isTeamExpand = true,isDashboardExpand=true,isMyKPIExpand=true;

    List< SliceValue> pieData = new ArrayList<>();
    List< SliceValue> pieData2 = new ArrayList<>();

    Fragment fr;
//    FloatingActionButton mFloatingActionButton;

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
        rootView = inflater.inflate(R.layout.fragment_kpi_approve_list_tahunan, container, false);
        lnMyKpi = rootView.findViewById(R.id.lnMyKPI);
        lnMyKpiArea = rootView.findViewById(R.id.lnMyKPIArea);
        lnDasboard = rootView.findViewById(R.id.lnMyDashboard);
        lnDashboardArea = rootView.findViewById(R.id.lnMyDashboardArea);
        lnMyTeam = rootView.findViewById(R.id.lnMyTeam);
        lnMyTeamArea = rootView.findViewById(R.id.lnMyTeamArea);

        imgExpandKPI = rootView.findViewById(R.id.imgExpandKPI);
        imgExpandTeam = rootView.findViewById(R.id.imgExpandTeam);
        imgExpandDashboard = rootView.findViewById(R.id.imgExpandDashboard);

        tvChart1 = rootView.findViewById(R.id.tvChart1);
        tvChart2 = rootView.findViewById(R.id.tvChart2);
        tvChartDesc1 = rootView.findViewById(R.id.tvChartDesc1);
        tvChartDesc2 = rootView.findViewById(R.id.tvChartDesc2);

        pieChartView = rootView.findViewById(R.id.chart);
        pieChartView2 = rootView.findViewById(R.id.chart2);


        //Chart1
        //======================================
        tvChart1.setText("Diagram 1");
        tvChartDesc1.setText("999 dari 999");
        if(pieData.size()<1) {

            pieData.add(new SliceValue(35, Color.BLUE).setLabel("completed"));
            pieData.add(new SliceValue(65, Color.GREEN).setLabel("not completed"));
        }
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true);
            pieChartView.setPieChartData(pieChartData);


        //======================================

        //Chart2
        //======================================
        tvChart2.setText("Diagram 2");
        tvChartDesc2.setText("999 dari 999");
        if(pieData2.size()<1) {

            pieData2.add(new SliceValue(75, Color.CYAN).setLabel("bawahan langsung"));
            pieData2.add(new SliceValue(25, Color.MAGENTA).setLabel("bawahan taklangsung"));
        }
            PieChartData pieChartData2 = new PieChartData(pieData2);
            pieChartData2.setHasLabels(true);
            pieChartView2.setPieChartData(pieChartData2);

        //======================================




        lnMyKpiArea.setVisibility(View.GONE);
        lnMyTeamArea.setVisibility(View.GONE);
        imgExpandDashboard.setImageResource(R.drawable.expand_up);

        lnMyKpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMyKPIExpand){
                    lnMyKpiArea.setVisibility(View.GONE);
                    imgExpandKPI.setImageResource(R.drawable.expand_down);
                    isMyKPIExpand=false;
                }
                else{
                    lnMyKpiArea.setVisibility(View.VISIBLE);
                    imgExpandKPI.setImageResource(R.drawable.expand_up);
                    isMyKPIExpand=true;
                }
            }
        });

        lnMyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTeamExpand){
                    lnMyTeamArea.setVisibility(View.GONE);
                    imgExpandTeam.setImageResource(R.drawable.expand_down);
                    isTeamExpand=false;
                }
                else{
                    lnMyTeamArea.setVisibility(View.VISIBLE);
                    imgExpandTeam.setImageResource(R.drawable.expand_up);
                    isTeamExpand=true;
                }
            }
        });

        lnDasboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDashboardExpand){
                    lnDashboardArea.setVisibility(View.GONE);
                    imgExpandDashboard.setImageResource(R.drawable.expand_down);
                    isDashboardExpand=false;
                }
                else{
                    lnDashboardArea.setVisibility(View.VISIBLE);
                    imgExpandDashboard.setImageResource(R.drawable.expand_up);
                    isDashboardExpand=true;
                }
            }
        });

        btnFillKpi = rootView.findViewById(R.id.btn_fill_kpi);
        btnFillKpiPJ = rootView.findViewById(R.id.btn_fill_kpi_pj);

        btnFillKpiPJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr = new KPIFillFragment();

                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                routingHomeDetailInterface.routingKPI("Fill PA", getContext(), "", "");
            }
        });

            btnFillKpi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ConnectivityReceiver.isConnected()) {
                        fr = new KPIFillTahunanFragment();

                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                        routingHomeDetailInterface.routingKPI("Fill PA tahunan", getContext(), "", "");

                    }else{
                        Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
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
            ka.setNIKAtasan1("181201101");
            ka.setNIKAtasan2("381201101");
            //==================
            if(i%2==0){
                ka.setStatus1("Not Approved");
            }
            else {
                ka.setStatus1("Approved");
            }
            KPIList.add(ka);
        }

        recyclerViewKPIHint = (RecyclerView) rootView.findViewById(R.id.recyclerViewKPIApproveList);
        recyclerViewKPIHint.setLayoutManager(new LinearLayoutManager(getContext()));
        kpiAdapter = new KPIApproveListAdapterTahunan(KPIList, getContext(), getActivity());
        recyclerViewKPIHint.setAdapter(kpiAdapter);

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

                } catch (Exception e) {

                }


                return true;

            }
        });
    }
}
