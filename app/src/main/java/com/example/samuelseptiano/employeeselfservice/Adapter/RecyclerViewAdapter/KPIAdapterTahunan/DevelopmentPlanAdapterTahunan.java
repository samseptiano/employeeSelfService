package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.DevelopmentPlanDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIStatusTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DevelopmentPlanAdapterTahunan extends RecyclerView.Adapter<DevelopmentPlanAdapterTahunan.MyViewHolder> {

    private List<KPIApproveList> KPIList;
    private Context context;
    private Activity activity;
    private ArrayList<KPIApproveList> KPIListFilter = new ArrayList<>();

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvjobtitle, tvDept;
        ImageView imgEmpPhoto;
        Button btnIdp;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_employeeName);
            tvDept = (TextView) view.findViewById(R.id.tv_dept);
            tvjobtitle = (TextView) view.findViewById(R.id.tv_job_title);
            imgEmpPhoto = (ImageView) view.findViewById(R.id.imgEmpPhoto);
            btnIdp = view.findViewById(R.id.btnIdp);
        }
    }

    public DevelopmentPlanAdapterTahunan(List<KPIApproveList> KPIList, Context context, Activity activity) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.KPIListFilter.addAll(KPIList);
    }

    @Override
    public DevelopmentPlanAdapterTahunan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.development_plan_employee_item, parent, false);

        return new DevelopmentPlanAdapterTahunan.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DevelopmentPlanAdapterTahunan.MyViewHolder holder, int position) {
        KPIApproveList kpiApproveList = KPIList.get(position);
        holder.tvName.setText(kpiApproveList.getEmpName());
        holder.tvjobtitle.setText(kpiApproveList.getJobTitle());
        holder.tvName.setText(kpiApproveList.getEmpName());
        holder.tvDept.setText(kpiApproveList.getDept()+" - "+kpiApproveList.getBranchName()+" - "+kpiApproveList.getJobStatus());
        holder.btnIdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new DevelopmentPlanDetailFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    bundle3.putString("empName", kpiApproveList.getEmpName());
                    bundle3.putString("jobtitle", kpiApproveList.getJobTitle());
                    bundle3.putString("dept", kpiApproveList.getDept());
                    bundle3.putString("branchName", kpiApproveList.getBranchName());
                    bundle3.putString("jobStatus", kpiApproveList.getJobStatus());
                    bundle3.putString("empPhoto", kpiApproveList.getEmpPhoto());
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity)context).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("DevelopmentPlanDetailFragment");
                    ft.commit();
                    Toast.makeText(context, "Development Plan Detail Area", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return KPIList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        KPIList.clear();
        if (charText.length() == 0) {
            KPIList.addAll(KPIListFilter);
        }
        else
        {
            for (KPIApproveList wp : KPIListFilter) {
                if (wp.getEmpName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getNIK().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStatus1().toLowerCase(Locale.getDefault()).contains(charText)) {
                    KPIList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}