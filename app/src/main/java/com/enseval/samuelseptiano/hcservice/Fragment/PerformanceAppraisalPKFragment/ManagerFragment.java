package com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment;

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

import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment.adapter.TabsAdapter;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment.interfaces.UpdateableFragmentListener;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIApproveListPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.UserList;
import com.enseval.samuelseptiano.hcservice.Model.FilterPAModel;
import com.enseval.samuelseptiano.hcservice.Model.FilterPJModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private List<KPIApproveListPJ> kpiApproveLists = new ArrayList<>();


    private List<KPIApproveListPJ> kpiApproveListsApprove = new ArrayList<>();
    private List<KPIApproveListPJ> kpiApproveListsNotApprove = new ArrayList<>();


    String titleTotal = "Total";
    String titleApprove = "Approved";
    String titleNotApprove = "Not Approved";


    Date date = new Date();

    String periodeAwal = Calendar.getInstance().get(Calendar.YEAR)+"-01-01";
    String periodeAkhir=new SimpleDateFormat("yyyy-MM-dd").format(date);
    String empType="";
    //HARDCODE
    //"131102891"
    String empNIK = "131102891";


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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                empType = bundle.getString("empType");
            }
            catch (Exception e){

            }
        }
        actionBar.setTitle(empType+" Team Performance");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_pj, container, false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        actionBar.setTitle(empType+" Team Performance");
        actionBar.setTitle("My Team Performance");

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        empNIK=usr.get(0).getEmpNIK();


        tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        lnProgressbar = view.findViewById(R.id.linlaHeaderProgressTab);

        tabLayout = (TabLayout) view.findViewById(R.id.fragment_manager_tab_bar_tab_layout);

        mViewPager = (ViewPager) view.findViewById(R.id.fragment_manager_tab_bar_pager);


        lnProgressbar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
        Call<List<KPIApproveListPJ>> call = apiService.getuserListPK(empNIK,periodeAwal,periodeAkhir,empType,"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveListPJ>>() {
            @Override
            public void onResponse(Call<List<KPIApproveListPJ>> call, Response<List<KPIApproveListPJ>> response) {
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



                mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove, kpiApproveLists,kpiApproveListsApprove,kpiApproveListsNotApprove,tahun,empType);

                mViewPager.setAdapter(mAdapter);
                tabLayout.setupWithViewPager(mViewPager);
                lnProgressbar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<List<KPIApproveListPJ>> call, Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_SHORT).show();
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


                ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                Call<List<KPIApproveListPJ>> call = apiService.getuserListPK(empNIK,periodeAwal,periodeAkhir,empType,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<List<KPIApproveListPJ>>() {
                    @Override
                    public void onResponse(Call<List<KPIApproveListPJ>> call, Response<List<KPIApproveListPJ>> response) {
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

                        mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove, kpiApproveLists,kpiApproveListsApprove,kpiApproveListsNotApprove,tahun,empType);

                        mViewPager.setAdapter(mAdapter);
                        tabLayout.setupWithViewPager(mViewPager);
                    }
                    @Override
                    public void onFailure(Call<List<KPIApproveListPJ>> call, Throwable t) {
                        Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

                pullToRefresh.setRefreshing(false);
            }
        });






        return view;
    }

    public void update(FilterPJModel filterPJModel){
        mAdapter.update(filterPJModel);

        titleTotal = "Total";
        titleApprove = "Approved";
        titleNotApprove = "Not Approved";

        kpiApproveLists = new ArrayList<>();
        kpiApproveListsApprove = new ArrayList<>();
        kpiApproveListsNotApprove = new ArrayList<>();

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
        Call<List<KPIApproveListPJ>> call = apiService.getuserListPK(empNIK,filterPJModel.getPeriodeAwal(),filterPJModel.getPeriodeAkhir(),empType,"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveListPJ>>() {
            @Override
            public void onResponse(Call<List<KPIApproveListPJ>> call, Response<List<KPIApproveListPJ>> response) {
                int statusCode = response.code();
                kpiApproveLists = response.body();

                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                Date dAwal = new Date();
                Date dAkhir = new Date();
                Date selesaiKontrak = new Date();
                try {
                    dAwal = sdformat.parse(filterPJModel.getPeriodeAwal());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    dAkhir = sdformat.parse(filterPJModel.getPeriodeAkhir());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                List<KPIApproveListPJ> aaa = new ArrayList<>();
                if(!filterPJModel.getDirektorat().equals("") && !filterPJModel.getSite().equals("")) {
                    for (int i = 0; i < kpiApproveLists.size(); i++) {
                        try {
                            selesaiKontrak = sdformat.parse(kpiApproveLists.get(i).getSelesaiKontrak());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                            aaa.add(kpiApproveLists.get(i));
                        }

                        //sebagai atasan 1
                        if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                                //if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                //}
                            } else {
                                if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())&& (dAwal.compareTo(selesaiKontrak)>=0 && dAkhir.compareTo(selesaiKontrak)<=0)) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                        //sebagai atasan 2
                        else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus().equals("A")) {
                                //if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                //}
                            } else {
                                if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName()) || filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())&& (dAwal.compareTo(selesaiKontrak)>=0 && dAkhir.compareTo(selesaiKontrak)<=0)) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                    }
                }
                else if(filterPJModel.getDirektorat().equals("") && !filterPJModel.getSite().equals("")){
                    for (int i = 0; i < kpiApproveLists.size(); i++) {
                        if (filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                            aaa.add(kpiApproveLists.get(i));
                        }

                        //sebagai atasan 1
                        if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                               // if (filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                //}

                            } else {
                                if (filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())&& (dAwal.compareTo(selesaiKontrak)>=0 && dAkhir.compareTo(selesaiKontrak)<=0)&& (dAwal.compareTo(selesaiKontrak)>=0 && dAkhir.compareTo(selesaiKontrak)<=0)) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                        //sebagai atasan 2
                        else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus().equals("A")) {
                               // if (filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                //}
                            } else {
                                if (filterPJModel.getSite().contains(kpiApproveLists.get(i).getLocationName())&& (dAwal.compareTo(selesaiKontrak)>=0 && dAkhir.compareTo(selesaiKontrak)<=0)) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                    }
                }
                else if(!filterPJModel.getDirektorat().equals("") && filterPJModel.getSite().equals("")){
                    for (int i = 0; i < kpiApproveLists.size(); i++) {
                        if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())) {
                            aaa.add(kpiApproveLists.get(i));
                        }

                        //sebagai atasan 1
                        if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                               // if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                                //}

                            } else {
                                if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())&& (dAwal.compareTo(selesaiKontrak)>=0 && dAkhir.compareTo(selesaiKontrak)<=0)) {
                                    kpiApproveListsApprove.add(kpiApproveLists.get(i));
                                }
                            }
                        }
                        //sebagai atasan 2
                        else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())) {
                            if (kpiApproveLists.get(i).getStatus().equals("A")) {
                               // if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())) {
                                    kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                               // }
                            } else {
                                if (filterPJModel.getDirektorat().contains(kpiApproveLists.get(i).getOrgName())&& (dAwal.compareTo(selesaiKontrak)>=0 && dAkhir.compareTo(selesaiKontrak)<=0)) {
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

                mAdapter = new TabsAdapter(getFragmentManager(), getActivity(),titleTotal, titleApprove,titleNotApprove, aaa,kpiApproveListsApprove,kpiApproveListsNotApprove,tahun,empType);

                mViewPager.setAdapter(mAdapter);
                tabLayout.setupWithViewPager(mViewPager);
            }
            @Override
            public void onFailure(Call<List<KPIApproveListPJ>> call, Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(getContext(),filterPAModel.getTahun(),Toast.LENGTH_LONG).show();
    }

}
