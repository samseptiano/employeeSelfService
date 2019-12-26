package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.ListDevPlanAdapter;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevelopmentPlanLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DevelopmentPlanSubDetailFragment extends Fragment {

    String title;
    View rootView;


    RecyclerView rvDevPlan;
    List<DevPlanDetail> devPlanDetailList = new ArrayList<>();
    DatePickerDialog  datePickerDialog;
    SimpleDateFormat dateFormatter;


    List<LinearLayout> lnDevplansDetail  = new ArrayList<>();

    IDPLayoutListModel idpLayoutListModel  = new IDPLayoutListModel();

    List<IDPLayoutListModel> listIDP  = new ArrayList<>();
    //=====================================================================

    //============== Detail =========================
    List<Button> btnevplan = new ArrayList<>();
    List<CheckBox> cbDevplan  = new ArrayList<>();
    List<TextView> tvDevplan  = new ArrayList<>();
    List<LinearLayout> lnDevplans  = new ArrayList<>();
    DevelopmentPlanLayoutListModel developmentPlanLayoutListModel =new DevelopmentPlanLayoutListModel();

    List<DevelopmentPlanLayoutListModel> listDevPlan  = new ArrayList<>();
    //==================================================


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            title = bundle.getString("idpTitle");
            devPlanDetailList = (List<DevPlanDetail>) bundle.getSerializable("devPlanDetail");
        }
        rootView =  inflater.inflate(R.layout.fragment_development_plan_sub_detail, container, false);



        final boolean[] input = {false};

        final int[] ctr = {1};


        TextView tvIdpTitle = rootView.findViewById(R.id.tvIdpTitle);
        Button btnAdd = rootView.findViewById(R.id.btnAdd);
        Button btnDelete = rootView.findViewById(R.id.btnDelete);

        MaterialSpinner spinnerImprovement = rootView.findViewById(R.id.spinnerImprovement);
        spinnerImprovement.setBackgroundResource(R.drawable.shapesignup);
        spinnerImprovement.setPadding(25, 10, 25, 10);

        rvDevPlan = rootView.findViewById(R.id.rv_idp);

        ListDevPlanAdapter listDevPlanAdapter = new ListDevPlanAdapter(devPlanDetailList,title, getContext(),"");


        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        //Setting the adapter
        rvDevPlan.setAdapter(listDevPlanAdapter);
        rvDevPlan.setLayoutManager(llm);

        List<String> improvement = new ArrayList<String>();
        improvement.add("Dummy 1");
        improvement.add("Dummy 2");
        improvement.add("Dummy 3");
        spinnerImprovement.setItems(improvement);

        tvIdpTitle.setText(title);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevPlanDetail devPlanDetail = new DevPlanDetail();
                devPlanDetail.setDevplanMethodDesk(spinnerImprovement.getText().toString());
                devPlanDetail.setDEVID("1");
                devPlanDetail.setPAID("2");
                devPlanDetail.setDEVPLANSTATUS("3");
                devPlanDetail.setDEVPLANMETHODID(Integer.toString(spinnerImprovement.getSelectedIndex() + 1));
                //Toast.makeText(context,steps.get(position).getCOMPID(),Toast.LENGTH_LONG).show();
                devPlanDetailList.add(devPlanDetail);
                listDevPlanAdapter.notifyDataSetChanged();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<devPlanDetailList.size();i++){
                    if(devPlanDetailList.get(i).isCheckedCb()){
                        devPlanDetailList.remove(i);
                        listDevPlanAdapter.notifyDataSetChanged();

                    }
                }
            }
        });




        return rootView;
    }


    private void showCalendar (EditText editText, EditText editTexts){

        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                try {
                    editText.setText(dateFormatter.format(newDate.getTime()));
                    editTexts.setText(dateFormatter.format(newDate.getTime()));
                    Toast.makeText(getContext(), "Tanggal dipilih : " + dateFormatter.format(newDate.getTime()), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();

                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


}
