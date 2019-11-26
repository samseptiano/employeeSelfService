package com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIApproveListAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.adapter.TabsAdapter;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.interfaces.UpdateableFragmentListener;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.FilterPAModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerFragment extends Fragment implements UpdateableFragmentListener {

    private ViewPager mViewPager;
    private TabsAdapter mAdapter;
    public String fragTitle;
    public String tahun="";
    TabLayout tabLayout;

    private List<KPIApproveList> kpiApproveLists = new ArrayList<>();

    private List<KPIApproveList> kpiApproveListsApprove = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsNotApprove = new ArrayList<>();


    String titleTotal = "Total";
    String titleApprove = "Approved";
    String titleNotApprove = "Not Approve";


    public ManagerFragment() {
        // Required empty public constructor
    }

    public static ManagerFragment newInstance() {
        ManagerFragment fragment = new ManagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("My team Performance");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("My Team Performance");

        tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        tabLayout = (TabLayout) view.findViewById(R.id.fragment_manager_tab_bar_tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_manager_tab_bar_pager);

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
                for(int i=0;i<kpiApproveLists.size();i++){
                    //sebagai atasan 1
                    if(kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())){
                        if(kpiApproveLists.get(i).getStatus()== null || kpiApproveLists.get(i).getStatus().equals("O") ||kpiApproveLists.get(i).getStatus().equals("S")){
                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                        }
                        else{
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));
                        }
                    }
                    //sebagai atasan 2
                    else if(kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())){
                        if(kpiApproveLists.get(i).getStatus().equals("A")){
                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                        }
                        else{
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));
                        }
                    }
                }
                titleTotal = titleTotal + "("+kpiApproveLists.size()+")";
                titleApprove = titleApprove + "("+kpiApproveListsApprove.size()+")";
                titleNotApprove = titleNotApprove + "("+kpiApproveListsNotApprove.size()+")";

                mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove, kpiApproveLists,kpiApproveListsApprove,kpiApproveListsNotApprove);

                mViewPager.setAdapter(mAdapter);
                tabLayout.setupWithViewPager(mViewPager);
            }
            @Override
            public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });




        return view;
    }

    public void update(FilterPAModel filterPAModel){
        mAdapter.update(filterPAModel);

        tahun = filterPAModel.getTahun();

        titleTotal = "Total";
        titleApprove = "Approved";
        titleNotApprove = "Not Approve";

        kpiApproveLists = new ArrayList<>();
        kpiApproveListsApprove = new ArrayList<>();
        kpiApproveListsNotApprove = new ArrayList<>();

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
                for(int i=0;i<kpiApproveLists.size();i++){
                    //sebagai atasan 1
                    if(kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())){
                        if(kpiApproveLists.get(i).getStatus()== null || kpiApproveLists.get(i).getStatus().equals("O") ||kpiApproveLists.get(i).getStatus().equals("S")){
                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                        }
                        else{
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));
                        }
                    }
                    //sebagai atasan 2
                    else if(kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())){
                        if(kpiApproveLists.get(i).getStatus().equals("A")){
                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                        }
                        else{
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));
                        }
                    }
                }
                titleTotal = titleTotal + "("+kpiApproveLists.size()+")";
                titleApprove = titleApprove + "("+kpiApproveListsApprove.size()+")";
                titleNotApprove = titleNotApprove + "("+kpiApproveListsNotApprove.size()+")";

                mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove, kpiApproveLists,kpiApproveListsApprove,kpiApproveListsNotApprove);

                mViewPager.setAdapter(mAdapter);
                tabLayout.setupWithViewPager(mViewPager);
            }
            @Override
            public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
        //Toast.makeText(getContext(),filterPAModel.getTahun(),Toast.LENGTH_LONG).show();
    }

}
