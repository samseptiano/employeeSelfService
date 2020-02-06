package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.SetupPA;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.ListDevPlanAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.EmployeeSetupDetailPAAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.EmployeeSetupPAAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.PositionSetupPAAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.MKPI;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PAEmployeeDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PASettingDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PASettingHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeDetailModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupPositionDetailModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupPositionModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeeSetupDetailPAFragment extends Fragment implements EmployeeSetupDetailPAAdapter.EventListener{

    View rootView;
    RecyclerView rvSetupEmployeeDetail;
    TextView tvEmpName,tvJobTitle;
    CircleImageView imgEmpPhoto;
    Button btnAdd,btnDelete;
    String idKPI;
    String NIK="";
    boolean isUbah=false;

    String empName = "", jobTitle="", dept, branchName, jobStatus, empPhoto="", empNik;
    String KPIType="",paid="";

    List<PAEmployeeDetail> paEmployeeDetails = new ArrayList<>();
    List<SetupEmployeeDetailModel> setupEmployeeDetailModelList = new ArrayList<>();
    List<SetupPositionDetailModel> masterSetupPositionDetailModelList = new ArrayList<>();

    EmployeeSetupDetailPAAdapter employeeSetupDetailPAAdapter;

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
            paid = bundle.getString("paid");
            NIK = bundle.getString("NIK");
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
                employeeSetupDetailPAAdapter = new EmployeeSetupDetailPAAdapter(setupEmployeeDetailModelList,getContext(),getActivity(),EmployeeSetupDetailPAFragment.this,KPIType, masterSetupPositionDetailModelList);
                showAddDialog(employeeSetupDetailPAAdapter);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    List<PAEmployeeDetail> aaa = new ArrayList<>();
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


                    Iterator<SetupEmployeeDetailModel> iter = setupEmployeeDetailModelList.iterator();

                    Iterator<PAEmployeeDetail> iter2 = aaa.iterator();

                    while (iter2.hasNext()) {
                        SetupEmployeeDetailModel p = iter.next();
                        PAEmployeeDetail p2 = iter2.next();

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
                    ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                    Call<Void> call = apiService.deletePAKPIEmployee(aaa, "Bearer " + usr.get(0).getToken());
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
//        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
//        ArrayList<UserRealmModel> usr;
//        usr = userRealmHelper.findAllArticle();
//
//        for(int i=0;i<30;i++){
//            SetupEmployeeDetailModel ka = new SetupEmployeeDetailModel();
//            ka.setPaId(Integer.toString(i));
//            ka.setBobot("15%");
//            ka.setId(Integer.toString(i));
//            ka.setEmpJobTitle("jobtitle");
//            ka.setKPIDesc("Lorem Ipsum");
//            ka.setKPIType("KUALITATIF");
//            ka.setKPIperspective("KUALITATIF");
//            if(i>20){
//                ka.setSemester("2");
//
//            }
//            else{
//                ka.setSemester("1");
//            }
//
//            List<KPIHint>kpiHints = new ArrayList<>();
//
//            for (int j=0;j<5;j++){
//                KPIHint kpiHint = new KPIHint();
//                kpiHint.setKpiGradeName("Hint "+j);
//                kpiHint.setKpiID(Integer.toString(j));
//                kpiHint.setKpiGradeCode(Integer.toString(j));
//                kpiHint.setKpiGradeID(Integer.toString(j));
//                kpiHints.add(kpiHint);
//            }
//
//            ka.setKpiHintList(kpiHints);
//            setupEmployeeDetailModelList.add(ka);
//
//            employeeSetupDetailPAAdapter = new EmployeeSetupDetailPAAdapter(setupEmployeeDetailModelList,getContext(),getActivity(),this,KPIType);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//            rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
//            rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
//            rvSetupEmployeeDetail.setAdapter(employeeSetupDetailPAAdapter);
//
//        }


    }

    private void prepareDataEmployeePAKuantitatif() {
        paEmployeeDetails = new ArrayList<>();
        setupEmployeeDetailModelList = new ArrayList<>();

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<PAEmployeeDetail>> call = apiService.getPAKPIEmployee("KUANTITATIF",NIK,"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<PAEmployeeDetail>>() {
            @Override
            public void onResponse(Call<List<PAEmployeeDetail>> call, Response<List<PAEmployeeDetail>> response) {
                paEmployeeDetails = response.body();
                Call<List<MKPI>> call2 = apiService.getMKPIALL("Bearer "+usr.get(0).getToken());
                call2.enqueue(new Callback<List<MKPI>>() {
                    @Override
                    public void onResponse(Call<List<MKPI>> call, Response<List<MKPI>> response) {
                        for(int i=0;i<response.body().size();i++){
                            SetupPositionDetailModel ka = new SetupPositionDetailModel();
                            ka.setPaId(response.body().get(i).getKPIID());
                            ka.setBobot(response.body().get(i).getBOBOT());
                            ka.setId(response.body().get(i).getKPIID());
                            ka.setTemplateJobTitle("JOBTITLE");
                            ka.setTemplateId("");          //MASIH HARDCODE
                            ka.setTemplateOrganisasi("ORGANISASI");
                            ka.setKPIDesc(response.body().get(i).getKPINAME());
                            ka.setKPIType("KUANTITATIF");
                            ka.setKPIperspective("KUANTITATIF");
                            ka.setSemester(response.body().get(i).getSEMESTER());

                            ka.setKpiHintList(response.body().get(i).getpA_ViewTransGrades());
                            masterSetupPositionDetailModelList.add(ka);
                        }

                    }
                    @Override
                    public void onFailure(Call<List<MKPI>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                    }
                });

                int j=0;
                for(int i=0;i<paEmployeeDetails.size();i++){
                    SetupEmployeeDetailModel ka = new SetupEmployeeDetailModel();
                    ka.setPaId(paEmployeeDetails.get(i).getPaid());
                    ka.setBobot(paEmployeeDetails.get(i).getBobot());
                    ka.setId(paEmployeeDetails.get(i).getKpiID());
                    ka.setEmpJobTitle("");
                    ka.setKPIDesc(paEmployeeDetails.get(i).getKpiname());
                    ka.setKPIType("KUANTITATIF");
                    ka.setKPIperspective("KUANTITATIF");
                    ka.setSemester(paEmployeeDetails.get(i).getSemester());
                    if(paEmployeeDetails.get(i).getSemester().equals("2")){
                        ka.setNumber(Integer.toString(j+1));
                        j++;
                    }
                    else{
                        ka.setNumber(Integer.toString(i+1));
                    }
                    ka.setKpiHintList(paEmployeeDetails.get(i).getHintList());
                    setupEmployeeDetailModelList.add(ka);

                    employeeSetupDetailPAAdapter = new EmployeeSetupDetailPAAdapter(setupEmployeeDetailModelList,getContext(),getActivity(),EmployeeSetupDetailPAFragment.this,KPIType, masterSetupPositionDetailModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
                    rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
                    rvSetupEmployeeDetail.setAdapter(employeeSetupDetailPAAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<PAEmployeeDetail>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public List<SetupEmployeeDetailModel> getsetupEmployeeDetailModels() {
        return setupEmployeeDetailModelList;
    }

    @Override
    public void setsetupEmployeeDetailModels(List<SetupEmployeeDetailModel> setupEmployeeDetailModelList) {
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

    private void showAddDialog(EmployeeSetupDetailPAAdapter employeeSetupDetailPAAdapter){
        Dialog dialogs = new Dialog(getContext());
        final String[] semester = {"1"};
        final boolean[] isAll = {true};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.add_kpi_kuantiatif_dialog);
        dialogs.setTitle("Title...");
        TextView tvTitle;
        EditText edtBobot,edtKPIDesc;
        TextView edtHint1,edtHint2,edtHint3,edtHint4,edtHint5;
        Button btnSaveKPI;
        MaterialSpinner spinnerSemester,spinnerKPIDesc;
        ImageButton btnClose;

//        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);

        tvTitle = dialogs.findViewById(R.id.tvTitle);
        btnSaveKPI = dialogs.findViewById(R.id.btnSaveKPI);

        btnClose = dialogs.findViewById(R.id.ib_close);
        spinnerSemester = dialogs.findViewById(R.id.spinnerSemester);
        spinnerKPIDesc = dialogs.findViewById(R.id.spinnerKPIDesc);

        edtBobot = dialogs.findViewById(R.id.edtBobot);

        edtHint1 = dialogs.findViewById(R.id.edtHint1);
        edtHint2 = dialogs.findViewById(R.id.edtHint2);
        edtHint3 = dialogs.findViewById(R.id.edtHint3);
        edtHint4 = dialogs.findViewById(R.id.edtHint4);
        edtHint5 = dialogs.findViewById(R.id.edtHint5);

        spinnerSemester.setBackgroundResource(R.drawable.shapedropdown);
        spinnerSemester.setPadding(25, 10, 25, 10);
        List<String> setup = new ArrayList<String>();
        setup.add("-ALL-");
        setup.add("SMT 1");
        setup.add("SMT 2");
        spinnerSemester.setItems(setup);

        spinnerKPIDesc.setBackgroundResource(R.drawable.shapedropdown);
        spinnerKPIDesc.setPadding(25, 10, 25, 10);
        List<String> setup2 = new ArrayList<String>();
        setup2.add("- PILIH -");
        for(int i=0;i<masterSetupPositionDetailModelList.size();i++){
            setup2.add(masterSetupPositionDetailModelList.get(i).getKPIDesc());
        }
        spinnerKPIDesc.setItems(setup2);

        spinnerSemester.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(spinnerSemester.getText().toString().equals("SMT 1")){
                    semester[0] ="1";
                    isAll[0] = false;
                }
                else if (spinnerSemester.getText().toString().equals("-ALL-")){
                    isAll[0] = true;
                }
                else{
                    semester[0] ="2";
                    isAll[0] = false;

                }
            }
        });

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

                if(isAll[0]){
                    List<PAEmployeeDetail> paEmployeeDetails = new ArrayList<>();
                    for(int x=0;x<2;x++) {
                        List<String> aaa = new ArrayList<>();
                        aaa.add(edtHint1.getText().toString());
                        aaa.add(edtHint2.getText().toString());
                        aaa.add(edtHint2.getText().toString());
                        aaa.add(edtHint2.getText().toString());
                        aaa.add(edtHint2.getText().toString());


                        SetupEmployeeDetailModel setupEmployeeDetailModel = new SetupEmployeeDetailModel();
                        setupEmployeeDetailModel.setChecked(false);
                        setupEmployeeDetailModel.setId(idKPI);
                        setupEmployeeDetailModel.setPaId(paid);
                        setupEmployeeDetailModel.setSemester(Integer.toString(x + 1));
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
                            PAEmployeeDetail paEmployeeDetail = new PAEmployeeDetail();
                            paEmployeeDetail.setBobot(setupEmployeeDetailModelList.get(i).getBobot());
                            //paEmployeeDetail.setHintList(kpiHints);
                            paEmployeeDetail.setKpiID(setupEmployeeDetailModelList.get(i).getId());
                            paEmployeeDetail.setKpiname(setupEmployeeDetailModelList.get(i).getKPIDesc());
                            paEmployeeDetail.setPaid(paid);
                            paEmployeeDetail.setSemester(setupEmployeeDetailModelList.get(i).getSemester());
                            paEmployeeDetails.add(paEmployeeDetail);
                        }
                    }
                        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                        ArrayList<UserRealmModel> usr;
                        usr = userRealmHelper.findAllArticle();
                        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                        Call<Void> call = apiService.postPAKPIEmployee(paEmployeeDetails, "Bearer " + usr.get(0).getToken());
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


                }
                else {
                    List<String> aaa = new ArrayList<>();
                    aaa.add(edtHint1.getText().toString());
                    aaa.add(edtHint2.getText().toString());
                    aaa.add(edtHint2.getText().toString());
                    aaa.add(edtHint2.getText().toString());
                    aaa.add(edtHint2.getText().toString());


                    SetupEmployeeDetailModel setupEmployeeDetailModel = new SetupEmployeeDetailModel();
                    setupEmployeeDetailModel.setChecked(false);
                    setupEmployeeDetailModel.setId(idKPI);
                    setupEmployeeDetailModel.setPaId(paid);
                    setupEmployeeDetailModel.setSemester(semester[0]);
                    setupEmployeeDetailModel.setKPIType("KUANTITATIF");
                    setupEmployeeDetailModel.setKPIDesc(spinnerKPIDesc.getText().toString());
                    setupEmployeeDetailModel.setBobot(edtBobot.getText().toString());


//                    List<KPIHint> kpiHints = new ArrayList<>();
//                    for (int i = 0; i < 5; i++) {
//                        KPIHint kpiHint = new KPIHint();
//                        kpiHint.setKpiGradeID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeID());
//                        kpiHint.setKpiID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiID());
//                        kpiHint.setKpiGradeCode(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeCode());
//                        kpiHint.setKpiGradeName(aaa.get(i));
//                        kpiHints.add(kpiHint);
//                    }

                    //=======================================

                    //setupEmployeeDetailModel.setKpiHintList(kpiHints);
                    setupEmployeeDetailModelList.add(setupEmployeeDetailModel);

                    List<PAEmployeeDetail> paEmployeeDetails = new ArrayList<>();
                    for (int i = 0; i < setupEmployeeDetailModelList.size(); i++) {
                        PAEmployeeDetail paEmployeeDetail = new PAEmployeeDetail();
                        paEmployeeDetail.setBobot(setupEmployeeDetailModelList.get(i).getBobot());
                       // paEmployeeDetail.setHintList(kpiHints);
                        paEmployeeDetail.setKpiID(setupEmployeeDetailModelList.get(i).getId());
                        paEmployeeDetail.setKpiname(setupEmployeeDetailModelList.get(i).getKPIDesc());
                        paEmployeeDetail.setPaid(paid);
                        paEmployeeDetail.setSemester(setupEmployeeDetailModelList.get(i).getSemester());
                        paEmployeeDetails.add(paEmployeeDetail);
                    }

                    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                    ArrayList<UserRealmModel> usr;
                    usr = userRealmHelper.findAllArticle();
                    ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                    Call<Void> call = apiService.postPAKPIEmployee(paEmployeeDetails, "Bearer " + usr.get(0).getToken());
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

                    //employeeSetupDetailPAAdapter.notifyDataSetChanged();
                }
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
