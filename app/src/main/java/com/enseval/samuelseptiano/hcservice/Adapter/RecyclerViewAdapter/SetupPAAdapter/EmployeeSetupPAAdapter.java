package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPAAdapter;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.EmployeeSetupDetailPAFragment;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeSetupPAAdapter extends RecyclerView.Adapter<EmployeeSetupPAAdapter.MyViewHolder> {

    private List<SetupEmployeeModel> KPIList;
    private Context context;
    private Activity activity;
    private ArrayList<SetupEmployeeModel> KPIListFilter = new ArrayList<>();

    String KPIType;

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    List<UserRealmModel> usr = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvjobtitle,tvBobotAlert;
        CircleImageView imgEmpPhoto;
        Button btnEdit;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_employeeName);
            tvjobtitle = (TextView) view.findViewById(R.id.tv_job_title);
            tvBobotAlert = (TextView) view.findViewById(R.id.tv_bobotAlert);
            imgEmpPhoto =  view.findViewById(R.id.imgEmpPhoto);
            btnEdit = view.findViewById(R.id.btnEdit);
        }
    }

    public EmployeeSetupPAAdapter(List<SetupEmployeeModel> KPIList, Context context, Activity activity,String KPIType) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.KPIListFilter.addAll(KPIList);
        this.KPIType = KPIType;
    }

    @Override
    public EmployeeSetupPAAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setup_pa_kuantitatif_employee_item, parent, false);

        return new EmployeeSetupPAAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmployeeSetupPAAdapter.MyViewHolder holder, int position) {
        SetupEmployeeModel kpiApproveList = KPIList.get(position);
        UserRealmHelper userRealmHelper = new UserRealmHelper(context);
        usr = userRealmHelper.findAllArticle();

        if(kpiApproveList.getNIK().equals(usr.get(0).getEmpNIK())) {
            holder.tvName.setText(kpiApproveList.getEmpName()+" (SELF)");
        }
        else{
            holder.tvName.setText(kpiApproveList.getEmpName());

        }
        holder.tvjobtitle.setText(kpiApproveList.getBranchName());


        Picasso.get()
                .load(kpiApproveList.getEmpPhoto())
                .placeholder(R.drawable.user)
                .error(R.drawable.imgalt)
                .into(holder.imgEmpPhoto);
        try {
            if (!kpiApproveList.getBobotTotal().equals("100")) {
                holder.tvBobotAlert.setText(kpiApproveList.getBobotTotal() + "%");
                holder.tvBobotAlert.setTextColor(context.getResources().getColor(R.color.red));

            } else {
                holder.tvBobotAlert.setText(kpiApproveList.getBobotTotal() + "%");
                holder.tvBobotAlert.setTextColor(context.getResources().getColor(R.color.black));
            }
        }catch(Exception e){
            holder.tvBobotAlert.setText("0%");
            holder.tvBobotAlert.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new EmployeeSetupDetailPAFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    bundle3.putString("empName", kpiApproveList.getEmpName());
                    bundle3.putString("NIK", kpiApproveList.getId());
                    bundle3.putString("paid", kpiApproveList.getPaid());
                    bundle3.putString("empNik", kpiApproveList.getNIK());
                    bundle3.putString("jobTitle", kpiApproveList.getJobTitle());
                    bundle3.putString("dept", kpiApproveList.getDept());
                    bundle3.putString("branchName", kpiApproveList.getBranchName());
                    bundle3.putString("jobStatus", kpiApproveList.getJobStatus());
                    bundle3.putString("empPhoto", kpiApproveList.getEmpPhoto());
                    bundle3.putString("KPIType", KPIType);

                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity)context).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("EmployeeSetupDetailPAFragment");
                    ft.commit();
                    Toast.makeText(context, "Employee Detail Setup Area", Toast.LENGTH_SHORT).show();
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
            for (SetupEmployeeModel wp : KPIListFilter) {
                if (wp.getEmpName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getJobTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    KPIList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}