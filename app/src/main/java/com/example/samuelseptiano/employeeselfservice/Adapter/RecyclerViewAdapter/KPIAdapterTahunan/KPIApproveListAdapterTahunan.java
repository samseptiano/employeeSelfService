package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.TotalFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
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
    EventListener listener;

    String tahun,direktorat,site;


    public interface EventListener {
        void onEvent(int position, String Tahun, String Direktorat, String Site, String Semester);
        String getTahun();
        String getDirektorat();
        String getSite();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvNIK, tvDept, tvjobtitle, tvScore;
        ImageView imgStatus;
        RatingBar rating;
        LinearLayout lnApprove;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_employeeName);
            tvNIK = (TextView) view.findViewById(R.id.tv_nik);
            tvDept = (TextView) view.findViewById(R.id.tv_dept);
            tvjobtitle = (TextView) view.findViewById(R.id.tv_job_title);
            tvScore = (TextView) view.findViewById(R.id.tv_score);
            imgStatus = (ImageView) view.findViewById(R.id.imgStatus);
            lnApprove = (LinearLayout) view.findViewById(R.id.lnKPIApprove);
            rating = view.findViewById(R.id.ratingScore);
        }
    }

    public KPIApproveListAdapterTahunan(List<KPIApproveList> KPIList, Context context, Activity activity, EventListener listener) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.listener = listener;
        this.KPIListFilter.addAll(KPIList);
        tahun = listener.getTahun();
        direktorat = listener.getDirektorat();
        site = listener.getSite();
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
        UserList userList = new UserList();
        userList.setEmpNiK(klist.getNIK());
        userList.setOrgName(klist.getDept());
        userList.setEmpName(klist.getEmpName());
        userList.setJobTitleName(klist.getJobTitle());
        userList.setNamaAtasanLangsung(klist.getNamaAtasan1());
        userList.setNamaAtasanTakLangsung(klist.getNamaAtasan2());
        userList.setNIKAtasanLangsung(klist.getNIKAtasan1());
        userList.setNIKAtasanTakLangsung(klist.getNIKAtasan2());

//        Toast.makeText(context,listener.getTahun()+" "+listener.getDirektorat()+" "+listener.getSite(),Toast.LENGTH_SHORT).show();

        userList.setLocationName("EPM - Makassar");
        userList.setCompanyName("PT. Enseval Putera Megatrading Tbk");

        holder.tvName.setText(klist.getEmpName());
        holder.tvScore.setText(klist.getScore());

        holder.tvjobtitle.setText(klist.getJobTitle());
        holder.rating.setRating((Float.parseFloat(klist.getStar())));
        holder.tvDept.setText(klist.getDept()+" - "+klist.getBranchName()+" - "+klist.getJobStatus());
        holder.tvNIK.setText(klist.getNIK());
        if(klist.getJobStatus().equals("Tetap")){
            holder.imgStatus.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgStatus.setVisibility(View.GONE);
        }
        holder.lnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                routingHomeDetailInterface.routingKPI("Approve PA tahunan",context,userList,"Approve");
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