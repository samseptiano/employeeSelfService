package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.DevelopmentPlanSubDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quintus Labs on 15/11/18.
 * www.quintuslabs.com
 */
public class ListImprovementAreaFullScreenAdapter extends RecyclerView.Adapter<ListImprovementAreaFullScreenAdapter.ViewHolder> {

    Context context;
    List<DevPlanHeader> steps;
    KPIHeader kh = new KPIHeader();
    String isEditable;
    String empPhoto,empName,jobTitle;

    public ListImprovementAreaFullScreenAdapter(List<DevPlanHeader> steps, KPIHeader kh, Context context, String isEditable,String empPhoto,String empName,String jobTitle) {
        this.steps = steps;
        this.context = context;
        this.kh = kh;
        this.isEditable = isEditable;
        this.empName = empName;
        this.empPhoto = empPhoto;
        this.jobTitle = jobTitle;

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
                    showIDPDialog(empPhoto, empName, jobTitle,holder.btnIdp.getText().toString(), kh.getDevPlanHeaderList().get(i).getDevPlanDetail(), i);

            }
        });
        holder.cbDevPlan.setChecked(false);
        holder.cbDevPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    steps.get(i).setChecked(true);
                }
                else{
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


    private void showIDPDialog(String empPhotos, String empNames, String jobTitles, String idpTitle,List<DevPlanDetail> devPlanDetailList, int position){
        Dialog dialogs = new Dialog(context);

        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.idp_dialog_fullscreen);
        dialogs.setTitle("Title...");
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialogs.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
        ImageView empPhoto =  dialogs.findViewById(R.id.imgEmpPhoto);
        TextView empName =  dialogs.findViewById(R.id.tv_employeeName);
        TextView jobTitle =  dialogs.findViewById(R.id.tv_dept);


        MaterialSpinner spinnerImprovement = dialogs.findViewById(R.id.spinnerImprovement);
        spinnerImprovement.setBackgroundResource(R.drawable.shapesignup);
        spinnerImprovement.setPadding(25, 10, 25, 10);
        List<View> lnList = new ArrayList<>();


        jobTitle.setText(jobTitles);
        empName.setText(empNames);


        tvIdpTitle.setText(idpTitle);
        List<String> improvement = new ArrayList<String>();
        improvement.add("- Pilih Development Plan -");
        for(int x=0;x<kh.getMasterDevelopmentPlanList().size();x++){
            improvement.add(kh.getMasterDevelopmentPlanList().get(x).getDevplanDesc());
        }
        spinnerImprovement.setItems(improvement);

        ListDevPlanAdapter listDevPlanAdapter = new ListDevPlanAdapter(devPlanDetailList,idpTitle, context);


        LinearLayoutManager llm = new LinearLayoutManager(context);

        //Setting the adapter
        rvDevPlan.setAdapter(listDevPlanAdapter);
        rvDevPlan.setLayoutManager(llm);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!spinnerImprovement.getText().toString().equals("- Pilih Development Plan -")) {
                    DevPlanDetail devPlanDetail = new DevPlanDetail();
                    devPlanDetail.setDevplanMethodDesk(spinnerImprovement.getText().toString());
                    devPlanDetail.setDEVID(steps.get(position).getCOMPID());
                    devPlanDetail.setPAID(steps.get(position).getPAID());
                    devPlanDetail.setDEVPLANSTATUS(kh.getStatus());
                    devPlanDetail.setDEVPLANMETHODID(Integer.toString(spinnerImprovement.getSelectedIndex() + 1));
                    //Toast.makeText(context,steps.get(position).getCOMPID(),Toast.LENGTH_LONG).show();
                    devPlanDetailList.add(devPlanDetail);
                    listDevPlanAdapter.notifyDataSetChanged();
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
