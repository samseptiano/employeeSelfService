package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.FinalPAFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalFragment.ApprovalListAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalFragment.PerhitunganPAAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalFragment.PerhitunganPAEmpAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalFragment.SortAdapter;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ApprDNModell;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.SortDNModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.textfield.TextInputEditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class DistribusiNormalFragment extends Fragment implements PerhitunganPAEmpAdapter.EventListener{

    View rootView;
    RecyclerView recyclerView, rvEmployee;
    PerhitunganPAAdapter perhitunganPAAdapter;
    PerhitunganPAEmpAdapter perhitunganPAEmpAdapter;

    List<PerhitunganPAModel> perhitunganPAModelList = new ArrayList<>();
    List<PerhitunganPAEMPModel> perhitunganPAEMPModelList = new ArrayList<>();

    List<ApprDNModell> apprDNModellList = new ArrayList<>();

    List<SortDNModel> sortDNModelList = new ArrayList<>();
    SortAdapter sortAdapter;

    WebView wbChart;
    BarChart barChart;

    ImageButton btnListAppr, btnSort, btnReject;
    TextInputEditText edtSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_distribusi_normal, container, false);

        wbChart = (WebView) rootView.findViewById(R.id.wbChart);
        barChart = (BarChart) rootView.findViewById(R.id.barchart);
        btnListAppr = rootView.findViewById(R.id.btnListAppr);
        btnSort = rootView.findViewById(R.id.btnSort);
        edtSearch = rootView.findViewById(R.id.edtSearch);
        btnReject = rootView.findViewById(R.id.btnReject);

        getApprList();
        btnListAppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showApprListDialog(apprDNModellList);
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRejectDialog();
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortDialog();

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    perhitunganPAEmpAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        contoh2bar();
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                float x=e.getX();
                float y=e.getY();

                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.CEILING);

                Toast.makeText(getContext(),df.format(x)+"",Toast.LENGTH_SHORT).show();

//                List<PerhitunganPAEMPModel> xx = new ArrayList<>();
//                xx.addAll(perhitunganPAEMPModelList);
//               for(int i=0;i<xx.size();i++){
//                   if(xx.get(i).getStarDN() != Math.ceil(x)){
//                       xx.remove(i);
//                   }
//               }
//                perhitunganPAEmpAdapter = new PerhitunganPAEmpAdapter(xx, getContext(), ConnectivityReceiver.isConnected(), getActivity());
//                rvEmployee.setAdapter(perhitunganPAEmpAdapter);
            }

            @Override
            public void onNothingSelected()
            {

            }
        });




        //============DN Table Grafik=======================
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDNGraphTable);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        prepareGraphTable();
        perhitunganPAAdapter = new PerhitunganPAAdapter(perhitunganPAModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity());
        recyclerView.setAdapter(perhitunganPAAdapter);
        //==================================================
        //============DN Table Employee=======================
        rvEmployee = (RecyclerView) rootView.findViewById(R.id.rvDNEmp);
         mLayoutManager = new LinearLayoutManager(getContext());
        rvEmployee.setLayoutManager(mLayoutManager);
        prepareEmployeeTable();
        perhitunganPAEmpAdapter = new PerhitunganPAEmpAdapter(perhitunganPAEMPModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity(), this);
        rvEmployee.setAdapter(perhitunganPAEmpAdapter);
        //==================================================
        return  rootView;
    }

    private void contoh2bar(){

        List<BarEntry> DNDone = new ArrayList();
        DNDone.add(new BarEntry(0f, 10f));
        DNDone.add(new BarEntry(1f, 12f));
        DNDone.add(new BarEntry(2f, 26f));
        DNDone.add(new BarEntry(3f, 192f));
        DNDone.add(new BarEntry(4f, 46f));
        DNDone.add(new BarEntry(5f, 0));

        List<BarEntry> DNundone = new ArrayList();
        DNundone.add(new BarEntry(0f, 11f));
        DNundone.add(new BarEntry(1f, 10f));
        DNundone.add(new BarEntry(2f, 6f));
        DNundone.add(new BarEntry(3f, 14f));
        DNundone.add(new BarEntry(4f, 11f));
        DNDone.add(new BarEntry(5f, 0));

//        List<String> year = new ArrayList();
//        year.add("2020");

        BarDataSet bardataset = new BarDataSet(DNDone, "DN");
        BarDataSet bardataset2 = new BarDataSet(DNundone, "Adjustment");
        barChart.animateY(5000);

        bardataset.setColors(getContext().getResources().getColor(R.color.colorPrimaryDark));
        bardataset2.setColors(getContext().getResources().getColor(R.color.light_grey2));
        BarData data = new BarData((IBarDataSet)  bardataset,(IBarDataSet)  bardataset2);
//
        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
//        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"
//
        barChart.getDescription().setText("Distribusi Normal");

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(xAxisFormatter);

        data.setBarWidth(barWidth); // set the width of each bar
        barChart.setData(data);
        barChart.groupBars(0f, groupSpace, barSpace); // perform the "explicit" grouping
        //barChart.animateXY(2000, 2000);
        barChart.invalidate(); // refresh
    }

    private void contoh1bar() {
        List<BarEntry> DNDone = new ArrayList();
        DNDone.add(new BarEntry(0f, 10f));
        DNDone.add(new BarEntry(1f, 12f));
        DNDone.add(new BarEntry(2f, 26f));
        DNDone.add(new BarEntry(3f, 192f));
        DNDone.add(new BarEntry(4f, 46f));

        List<BarEntry> DNundone = new ArrayList();
        DNundone.add(new BarEntry(0f, 11f));
        DNundone.add(new BarEntry(1f, 10f));
        DNundone.add(new BarEntry(2f, 6f));
        DNundone.add(new BarEntry(3f, 14f));
        DNundone.add(new BarEntry(4f, 11f));

        BarDataSet bardataset = new BarDataSet(DNDone, "Done");
        barChart.animateY(5000);

        bardataset.setColors(getContext().getResources().getColor(R.color.colorPrimaryDark));
        BarData data = new BarData((IBarDataSet) bardataset);

        barChart.getDescription().setText("Distribusi Normal");

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(xAxisFormatter);

        barChart.setData(data);
        barChart.invalidate(); // refresh
    }

    public void getApprList(){
        for(int i=0;i<3;i++){
            ApprDNModell ap = new ApprDNModell();
            ap.setApprName("Donald Duck");
            ap.setApprStatus("Approved");
            ap.setApprDate("13/02/2020");
            apprDNModellList.add(ap);
        }
    }

    public void showApprListDialog(List<ApprDNModell> apprDNModellList){
        Dialog dialog = new Dialog(getContext());
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.approval_list_dn_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        RecyclerView rvApprovalList = dialog.findViewById(R.id.rv_approvalListDN);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvApprovalList.setLayoutManager(mLayoutManager);
        ApprovalListAdapter approvalListAdapter = new ApprovalListAdapter(apprDNModellList,getContext(),ConnectivityReceiver.isConnected(),getActivity());
        rvApprovalList.setAdapter(approvalListAdapter);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showRejectDialog(){
        Dialog dialog = new Dialog(getContext());
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.approval_list_dn_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        RecyclerView rvApprovalList = dialog.findViewById(R.id.rv_approvalListDN);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvApprovalList.setLayoutManager(mLayoutManager);
        ApprovalListAdapter approvalListAdapter = new ApprovalListAdapter(apprDNModellList,getContext(),ConnectivityReceiver.isConnected(),getActivity());
        rvApprovalList.setAdapter(approvalListAdapter);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void showSortDialog(){
        Dialog dialog = new Dialog(getContext());
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.sort_dn_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        RecyclerView rvSortDN = dialog.findViewById(R.id.rv_sortDN);
        Button plusBtn = dialog.findViewById(R.id.btnPlus);
        Button minusBtn = dialog.findViewById(R.id.btnMinus);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSave = dialog.findViewById(R.id.btnSort);


        if(sortDNModelList.size()<1) {
            SortDNModel sd = new SortDNModel();
            sd.setOrder("");
            sd.setSortBy("");
            sd.setOrdinal(1);
            sortDNModelList.add(sd);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSortDN.setLayoutManager(mLayoutManager);
            sortAdapter = new SortAdapter(sortDNModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity(),perhitunganPAEMPModelList);
            rvSortDN.setAdapter(sortAdapter);
        }
        else{
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSortDN.setLayoutManager(mLayoutManager);
            sortAdapter = new SortAdapter(sortDNModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity(),perhitunganPAEMPModelList);
            rvSortDN.setAdapter(sortAdapter);
        }
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDNModel sd = new SortDNModel();
                sd.setOrder("");
                sd.setSortBy("");
                sd.setOrdinal(1);
                sortDNModelList.add(sd);
                sortAdapter.notifyDataSetChanged();
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDNModelList.remove(sortDNModelList.size()-1);
                sortAdapter.notifyDataSetChanged();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSort(sortDNModelList);
                dialog.dismiss();
                perhitunganPAEmpAdapter.notifyDataSetChanged();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }


private void prepareGraphTable(){

    for (int i=0;i<5;i++){
        PerhitunganPAModel pa = new PerhitunganPAModel();
        pa.setBA("70,34");
        pa.setJumlah("10");
        pa.setPersen("10%");
        pa.setJumlah2("15");
        pa.setPersen2("15%");
        pa.setN("K");
        pa.setRange("kx < k<= AB <K");
        pa.setStar(4);
        perhitunganPAModelList.add(pa);
    }
}

    private void prepareEmployeeTable(){

        for (int i=0;i<10;i++){
            PerhitunganPAEMPModel pa = new PerhitunganPAEMPModel();
            pa.setAlasanAdj("");
            pa.setAlasanReject("");

            pa.setEmpBranchName("Jakarta");
            pa.setEmpJobTitle("Salesman KND - Jakarta");
            pa.setEmpEmail("test@enseval.com");
            pa.setEmpName("Samuel Septiano");
            pa.setNilaiAdj("A");
            pa.setNilaiDN("L");
            pa.setStarAdj(3);
            pa.setStarDN(4);
            pa.setScore("75.5");

            perhitunganPAEMPModelList.add(pa);
        }
    }

    @Override
    public List<SortDNModel> getSort() {
        return sortDNModelList;
    }

    @Override
    public void setSort(List<SortDNModel> sortDNModelList) {
        this.sortDNModelList = sortDNModelList;
    }
}


class DayAxisValueFormatter extends ValueFormatter {
    private final BarLineChartBase<?> chart;
    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }
    @Override
    public String getFormattedValue(float value) {
//        float a = value;
//        String aa="";
//        if(value>0){
//            aa = Float.toString(value);
//        }
        return Float.toString(value+1);
    }
}