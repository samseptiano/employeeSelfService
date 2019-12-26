package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Quintus Labs on 15/11/18.
 * www.quintuslabs.com
 */
public class ListImprovementAreaAdapter extends RecyclerView.Adapter<ListImprovementAreaAdapter.ViewHolder> {

    Context context;
    List<DevPlanHeader> steps;
    KPIHeader kh = new KPIHeader();
    String isEditable;

    public ListImprovementAreaAdapter(List<DevPlanHeader> steps, KPIHeader kh, Context context, String isEditable) {
        this.steps = steps;
        this.context = context;
        this.kh = kh;
        this.isEditable = isEditable;
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.improvement_area_development_plan, viewGroup, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        holder.setIsRecyclable(false);
        if(isEditable.equals("N")){
            holder.btnIdp.setEnabled(false);
        }
        else{
            holder.btnIdp.setEnabled(true);
        }
        int x = holder.getLayoutPosition();
        holder.tvNumber.setText((i+1)+". ");
        holder.tvDevPlan.setText(steps.get(i).getCOMPNAME());
        holder.btnIdp.setText((i+1)+". "+steps.get(i).getCOMPNAME());
        holder.btnIdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIDPDialog(holder.btnIdp.getText().toString(),kh.getDevPlanHeaderList().get(i).getDevPlanDetail(),i);
            }
        });
//        holder.cbDevPlan.setChecked(false);
        holder.cbDevPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Toast.makeText(context,"cb ke: "+i+" = true",Toast.LENGTH_LONG).show();
                    steps.get(i).setChecked(true);
                }
                else{
                    //Toast.makeText(context,"cb ke: "+i+" = false",Toast.LENGTH_LONG).show();
                    steps.get(i).setChecked(false);

                }
            }
        });

    }

    public List<DevPlanHeader> getStepList() {
        return steps;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbDevPlan;
        TextView tvNumber,tvDevPlan;
        Button btnIdp;
        public ViewHolder(View itemView) {
            super(itemView);
            cbDevPlan = itemView.findViewById(R.id.cbDevPlan);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvDevPlan = itemView.findViewById(R.id.tvDevPlan);
            btnIdp = itemView.findViewById(R.id.btnIdp);
        }

    }


    private void showIDPDialog(String idpTitle,List<DevPlanDetail> devPlanDetailList, int position){
        Dialog dialogs = new Dialog(context);

        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.idp_dialog);
        dialogs.setTitle("Title...");
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);

        Button btnAdd, btnDelete, btnSave, btnGoToIdp;
        RecyclerView rvDevPlan;

        ImageButton closeButton = (ImageButton) dialogs.findViewById(R.id.ib_close);
        btnAdd = dialogs.findViewById(R.id.btnAdd);
        btnDelete = dialogs.findViewById(R.id.btnDelete);
        TextView tvIdpTitle = dialogs.findViewById(R.id.tvIdpTitle);
        rvDevPlan = dialogs.findViewById(R.id.rv_idp);
        btnSave = dialogs.findViewById(R.id.btnSave);
        btnGoToIdp = dialogs.findViewById(R.id.btnGotoIdp);
        MaterialSpinner spinnerImprovement = dialogs.findViewById(R.id.spinnerImprovement);
        spinnerImprovement.setBackgroundResource(R.drawable.shapedropdown);
        spinnerImprovement.setPadding(25, 10, 25, 10);
        List<View> lnList = new ArrayList<>();

        tvIdpTitle.setText(idpTitle);
        List<String> improvement = new ArrayList<String>();
        improvement.add("- Pilih Development Plan -");
        for(int x=0;x<kh.getMasterDevelopmentPlanList().size();x++){
            improvement.add(kh.getMasterDevelopmentPlanList().get(x).getDevplanDesc());
        }
        spinnerImprovement.setItems(improvement);

        ListDevPlanAdapter listDevPlanAdapter = new ListDevPlanAdapter(devPlanDetailList,idpTitle, context,"DIALOG");


        LinearLayoutManager llm = new LinearLayoutManager(context);

        //Setting the adapter
        rvDevPlan.setAdapter(listDevPlanAdapter);
        rvDevPlan.setLayoutManager(llm);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ctr=0;
                if(!spinnerImprovement.getText().toString().equals("- Pilih Development Plan -")) {
                    DevPlanDetail devPlanDetail = new DevPlanDetail();
                    devPlanDetail.setDevplanMethodDesk(spinnerImprovement.getText().toString());
                    devPlanDetail.setDEVID(steps.get(position).getCOMPID());
                    devPlanDetail.setPAID(steps.get(position).getPAID());
                    devPlanDetail.setDEVPLANSTATUS(kh.getStatus());
                    devPlanDetail.setDEVPLANMETHODID(Integer.toString(spinnerImprovement.getSelectedIndex() + 1));
                    for(int i=0;i<devPlanDetailList.size();i++) {
                        if (spinnerImprovement.getText().toString().equals(devPlanDetailList.get(i).getDevplanMethodDesk())) {
                            ctr++;
                            Toast.makeText(context,"Anda Sudah memilih Development Plan Ini",Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(ctr==0) {
                        devPlanDetailList.add(devPlanDetail);
                        listDevPlanAdapter.notifyDataSetChanged();
                    }

                }
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

        closeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialogs.dismiss();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });

        dialogs.show();
    }
}
