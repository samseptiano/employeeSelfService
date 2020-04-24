package com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIApproveListAdapter;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment.interfaces.UpdateableFragmentListener;
import com.enseval.samuelseptiano.hcservice.Helper.EmpOrgRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpLocationModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpOrgModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIApproveListPJ;
import com.enseval.samuelseptiano.hcservice.Model.FilterPAModel;
import com.enseval.samuelseptiano.hcservice.Model.FilterPJModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.EmpOrgRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TotalFragment extends Fragment implements UpdateableFragmentListener,KPIApproveListAdapter.EventListener {

    View rootView;
    RecyclerView recyclerView;
    KPIApproveListAdapter kpiApproveListAdapter;
    String type="",direktorat="",site="",tahun="";
    String typee = "",jenis="",empType="";

    Drawable icon;

    ImageView imgNoResult;
    List<EmpOrgModel> empOrgModelList = new ArrayList<>();
    List<EmpLocationModel> empLocationModelList = new ArrayList<>();

    List<String> orgNameList = new ArrayList<>();
    List<String> locationNameList = new ArrayList<>();

    public FilterPAModel filterPAModel;
    LinearLayout lnProgresHeader;

    LinearLayout lnFilter;
    ImageButton imgBtnFilter;

    private List<KPIApproveListPJ> kpiApproveLists = new ArrayList<>();

    public TotalFragment(String s, List<KPIApproveListPJ> kpiApproveLists, Drawable icon,String empType) {
        type = s;
        this.kpiApproveLists = kpiApproveLists;
        this.icon = icon;
        this.empType=empType;
        // Required empty public constructor
    }

    public static TotalFragment newInstance(String s, List<KPIApproveListPJ> kpiApproveLists, Drawable icon,String empType) {
        TotalFragment fragment = new TotalFragment(s,kpiApproveLists,icon,empType);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tahun = bundle.getString("tahun");
            site = bundle.getString("Site");
            direktorat = bundle.getString("Direktorat");
            typee = bundle.getString("types");
            jenis = bundle.getString("jenis");
            empType = bundle.getString("empType");
            filterPAModel = (FilterPAModel) bundle.getSerializable("operan");
        }

        if(tahun==null ||tahun.equals("")){
            tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_total_pj, container, false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(false);
//        actionBar.setTitle(empType+" Team Performance");
        actionBar.setTitle("My Team Performance");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tahun = bundle.getString("tahun");
            site = bundle.getString("Site");
            direktorat = bundle.getString("Direktorat");
            typee = bundle.getString("types");
            jenis = bundle.getString("jenis");
            filterPAModel = (FilterPAModel) bundle.getSerializable("operan");

        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvTotalPA);
        lnFilter = rootView.findViewById(R.id.lnFilter);
        imgBtnFilter = rootView.findViewById(R.id.imgBtnFilter);
        imgNoResult = rootView.findViewById(R.id.imgNoResult);
        SwipeRefreshLayout pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        lnProgresHeader = rootView.findViewById(R.id.linlaHeaderProgressTotal);

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        pullToRefresh.setEnabled(false);
        prepareData(usr.get(0).getEmpNIK(),usr.get(0).getToken());


        if(typee.equals("STATUS")){
            lnFilter.setVisibility(View.VISIBLE);
            pullToRefresh.setEnabled(true);
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    prepareData(usr.get(0).getEmpNIK(),usr.get(0).getToken());
                    pullToRefresh.setRefreshing(false);
                }
            });

            imgBtnFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogFilter();
                }
            });
        }

        return rootView;
    }



    private void prepareData(String nik, String token) {

        EmpOrgRealmHelper empOrgRealmHelper = new EmpOrgRealmHelper(getContext());
        ArrayList<EmpOrgRealmModel> org;
        org = empOrgRealmHelper.findAllArticle();

        for (int i=0;i<org.size();i++){
            EmpOrgModel em = new EmpOrgModel();
            em.setOrgName(org.get(i).getOrgName());
            em.setOrgCode(org.get(i).getOrgCode());
            orgNameList.add(org.get(i).getOrgName());
            empOrgModelList.add(em);
        }

                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                Call<List<EmpLocationModel>> call2 = apiService.getEmpLocation(nik,Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+token);
                call2.enqueue(new Callback<List<EmpLocationModel>>() {
                    @Override
                    public void onResponse(Call<List<EmpLocationModel>> call, Response<List<EmpLocationModel>> response) {
                        int statusCode = response.code();
                         empLocationModelList= response.body();
                        for (int i=0;i<empLocationModelList.size();i++){
                            locationNameList.add(empLocationModelList.get(i).getLocationname());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<EmpLocationModel>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        if(kpiApproveLists.size()>0) {
            kpiApproveListAdapter = new KPIApproveListAdapter(kpiApproveLists, getContext(), getActivity(), this,empType);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(kpiApproveListAdapter);
        }
        else{
            imgNoResult.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            imgNoResult.setImageDrawable(icon);
//            Toast.makeText(getContext(),type,Toast.LENGTH_LONG).show();
//            if(type.contains("NOT APPROVED")){
//                imgNoResult.setImageDrawable(getResources().getDrawable(R.drawable.approved));
//            }
//            if(type.contains("APPROVED")){
//                imgNoResult.setImageDrawable(getResources().getDrawable(R.drawable.complete));
//            }
            //Toast.makeText(getContext(),"No Result Found",Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void update(FilterPJModel filterPjModel) {
        //Toast.makeText(getContext(),"Operan: "+tahun+" " + filterPAModel.getDirektorat(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEvent(int position, String Tahun, String Direktorat, String Site, String Semester) {
        tahun = Tahun;
        direktorat = Direktorat;
        site = Site;

    }

    @Override
    public String getTahun() {
        return tahun;
    }

    @Override
    public String getDirektorat() {
        return direktorat;
    }

    @Override
    public String getSite() {
        return site;
    }

    private void showDialogFilter(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.filter_dialog_pk);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);


        TagsEditText mTagsEditText = dialog.findViewById(R.id.tagsDirektorat);
        TagsEditText mTagsSite = dialog.findViewById(R.id.tagsSite);
        Button btnReset = dialog.findViewById(R.id.btnReset);
        Button btnOk = dialog.findViewById(R.id.btnOk);


        mTagsEditText.setHint("Type Organization Name");
        //mTagsEditText.setTagsListener(MainActivity.this);
        mTagsEditText.setTagsWithSpacesEnabled(true);
        mTagsEditText.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, orgNameList));
        mTagsEditText.setThreshold(1);


        mTagsSite.setHint("Ketik Site Name");
        //mTagsEditText.setTagsListener(MainActivity.this);
        mTagsSite.setTagsWithSpacesEnabled(true);
        mTagsSite.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, locationNameList));
        mTagsSite.setThreshold(1);



        List<String> tahuns = new ArrayList<String>();
        tahuns.add("- tahun -");
        tahuns.add(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1));
        tahuns.add(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tahuns);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                direktorat = mTagsEditText.getText().toString();
                site = mTagsSite.getText().toString();
                FilterPAModel filterPAModel = new FilterPAModel(tahun,direktorat,site);
                kpiApproveLists = new ArrayList<>();
                //========================================================================================================
                UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();

                lnProgresHeader.setVisibility(View.VISIBLE);
                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                //"131102891"
                Call<List<KPIApproveListPJ>> call = apiService.getuserListPK(usr.get(0).getEmpNIK(),"2020-09-01","2020-09-09",empType,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<List<KPIApproveListPJ>>() {
                    @Override
                    public void onResponse(Call<List<KPIApproveListPJ>> call, Response<List<KPIApproveListPJ>> response) {
                        int statusCode = response.code();
                        kpiApproveLists = response.body();

                        if(kpiApproveLists.size()>0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView = (RecyclerView) rootView.findViewById(R.id.rvTotalPA);
                            lnFilter = rootView.findViewById(R.id.lnFilter);
                            imgBtnFilter = rootView.findViewById(R.id.imgBtnFilter);
                            imgNoResult = rootView.findViewById(R.id.imgNoResult);

                            kpiApproveListAdapter = new KPIApproveListAdapter(kpiApproveLists, getContext(), getActivity(), TotalFragment.this,empType);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(kpiApproveListAdapter);
                            imgNoResult.setVisibility(View.GONE);
                        }
                        else{
                            recyclerView.setVisibility(View.GONE);
                            imgNoResult.setVisibility(View.VISIBLE);
                            imgNoResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.no_results_found));
                            Toast.makeText(getContext(),"No Result Found",Toast.LENGTH_LONG).show();
                        }
                        lnProgresHeader.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<List<KPIApproveListPJ>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                        lnProgresHeader.setVisibility(View.GONE);

                    }
                });
                //========================================================================================================

//                mManagerFragment.update(filterPAModel);
                dialog.dismiss();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTagsEditText.setText("");
                mTagsSite.setText("");
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
}
