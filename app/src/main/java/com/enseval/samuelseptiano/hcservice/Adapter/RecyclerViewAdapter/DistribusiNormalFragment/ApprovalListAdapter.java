package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalFragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ApprDNModell;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.List;

public class ApprovalListAdapter extends RecyclerView.Adapter<ApprovalListAdapter.MyViewHolder> {

    private List<ApprDNModell> apprDNModellList;
    private Context context;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvApproval,tvApprovalName,tvDate,tvStatus;
        public MyViewHolder(View view) {
            super(view);
            tvApproval = view.findViewById(R.id.tvApproval);
            tvApprovalName = view.findViewById(R.id.tvApprovalName);
            tvDate = view.findViewById(R.id.tvDate);
            tvStatus = view.findViewById(R.id.tvStatus);
        }

    }


    public ApprovalListAdapter(List<ApprDNModell> apprDNModellList, Context context, Boolean isConnected, Activity activity) {
        this.context = context;
        this.apprDNModellList = apprDNModellList;
        this.isConnected = isConnected;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approval_list_dn_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ApprDNModell apprDNModell = apprDNModellList.get(position);

        holder.tvApproval.setText("Approval "+position+1);
        holder.tvApprovalName.setText(apprDNModell.getApprName());
        holder.tvStatus.setText(apprDNModell.getApprStatus());
        holder.tvDate.setText(apprDNModell.getApprDate());
    }

    @Override
    public int getItemCount() {
        return apprDNModellList.size();
    }


}