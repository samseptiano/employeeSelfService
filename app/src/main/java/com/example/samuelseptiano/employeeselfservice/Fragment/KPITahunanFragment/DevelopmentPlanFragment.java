package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.DevelopmentPlanAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIApproveListAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.adapter.TabsAdapter;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
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
    List<KPIApproveList> kpiApproveListList = new ArrayList<>();
    DevelopmentPlanAdapterTahunan developmentPlanAdapterTahunan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Development Plan");

        rootView = inflater.inflate(R.layout.fragment_development_plan, container, false);
        rvDevplan = rootView.findViewById(R.id.rv_DevelopmentPlan);

        prepareData();
        return rootView;
    }


    private void prepareData() {
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<KPIApproveList>> call = apiService.getuserList(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveList>>() {
            @Override
            public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
                int statusCode = response.code();
                kpiApproveListList = response.body();
                developmentPlanAdapterTahunan = new DevelopmentPlanAdapterTahunan(kpiApproveListList,getContext(),getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                rvDevplan.setLayoutManager(mLayoutManager);
                rvDevplan.setItemAnimator(new DefaultItemAnimator());
                rvDevplan.setAdapter(developmentPlanAdapterTahunan);
            }
            @Override
            public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
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
