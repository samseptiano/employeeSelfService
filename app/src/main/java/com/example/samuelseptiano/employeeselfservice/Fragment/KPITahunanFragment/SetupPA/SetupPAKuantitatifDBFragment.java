package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.SetupPA;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.EmployeeSetupPAAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.PositionSetupPAAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpJobTtlModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpOrgModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PASettingHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PaImportModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupPositionModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetupPAKuantitatifDBFragment extends Fragment implements PositionSetupPAAdapter.EventListener {

    View rootView;
    RecyclerView rvSetupEmployee;
    RecyclerView rvSetupPosition;


    LinearLayout lnSetupPosition;
    MaterialSpinner spinnerSetup;
    List<SetupEmployeeModel> setupEmployeeModelList = new ArrayList<>();
    List<SetupPositionModel> setupPositionModelList = new ArrayList<>();

    List<EmpOrgModel> empOrgModelList = new ArrayList<>();
    List<EmpJobTtlModel> empJobTtlModelList = new ArrayList<>();


    EmployeeSetupPAAdapter employeeSetupPAAdapter;
    PositionSetupPAAdapter positionSetupPAAdapter;
    Button btnAdd, btnImport, btnPublish,btnDelete;

    List<PASettingHeader> paSettingHeaderList = new ArrayList<>();
    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
    ArrayList<UserRealmModel> usr;
    SwipeRefreshLayout pullToRefresh;
    String greeting="POSITION";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greeting = (savedInstanceState != null) ? savedInstanceState.getString("greeting") : "POSITION";
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Setup PA Kuantitatif");
        rootView =  inflater.inflate(R.layout.fragment_setup_pakuantitatif_db, container, false);
        rvSetupEmployee = rootView.findViewById(R.id.rv_setup_employee);
        rvSetupPosition = rootView.findViewById(R.id.rv_setup_position);
        pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        lnSetupPosition= rootView.findViewById(R.id.lnSetupPosition);

        btnAdd= rootView.findViewById(R.id.btnAdd);
        btnImport= rootView.findViewById(R.id.btnImport);
        btnPublish= rootView.findViewById(R.id.btnPublish);

        btnDelete= rootView.findViewById(R.id.btnDelete);

        usr = userRealmHelper.findAllArticle();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPositionDialog();
            }
        });


        try {
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    greeting="POSITION";
                    refreshData();

                    pullToRefresh.setRefreshing(false);
                }
            });
            refreshData();
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
//                        hhAdapter.filter(text);
                    //hAdapter.filter(text);
                    if(greeting.equals("EMPLOYEE")){
                    employeeSetupPAAdapter.filter(ss);}
                    else{
                        positionSetupPAAdapter.filter(ss);
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return true;

            }
        });
    }

    private void refreshData(){
        spinnerSetup = rootView.findViewById(R.id.spinnerSetup);
        spinnerSetup.setBackgroundResource(R.drawable.shapedropdown);
        spinnerSetup.setPadding(25, 10, 25, 10);
        List<String> setup = new ArrayList<String>();
        setup.add("EMPLOYEE");
        setup.add("POSITION");
        spinnerSetup.setItems(setup);
        spinnerSetup.setSelectedIndex(1);
        spinnerSetup.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(spinnerSetup.getText().toString().equals("EMPLOYEE")){
                    rvSetupEmployee.setVisibility(View.VISIBLE);
                    setupEmployeeModelList.clear();
                    prepareDataEmployee();
                    lnSetupPosition.setVisibility(View.GONE);
                    greeting="EMPLOYEE";
                    btnPublish.setVisibility(View.GONE);
                }
                else{
                    greeting="POSITION";
                    setupPositionModelList.clear();
                    prepareDataPosition();
                    btnPublish.setVisibility(View.VISIBLE);

                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<PASettingHeader> aaa = new ArrayList<>();

                            for(int i=0;i<paSettingHeaderList.size();i++){
                                if(paSettingHeaderList.get(i).isChecked()){
                                    aaa.add(paSettingHeaderList.get(i));
                                }
                            }



                            ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                            Call<Void> call = apiService.deletePAKPISettingHeader(aaa,"Bearer "+usr.get(0).getToken());
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    int statusCode = response.code();
                                    Toast.makeText(getContext(),"Sukses",Toast.LENGTH_LONG).show();
                                    for(int i=0;i<setupPositionModelList.size();i++){
                                        if(setupPositionModelList.get(i).isChecked()){
                                            setupPositionModelList.remove(i);
                                        }
                                    }
                                    prepareDataPosition();

                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                                }
                            });

                            positionSetupPAAdapter.notifyDataSetChanged();
                        }
                    });

                    rvSetupEmployee.setVisibility(View.GONE);
                    lnSetupPosition.setVisibility(View.VISIBLE);

                }
            }
        });

        if(greeting.equals("EMPLOYEE")){
            rvSetupEmployee.setVisibility(View.VISIBLE);
            setupEmployeeModelList.clear();
            prepareDataEmployee();
            btnPublish.setVisibility(View.GONE);
            lnSetupPosition.setVisibility(View.GONE);
        }
        else if(greeting.equals("POSITION")){
            setupPositionModelList.clear();
            prepareDataPosition();
            btnPublish.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<PASettingHeader> aaa = new ArrayList<>();

                    for(int i=0;i<paSettingHeaderList.size();i++){
                        if(paSettingHeaderList.get(i).isChecked()){
                            aaa.add(paSettingHeaderList.get(i));
                        }
                    }



                    ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                    Call<Void> call = apiService.deletePAKPISettingHeader(aaa,"Bearer "+usr.get(0).getToken());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int statusCode = response.code();
                            Toast.makeText(getContext(),"Sukses",Toast.LENGTH_LONG).show();
                            for(int i=0;i<setupPositionModelList.size();i++){
                                if(setupPositionModelList.get(i).isChecked()){
                                    setupPositionModelList.remove(i);
                                }
                            }
                            prepareDataPosition();

                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                        }
                    });

                    positionSetupPAAdapter.notifyDataSetChanged();
                }
            });

            btnPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<PaImportModel> paImportModels = new ArrayList<>();
                    int ctr = 0;
                    for (int i = 0; i < paSettingHeaderList.size(); i++) {
                        PaImportModel paImportModel = new PaImportModel();
                        if (paSettingHeaderList.get(i).isChecked() && paSettingHeaderList.get(i).getBobot().equals("100")) {
                            paImportModel.setTempCompID(paSettingHeaderList.get(i).getTempKPIID());
                            paImportModel.setPeriode(paSettingHeaderList.get(i).getYear());
                            paImportModel.setKpiType("KUANTITATIF");
                            paImportModels.add(paImportModel);
                        } else if (paSettingHeaderList.get(i).isChecked() &&!paSettingHeaderList.get(i).getBobot().equals("100")) {
                            ctr++;
                        }
                    }
                    if(ctr==0){
                    ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                    Call<Void> call = apiService.importTemplate(paImportModels, "Bearer " + usr.get(0).getToken());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int statusCode = response.code();
                            Toast.makeText(getContext(), "Publish Faktor Kuantitatif berhasil ", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

                        }

                    });
                }
                    else{
                        Toast.makeText(getContext(), "Jumlah bobot tidak sama dengan 100%, silahkan diupdate kembali", Toast.LENGTH_LONG).show();
                    }
                }
            });

            rvSetupEmployee.setVisibility(View.GONE);
            lnSetupPosition.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("load", greeting);
    }

    private void prepareDataEmployee() {
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Load Data...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<KPIApproveList>> call = apiService.getuserListWithSelf(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveList>>() {
            @Override
            public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
                int statusCode = response.code();
                for(int i=0;i<response.body().size();i++){
                    SetupEmployeeModel ka = new SetupEmployeeModel();
                    ka.setEmpName(response.body().get(i).getEmpName());
                    ka.setId(response.body().get(i).getNIK());
                    ka.setNIK(response.body().get(i).getNIK());
                    ka.setJobTitle(response.body().get(i).getJobTitle());
                    ka.setBranchName(response.body().get(i).getBranchName());
                    ka.setDept(response.body().get(i).getDept());
                    ka.setStar("");
                    ka.setPaid(response.body().get(i).getPaid());
                    ka.setStatus("");
                    ka.setBobotTotal(response.body().get(i).getBobotTotal());
                    ka.setJobStatus("");
                    ka.setDateStart("");
                    ka.setDateEnd("");
                    //=== parameter list bawahan berdasarkan nik akun tersebut ===
                    ka.setNIKAtasan1(response.body().get(i).getNIKAtasan1());
                    ka.setNIKAtasan2(response.body().get(i).getNIKAtasan2());
                    ka.setNamaAtasan1(response.body().get(i).getNamaAtasan1());
                    ka.setNamaAtasan2(response.body().get(i).getNamaAtasan2());
                    ka.setStatus1(response.body().get(i).getStatus1());
                    ka.setStatus2(response.body().get(i).getStatus2());
                    ka.setStatus(response.body().get(i).getStatus());
                    ka.setEmpPhoto(response.body().get(i).getEmpPhoto());

                    setupEmployeeModelList.add(ka);

                    employeeSetupPAAdapter = new EmployeeSetupPAAdapter(setupEmployeeModelList,getContext(),getActivity(),"KUANTITATIF");
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    rvSetupEmployee.setLayoutManager(mLayoutManager);
                    rvSetupEmployee.setItemAnimator(new DefaultItemAnimator());
                    rvSetupEmployee.setAdapter(employeeSetupPAAdapter);
                    pd.dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }

    private void prepareDataPosition() {
        spinnerSetup.setSelectedIndex(1);
            UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
            ArrayList<UserRealmModel> usr;
            usr = userRealmHelper.findAllArticle();

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Load Data...");
        pd.setCancelable(false);
        pd.show();

            ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
            Call<List<PASettingHeader>> call = apiService.getPAKPISettingHeader(usr.get(0).getEmpNIK(),"Bearer "+usr.get(0).getToken());
            call.enqueue(new Callback<List<PASettingHeader>>() {
                @Override
                public void onResponse(Call<List<PASettingHeader>> call, Response<List<PASettingHeader>> response) {
                    int statusCode = response.code();
                    paSettingHeaderList = response.body();

                    ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                    Call<List<EmpOrgModel>> call2 = apiService.getEmpOrg(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
                    call2.enqueue(new Callback<List<EmpOrgModel>>() {
                        @Override
                        public void onResponse(Call<List<EmpOrgModel>> call, Response<List<EmpOrgModel>> response) {
                            int statusCode = response.code();

                            empOrgModelList = response.body();
                            ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                            Call<List<EmpJobTtlModel>> call3 = apiService.getEmpJobTtl(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
                            call3.enqueue(new Callback<List<EmpJobTtlModel>>() {
                                @Override
                                public void onResponse(Call<List<EmpJobTtlModel>> call, Response<List<EmpJobTtlModel>> response) {
                                    int statusCode = response.code();
                                    empJobTtlModelList = response.body();
                                    positionSetupPAAdapter = new PositionSetupPAAdapter(paSettingHeaderList,getContext(),getActivity(),SetupPAKuantitatifDBFragment.this,empJobTtlModelList,empOrgModelList);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                    rvSetupPosition.setLayoutManager(mLayoutManager);
                                    rvSetupPosition.setItemAnimator(new DefaultItemAnimator());
                                    rvSetupPosition.setAdapter(positionSetupPAAdapter);
                                    pd.dismiss();
                                }
                                @Override
                                public void onFailure(Call<List<EmpJobTtlModel>> call, Throwable t) {
                                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                }
                            });

                        }
                        @Override
                        public void onFailure(Call<List<EmpOrgModel>> call, Throwable t) {
                            Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    });



                }
                @Override
                public void onFailure(Call<List<PASettingHeader>> call, Throwable t) {
                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            });

    }

    private void showAddPositionDialog(){
        Dialog dialogs = new Dialog(getContext());

        final String[] semester = {"1"};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.add_setup_position_dialog);
        dialogs.setTitle("Title...");

        Button btnAddTemplate;
        EditText edtTemplateName;

        btnAddTemplate = dialogs.findViewById(R.id.btnAddTemplate);
        edtTemplateName = dialogs.findViewById(R.id.edtTemplateName);

        ImageButton btnClose;
        btnClose = dialogs.findViewById(R.id.ib_close);
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        btnAddTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupPositionModel setupPositionModel1 = new SetupPositionModel();
                setupPositionModel1.setYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                setupPositionModel1.setPositionName(edtTemplateName.getText().toString());
                setupPositionModel1.setId("");
                setupPositionModel1.setJabatan(null);
                setupPositionModel1.setOrganisasi(null);
                setupPositionModelList.add(setupPositionModel1);
                positionSetupPAAdapter.notifyDataSetChanged();



                List<PASettingHeader>paSettingHeaders = new ArrayList<>();
                PASettingHeader paSettingHeader = new PASettingHeader();
                paSettingHeader.setYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR))); //HARDCODE
                paSettingHeader.setTempKPIName(edtTemplateName.getText().toString());
                paSettingHeader.setTempKPIID("");
                paSettingHeader.setChecked(false);
                paSettingHeader.setStatusDeployYN("Y");
                paSettingHeaders.add(paSettingHeader);

                UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                Call<Void> call = apiService.postPAKPISettingHeader(paSettingHeaders,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int statusCode = response.code();
                        Toast.makeText(getContext(),"Sukses",Toast.LENGTH_LONG).show();
                        prepareDataPosition();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                    }
                });

                dialogs.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });

        dialogs.show();
    }

    @Override
    public List<SetupPositionModel> getSetupPositionModels() {
        return setupPositionModelList;
    }

    @Override
    public void setSetupPositionModels(List<SetupPositionModel> setupPositionModelList) {
        this.setupPositionModelList = setupPositionModelList;

    }
}
