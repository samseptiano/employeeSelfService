package com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.EmployeeSetupPAAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.PositionSetupPAAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPKAdapter.EmployeeSetupPKAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPKAdapter.PositionSetupPKAdapter;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.EmpJobTitleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EmpOrgRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobTtlModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpOrgModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIApproveListPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingSetting;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PaImportModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.SetupPositionModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingSetting;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PkImportModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.SetupEmployeePKModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.SetupPositionPKModel;
import com.enseval.samuelseptiano.hcservice.Model.FilterPAModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.EmpJobTtlRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.EmpOrgRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetupPKKuantitatifDBFragment extends Fragment implements PositionSetupPKAdapter.EventListener {

    View rootView;
    RecyclerView rvSetupEmployee;
    RecyclerView rvSetupPosition;


    LinearLayout lnSetupPosition;
    MaterialSpinner spinnerSetup;
    List<SetupEmployeePKModel> setupEmployeeModelList = new ArrayList<>();
    List<SetupPositionPKModel> setupPositionModelList = new ArrayList<>();

    List<EmpOrgModel> empOrgModelList = new ArrayList<>();
    List<EmpJobTtlModel> empJobTtlModelList = new ArrayList<>();


    EmployeeSetupPKAdapter employeeSetupPAAdapter;
    PositionSetupPKAdapter positionSetupPAAdapter;
    Button btnAdd, btnImport, btnPublish,btnDelete;

    String empType="";

    List<PKSettingHeader> paSettingHeaderList = new ArrayList<>();
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
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            empType = bundle.getString("empType");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        actionBar.setTitle("Setup "+empType+" Kuantitatif");
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
                            List<PKSettingHeader> aaa = new ArrayList<>();

                            for(int i=0;i<paSettingHeaderList.size();i++){
                                if(paSettingHeaderList.get(i).isChecked()){
                                    aaa.add(paSettingHeaderList.get(i));
                                }
                            }



                            ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                            Call<Void> call = apiService.deletePAKPISettingHeaderPK(aaa,"Bearer "+usr.get(0).getToken());
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
                    List<PKSettingHeader> aaa = new ArrayList<>();

                    for(int i=0;i<paSettingHeaderList.size();i++){
                        if(paSettingHeaderList.get(i).isChecked()){
                            aaa.add(paSettingHeaderList.get(i));
                        }
                    }

                    if(aaa.size()>0){
                        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                .setMessage("Apakah anda ingin menghapus template?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {


                                        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                                        Call<Void> call = apiService.deletePAKPISettingHeaderPK(aaa,"Bearer "+usr.get(0).getToken());
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
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();
                                    }
                                }).setCancelable(false)
                                .show();
                    }


                }
            });

            btnPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog alertbox = new AlertDialog.Builder(getContext())
                        .setMessage("Apakah anda ingin mempublish template ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                List<PkImportModel> paImportModels = new ArrayList<>();
                                int ctr = 0;
                                for (int i = 0; i < paSettingHeaderList.size(); i++) {
                                    float total=0;
                                    for(int j=0;j<paSettingHeaderList.get(i).getPaSettingDetails().size();j++){
                                        total+=Float.parseFloat(paSettingHeaderList.get(i).getPaSettingDetails().get(j).getBobot().replace(",","."));
                                    }

                                    PkImportModel paImportModel = new PkImportModel();
                                    if (paSettingHeaderList.get(i).isChecked() && total == 100) {
                                        paImportModel.setTempCompID(paSettingHeaderList.get(i).getTempKPIID());
                                        paImportModel.setPeriode(paSettingHeaderList.get(i).getYear());
                                        paImportModel.setKpiType("KUANTITATIF");
                                        paImportModel.setEmpnik(usr.get(0).getEmpNIK());
                                        paImportModels.add(paImportModel);
                                    } else if (paSettingHeaderList.get(i).isChecked() && total != 100) {
                                        ctr++;
                                    }
                                }
                                if(ctr==0){
                                    ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                                    Call<Void> call = apiService.importTemplatePK(paImportModels, "Bearer " + usr.get(0).getToken());
                                    call.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            int statusCode = response.code();
                                            Toast.makeText(getContext(), "Publish Faktor Kuantitatif berhasil ", Toast.LENGTH_LONG).show();
                                            prepareDataPosition();
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
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        }).setCancelable(false)
                        .show();
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

        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
        Call<List<KPIApproveListPJ>> call = apiService.getuserListPKWithSelf(usr.get(0).getEmpNIK(),empType,"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveListPJ>>() {
            @Override
            public void onResponse(Call<List<KPIApproveListPJ>> call, Response<List<KPIApproveListPJ>> response) {
                int statusCode = response.code();
                if(response.body().size()>0){
                    for(int i=0;i<response.body().size();i++){
                        SetupEmployeePKModel ka = new SetupEmployeePKModel();
                        ka.setEmpName(response.body().get(i).getEmpName());
                        ka.setId(response.body().get(i).getNIK());
                        ka.setNIK(response.body().get(i).getNIK());
                        ka.setJobTitle(response.body().get(i).getJobTitle());
                        ka.setBranchName(response.body().get(i).getBranchName());
                        ka.setDept(response.body().get(i).getDept());
                        ka.setStar("");
                        ka.setPkid(response.body().get(i).getPkid());
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

                        employeeSetupPAAdapter = new EmployeeSetupPKAdapter(setupEmployeeModelList,getContext(),getActivity(),"KUANTITATIF");
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        rvSetupEmployee.setLayoutManager(mLayoutManager);
                        rvSetupEmployee.setItemAnimator(new DefaultItemAnimator());
                        rvSetupEmployee.setAdapter(employeeSetupPAAdapter);

                    }
                    pd.dismiss();

                }
                else{
                    pd.dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<KPIApproveListPJ>> call, Throwable t) {
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

        EmpJobTitleRealmHelper empJobTitleRealmHelper = new EmpJobTitleRealmHelper(getContext());
        ArrayList<EmpJobTtlRealmModel> jobttl;
        jobttl = empJobTitleRealmHelper.findAllArticle();

        EmpOrgRealmHelper empOrgRealmHelper = new EmpOrgRealmHelper(getContext());
        ArrayList<EmpOrgRealmModel> org;
        org = empOrgRealmHelper.findAllArticle();


        for(int i=0;i<jobttl.size();i++){
            EmpJobTtlModel ej = new EmpJobTtlModel();
            ej.setJobttlcode(jobttl.get(i).getJobttlcode());
            ej.setJobttlname(jobttl.get(i).getJobttlname());
            empJobTtlModelList.add(ej);
        }
        for(int i=0;i<org.size();i++){
            EmpOrgModel eo = new EmpOrgModel();
            eo.setOrgCode(org.get(i).getOrgCode());
            eo.setOrgName(org.get(i).getOrgName());
            empOrgModelList.add(eo);
        }

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Load Data...");
        pd.setCancelable(false);
        pd.show();

            ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
            Call<List<PKSettingHeader>> call = apiService.getPAKPISettingHeaderPK(usr.get(0).getEmpNIK(),"Bearer "+usr.get(0).getToken());
            call.enqueue(new Callback<List<PKSettingHeader>>() {
                @Override
                public void onResponse(Call<List<PKSettingHeader>> call, Response<List<PKSettingHeader>> response) {
                    int statusCode = response.code();
                    paSettingHeaderList = response.body();

//                        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
//                        Call<List<EmpOrgModel>> call2 = apiService.getEmpOrg(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
//                        call2.enqueue(new Callback<List<EmpOrgModel>>() {
//                            @Override
//                            public void onResponse(Call<List<EmpOrgModel>> call, Response<List<EmpOrgModel>> response) {
//                                int statusCode = response.code();
//
//                                empOrgModelList = response.body();
//                                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
//                                Call<List<EmpJobTtlModel>> call3 = apiService.getEmpJobTtl(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
//                                call3.enqueue(new Callback<List<EmpJobTtlModel>>() {
//                                    @Override
//                                    public void onResponse(Call<List<EmpJobTtlModel>> call, Response<List<EmpJobTtlModel>> response) {
//                                    int statusCode = response.code();


                                    positionSetupPAAdapter = new PositionSetupPKAdapter(paSettingHeaderList,getContext(),getActivity(), SetupPKKuantitatifDBFragment.this,empJobTtlModelList,empOrgModelList);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                    rvSetupPosition.setLayoutManager(mLayoutManager);
                                    rvSetupPosition.setItemAnimator(new DefaultItemAnimator());
                                    rvSetupPosition.setAdapter(positionSetupPAAdapter);
                                    pd.dismiss();
//                                }
//                                @Override
//                                public void onFailure(Call<List<EmpJobTtlModel>> call, Throwable t) {
//                                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
//                                    pd.dismiss();
//                                }
//                            });
//
//                        }
//                        @Override
//                        public void onFailure(Call<List<EmpOrgModel>> call, Throwable t) {
//                            Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
//                            pd.dismiss();
//                        }
//                    });

                }
                @Override
                public void onFailure(Call<List<PKSettingHeader>> call, Throwable t) {
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
                SetupPositionPKModel setupPositionModel1 = new SetupPositionPKModel();
                setupPositionModel1.setYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                setupPositionModel1.setPositionName(edtTemplateName.getText().toString());
                setupPositionModel1.setId("");
                setupPositionModel1.setJabatan(null);
                setupPositionModel1.setOrganisasi(null);
                setupPositionModelList.add(setupPositionModel1);
                positionSetupPAAdapter.notifyDataSetChanged();

                List<PKSettingSetting> paSettingSettingList = new ArrayList<>();
                PKSettingSetting ps = new PKSettingSetting();
                ps.setTempJobTitleCode(usr.get(0).getJobTitleCode());
                ps.setTempOrgCode("");
                ps = new PKSettingSetting();
                ps.setTempJobTitleCode("");
                ps.setTempOrgCode(usr.get(0).getOrgCode());
                paSettingSettingList.add(ps);

                List<PKSettingHeader>paSettingHeaders = new ArrayList<>();
                PKSettingHeader paSettingHeader = new PKSettingHeader();
                paSettingHeader.setYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR))); //HARDCODE
                paSettingHeader.setTempKPIName(edtTemplateName.getText().toString());
                paSettingHeader.setTempKPIID("");
                paSettingHeader.setPaSettingSettings(paSettingSettingList);
                paSettingHeader.setChecked(false);
                paSettingHeader.setStatusDeployYN("Y");
                paSettingHeaders.add(paSettingHeader);
                paSettingHeader.setEmpNIK(usr.get(0).getEmpNIK());

                UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                Call<Void> call = apiService.postPAKPISettingHeaderPK(paSettingHeaders,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int statusCode = response.code();

                        Toast.makeText(getContext(),"Sukses",Toast.LENGTH_LONG).show();
                        prepareDataPosition();

//                        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
//                        Call<List<KPISettingSettingResponse>> call2 = apiService.postPAKPISettingSetting(paSettingHeaders,"Bearer "+usr.get(0).getToken());
//                        call2.enqueue(new Callback<List<KPISettingSettingResponse>>() {
//                            @Override
//                            public void onResponse(Call<List<KPISettingSettingResponse>> call2, Response<List<KPISettingSettingResponse>> response) {
//                                int statusCode = response.code();
//
//                            }
//                            @Override
//                            public void onFailure(Call<List<KPISettingSettingResponse>> call, Throwable t) {
//                                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
//
//                            }
//                        });


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
    public List<SetupPositionPKModel> getSetupPositionModels() {
        return setupPositionModelList;
    }

    @Override
    public void setSetupPositionModels(List<SetupPositionPKModel> setupPositionModelList) {
        this.setupPositionModelList = setupPositionModelList;

    }
}
