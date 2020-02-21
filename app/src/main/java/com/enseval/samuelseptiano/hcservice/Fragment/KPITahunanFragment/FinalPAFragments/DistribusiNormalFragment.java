package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.FinalPAFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Adapter.PaginationAdapter.PaginationScrollListener;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.ApprovalListAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.OnLoadMoreListener;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.PerhitunganPAAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.PerhitunganPAEmpAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.SortAdapter;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ApprDNModell;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ApproveRejectDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.DistNormalH;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ProcessDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.SortDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Home.Home;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.softrunapps.paginatedrecyclerview.PaginatedAdapter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.enseval.samuelseptiano.hcservice.Adapter.PaginationAdapter.PaginationScrollListener.PAGE_START;


public class DistribusiNormalFragment extends Fragment implements PerhitunganPAEmpAdapter.EventListener{

    View rootView;
    RecyclerView recyclerView, rvEmployee;
    PerhitunganPAAdapter perhitunganPAAdapter;
    PerhitunganPAEmpAdapter perhitunganPAEmpAdapter;

    private String DNID = "";
    int page=0;
    int pageSize=500;

    List<PerhitunganPAModel> perhitunganPAModelList = new ArrayList<>();
    List<PerhitunganPAEMPModel> perhitunganPAEMPModelList = new ArrayList<>();

    List<ApprDNModell> apprDNModellList = new ArrayList<>();

    List<DistNormalH> distNormalHList = new ArrayList<>();

    List<SortDNModel> sortDNModelList = new ArrayList<>();
    SortAdapter sortAdapter;

    WebView wbChart;
    BarChart barChart;

    ImageButton btnListAppr, btnSort, btnReject;
    Button btnApprove;
    TextInputEditText edtSearch;

    MaterialSpinner spinnerDN;
    List<String> dataDN=new ArrayList<>();

    TextView tvMean,tvStdDev;

    ArrayList<UserRealmModel> usr = new ArrayList<>();

    LinearLayoutManager mLayoutManager;

    Button btnProcessDN;

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
        btnApprove = rootView.findViewById(R.id.btnApproveDN);
        tvMean = rootView.findViewById(R.id.tvMean);
        tvStdDev = rootView.findViewById(R.id.tvStandarDeviasi);

        spinnerDN = rootView.findViewById(R.id.spinnerDN);
        spinnerDN.setBackgroundResource(R.drawable.shapedropdown);
        spinnerDN.setPadding(25, 10, 25, 10);
        btnProcessDN = rootView.findViewById(R.id.btnProcessDN);

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        usr = userRealmHelper.findAllArticle();

        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                    //============================ HARDCODE =====================
        Call<List<DistNormalH>> call = apiService.getDistNormalH(usr.get(0).getEmpNIK(),usr.get(0).getPrivilege(), "Bearer " + usr.get(0).getToken());
        call.enqueue(new Callback<List<DistNormalH>>() {
            @Override
            public void onResponse(Call<List<DistNormalH>> call, Response<List<DistNormalH>> response) {
                int statusCode = response.code();
                distNormalHList = response.body();
                if(response.body().size()<1){}
                else {
                    dataDN.add("-PILIH-");
                    for (int j = 0; j < distNormalHList.size(); j++) {
                        dataDN.add(distNormalHList.get(j).getDNGROUPNAME());
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, dataDN);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDN.setAdapter(dataAdapter);


                    spinnerDN.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            for (int j = 0; j < distNormalHList.size(); j++) {
                                if(item.equals(distNormalHList.get(j).getDNGROUPNAME())){

                                    getApprList(distNormalHList.get(j).getDNID());
//

                                    prepareGraphTable(distNormalHList.get(j));

                                    tvMean.setText("Mean :"+distNormalHList.get(j).getDNMEAN());
                                    tvStdDev.setText("Standar Deviasi :"+distNormalHList.get(j).getDNSTDEV());

                                    contoh2bar(distNormalHList.get(j));

                                    DNID = distNormalHList.get(j).getDNID();

                                    prepareEmployeeTable(distNormalHList.get(j).getDNID(),page,pageSize);
                                    LinearLayoutManager finalMLayoutManager = mLayoutManager;
                                    int finalJ = j;


                                    btnProcessDN.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                                            //============================ HARDCODE =====================
                                            ProcessDNModel processDNModel = new ProcessDNModel();
                                            processDNModel.setDnID(distNormalHList.get(finalJ).getDNID());
                                            processDNModel.setGroupID(distNormalHList.get(finalJ).getDNID());
                                            processDNModel.setGroupIDName(distNormalHList.get(finalJ).getDNGROUPNAME());
                                            processDNModel.setPeriode(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                                            processDNModel.setUpdUser(usr.get(0).getUsername());
                                            Call<Void> call = apiService.processDN(processDNModel, "Bearer " + usr.get(0).getToken());
                                            call.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    int statusCode = response.code();
                                                    Toast.makeText(getContext(), "Process Complete", Toast.LENGTH_LONG).show();

                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });

                                }
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<DistNormalH>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });


        btnListAppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showApprListDialog(apprDNModellList);
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRejectDialog(spinnerDN.getText().toString(),DNID);
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

        //============DN Table Employee=======================
        rvEmployee = (RecyclerView) rootView.findViewById(R.id.rvDNEmp);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvEmployee.setLayoutManager(mLayoutManager);
     //==================================================

        //============DN Table Grafik=======================
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDNGraphTable);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        perhitunganPAAdapter = new PerhitunganPAAdapter(perhitunganPAModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity());
        recyclerView.setAdapter(perhitunganPAAdapter);
        //==================================================

        return  rootView;
    }

    private void contoh2bar(DistNormalH dh){

        List<BarEntry> DNDone = new ArrayList();
        DNDone.add(new BarEntry(0f, Float.parseFloat(dh.getJUMLAHDNBAE().replace(",","."))));
        DNDone.add(new BarEntry(1f, Float.parseFloat(dh.getJUMLAHDNBAB().replace(",","."))));
        DNDone.add(new BarEntry(2f, Float.parseFloat(dh.getJUMLAHDNBAL().replace(",","."))));
        DNDone.add(new BarEntry(3f, Float.parseFloat(dh.getJUMLAHDNBAA().replace(",","."))));
        DNDone.add(new BarEntry(4f, Float.parseFloat(dh.getJUMLAHDNBAK().replace(",","."))));
        DNDone.add(new BarEntry(5f, 0));

        List<BarEntry> DNundone = new ArrayList();
        DNundone.add(new BarEntry(0f, Float.parseFloat(dh.getJUMLAHDNBAEADJ().replace(",","."))));
        DNundone.add(new BarEntry(1f, Float.parseFloat(dh.getJUMLAHDNBABADJ().replace(",","."))));
        DNundone.add(new BarEntry(2f, Float.parseFloat(dh.getJUMLAHDNBALADJ().replace(",","."))));
        DNundone.add(new BarEntry(3f, Float.parseFloat(dh.getJUMLAHDNBAAADJ().replace(",","."))));
        DNundone.add(new BarEntry(4f, Float.parseFloat(dh.getJUMLAHDNBAKADJ().replace(",","."))));
        DNDone.add(new BarEntry(5f, 0));

//        List<String> year = new ArrayList();
//        year.add("2020");

        BarDataSet bardataset = new BarDataSet(DNDone, "DN");
        BarDataSet bardataset2 = new BarDataSet(DNundone, "DN Adj.");
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

    public void getApprList(String dnID){

        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
        //============================ HARDCODE =====================
        Call<List<ApprDNModell>> call = apiService.getDistNormarAppr(dnID, "Bearer " + usr.get(0).getToken());
        call.enqueue(new Callback<List<ApprDNModell>>() {
            @Override
            public void onResponse(Call<List<ApprDNModell>> call, Response<List<ApprDNModell>> response) {
                int statusCode = response.code();
                   apprDNModellList = response.body();
                for(int x=0;x<apprDNModellList.size();x++){
                                        if(usr.get(0).getEmpNIK().equals(apprDNModellList.get(x).getApprNIK())
                                        && (apprDNModellList.get(x).getApprStatus().equals("APPROVED")
                                                || apprDNModellList.get(x).getApprStatus().equals("REJECTED"))){
                                            btnApprove.setVisibility(View.GONE);
                                        }
                                    }

                btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                        //============================ HARDCODE =====================
                        ApproveRejectDNModel ar = new ApproveRejectDNModel();
                        ar.setDNID(dnID);
                        ar.setALASANREJECT("");
                        ar.setEMPNIK(usr.get(0).getEmpNIK());
                        ar.setUPDUSER(usr.get(0).getUsername());
                        for(int x=0;x<apprDNModellList.size();x++){
                            if(usr.get(0).getEmpNIK().equals(apprDNModellList.get(x).getApprNIK())){
                                    ar.setDNAPPROVALSEQ(apprDNModellList.get(x).getDnApprovalSeq());
                            }
                        }
                        Call<Void> call = apiService.approveDistNormal(ar, "Bearer " + usr.get(0).getToken());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int statusCode = response.code();
                                Toast.makeText(getContext(),"Approve Berhasil",Toast.LENGTH_LONG).show();
                                    prepareEmployeeTable(dnID,page,pageSize);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });

            }

            @Override
            public void onFailure(Call<List<ApprDNModell>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });

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

    public void showRejectDialog( String title, String DNID){
        Dialog dialog = new Dialog(getContext());
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.reject_dn_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        EditText edtAlasanReject = dialog.findViewById(R.id.edtReject);
        Button btnRejectDN = dialog.findViewById(R.id.btnRejectDN);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        tvTitle.setText("Reject "+title);

        btnRejectDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAlasanReject.getText().toString().length()>0){
                    ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                    //============================ HARDCODE =====================
                    ApproveRejectDNModel ar = new ApproveRejectDNModel();
                    ar.setDNID(DNID);
                    ar.setALASANREJECT(edtAlasanReject.getText().toString());
                    ar.setEMPNIK(usr.get(0).getEmpNIK());
                    ar.setUPDUSER(usr.get(0).getUsername());
                    for(int x=0;x<apprDNModellList.size();x++){
                        if(usr.get(0).getEmpNIK().equals(apprDNModellList.get(x).getApprNIK())){
                            ar.setDNAPPROVALSEQ(apprDNModellList.get(x).getDnApprovalSeq());
                        }
                    }
                    Call<Void> call = apiService.rejectDistNormal(ar, "Bearer " + usr.get(0).getToken());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int statusCode = response.code();
                            Toast.makeText(getContext(),"Reject Berhasil",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            prepareEmployeeTable(DNID,page,pageSize);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
                else{
                    Toast.makeText(getContext(),"Alasan reject tidak boleh kosong..", Toast.LENGTH_LONG).show();

                }

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


private void prepareGraphTable(DistNormalH dh){
    perhitunganPAModelList = new ArrayList<>();
        PerhitunganPAModel pa = new PerhitunganPAModel();
        pa.setBA(dh.getDNBAE());
        pa.setRange(dh.getRANGEDNE());
        pa.setJumlah(dh.getJUMLAHDNBAE());
        pa.setPersen(dh.getPERSENDNBAE());
        pa.setJumlah2(dh.getJUMLAHDNBAEADJ());
        pa.setPersen2(dh.getPERSENDNBAEADJ());
        pa.setN("E");
        pa.setStar(1);
        perhitunganPAModelList.add(pa);

        pa = new PerhitunganPAModel();
        pa.setBA(dh.getDNBAB());
    pa.setRange(dh.getRANGEDNB());
    pa.setJumlah(dh.getJUMLAHDNBAB());
        pa.setPersen(dh.getPERSENDNBAB());
        pa.setJumlah2(dh.getJUMLAHDNBABADJ());
        pa.setPersen2(dh.getPERSENDNBABADJ());
        pa.setN("B");
        pa.setStar(2);
        perhitunganPAModelList.add(pa);

        pa = new PerhitunganPAModel();
        pa.setBA(dh.getDNBAL());
    pa.setRange(dh.getRANGEDNL());
    pa.setJumlah(dh.getJUMLAHDNBAL());
        pa.setPersen(dh.getPERSENDNBAL());
        pa.setJumlah2(dh.getJUMLAHDNBALADJ());
        pa.setPersen2(dh.getPERSENDNBALADJ());
        pa.setN("L");
        pa.setStar(3);
        perhitunganPAModelList.add(pa);

        pa = new PerhitunganPAModel();
        pa.setBA(dh.getDNBAA());
    pa.setRange(dh.getRANGEDNA());
    pa.setJumlah(dh.getJUMLAHDNBAA());
        pa.setPersen(dh.getPERSENDNBAA());
        pa.setJumlah2(dh.getJUMLAHDNBAAADJ());
        pa.setPersen2(dh.getPERSENDNBAAADJ());
        pa.setN("A");
        pa.setStar(4);
        perhitunganPAModelList.add(pa);

        pa = new PerhitunganPAModel();
        pa.setBA("");
        pa.setJumlah("0");
        pa.setPersen("0");
        pa.setJumlah2("");
    pa.setRange("");
    pa.setPersen2("");
        pa.setN("K");
        pa.setStar(5);
        perhitunganPAModelList.add(pa);
    perhitunganPAAdapter = new PerhitunganPAAdapter(perhitunganPAModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity());
    recyclerView.setAdapter(perhitunganPAAdapter);

}

private void prepareEmployeeTable(String dnid,int page,int pageSize) {

    ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
    //============================ HARDCODE =====================
    apiService.getDistNormalD(dnid,page,pageSize,"Bearer "+usr.get(0).getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<List<PerhitunganPAEMPModel>>() {

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<PerhitunganPAEMPModel> homeList) {

                            //if(homeList.size()!=0){
//                                for(int i=0;i<homeList.size();i++) {
//                                perhitunganPAEMPModelList.add(homeList.get(i));
//                                }
                                perhitunganPAEMPModelList = homeList;
                            //}
                        }

                        @Override
                        public void onError(Throwable e) {
                            //Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                            //============DN Table Employee=======================
                            perhitunganPAEmpAdapter = new PerhitunganPAEmpAdapter(perhitunganPAEMPModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity(), DistribusiNormalFragment.this);
                            //rvEmployee.setAdapter(perhitunganPAEmpAdapter);
                            perhitunganPAEmpAdapter.setStartPage(page); //set first page of data. default value is 1.
                            perhitunganPAEmpAdapter.setPageSize(pageSize);
                            perhitunganPAEmpAdapter.setDefaultRecyclerView(getActivity(), R.id.rvDNEmp); //set recyclerview with id and Activity. by default set LinearLayout for LayoutManager and setFixSize to true
                            perhitunganPAEmpAdapter.setOnPaginationListener(new PaginatedAdapter.OnPaginationListener() {
                                @Override
                                public void onCurrentPage(int page) {
                                    perhitunganPAEmpAdapter.submitItems(perhitunganPAEMPModelList);
                                }

                                @Override
                                public void onNextPage(int pages) {
                                    // call your method to get next page
                                    Toast.makeText(getContext(),"NEXT",Toast.LENGTH_LONG).show();
                                    prepareEmployeeTable(dnid,page+pageSize,pageSize);
                                }

                                @Override
                                public void onFinish() {
                                    // end of the list and all pages loaded
                                }
                            });

//                            prepareEmployeeTable(dnid,page+pageSize,pageSize);
                            //==================================================
                        }
                    });

}


    @Override
    public List<SortDNModel> getSort() {
        return sortDNModelList;
    }

    @Override
    public void setSort(List<SortDNModel> sortDNModelList) {
        this.sortDNModelList = sortDNModelList;
    }

    @Override
    public void SetValue(List<PerhitunganPAEMPModel> perhitunganPAEMPModelList) {
        this.perhitunganPAEMPModelList = perhitunganPAEMPModelList;
    }

    @Override
    public void onBottomReached(int position) {
        //prepareEmployeeTable("1",page+20,pageSize);
    }

    @Override
    public void reCallAPI() {
        prepareEmployeeTable(DNID,page,pageSize);
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