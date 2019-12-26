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
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeDetailModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupPositionModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class EmployeeSetupDetailPAFragment extends Fragment implements EmployeeSetupDetailPAAdapter.EventListener{

    View rootView;
    RecyclerView rvSetupEmployeeDetail;
    TextView tvEmpName,tvJobTitle;
    CircleImageView imgEmpPhoto;
    Button btnAdd,btnDelete;

    List<SetupEmployeeDetailModel> setupEmployeeDetailModelList = new ArrayList<>();

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
        String empName = "", jobTitle="", dept, branchName, jobStatus, empPhoto="", empNik;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            empNik = bundle.getString("empNik");
            empName = bundle.getString("empName");
            jobTitle = bundle.getString("jobTitle");
            dept = bundle.getString("dept");
            jobStatus = bundle.getString("jobStatus");
            branchName = bundle.getString("branchName");
            empPhoto = bundle.getString("empPhoto");
        }
        tvEmpName = rootView.findViewById(R.id.tv_employeeName);
        tvJobTitle = rootView.findViewById(R.id.tv_job_title);
        imgEmpPhoto = rootView.findViewById(R.id.imgEmpPhoto);
        btnAdd = rootView.findViewById(R.id.btnAddKPI);
        btnDelete = rootView.findViewById(R.id.btnDelKPI);


        tvEmpName.setText(empName);
        tvJobTitle.setText(jobTitle);

        Picasso.get()
                .load(empPhoto)
                .placeholder(R.drawable.user)
                .error(R.drawable.imgalt)
                .into(imgEmpPhoto);

        rvSetupEmployeeDetail = rootView.findViewById(R.id.rv_setup_emp_pa);
        prepareDataEmployeePA();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<setupEmployeeDetailModelList.size();i++){
                    if(setupEmployeeDetailModelList.get(i).isChecked()){
                        setupEmployeeDetailModelList.remove(i);
                    }
                }
                employeeSetupDetailPAAdapter.notifyDataSetChanged();
            }
        });



        return  rootView;
    }

    private void prepareDataEmployeePA() {
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        for(int i=0;i<30;i++){
            SetupEmployeeDetailModel ka = new SetupEmployeeDetailModel();
            ka.setPaId(Integer.toString(i));
            ka.setBobot("15%");
            ka.setId(Integer.toString(i));
            ka.setEmpJobTitle("jobtitle");
            ka.setKPIDesc("Lorem Imsum");
            ka.setKPIType("KUANTITATIF");
            ka.setKPIperspective("KUANTITATIF");
            if(i>20){
                ka.setSemester("2");

            }
            else{
                ka.setSemester("1");
            }
            setupEmployeeDetailModelList.add(ka);

            employeeSetupDetailPAAdapter = new EmployeeSetupDetailPAAdapter(setupEmployeeDetailModelList,getContext(),getActivity(),this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
            rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
            rvSetupEmployeeDetail.setAdapter(employeeSetupDetailPAAdapter);

        }


    }

    @Override
    public List<SetupEmployeeDetailModel> getsetupEmployeeDetailModels() {
        return setupEmployeeDetailModelList;
    }

    @Override
    public void setsetupEmployeeDetailModels(List<SetupEmployeeDetailModel> setupEmployeeDetailModelList) {
        this.setupEmployeeDetailModelList = setupEmployeeDetailModelList;
    }
    private void showAddDialog(){
        Dialog dialogs = new Dialog(getContext());

        final String[] semester = {"1"};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.add_kpi_kuantiatif_dialog);
        dialogs.setTitle("Title...");
        TextView tvTitle;
        EditText edtBobot,edtKPIDesc,edtHint1,edtHint2,edtHint3,edtHint4,edtHint5;
        Button btnSaveKPI;
        MaterialSpinner spinnerSemester;
        ImageButton btnClose;

//        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);

        tvTitle = dialogs.findViewById(R.id.tvTitle);
        btnSaveKPI = dialogs.findViewById(R.id.btnSaveKPI);

        btnClose = dialogs.findViewById(R.id.ib_close);
        spinnerSemester = dialogs.findViewById(R.id.spinnerSemester);
        edtBobot = dialogs.findViewById(R.id.edtBobot);
        edtKPIDesc = dialogs.findViewById(R.id.edtKPIDesc);

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

        spinnerSemester.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(spinnerSemester.getText().toString().equals("SMT 1")){
                    semester[0] ="1";
                }
                else{
                    semester[0] ="2";
                }
            }
        });
        btnSaveKPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> aaa = new ArrayList<>();
                aaa.add(edtHint1.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());


                SetupEmployeeDetailModel setupEmployeeDetailModel = new SetupEmployeeDetailModel();
                setupEmployeeDetailModel.setChecked(false);
                setupEmployeeDetailModel.setSemester( semester[0]);
                setupEmployeeDetailModel.setKPIType("KUANTITATIF");
                setupEmployeeDetailModel.setKPIDesc(edtKPIDesc.getText().toString());
                setupEmployeeDetailModel.setBobot(edtBobot.getText().toString());
                List<KPIHint> kpiHints = new ArrayList<>();
                for(int i=0;i<5;i++) {
                    KPIHint kpiHint = new KPIHint();
                    kpiHint.setKpiGradeID(i+"");
                    kpiHint.setKpiID(i+"");
                    kpiHint.setKpiGradeCode(i+"");
                    kpiHint.setKpiGradeName(aaa.get(i));
                    kpiHints.add(kpiHint);
                }

                setupEmployeeDetailModel.setKpiHintList(kpiHints);

                setupEmployeeDetailModelList.add(setupEmployeeDetailModel);
                employeeSetupDetailPAAdapter.notifyDataSetChanged();
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
