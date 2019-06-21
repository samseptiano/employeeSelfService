package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIApproveListPJ;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KPIApproveListAdapter extends RecyclerView.Adapter<KPIApproveListAdapter.MyViewHolder> {

    private List<KPIApproveListPJ> KPIList;
    private Context context;
    private Activity activity;
    private ArrayList<KPIApproveListPJ> KPIListFilter = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvNIK, tvDept, tvjobtitle, tvStatus,tvStartJoin,tvLastJoin;
        LinearLayout lnApprove;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_employeeName);
            tvNIK = (TextView) view.findViewById(R.id.tv_nik);
            tvDept = (TextView) view.findViewById(R.id.tv_dept);
            tvjobtitle = (TextView) view.findViewById(R.id.tv_job_title);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            lnApprove = (LinearLayout) view.findViewById(R.id.lnKPIApprove);
            tvLastJoin = (TextView) view.findViewById(R.id.tv_last_join);
            tvStartJoin = (TextView) view.findViewById(R.id.tv_start_join);
        }
    }

    public KPIApproveListAdapter(List<KPIApproveListPJ> KPIList, Context context, Activity activity) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.KPIListFilter.addAll(KPIList);
    }

    @Override
    public KPIApproveListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kpi_approve_item_list, parent, false);

        return new KPIApproveListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KPIApproveListAdapter.MyViewHolder holder, int position) {
        KPIApproveListPJ klist = KPIList.get(position);

        holder.tvName.setText(klist.getEmpName());

        if(klist.getStatus().equals("Approved")){
            holder.tvStatus.setText(klist.getStatus());
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
        else {
            holder.tvStatus.setText(klist.getStatus());
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));

        }
        holder.tvStartJoin.setText("Periode: "+klist.getTanggalMasaKontrakAwal());
        holder.tvLastJoin.setText(klist.getTanggalMasaKontrakAkhir());
        holder.tvjobtitle.setText(klist.getJobTitle());
        holder.tvDept.setText(klist.getDept());
        holder.tvNIK.setText(klist.getNIK());
        holder.lnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                routingHomeDetailInterface.routingKPI("Approve PA",context,klist.getNIK(),"Approve");
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
            for (KPIApproveListPJ wp : KPIListFilter) {
                if (wp.getEmpName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getNIK().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStatus().toLowerCase(Locale.getDefault()).contains(charText)) {
                    KPIList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}