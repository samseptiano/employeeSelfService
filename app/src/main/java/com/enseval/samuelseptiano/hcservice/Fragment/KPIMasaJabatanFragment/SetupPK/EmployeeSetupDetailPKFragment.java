package com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPKAdapter.EmployeeSetupDetailPKAdapter;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PAEmployeeDetail;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.MKPIPK;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKEmployeeDetail;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.SetupEmployeeDetailPKModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.SetupPositionDetailPKModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeeSetupDetailPKFragment extends Fragment implements EmployeeSetupDetailPKAdapter.EventListener{

    View rootView;
    RecyclerView rvSetupEmployeeDetail;
    TextView tvEmpName,tvJobTitle;
    CircleImageView imgEmpPhoto;
    Button btnAdd,btnDelete;
    String idKPI;
    String NIK="";
    boolean isUbah=false;

    String empName = "", jobTitle="", dept, branchName, jobStatus, empPhoto="", empNik;
    String KPIType="",pkid="";

    List<PKEmployeeDetail> paEmployeeDetails = new ArrayList<>();
    List<SetupEmployeeDetailPKModel> setupEmployeeDetailModelList = new ArrayList<>();
    List<SetupPositionDetailPKModel> masterSetupPositionDetailModelList = new ArrayList<>();

    EmployeeSetupDetailPKAdapter employeeSetupDetailPAAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_employee_setup_detail_pa, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            empNik = bundle.getString("empNik");
            empName = bundle.getString("empName");
            jobTitle = bundle.getString("jobTitle");
            dept = bundle.getString("dept");
            pkid = bundle.getString("pkid");
            NIK = bundle.getString("NIK");
            //NIK="120901480";
            jobStatus = bundle.getString("jobStatus");
            branchName = bundle.getString("branchName");
            empPhoto = bundle.getString("empPhoto");
            KPIType = bundle.getString("KPIType");
        }
        tvEmpName = rootView.findViewById(R.id.tv_employeeName);
        tvJobTitle = rootView.findViewById(R.id.tv_job_title);
        imgEmpPhoto = rootView.findViewById(R.id.imgEmpPhoto);
        btnAdd = rootView.findViewById(R.id.btnAddKPI);
        btnDelete = rootView.findViewById(R.id.btnDelKPI);


        tvEmpName.setText(empName);
        tvJobTitle.setText(branchName);

        Picasso.get()
                .load(empPhoto)
                .placeholder(R.drawable.user)
                .error(R.drawable.imgalt)
                .into(imgEmpPhoto);

        rvSetupEmployeeDetail = rootView.findViewById(R.id.rv_setup_emp_pa);
        if(KPIType.equals("KUANTITATIF")) {
            prepareDataEmployeePAKuantitatif();
        }
        else{
            prepareDataEmployeePAKualitatif();
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeSetupDetailPAAdapter = new EmployeeSetupDetailPKAdapter(setupEmployeeDetailModelList,getContext(),getActivity(), EmployeeSetupDetailPKFragment.this,KPIType, masterSetupPositionDetailModelList);
                showAddDialog(employeeSetupDetailPAAdapter);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    List<PKEmployeeDetail> aaa = new ArrayList<>();
                    aaa = paEmployeeDetails;
//                for (int i = 0; i < aaa.size(); i++) {
//                    for(int j=0;j<setupEmployeeDetailModelList.size();j++) {
//                        if (!setupEmployeeDetailModelList.get(j).isChecked()&& setupEmployeeDetailModelList.get(j).getId().equals(aaa.get(i).getKpiID())) {
//                            aaa.remove(i);
//                            //}
//                        }
//                    }
//                }
//
//                for(int i=0;i<setupEmployeeDetailModelList.size();i++){
//                    if(setupEmployeeDetailModelList.get(i).isChecked()){
//                        setupEmployeeDetailModelList.remove(i);
//                    }
//                }


                    Iterator<SetupEmployeeDetailPKModel> iter = setupEmployeeDetailModelList.iterator();

                    Iterator<PKEmployeeDetail> iter2 = aaa.iterator();

                    while (iter2.hasNext()) {
                        SetupEmployeeDetailPKModel p = iter.next();
                        PKEmployeeDetail p2 = iter2.next();

                        if (!p.isChecked()) {
                            if (p.getId().equals(p2.getKpiID())) {
                                iter2.remove();
                            }
                        } else {
                            iter.remove();
                        }
                    }


                    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                    ArrayList<UserRealmModel> usr;
                    usr = userRealmHelper.findAllArticle();
                    ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                    Call<Void> call = apiService.deletePAKPIEmployeePK(aaa, "Bearer " + usr.get(0).getToken());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int statusCode = response.code();
                            Toast.makeText(getContext(), "Sukses Delete", Toast.LENGTH_LONG).show();
                            prepareDataEmployeePAKuantitatif();


//                            employeeSetupDetailPAAdapter = new EmployeeSetupDetailPAAdapter(setupEmployeeDetailModelList, getContext(), getActivity(), EmployeeSetupDetailPAFragment.this, KPIType, masterSetupPositionDetailModelList);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                            rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
//                            rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
//                            rvSetupEmployeeDetail.setAdapter(employeeSetupDetailPAAdapter);

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

                        }
                    });

                    employeeSetupDetailPAAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                }
            }
        });



        return  rootView;
    }

    private void prepareDataEmployeePAKualitatif() {
    }

    private void prepareDataEmployeePAKuantitatif() {
        paEmployeeDetails = new ArrayList<>();
        setupEmployeeDetailModelList = new ArrayList<>();
        masterSetupPositionDetailModelList = new ArrayList<>();
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
        Call<List<PKEmployeeDetail>> call = apiService.getPAKPIEmployeePK("KUANTITATIF",NIK,"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<PKEmployeeDetail>>() {
            @Override
            public void onResponse(Call<List<PKEmployeeDetail>> call, Response<List<PKEmployeeDetail>> response) {
                paEmployeeDetails = response.body();
                Call<List<MKPIPK>> call2 = apiService.getMKPIALLPK("Bearer "+usr.get(0).getToken());
                call2.enqueue(new Callback<List<MKPIPK>>() {
                    @Override
                    public void onResponse(Call<List<MKPIPK>> call, Response<List<MKPIPK>> response) {
                        for(int i=0;i<response.body().size();i++){
                            SetupPositionDetailPKModel ka = new SetupPositionDetailPKModel();
                            ka.setPaId(response.body().get(i).getKPIID());
                            ka.setBobot(response.body().get(i).getBOBOT());
                            ka.setId(response.body().get(i).getKPIID());
                            ka.setTemplateJobTitle("JOBTITLE");
                            ka.setTemplateId("");          //MASIH HARDCODE
                            ka.setTemplateOrganisasi("ORGANISASI");
                            ka.setKPIDesc(response.body().get(i).getKPINAME());
                            ka.setKPIType("KUANTITATIF");
                            ka.setKPIperspective("KUANTITATIF");

                            ka.setKpiHintList(response.body().get(i).getpK_ViewTransGrades());
                            masterSetupPositionDetailModelList.add(ka);
                        }

                    }
                    @Override
                    public void onFailure(Call<List<MKPIPK>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                    }
                });

                int j=0;
                for(int i=0;i<paEmployeeDetails.size();i++){
                    SetupEmployeeDetailPKModel ka = new SetupEmployeeDetailPKModel();
                    ka.setPkId(paEmployeeDetails.get(i).getPkid());
                    ka.setBobot(paEmployeeDetails.get(i).getBobot());
                    ka.setId(paEmployeeDetails.get(i).getKpiID());
                    ka.setKPIDesc(paEmployeeDetails.get(i).getKpiname());
                    ka.setKPIType("KUANTITATIF");
                    ka.setKPIperspective("KUANTITATIF");
                    ka.setNumber(Integer.toString(i+1));

                    ka.setKpiHintList(paEmployeeDetails.get(i).getHintList());
                    setupEmployeeDetailModelList.add(ka);

                    employeeSetupDetailPAAdapter = new EmployeeSetupDetailPKAdapter(setupEmployeeDetailModelList,getContext(),getActivity(), EmployeeSetupDetailPKFragment.this,KPIType, masterSetupPositionDetailModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
                    rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
                    rvSetupEmployeeDetail.setAdapter(employeeSetupDetailPAAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<PKEmployeeDetail>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public List<SetupEmployeeDetailPKModel> getsetupEmployeeDetailModels() {
        return setupEmployeeDetailModelList;
    }

    @Override
    public void setsetupEmployeeDetailModels(List<SetupEmployeeDetailPKModel> setupEmployeeDetailModelList) {
        this.setupEmployeeDetailModelList = setupEmployeeDetailModelList;
    }

    @Override
    public void setUbah(boolean isUbah) {
        this.isUbah = isUbah;
    }

    @Override
    public boolean getUbah() {
        return isUbah;
    }

    private void showAddDialog(EmployeeSetupDetailPKAdapter employeeSetupDetailPAAdapter){
        Dialog dialogs = new Dialog(getContext());
        final String[] semester = {"1"};
        final boolean[] isAll = {true};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.add_pk_kpi_kuantiatif_dialog);
        dialogs.setTitle("Title...");
        TextView tvTitle;
        EditText edtBobot,edtKPIDesc;
        TextView edtHint1,edtHint2,edtHint3,edtHint4,edtHint5;
        Button btnSaveKPI;
        MaterialSpinner spinnerKPIDesc;
        ImageButton btnClose;

//        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);

        tvTitle = dialogs.findViewById(R.id.tvTitle);
        btnSaveKPI = dialogs.findViewById(R.id.btnSaveKPI);

        btnClose = dialogs.findViewById(R.id.ib_close);
        spinnerKPIDesc = dialogs.findViewById(R.id.spinnerKPIDesc);

        edtBobot = dialogs.findViewById(R.id.edtBobot);

        edtHint1 = dialogs.findViewById(R.id.edtHint1);
        edtHint2 = dialogs.findViewById(R.id.edtHint2);
        edtHint3 = dialogs.findViewById(R.id.edtHint3);
        edtHint4 = dialogs.findViewById(R.id.edtHint4);
        edtHint5 = dialogs.findViewById(R.id.edtHint5);

        spinnerKPIDesc.setBackgroundResource(R.drawable.shapedropdown);
        spinnerKPIDesc.setPadding(25, 10, 25, 10);
        List<String> setup2 = new ArrayList<String>();
        for(int i=0;i<masterSetupPositionDetailModelList.size();i++){
            setup2.add(masterSetupPositionDetailModelList.get(i).getKPIDesc());
        }
        Collections.sort(setup2);
        setup2.add(0,"- PILIH -");
        spinnerKPIDesc.setItems(setup2);

        spinnerKPIDesc.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                for (int i=0;i<masterSetupPositionDetailModelList.size();i++){
                    if(spinnerKPIDesc.getText().equals(masterSetupPositionDetailModelList.get(i).getKPIDesc())){
                        try{edtHint1.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(4).getKpiGradeName());
                        edtHint2.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(3).getKpiGradeName());
                        edtHint3.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(2).getKpiGradeName());
                        edtHint4.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(1).getKpiGradeName());
                        edtHint5.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(0).getKpiGradeName());
                        idKPI = masterSetupPositionDetailModelList.get(i).getPaId();}
                        catch (Exception e){
                            edtHint1.setText("");
                            edtHint2.setText("");
                            edtHint3.setText("");
                            edtHint4.setText("");
                            edtHint5.setText("");
                        }
                        break;
                    }
                    else{
                        edtHint1.setText("");
                        edtHint2.setText("");
                        edtHint3.setText("");
                        edtHint4.setText("");
                        edtHint5.setText("");

                    }
                }
            }
        });

        btnSaveKPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    List<PKEmployeeDetail> paEmployeeDetails = new ArrayList<>();
                        List<String> aaa = new ArrayList<>();
                        aaa.add(edtHint1.getText().toString());
                        aaa.add(edtHint2.getText().toString());
                        aaa.add(edtHint2.getText().toString());
                        aaa.add(edtHint2.getText().toString());
                        aaa.add(edtHint2.getText().toString());


                        SetupEmployeeDetailPKModel setupEmployeeDetailModel = new SetupEmployeeDetailPKModel();
                        setupEmployeeDetailModel.setChecked(false);
                        setupEmployeeDetailModel.setId(idKPI);
                        setupEmployeeDetailModel.setPkId(pkid);
                        setupEmployeeDetailModel.setKPIType("KUANTITATIF");
                        setupEmployeeDetailModel.setKPIDesc(spinnerKPIDesc.getText().toString());
                        setupEmployeeDetailModel.setBobot(edtBobot.getText().toString());


//                        List<KPIHint> kpiHints = new ArrayList<>();
//                        for (int i = 0; i < 5; i++) {
//                            KPIHint kpiHint = new KPIHint();
//                            kpiHint.setKpiGradeID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeID());
//                            kpiHint.setKpiID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiID());
//                            kpiHint.setKpiGradeCode(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeCode());
//                            kpiHint.setKpiGradeName(aaa.get(i));
//                            kpiHints.add(kpiHint);
//                        }

                        //=======================================

                        //setupEmployeeDetailModel.setKpiHintList(kpiHints);
                        setupEmployeeDetailModelList.add(setupEmployeeDetailModel);

                        for (int i = 0; i < setupEmployeeDetailModelList.size(); i++) {
                            PKEmployeeDetail paEmployeeDetail = new PKEmployeeDetail();
                            paEmployeeDetail.setBobot(setupEmployeeDetailModelList.get(i).getBobot());
                            //paEmployeeDetail.setHintList(kpiHints);
                            paEmployeeDetail.setKpiID(setupEmployeeDetailModelList.get(i).getId());
                            paEmployeeDetail.setKpiname(setupEmployeeDetailModelList.get(i).getKPIDesc());
                            paEmployeeDetail.setPkid(pkid);
                            //paEmployeeDetail.setEmpnik("111111");
                            paEmployeeDetails.add(paEmployeeDetail);
                        }

                        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                        ArrayList<UserRealmModel> usr;
                        usr = userRealmHelper.findAllArticle();
                        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                        Call<Void> call = apiService.postPAKPIEmployeePK(paEmployeeDetails, "Bearer " + usr.get(0).getToken());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int statusCode = response.code();
                                Toast.makeText(getContext(), "Sukses", Toast.LENGTH_LONG).show();
                                prepareDataEmployeePAKuantitatif();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

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
}
