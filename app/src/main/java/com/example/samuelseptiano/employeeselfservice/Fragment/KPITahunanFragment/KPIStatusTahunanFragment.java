package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.TotalFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
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

public class KPIStatusTahunanFragment extends Fragment {

    View rootview;
    ImageButton imgBtnOpen, imgBtnProgress, imgBtnCompleted;
    TextView tvopen, tvprogress,tvcomplete;
    LinearLayout lnProgresHeader;
    String tahun = "";

    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
    ArrayList<UserRealmModel> usr;

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    private List<KPIApproveList> kpiApproveLists = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsApprove = new ArrayList<>();
    private List<KPIApproveList> kpiApproveListsNotApprove = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Status PA");
        rootview =  inflater.inflate(R.layout.fragment_kpi_status_tahunan, container, false);

        tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        lnProgresHeader = rootview.findViewById(R.id.linlaHeaderProgressStatus);
        imgBtnOpen = rootview.findViewById(R.id.imgBtnOpen);
        imgBtnCompleted = rootview.findViewById(R.id.imgBtnComplete);
        imgBtnProgress = rootview.findViewById(R.id.imgBtnProgress);
        tvopen = rootview.findViewById(R.id.tvopen);
        tvprogress = rootview.findViewById(R.id.tvprogress);
        tvcomplete = rootview.findViewById(R.id.tvcomplete);
        usr = userRealmHelper.findAllArticle();



        SwipeRefreshLayout pullToRefresh = rootview.findViewById(R.id.swipeContainer);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEmp();
                pullToRefresh.setRefreshing(false);
            }
        });

       getEmp();





        return rootview;
    }

    private void getEmp(){
        lnProgresHeader.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<KPIApproveList>> call = apiService.getuserList(usr.get(0).getEmpNIK(),tahun,"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveList>>() {
            @Override
            public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
                int statusCode = response.code();
                kpiApproveLists = response.body();
                kpiApproveListsApprove = new ArrayList<>();
                kpiApproveListsNotApprove = new ArrayList<>();
                for(int i=0;i<kpiApproveLists.size();i++) {
                    //sebagai atasan 1
                    if (kpiApproveLists.get(i).getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                        if (kpiApproveLists.get(i).getStatus() == null || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                        } else {
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));
                        }
                    }
                    //sebagai atasan 2
                    else if (kpiApproveLists.get(i).getNIKAtasan2().equals(usr.get(0).getEmpNIK())) {
                        if (kpiApproveLists.get(i).getStatus().equals("A") || kpiApproveLists.get(i).getStatus().equals("O") || kpiApproveLists.get(i).getStatus().equals("S")) {
                            kpiApproveListsNotApprove.add(kpiApproveLists.get(i));
                        } else if(kpiApproveLists.get(i).getStatus().equals("C")) {
                            kpiApproveListsApprove.add(kpiApproveLists.get(i));
                        }
                    }
                }

                tvopen.setText("("+Integer.toString(kpiApproveListsNotApprove.size())+")");
                tvcomplete.setText("("+Integer.toString(kpiApproveListsApprove.size())+")");
                tvprogress.setText("("+Integer.toString(kpiApproveListsNotApprove.size())+")");

                imgBtnOpen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fr = new TotalFragment("",kpiApproveListsNotApprove,getContext().getResources().getDrawable(R.drawable.approved));
                        Bundle bundle3 = new Bundle();
                        bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                        bundle3.putString("types", "STATUS");
                        bundle3.putString("tahun", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                        bundle3.putString("jenis", "OPEN");
                        fr.setArguments(bundle3);
                        fr.setArguments(bundle3);
                        fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_frame, fr);
                        ft.addToBackStack("KPIApproveListTahunanFragment");
                        ft.commit();
                        Toast.makeText(getContext(), "Approvee KPI Area", Toast.LENGTH_SHORT).show();
                    }
                });

                imgBtnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fr = new TotalFragment("",kpiApproveListsApprove,getContext().getResources().getDrawable(R.drawable.approved));
                        Bundle bundle3 = new Bundle();
                        bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                        bundle3.putString("types", "STATUS");
                        bundle3.putString("tahun", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                        bundle3.putString("jenis", "COMPLETE");
                        fr.setArguments(bundle3);
                        fr.setArguments(bundle3);
                        fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_frame, fr);
                        ft.addToBackStack("KPIApproveListTahunanFragment");
                        ft.commit();
                        Toast.makeText(getContext(), "Approvee KPI Area", Toast.LENGTH_SHORT).show();
                    }
                });

                imgBtnProgress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fr = new TotalFragment("",kpiApproveListsNotApprove,getContext().getResources().getDrawable(R.drawable.complete));
                        Bundle bundle3 = new Bundle();
                        bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                        bundle3.putString("types", "STATUS");
                        bundle3.putString("tahun", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                        bundle3.putString("jenis", "PROGRESS");
                        fr.setArguments(bundle3);
                        fr.setArguments(bundle3);
                        fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_frame, fr);
                        ft.addToBackStack("KPIApproveListTahunanFragment");
                        ft.commit();
//                Toast.makeText(getContext(), "Approvee KPI Area", Toast.LENGTH_SHORT).show();
                    }
                });
                lnProgresHeader.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                Toast.makeText(getContext(),"No connection to internet",Toast.LENGTH_LONG).show();
                lnProgresHeader.setVisibility(View.GONE);
            }
        });
    }

}
