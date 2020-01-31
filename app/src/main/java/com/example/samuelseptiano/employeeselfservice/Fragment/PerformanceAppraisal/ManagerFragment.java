package com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

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
    LinearLayout lnProgressbar;
    private List<KPIApproveList> kpiApproveLists = new ArrayList<>();

    private List<KPIApproveList> kpiApproveListsApprove = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsNotApprove = new ArrayList<>();


    String titleTotal = "Total";
    String titleApprove = "Approved";
    String titleNotApprove = "Not Approved";


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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        lnProgressbar = view.findViewById(R.id.linlaHeaderProgressTab);

        tabLayout = (TabLayout) view.findViewById(R.id.fragment_manager_tab_bar_tab_layout);

        mViewPager = (ViewPager) view.findViewById(R.id.fragment_manager_tab_bar_pager);

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        lnProgressbar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<KPIApproveList>> call = apiService.getuserList(usr.get(0).getEmpNIK(),tahun,"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveList>>() {
            @Override
            public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
                int statusCode = response.code();
                kpiApproveLists = response.body();
                for (int i = 0; i < kpiApproveLists.size(); i++) {

                    //sebagai atasan 1
                    if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                        if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));

                        } else {
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));

                        }
                    }
                    //sebagai atasan 2
                    else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK()) && kpiApproveLists.get(i).getStatus2().equals("") && kpiApproveLists.get(i).getStatus1().equals("Approved")) {
                        if (kpiApproveLists.get(i).getStatus().equals("A")) {
                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));

                        } else {
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));
                        }
                    }
                    else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK()) && kpiApproveLists.get(i).getStatus2().equals("Approved") && kpiApproveLists.get(i).getStatus1().equals("Approved")) {
//                        if (kpiApproveLists.get(i).getStatus().equals("A")) {
//                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
//
//                        } else {
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));
//                        }
                    }
                }
                titleTotal = titleTotal + "("+kpiApproveLists.size()+")";
                titleApprove = titleApprove + "("+kpiApproveListsApprove.size()+")";
                titleNotApprove = titleNotApprove + "("+kpiApproveListsNotApprove.size()+")";



                mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove, kpiApproveLists,kpiApproveListsApprove,kpiApproveListsNotApprove,tahun);

                mViewPager.setAdapter(mAdapter);
                tabLayout.setupWithViewPager(mViewPager);
                lnProgressbar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                lnProgressbar.setVisibility(View.GONE);
            }
        });

        SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swipeContainer);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                kpiApproveLists = new ArrayList<>();
                kpiApproveListsApprove = new ArrayList<>();
                kpiApproveListsNotApprove = new ArrayList<>();


                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                Call<List<KPIApproveList>> call = apiService.getuserList(usr.get(0).getEmpNIK(),tahun,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<List<KPIApproveList>>() {
                    @Override
                    public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
                        int statusCode = response.code();
                        kpiApproveLists = response.body();

                        for (int i = 0; i < kpiApproveLists.size(); i++) {

                            //sebagai atasan 1
                            if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                                if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));

                                } else {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));

                                }
                            }
                            //sebagai atasan 2
                            else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK()) && kpiApproveLists.get(i).getStatus2().equals("") && kpiApproveLists.get(i).getStatus1().equals("Approved")) {
                                if (kpiApproveLists.get(i).getStatus().equals("A")) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));

                                } else {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                            else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK()) && kpiApproveLists.get(i).getStatus2().equals("Approved") && kpiApproveLists.get(i).getStatus1().equals("Approved")) {
//                        if (kpiApproveLists.get(i).getStatus().equals("A")) {
//                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
//
//                        } else {
                                kpiApproveListsApprove.add(kpiApproveLists.get(i));
//                        }
                            }
                        }

                        titleTotal = "Total" + "("+kpiApproveLists.size()+")";
                        titleApprove = "Approved" + "("+kpiApproveListsApprove.size()+")";
                        titleNotApprove = "Not Approved" + "("+kpiApproveListsNotApprove.size()+")";

                        mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove, kpiApproveLists,kpiApproveListsApprove,kpiApproveListsNotApprove,tahun);

                        mViewPager.setAdapter(mAdapter);
                        tabLayout.setupWithViewPager(mViewPager);
                    }
                    @Override
                    public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });

                pullToRefresh.setRefreshing(false);
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

        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<KPIApproveList>> call = apiService.getuserList(usr.get(0).getEmpNIK(),tahun,"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveList>>() {
            @Override
            public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
                int statusCode = response.code();
                kpiApproveLists = response.body();
                List<KPIApproveList> aaa = new ArrayList<>();
                if(!filterPAModel.getDirektorat().equals("") && !filterPAModel.getSite().equals("")) {
                    for (int i = 0; i < kpiApproveLists.size(); i++) {
                        if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                            aaa.add(kpiApproveLists.get(i));
                        }

                        //sebagai atasan 1
                        if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                                if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                }

                            } else {
                                if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                        //sebagai atasan 2
                        else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus().equals("A")) {
                                if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                }
                            } else {
                                if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                    }
                }
                else if(filterPAModel.getDirektorat().equals("") && !filterPAModel.getSite().equals("")){
                    for (int i = 0; i < kpiApproveLists.size(); i++) {
                        if (filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                            aaa.add(kpiApproveLists.get(i));
                        }

                        //sebagai atasan 1
                        if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                                if (filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                }

                            } else {
                                if (filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                        //sebagai atasan 2
                        else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus().equals("A")) {
                                if (filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                }
                            } else {
                                if (filterPAModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                    }
                }
                else if(!filterPAModel.getDirektorat().equals("") && filterPAModel.getSite().equals("")){
                    for (int i = 0; i < kpiApproveLists.size(); i++) {
                        if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())) {
                            aaa.add(kpiApproveLists.get(i));
                        }

                        //sebagai atasan 1
                        if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                                if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                }

                            } else {
                                if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                        //sebagai atasan 2
                        else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus().equals("A")) {
                                if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                }
                            } else {
                                if (filterPAModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                    }
                }
                else{
                    for (int i = 0; i < kpiApproveLists.size(); i++) {
                            aaa.add(kpiApproveLists.get(i));


                        //sebagai atasan 1
                        if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK()) && kpiApproveLists.get(i).getStatus1().equals("")) {
                            if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));

                            } else {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));

                            }
                        }
                        //sebagai atasan 2
                        else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK()) && kpiApproveLists.get(i).getStatus2().equals("") && kpiApproveLists.get(i).getStatus1().equals("Approved")) {
                            if (kpiApproveLists.get(i).getStatus().equals("A")) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));

                            } else {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                            }
                        }
                    }
                }
                titleTotal = titleTotal + "("+aaa.size()+")";
                titleApprove = titleApprove + "("+kpiApproveListsApprove.size()+")";
                titleNotApprove = titleNotApprove + "("+kpiApproveListsNotApprove.size()+")";

                mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove, aaa,kpiApproveListsApprove,kpiApproveListsNotApprove,tahun);

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
