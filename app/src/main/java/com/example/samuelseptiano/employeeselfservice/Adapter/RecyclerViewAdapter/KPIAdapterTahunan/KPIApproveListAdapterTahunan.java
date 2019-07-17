package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;



import android.app.Activity;
import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KPIApproveListAdapterTahunan extends RecyclerView.Adapter<KPIApproveListAdapterTahunan.MyViewHolder> {

    private List<KPIApproveList> KPIList;
    private Context context;
    private Activity activity;
    private ArrayList<KPIApproveList> KPIListFilter = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvNIK, tvDept, tvjobtitle, tvStatus;
        LinearLayout lnApprove;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_employeeName);
            tvNIK = (TextView) view.findViewById(R.id.tv_nik);
            tvDept = (TextView) view.findViewById(R.id.tv_dept);
            tvjobtitle = (TextView) view.findViewById(R.id.tv_job_title);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            lnApprove = (LinearLayout) view.findViewById(R.id.lnKPIApprove);
        }
    }

    public KPIApproveListAdapterTahunan(List<KPIApproveList> KPIList, Context context, Activity activity) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.KPIListFilter.addAll(KPIList);
    }

    @Override
    public KPIApproveListAdapterTahunan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kpi_approve_item_list_tahunan, parent, false);

        return new KPIApproveListAdapterTahunan.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KPIApproveListAdapterTahunan.MyViewHolder holder, int position) {
        KPIApproveList klist = KPIList.get(position);

        holder.tvName.setText(klist.getEmpName());

        if(klist.getStatus1().equals("Approved")){
            holder.tvStatus.setText(klist.getStatus1());
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
        else {
            holder.tvStatus.setText(klist.getStatus1());
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));

        }
        holder.tvjobtitle.setText(klist.getJobTitle());
        holder.tvDept.setText(klist.getDept());
        holder.tvNIK.setText(klist.getNIK());
        holder.lnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                routingHomeDetailInterface.routingKPI("Approve PA tahunan",context,klist.getNIK(),"Approve");
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