package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIApproveListPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
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
    ArrayList<UserRealmModel> usr;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPosition, tvDept, tvjobtitle, tvStatus,tvStartJoin,tvLastJoin;
        LinearLayout lnApprove;
        ImageView empPhoto;


        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_employeeName);
            tvPosition = (TextView) view.findViewById(R.id.tv_position);
            tvDept = (TextView) view.findViewById(R.id.tv_dept);
            tvjobtitle = (TextView) view.findViewById(R.id.tv_job_title);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            lnApprove = (LinearLayout) view.findViewById(R.id.lnKPIApprove);
            tvLastJoin = (TextView) view.findViewById(R.id.tv_last_join);
            tvStartJoin = (TextView) view.findViewById(R.id.tv_start_join);
            tvStartJoin = (TextView) view.findViewById(R.id.tv_start_join);
            empPhoto = (ImageView) view.findViewById(R.id.imgKPIApprove);
        }
    }

    public KPIApproveListAdapter(List<KPIApproveListPJ> KPIList, Context context, Activity activity) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.KPIListFilter.addAll(KPIList);

        UserRealmHelper userRealmHelper = new UserRealmHelper(context);
        usr = userRealmHelper.findAllArticle();
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
        UserList userList = new UserList();
        userList.setEmpNiK(klist.getNIK());
        userList.setOrgName(klist.getDept());
        userList.setJobTitleName(klist.getJobTitle());
        userList.setEmpName(klist.getEmpName());
        userList.setNIKAtasanTakLangsung(klist.getNIKAtasan2());
        userList.setNIKAtasanLangsung(klist.getNIKAtasan());
        userList.setNamaAtasanLangsung(klist.getNamaAtasan());
        userList.setNamaAtasanTakLangsung(klist.getNamaAtasan2());

        //======================================
//        Toast.makeText(context,klist.getNIKAtasan(),Toast.LENGTH_LONG).show();
//        Toast.makeText(context,klist.getNIKAtasan2(),Toast.LENGTH_LONG).show();
        //======================================

        userList.setDateStart(klist.getTanggalMasaKontrakAwal());
        userList.setDateEnd(klist.getTanggalMasaKontrakAkhir());
        userList.setFotoAtasanLangsung(klist.getFotoAtasan());
        userList.setFotoAtasanTakLangsung(klist.getFotoAtasan2());
        userList.setPositionName(klist.getPositionName());
        userList.setStatus(klist.getStatus());
        userList.setStatus1(klist.getStatus1());
        userList.setStatus2(klist.getStatus2());

        holder.tvName.setText(klist.getEmpName());

        if(klist.getStatus().equals("2") && usr.get(0).getEmpNIK().equals(klist.getNIKAtasan())){
            holder.tvStatus.setText("Approved");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
        else if(klist.getStatus().equals("3") && usr.get(0).getEmpNIK().equals(klist.getNIKAtasan2())){
            holder.tvStatus.setText("Approved");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
        else{
            holder.tvStatus.setText("Not Approved");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        holder.tvStartJoin.setText("Periode: "+klist.getTanggalMasaKontrakAwal());
        holder.tvLastJoin.setText(klist.getTanggalMasaKontrakAkhir());
        holder.tvjobtitle.setText(klist.getJobTitle());
        holder.tvDept.setText(klist.getDept());
        holder.tvPosition.setText(klist.getPositionName());
        holder.lnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                routingHomeDetailInterface.routingKPI("Approve PA",context,userList,"Approve");
            }
        });

        try {
            byte [] encodeByte=Base64.decode(klist.getEmpPhoto(),Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if(size.x >1081 ){
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                //Point size = new Point();
                display.getSize(size);
                int width = size.x - (size.x/3);
                int height = size.y - (size.y/3);

                int newWidth = width; //this method should return the width of device screen.
                float scaleFactor = (float)newWidth/(float)imageWidth;
                int newHeight = (int)(imageHeight * scaleFactor);

                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
            }
            else{
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                //Point size = new Point();
                display.getSize(size);
                int width = size.x - (size.x/3);
                int height = size.y - (size.y/3);

                int newWidth = width; //this method should return the width of device screen.
                float scaleFactor = (float)newWidth/(float)imageWidth;
                int newHeight = (int)(imageHeight * scaleFactor);

                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
            }

           holder.empPhoto.setImageBitmap(bitmap);
        } catch(Exception e) {
            e.getMessage();
            //return null;
        }

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
                if (wp.getEmpName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getNIK().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStatus1().toLowerCase(Locale.getDefault()).contains(charText)) {
                    KPIList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}