package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.DevelopmentPlanAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.HomeFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.UserListDevPlan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DevelopmentPlanFragment extends Fragment {

    View rootView;
    RecyclerView rvDevplan;
    List<UserListDevPlan> kpiApproveListList = new ArrayList<>();
    DevelopmentPlanAdapterTahunan developmentPlanAdapterTahunan;
    LinearLayout lnProgress;
    SwipeRefreshLayout pullToRefresh;

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
        actionBar.setTitle("Development Plan");

        rootView = inflater.inflate(R.layout.fragment_development_plan, container, false);
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
                developmentPlanAdapterTahunan = new DevelopmentPlanAdapterTahunan(kpiApproveListList,getContext(),getActivity());
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



}
