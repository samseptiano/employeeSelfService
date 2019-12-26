package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.SetupPA;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.DevelopmentPlanAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.EmployeeSetupPAAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.PositionSetupPAAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.UserListDevPlan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupPositionModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetupPAKuantitatifDBFragment extends Fragment {

    View rootView;
    RecyclerView rvSetupEmployee;
    RecyclerView rvSetupPosition;


    LinearLayout lnSetupPosition;
    MaterialSpinner spinnerSetup;
    List<SetupEmployeeModel> setupEmployeeModelList = new ArrayList<>();
    List<SetupPositionModel> setupPositionModelList = new ArrayList<>();

    EmployeeSetupPAAdapter employeeSetupPAAdapter;
    PositionSetupPAAdapter positionSetupPAAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_setup_pakuantitatif_db, container, false);
        rvSetupEmployee = rootView.findViewById(R.id.rv_setup_employee);
        rvSetupPosition = rootView.findViewById(R.id.rv_setup_position);

        spinnerSetup = rootView.findViewById(R.id.spinnerSetup);
        lnSetupPosition= rootView.findViewById(R.id.lnSetupPosition);

        spinnerSetup.setBackgroundResource(R.drawable.shapedropdown);
        spinnerSetup.setPadding(25, 10, 25, 10);
        List<String> setup = new ArrayList<String>();
        setup.add("EMPLOYEE");
        setup.add("POSITION");
        spinnerSetup.setItems(setup);

        spinnerSetup.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(spinnerSetup.getText().toString().equals("EMPLOYEE")){
                    rvSetupEmployee.setVisibility(View.VISIBLE);
                    prepareDataEmployee();
                    lnSetupPosition.setVisibility(View.GONE);
                }
                else{
                    prepareDataPosition();
                    rvSetupEmployee.setVisibility(View.GONE);
                    lnSetupPosition.setVisibility(View.VISIBLE);

                }
            }
        });
        prepareDataEmployee();
        return rootView;
    }

    private void prepareDataEmployee() {
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        for(int i=0;i<10;i++){
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
            if(i%2==0){
                ka.setStatus1("Not Approved");
            }
            else {
                ka.setStatus1("Approved");
            }
            setupEmployeeModelList.add(ka);

            employeeSetupPAAdapter = new EmployeeSetupPAAdapter(setupEmployeeModelList,getContext(),getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSetupEmployee.setLayoutManager(mLayoutManager);
            rvSetupEmployee.setItemAnimator(new DefaultItemAnimator());
            rvSetupEmployee.setAdapter(employeeSetupPAAdapter);

        }


    }

    private void prepareDataPosition() {
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        for(int i=0;i<10;i++){
            SetupPositionModel setupPositionModel = new SetupPositionModel();
           setupPositionModel.setId(Integer.toString(i));
            setupPositionModel.setPositionName("Position "+i);
            setupPositionModel.setYear("2019");
            setupPositionModelList.add(setupPositionModel);

            positionSetupPAAdapter = new PositionSetupPAAdapter(setupPositionModelList,getContext(),getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSetupPosition.setLayoutManager(mLayoutManager);
            rvSetupPosition.setItemAnimator(new DefaultItemAnimator());
            rvSetupPosition.setAdapter(positionSetupPAAdapter);

        }


    }

}
