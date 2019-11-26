package com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIApproveListAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIApproveListTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.adapter.TabsAdapter;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.interfaces.UpdateableFragmentListener;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.FilterPAModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TotalFragment extends Fragment implements UpdateableFragmentListener,KPIApproveListAdapterTahunan.EventListener {

    View rootView;
    RecyclerView recyclerView;
    KPIApproveListAdapterTahunan kpiApproveListAdapterTahunan;
    String type="",direktorat="",site="",tahun="";
    String typee = "",jenis="";

    ImageView imgNoResult;

    public FilterPAModel filterPAModel;

    LinearLayout lnFilter;
    ImageButton imgBtnFilter;

    private List<KPIApproveList> kpiApproveLists = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsApprove = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsNotApprove = new ArrayList<>();

    public TotalFragment(String s, List<KPIApproveList> kpiApproveLists) {
        type = s;
        this.kpiApproveLists = kpiApproveLists;
        // Required empty public constructor
    }

    public static TotalFragment newInstance(String s,List<KPIApproveList> kpiApproveLists) {
        TotalFragment fragment = new TotalFragment(s,kpiApproveLists);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tahun = bundle.getString("Tahun");
            site = bundle.getString("Site");
            direktorat = bundle.getString("Direktorat");
            typee = bundle.getString("types");
            jenis = bundle.getString("jenis");
            filterPAModel = (FilterPAModel) bundle.getSerializable("operan");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_total, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tahun = bundle.getString("Tahun");
            site = bundle.getString("Site");
            direktorat = bundle.getString("Direktorat");
            typee = bundle.getString("types");
            jenis = bundle.getString("jenis");
        }

        try {
            Toast.makeText(getContext(), filterPAModel.getDirektorat(), Toast.LENGTH_LONG).show();
        }
        catch(Exception e){

        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvTotalPA);
        lnFilter = rootView.findViewById(R.id.lnFilter);
        imgBtnFilter = rootView.findViewById(R.id.imgBtnFilter);
        imgNoResult = rootView.findViewById(R.id.imgNoResult);

//        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
//        ArrayList<UserRealmModel> usr;
//        usr = userRealmHelper.findAllArticle();

//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<List<KPIApproveList>> call = apiService.getuserList(usr.get(0).getEmpNIK(),"Bearer "+usr.get(0).getToken());
//        call.enqueue(new Callback<List<KPIApproveList>>() {
//            @Override
//            public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
//                int statusCode = response.code();
//                kpiApproveLists = response.body();
//                //Toast.makeText(getContext(),kpiApproveLists.get(0).getEmpPhoto(),Toast.LENGTH_LONG).show();
//                //kpiApproveListAdapterTahunan = new KPIApproveListAdapterTahunan(kpiApproveLists,getContext(),getActivity(), this);

                if(kpiApproveLists.size()>0) {
                    kpiApproveListAdapterTahunan = new KPIApproveListAdapterTahunan(kpiApproveLists, getContext(), getActivity(), TotalFragment.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(kpiApproveListAdapterTahunan);
                }
                else{
                    imgNoResult.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"No Result Found",Toast.LENGTH_LONG).show();
                }



//            }
//            @Override
//            public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
//                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
//            }
//        });



        if(typee.equals("STATUS")){
            lnFilter.setVisibility(View.VISIBLE);

            imgBtnFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogFilter();
                }
            });
        }


        //prepareData();

        return rootView;
    }

    private void prepareData() {

            for(int i=0;i<3;i++){
                KPIApproveList ka = new KPIApproveList();
                ka.setDept("");
                ka.setEmpName("Adiwinata Saputra");
                ka.setId("190300270");
                ka.setNIK("190300270");
                ka.setJobTitle("Salesman");
                ka.setBranchName("Makassar");
                ka.setDept("KND");
                ka.setStar("4");
                ka.setStatus("Complete");
                ka.setJobStatus("Tetap");
                ka.setDateStart("22/12/2018");
                ka.setDateEnd("22/03/2019");
                //=== parameter list bawahan berdasarkan nik akun tersebut ===
                ka.setNIKAtasan1("030300324");
                ka.setNIKAtasan2("070500332");
                ka.setNamaAtasan1("Norish Hanoch Wairata");
                ka.setNamaAtasan2("Rudy Djajasaputra");
        //==================
                    if(i%2==0){
                        ka.setStatus1("Not Approved");
                    }
                    else {
                        ka.setStatus1("Approved");
                    }
                        kpiApproveLists.add(ka);

                }
                    kpiApproveListAdapterTahunan.notifyDataSetChanged();


    }

    @Override
    public void update(FilterPAModel filterPAModel) {
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
        dialog.setContentView(R.layout.filter_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        MaterialSpinner spinnerTahun = dialog.findViewById(R.id.spinnerTahun);
        spinnerTahun.setBackgroundResource(R.drawable.shapesignup);
        spinnerTahun.setPadding(25, 10, 25, 10);


        TagsEditText mTagsEditText = dialog.findViewById(R.id.tagsDirektorat);
        TagsEditText mTagsSite = dialog.findViewById(R.id.tagsSite);
        Button btnReset = dialog.findViewById(R.id.btnReset);
        Button btnOk = dialog.findViewById(R.id.btnOk);


        mTagsEditText.setHint("Enter names of fruits");
        //mTagsEditText.setTagsListener(MainActivity.this);
        mTagsEditText.setTagsWithSpacesEnabled(true);
        mTagsEditText.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.fruits)));
        mTagsEditText.setThreshold(1);


        mTagsSite.setHint("Enter names of fruits");
        //mTagsEditText.setTagsListener(MainActivity.this);
        mTagsSite.setTagsWithSpacesEnabled(true);
        mTagsSite.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.fruits)));
        mTagsSite.setThreshold(1);



        List<String> tahuns = new ArrayList<String>();
        tahuns.add("2018");
        tahuns.add("2019");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tahuns);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerTahun.setAdapter(dataAdapter);



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tahun = spinnerTahun.getItems().get(spinnerTahun.getSelectedIndex()).toString();
                direktorat = mTagsEditText.getText().toString();
                site = mTagsSite.getText().toString();
                FilterPAModel filterPAModel = new FilterPAModel(tahun,direktorat,site);
                kpiApproveLists = new ArrayList<>();
                //========================================================================================================
                UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<List<KPIApproveList>> call = apiService.getuserList(usr.get(0).getEmpNIK(),tahun,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<List<KPIApproveList>>() {
                    @Override
                    public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
                        int statusCode = response.code();
                        kpiApproveLists = response.body();

                        if(kpiApproveLists.size()>0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView = (RecyclerView) rootView.findViewById(R.id.rvTotalPA);
                            lnFilter = rootView.findViewById(R.id.lnFilter);
                            imgBtnFilter = rootView.findViewById(R.id.imgBtnFilter);
                            imgNoResult = rootView.findViewById(R.id.imgNoResult);

                            kpiApproveListAdapterTahunan = new KPIApproveListAdapterTahunan(kpiApproveLists, getContext(), getActivity(), TotalFragment.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(kpiApproveListAdapterTahunan);
                            imgNoResult.setVisibility(View.GONE);
                        }
                        else{
                            recyclerView.setVisibility(View.GONE);
                            imgNoResult.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(),"No Result Found",Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
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
                spinnerTahun.setSelectedIndex(0);
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
