package com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIApproveListAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.ViewPagerAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile.ProfileDataFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile.QRFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIApproveListPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIDashboard;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KPIApproveListFragment extends Fragment {

    View rootView;
    RecyclerView recyclerViewKPIHint;
    KPIApproveListAdapter kpiAdapter;
    List<KPIApproveListPJ> KPIList;
    Button btnFillKpi;
    Fragment fr;
    LinearLayout lnMyTeam,lnMyTeamArea,lnDasboard,lnDashboardArea, lnProgessBar;
    ImageView imgExpandTeam, imgExpandDashboard;
    PieChartView pieChartView,pieChartView2;
    FloatingActionButton mFloatingActionButton;
    TextView tvChart1,tvChart2,tvChartDesc2,tvChartDesc1,tvTotal,tvComplete,tvIncomplete;

    List<SliceValue> pieData = new ArrayList<>();
    List< SliceValue> pieData2 = new ArrayList<>();

    ViewPager viewPager;
    TabLayout tabLayout;

    boolean isTeamExpand=true;
    boolean isDashboardExpand=true;

    public Boolean getConnState() {
        return connState;
    }

    public void setConnState(Boolean connState) {
        this.connState = connState;
    }

    Boolean connState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kpi_approve_list, container, false);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floating_action_button);
        lnDasboard = rootView.findViewById(R.id.lnMyDashboard);
        lnDashboardArea = rootView.findViewById(R.id.lnMyDashboardArea);
        lnMyTeam = rootView.findViewById(R.id.lnMyTeam);
        lnMyTeamArea = rootView.findViewById(R.id.lnMyTeamArea);
        imgExpandTeam = rootView.findViewById(R.id.imgExpandTeam);
        imgExpandDashboard = rootView.findViewById(R.id.imgExpandDashboard);
        tvChart1 = rootView.findViewById(R.id.tvChart1);
        tvChart2 = rootView.findViewById(R.id.tvChart2);
        tvChartDesc1 = rootView.findViewById(R.id.tvChartDesc1);
        tvChartDesc2 = rootView.findViewById(R.id.tvChartDesc2);
        lnProgessBar = rootView.findViewById(R.id.linlaQuestions);
        tvTotal = rootView.findViewById(R.id.tv_total);
                tvComplete = rootView.findViewById(R.id.tv_complete);
        tvIncomplete = rootView.findViewById(R.id.tv_incomplete);

        pieChartView = rootView.findViewById(R.id.chart);
        pieChartView2 = rootView.findViewById(R.id.chart2);





        //====================== CUSTOM APPBAR LAYOUT =============================================
//       // ((AppCompatActivity)getA=ctivity()).getSupportActionBar().hide();
//        ((AppCompatActivity)getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        LayoutInflater mInflater = LayoutInflater.from(getContext());
//        View mCustomView = mInflater.inflate(R.layout.custom_appbar, null);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(mCustomView);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("KPI Approve List");
        //====================== CUSTOM APPBAR LAYOUT ====================================================

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();
        String posId = usr.get(0).getPositionId();
        String nik = usr.get(0).getEmpNIK();

        //Chart1
        //======================================
        tvChartDesc1.setText("999 dari 999");
        tvChart1.setText("Diagram 1");
        if(pieData.size()<1) {

            pieData.add(new SliceValue(35, Color.BLUE).setLabel("completed"));
            pieData.add(new SliceValue(65, Color.GREEN).setLabel("not completed"));
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartView.setPieChartData(pieChartData);

        //======================================

        //Chart2
        //======================================
        tvChartDesc2.setText("999 dari 999");
        tvChart2.setText("Diagram 2");
        if(pieData2.size()<1) {

            pieData2.add(new SliceValue(75, Color.CYAN).setLabel("bawahan langsung"));
            pieData2.add(new SliceValue(25, Color.MAGENTA).setLabel("bawahan taklangsung"));
        }
        PieChartData pieChartData2 = new PieChartData(pieData2);
        pieChartData2.setHasLabels(true);
        pieChartView2.setPieChartData(pieChartData2);

        //======================================


        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        lnProgessBar.setVisibility(View.VISIBLE);
        //"9859"


        apiService.getuserListPJ(usr.get(0).getEmpNIK(),"Bearer "+usr.get(0).getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<List<UserList>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<UserList> userLists) {
                        //Toast.makeText(getContext(),"next",Toast.LENGTH_SHORT).show();
                        KPIList = new ArrayList<KPIApproveListPJ>();
                        for(int i=0;i<userLists.size();i++){
                            KPIApproveListPJ ka = new KPIApproveListPJ();
                            ka.setPositionName(userLists.get(i).getPositionName());
                            ka.setDept(userLists.get(i).getOrgName());
                            ka.setEmpName(userLists.get(i).getEmpName());
                            ka.setTanggalMasaKontrakAwal(userLists.get(i).getDateStart().split(" ")[0]);
                            try {

                                ka.setTanggalMasaKontrakAkhir(userLists.get(i).getDateEnd().split(" ")[0]);
                            }
                            catch (Exception e){
                                ka.setTanggalMasaKontrakAkhir("-");
                            }
                            ka.setId(userLists.get(i).getEmpId());
                            ka.setNIK(userLists.get(i).getEmpNiK());
                            ka.setJobTitle(userLists.get(i).getJobTitleName());
                            //=== parameter list bawahan berdasarkan nik akun tersebut ===
                            ka.setNIKAtasan(userLists.get(i).getNIKAtasanLangsung());
                            ka.setNIKAtasan2(userLists.get(i).getNIKAtasanTakLangsung());


                            //===============================================
//                    Toast.makeText(getContext(),userLists.get(i).getNIKAtasanLangsung(),Toast.LENGTH_LONG).show();
//                    Toast.makeText(getContext(),userLists.get(i).getNIKAtasanTakLangsung(),Toast.LENGTH_LONG).show();
                            //===============================================

                            ka.setNamaAtasan(userLists.get(i).getNamaAtasanLangsung());
                            ka.setNamaAtasan2(userLists.get(i).getNamaAtasanTakLangsung());
                            ka.setFotoAtasan(userLists.get(i).getFotoAtasanLangsung());
                            ka.setFotoAtasan2(userLists.get(i).getFotoAtasanTakLangsung());
                            ka.setEmpPhoto(userLists.get(i).getEmpPhoto());
                            ka.setStatus(userLists.get(i).getStatus());

                            if(userLists.get(i).getStatus().equals("1")){
                                ka.setStatus1("Not Approved");
                                ka.setStatus2("Not Approved");
                            }
                            else if(userLists.get(i).getStatus().equals("2")){
                                ka.setStatus1("Approved");
                                ka.setStatus2("Not Approved");
                            }
                            else if(userLists.get(i).getStatus().equals("3")){
                                ka.setStatus1("Approved");
                                ka.setStatus2("Approved");
                            }

                            //==================
                            if(i%2==0){
                                ka.setStatus1("Not Approved");
                            }
                            else {
                                ka.setStatus1("Approved");
                            }
                            KPIList.add(ka);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        lnProgessBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        recyclerViewKPIHint = (RecyclerView) rootView.findViewById(R.id.recyclerViewKPIApproveList);
                        recyclerViewKPIHint.setLayoutManager(new LinearLayoutManager(getContext()));
                        kpiAdapter = new KPIApproveListAdapter(KPIList, getContext(), getActivity());
                        recyclerViewKPIHint.setAdapter(kpiAdapter);
                        lnProgessBar.setVisibility(View.GONE);
                    }
                });

        ApiInterface apiServices = ApiClient.getClient(getContext()).create(ApiInterface.class);
        //retrofit post
        Call<KPIDashboard> calls = apiServices.getKPIDashboardPJ(usr.get(0).getEmpNIK(),"Bearer "+usr.get(0).getToken());
        calls.enqueue(new Callback<KPIDashboard>() {
            @Override
            public void onResponse(Call<KPIDashboard> calls, Response<KPIDashboard> response) {
                int statusCode = response.code();
                tvTotal.setText(response.body().getTotal());
                tvComplete.setText(response.body().getTotalStatusApproved());
                tvIncomplete.setText(response.body().getTotalStatusNotApproved());
                //Toast.makeText(getContext(),"Sukses ",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(Call<KPIDashboard> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });





//        lnMyTeamArea.setVisibility(View.GONE);
        imgExpandDashboard.setImageResource(R.drawable.expand_up);
        imgExpandTeam.setImageResource(R.drawable.expand_up);

        lnMyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTeamExpand){
                    lnMyTeamArea.setVisibility(View.GONE);
                    imgExpandTeam.setImageResource(R.drawable.expand_down);
                    isTeamExpand=false;
                }
                else {
                    imgExpandTeam.setImageResource(R.drawable.expand_up);
                    lnMyTeamArea.setVisibility(View.VISIBLE);
                    isTeamExpand=true;
                }
//                lnDashboardArea.setVisibility(View.GONE);
                //imgExpandDashboard.setImageResource(R.drawable.expand_down);
            }
        });

        lnDasboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDashboardExpand){
                    lnDashboardArea.setVisibility(View.GONE);
                    imgExpandDashboard.setImageResource(R.drawable.expand_down);
                    isDashboardExpand=false;
                }
                else {
                    imgExpandDashboard.setImageResource(R.drawable.expand_up);
                    lnDashboardArea.setVisibility(View.VISIBLE);
                    isDashboardExpand=true;
                }
//                lnDashboardArea.setVisibility(View.VISIBLE);
                //imgExpandDashboard.setImageResource(R.drawable.expand_up);
            }
        });


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()) {

                }else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFloatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(),"Pengisian KPI Mandiri", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("PK Approvee List");
        setHasOptionsMenu(true);






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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s.toLowerCase(Locale.getDefault());
                try {
                    kpiAdapter.filter(text);


                } catch (Exception e) {

                }


                return true;

            }
        });
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment fr;

        fr = new ProfileDataFragment();
        adapter.addFragment(fr, "Profil Data");
        fr = new QRFragment();
        adapter.addFragment(fr, "QR Saya");
        viewPager.setAdapter(adapter);
    }

}
