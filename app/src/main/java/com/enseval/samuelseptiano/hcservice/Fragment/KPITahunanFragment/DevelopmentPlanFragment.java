package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.DevelopmentPlanAdapterTahunan;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.DevelopmentPlan.UserListDevPlan;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DevelopmentPlanFragment extends Fragment implements DevelopmentPlanAdapterTahunan.EventListener {

    View rootView;
    RecyclerView rvDevplan;
    List<UserListDevPlan> kpiApproveListList = new ArrayList<>();
    DevelopmentPlanAdapterTahunan developmentPlanAdapterTahunan;
    LinearLayout lnProgress;
    SwipeRefreshLayout pullToRefresh;
    MaterialSpinner spinnerStatus;

    String status = "ALL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Individual Development Plan");
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_development_plan, container, false);

        spinnerStatus = rootView.findViewById(R.id.spinnerStatus);
        spinnerStatus.setBackgroundResource(R.drawable.shapedropdown);
        spinnerStatus.setPadding(25, 10, 25, 10);

        List<String> statuss = new ArrayList<String>();
        statuss.add("ALL");
        statuss.add("APPROVED");
        statuss.add("NOT APPROVED");

        spinnerStatus.setItems(statuss);

        spinnerStatus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (item.toString().equals("APPROVED")) {
                    status = "APPROVED";
                    developmentPlanAdapterTahunan.notifyDataSetChanged();
                }
                else if (item.toString().equals("NOT APPROVED")) {
                    status = "NOT APPROVED";
                    developmentPlanAdapterTahunan.notifyDataSetChanged();
                }
                else{
                    status = "ALL";
                    developmentPlanAdapterTahunan.notifyDataSetChanged();
                }
            }
        });


        rvDevplan = rootView.findViewById(R.id.rv_DevelopmentPlan);
        lnProgress = rootView.findViewById(R.id.linlaDevPlanProgress);
        pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        try {
            lnProgress.setVisibility(View.VISIBLE);
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    prepareData();


                    pullToRefresh.setRefreshing(false);
                }
            });
            prepareData();
        }
        catch (Exception e){

        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem mSearch = menu.findItem(R.id.mi_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return  false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String ss = s.toLowerCase(Locale.getDefault());
                try {
                  developmentPlanAdapterTahunan.filter(s);
                } catch (Exception e) {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return true;

            }
        });
    }
    private void prepareData() {
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        //lnProgress.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<UserListDevPlan>> call = apiService.getuserListDevPlan(usr.get(0).getEmpNIK(),"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<UserListDevPlan>>() {
            @Override
            public void onResponse(Call<List<UserListDevPlan>> call, Response<List<UserListDevPlan>> response) {
                int statusCode = response.code();
                kpiApproveListList = response.body();
                developmentPlanAdapterTahunan = new DevelopmentPlanAdapterTahunan(kpiApproveListList,getContext(),getActivity(),DevelopmentPlanFragment.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                rvDevplan.setLayoutManager(mLayoutManager);
                rvDevplan.setItemAnimator(new DefaultItemAnimator());
                rvDevplan.setAdapter(developmentPlanAdapterTahunan);
                lnProgress.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<List<UserListDevPlan>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                lnProgress.setVisibility(View.GONE);

            }
        });

//        for(int i=0;i<10;i++){
//            KPIApproveList ka = new KPIApproveList();
//            ka.setDept("");
//            ka.setEmpName("Adiwinata Saputra");
//            ka.setId("190300270");
//            ka.setNIK("190300270");
//            ka.setJobTitle("Salesman");
//            ka.setBranchName("Makassar");
//            ka.setDept("KND");
//            ka.setStar("4");
//            ka.setStatus("Complete");
//            ka.setJobStatus("Tetap");
//            ka.setDateStart("22/12/2018");
//            ka.setDateEnd("22/03/2019");
//            //=== parameter list bawahan berdasarkan nik akun tersebut ===
//            ka.setNIKAtasan1("030300324");
//            ka.setNIKAtasan2("070500332");
//            ka.setNamaAtasan1("Norish Hanoch Wairata");
//            ka.setNamaAtasan2("Rudy Djajasaputra");
//            //==================
//            if(i%2==0){
//                ka.setStatus1("Not Approved");
//            }
//            else {
//                ka.setStatus1("Approved");
//            }
//            kpiApproveListList.add(ka);
//
//        }


    }


    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }
}
