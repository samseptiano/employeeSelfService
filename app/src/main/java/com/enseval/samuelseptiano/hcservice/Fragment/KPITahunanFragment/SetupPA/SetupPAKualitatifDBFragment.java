package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.EmployeeSetupPAAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.PositionKualitatifSetupPAAdapter;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobLvlModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PaImportModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.SetupPositionKualitatifModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeaderKualitatif;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetupPAKualitatifDBFragment extends Fragment implements PositionKualitatifSetupPAAdapter.EventListener {

    View rootView;
    RecyclerView rvSetupEmployee;
    RecyclerView rvSetupPosition;


    LinearLayout lnSetupPosition;
    MaterialSpinner spinnerSetup;
    List<SetupEmployeeModel> setupEmployeeModelList = new ArrayList<>();
    List<SetupPositionKualitatifModel> setupPositionModelList = new ArrayList<>();
    List<EmpJobLvlModel> empJobLvlModelList = new ArrayList<>();

    EmployeeSetupPAAdapter employeeSetupPAAdapter;
    PositionKualitatifSetupPAAdapter positionSetupPAAdapter;
    Button btnAdd, btnImport, btnDelete, btnPublish;
    List<PASettingHeaderKualitatif> paSettingHeaderKualitatifList = new ArrayList<>();
    ArrayList<UserRealmModel> usr;
    SwipeRefreshLayout pullToRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setup_pakualitatif_db, container, false);

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Setup PA Kualitatif");
        rvSetupEmployee = rootView.findViewById(R.id.rv_setup_employee);
        rvSetupPosition = rootView.findViewById(R.id.rv_setup_position);

        spinnerSetup = rootView.findViewById(R.id.spinnerSetup);
        lnSetupPosition = rootView.findViewById(R.id.lnSetupPosition);

        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnImport = rootView.findViewById(R.id.btnImport);
        btnDelete = rootView.findViewById(R.id.btnDelete);
        btnPublish = rootView.findViewById(R.id.btnPublish);
        pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());

        usr = userRealmHelper.findAllArticle();

        try {
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

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



    private void refreshData(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPositionDialog();
            }
        });


        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                        .setMessage("Anda ingin mempublish template ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                List<PaImportModel> paImportModels = new ArrayList<>();
                                int ctr = 0;

                                for(int i=0;i<paSettingHeaderKualitatifList.size();i++){
                                    PaImportModel paImportModel = new PaImportModel();
                                    float total=0;
                                    for(int j=0;j<paSettingHeaderKualitatifList.get(i).getPaSettingDetails().size();j++){
                                        total+=Float.parseFloat(paSettingHeaderKualitatifList.get(i).getPaSettingDetails().get(j).getBobot().replace(",","."));
                                    }
                                    if(paSettingHeaderKualitatifList.get(i).isChecked() && total == 100) {
                                        paImportModel.setTempCompID(paSettingHeaderKualitatifList.get(i).getTempCompID());
                                        paImportModel.setPeriode(paSettingHeaderKualitatifList.get(i).getYear());
                                        paImportModel.setKpiType("KUALITATIF");
                                        paImportModel.setEmpnik(usr.get(0).getEmpNIK());
                                        paImportModels.add(paImportModel);
                                    }
                                    else if(paSettingHeaderKualitatifList.get(i).isChecked() && total !=100){
                                        ctr++;
                                    }
                                }

                                if(ctr==0) {
                                    ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                                    Call<Void> call = apiService.importTemplate(paImportModels, "Bearer " + usr.get(0).getToken());
                                    call.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            int statusCode = response.code();
                                            Toast.makeText(getContext(), "Publish Faktor Kualitatif berhasil ", Toast.LENGTH_LONG).show();
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<PASettingHeaderKualitatif> aaa = new ArrayList<>();

                for(int i=0;i<paSettingHeaderKualitatifList.size();i++){
                    if(paSettingHeaderKualitatifList.get(i).isChecked()){
                        aaa.add(paSettingHeaderKualitatifList.get(i));
                    }
                }

                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                        .setMessage("Apakah anda ingin menghapus template ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                                Call<Void> call = apiService.deletePAKPISettingHeaderKualitatif(aaa, "Bearer " + usr.get(0).getToken());
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        int statusCode = response.code();
                                        Toast.makeText(getContext(), "Sukses", Toast.LENGTH_LONG).show();
                                        prepareDataPosition();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

                                    }

                                });
                            }
                        })
                        .setNegativeButton("Keluar", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        }).setCancelable(false)
                        .show();
            }});

        spinnerSetup.setBackgroundResource(R.drawable.shapedropdown);
        spinnerSetup.setPadding(25, 10, 25, 10);
        List<String> setup = new ArrayList<String>();
        setup.add("EMPLOYEE");
        setup.add("POSITION");
        spinnerSetup.setItems(setup);

        spinnerSetup.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (spinnerSetup.getText().toString().equals("EMPLOYEE")) {
                    rvSetupEmployee.setVisibility(View.VISIBLE);
                    prepareDataEmployee();
                    lnSetupPosition.setVisibility(View.GONE);
                } else {
                    prepareDataPosition();
                    rvSetupEmployee.setVisibility(View.GONE);
                    lnSetupPosition.setVisibility(View.VISIBLE);

                }
            }
        });
        spinnerSetup.setVisibility(View.INVISIBLE);
        prepareDataPosition();
        rvSetupEmployee.setVisibility(View.GONE);
        lnSetupPosition.setVisibility(View.VISIBLE);
    }



    private void prepareDataEmployee() {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Load Data...");
        pd.setCancelable(false);
        pd.show();

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        for (int i = 0; i < 10; i++) {
            SetupEmployeeModel ka = new SetupEmployeeModel();
            ka.setDept("");
            ka.setEmpName("Adiwinata Saputra");
            ka.setId("190300270");
            ka.setNIK("190300270");
            ka.setJobTitle("Salesman");
            ka.setBranchName("Makassar");
            ka.setDept("KND");
            ka.setStar("4");
            ka.setStatus("Complete");
            ka.setJobStatus("Tetap");
            ka.setDateStart("22/12/2018");
            ka.setDateEnd("22/03/2019");
            //=== parameter list bawahan berdasarkan nik akun tersebut ===
            ka.setNIKAtasan1("030300324");
            ka.setNIKAtasan2("070500332");
            ka.setNamaAtasan1("Norish Hanoch Wairata");
            ka.setNamaAtasan2("Rudy Djajasaputra");
            //==================
            if (i % 2 == 0) {
                ka.setStatus1("Not Approved");
            } else {
                ka.setStatus1("Approved");
            }
            setupEmployeeModelList.add(ka);

            employeeSetupPAAdapter = new EmployeeSetupPAAdapter(setupEmployeeModelList, getContext(), getActivity(),"KUALITATIF");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSetupEmployee.setLayoutManager(mLayoutManager);
            rvSetupEmployee.setItemAnimator(new DefaultItemAnimator());
            rvSetupEmployee.setAdapter(employeeSetupPAAdapter);

        }
        pd.dismiss();

    }

    private void prepareDataPosition() {
        paSettingHeaderKualitatifList = new ArrayList<>();
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Load Data...");
        pd.setCancelable(false);
        pd.show();
        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<PASettingHeaderKualitatif>> call = apiService.getPAKualitatifSettingHeader("Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<PASettingHeaderKualitatif>>() {
            @Override
            public void onResponse(Call<List<PASettingHeaderKualitatif>> call, Response<List<PASettingHeaderKualitatif>> response) {
                int statusCode = response.code();

                paSettingHeaderKualitatifList = response.body();

                positionSetupPAAdapter = new PositionKualitatifSetupPAAdapter(paSettingHeaderKualitatifList, getContext(), getActivity(), SetupPAKualitatifDBFragment.this, empJobLvlModelList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                rvSetupPosition.setLayoutManager(mLayoutManager);
                rvSetupPosition.setItemAnimator(new DefaultItemAnimator());
                rvSetupPosition.setAdapter(positionSetupPAAdapter);

                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                Call<List<EmpJobLvlModel>> call2 = apiService.getEmpJobLvl("Bearer "+usr.get(0).getToken());
                call2.enqueue(new Callback<List<EmpJobLvlModel>>() {
                    @Override
                    public void onResponse(Call<List<EmpJobLvlModel>> call, Response<List<EmpJobLvlModel>> response) {
                        int statusCode = response.code();
                        empJobLvlModelList = response.body();

                        positionSetupPAAdapter = new PositionKualitatifSetupPAAdapter(paSettingHeaderKualitatifList, getContext(), getActivity(), SetupPAKualitatifDBFragment.this, empJobLvlModelList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        rvSetupPosition.setLayoutManager(mLayoutManager);
                        rvSetupPosition.setItemAnimator(new DefaultItemAnimator());
                        rvSetupPosition.setAdapter(positionSetupPAAdapter);
                        pd.dismiss();

                    }
                    @Override
                    public void onFailure(Call<List<EmpJobLvlModel>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                });


            }
            @Override
            public void onFailure(Call<List<PASettingHeaderKualitatif>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });

    }

    private void showAddPositionDialog() {
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
                SetupPositionKualitatifModel setupPositionModel1 = new SetupPositionKualitatifModel();
                setupPositionModel1.setYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                setupPositionModel1.setPositionName(edtTemplateName.getText().toString());
                setupPositionModel1.setId("");
                setupPositionModel1.setTabDab("");
                setupPositionModel1.setGolongan(null);


                List<PASettingHeaderKualitatif>paSettingHeaders = new ArrayList<>();
                PASettingHeaderKualitatif paSettingHeader = new PASettingHeaderKualitatif();
                paSettingHeader.setYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR))); //HARDCODE
                paSettingHeader.setTempCompName(edtTemplateName.getText().toString());
                paSettingHeader.setTempCompID("");
                paSettingHeader.setChecked(false);
                paSettingHeader.setTabDab("N");
                paSettingHeader.setTempCompSubYN("N");
                paSettingHeader.setPaSettingDetails(null);
                paSettingHeader.setPaSettingSettings(null);

                paSettingHeaders.add(paSettingHeader);



                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                Call<Void> call2 = apiService.postPAKPISettingHeaderKualitatif(paSettingHeaders,"Bearer "+usr.get(0).getToken());
                call2.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int statusCode = response.code();
                        Toast.makeText(getContext(),"Sukses",Toast.LENGTH_LONG).show();
                        prepareDataPosition();
                        dialogs.dismiss();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                        dialogs.dismiss();

                    }
                });


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
    public List<PASettingHeaderKualitatif> getSetupPositionModels() {
        return paSettingHeaderKualitatifList;
    }

    @Override
    public void setSetupPositionModels(List<PASettingHeaderKualitatif> setupPositionModelList) {
        this.paSettingHeaderKualitatifList = setupPositionModelList;
    }
}
