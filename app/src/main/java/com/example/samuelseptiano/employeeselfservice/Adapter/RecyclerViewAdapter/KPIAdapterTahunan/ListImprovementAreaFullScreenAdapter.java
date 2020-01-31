package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.DevelopmentPlanDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.DevelopmentPlanSubDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIKuantitatifTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.ImportDevPlanModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.UserListDevPlan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import lecho.lib.hellocharts.model.Line;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Quintus Labs on 15/11/18.
 * www.quintuslabs.com
 */
public class ListImprovementAreaFullScreenAdapter extends RecyclerView.Adapter<ListImprovementAreaFullScreenAdapter.ViewHolder> implements ListDevPlanAdapter.EventListener{

    Context context;
    List<DevPlanHeader> steps;
    KPIHeader kh = new KPIHeader();
    String isEditable;
    String empPhoto,empName,jobTitle;

    boolean isUbah;
    Activity activity;

    EventListener listener;

    @Override
    public void onEvent(int position, List<KPIQuestions> questions, int action) {

    }

    @Override
    public void setDetail(List<DevPlanDetail> steps) {

    }

    @Override
    public List<DevPlanDetail> getDetail() {
        return null;
    }

    @Override
    public boolean getUbah() {
        return isUbah;
    }

    @Override
    public void setUbah(boolean isUbah) {
        this.listener.setUbah(isUbah);
    }

    public interface EventListener {
        void onEvent(int position, List<KPIQuestions> questions, int action);

        List<ImageUploadModelPA> onResult();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void redirect(int tabPos);

        String isEditables();

        String getSemster();

        void setQuestion(KPIHeader a, int semester);

        void setEditables(String edit, KPIHeaderPJ kh);

        void setSemester(String semester, KPIHeaderPJ kh);

        KPIHeader getQuestionSmt1();

        KPIHeader getQuestionSmt2();

        void getQuestion();

        boolean getUbah();
        void setUbah(boolean isUbah);
    }

    public ListImprovementAreaFullScreenAdapter(List<DevPlanHeader> steps, KPIHeader kh, Context context, String isEditable, String empPhoto, String empName, String jobTitle, EventListener listener, Activity activity) {
        this.steps = steps;
        this.context = context;
        this.kh = kh;
        this.isEditable = isEditable;
        this.empName = empName;
        this.empPhoto = empPhoto;
        this.jobTitle = jobTitle;
        this.listener = listener;
        this.activity = activity;
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
                showIDPDialog(empPhoto, empName, jobTitle,steps.get(i).getCOMPNAME(), kh.getDevPlanHeaderList().get(i).getDevPlanDetail(), i,"");
                //showIDPDialogSmall(steps.get(i).getCOMPNAME(),kh.getDevPlanHeaderList().get(i).getDevPlanDetail(), i);

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

    public void getData(){
        listener.onEvent(0,kh.getKpiQuestionsList(),0);
    }


    public void showIDPDialog(String empPhotos, String empNames, String jobTitles, String idpTitle,List<DevPlanDetail> devPlanDetailList, int position, String fromGoToIDP){
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
        LinearLayout lnBtnSave;

        lnBtnSave = dialogs.findViewById(R.id.lnBtnSave);
        Toolbar tbDialog = dialogs.findViewById(R.id.toolbar2);
        ImageButton closeButton = (ImageButton) dialogs.findViewById(R.id.ib_close);
        btnAdd = dialogs.findViewById(R.id.btnAdd);
        btnDelete = dialogs.findViewById(R.id.btnDelete);
        TextView tvIdpTitle = dialogs.findViewById(R.id.tvIdpTitle);
        rvDevPlan = dialogs.findViewById(R.id.rv_idp);
        btnSave = dialogs.findViewById(R.id.btnSave);
        btnGoToIdp = dialogs.findViewById(R.id.btnGotoIdp);
        CircleImageView empPhoto =  dialogs.findViewById(R.id.imgEmpPhoto);
        TextView empName =  dialogs.findViewById(R.id.tv_employeeName);
        TextView jobTitle =  dialogs.findViewById(R.id.tv_dept);


        tbDialog.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromGoToIDP.equals("")){
                    dialogs.dismiss();
                }
                else{
                    dialogs.dismiss();
                    activity.onBackPressed();
                }
            }
        });

        dialogs.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if(fromGoToIDP.equals("")){
                        dialogs.dismiss();
                    }
                    else if (!fromGoToIDP.equals("")){
                        dialogs.dismiss();
                        activity.onBackPressed();
                    }

                }
                else if(i == KeyEvent.KEYCODE_DEL) {
                    return false;
                }
                else if(i == KeyEvent.KEYCODE_ENTER) {
                    return false;
                }
                return true;
            }

        });



        MaterialSpinner spinnerImprovement = dialogs.findViewById(R.id.spinnerImprovement);
        spinnerImprovement.setBackgroundResource(R.drawable.shapedropdown);
        spinnerImprovement.setPadding(25, 10, 25, 10);
        List<View> lnList = new ArrayList<>();

        try {
            Picasso.get()
                    .load(empPhotos)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.imgalt)
                    .into(empPhoto);
        }
        catch (Exception e){}

        jobTitle.setText(jobTitles);
        empName.setText(empNames);


        if(listener.isEditables().equals("N")){
            btnAdd.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            spinnerImprovement.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
        }

        tvIdpTitle.setText(idpTitle);
        List<String> improvement = new ArrayList<String>();
        improvement.add("- Pilih Development Plan -");
        for(int x=0;x<kh.getMasterDevelopmentPlanList().size();x++){
            improvement.add(kh.getMasterDevelopmentPlanList().get(x).getDevplanDesc());
        }
        spinnerImprovement.setItems(improvement);

        ListDevPlanAdapter listDevPlanAdapter = new ListDevPlanAdapter(devPlanDetailList,idpTitle, context,"",this);


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
                    devPlanDetail.setCheckedCb(false);
                    devPlanDetail.setDEVPLANMETHODID(Integer.toString(spinnerImprovement.getSelectedIndex()));
                    for(int i=0;i<devPlanDetailList.size();i++) {
                        if (spinnerImprovement.getText().toString().equals(devPlanDetailList.get(i).getDevplanMethodDesk())) {
                            ctr++;
                            Toast.makeText(context,"Anda Sudah memilih Development Plan Ini",Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(ctr==0) {
                        devPlanDetailList.add(devPlanDetail);
//                        ListDevPlanAdapter listDevPlanAdapter = new ListDevPlanAdapter(devPlanDetailList,idpTitle, context,"");
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
                if(fromGoToIDP.equals("")){
                    dialogs.dismiss();
                }
                else{
                    activity.getFragmentManager().popBackStack("KPIKuantitatifFragmentTahunan", 0);
                }
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

    public void showIDPDialogSmall(String idpTitle,List<DevPlanDetail> devPlanDetailList, int position){
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

        ListDevPlanAdapter listDevPlanAdapter = new ListDevPlanAdapter(devPlanDetailList,idpTitle, context,"DIALOG",this);


        LinearLayoutManager llm = new LinearLayoutManager(context);

        //Setting the adapter
        rvDevPlan.setAdapter(listDevPlanAdapter);
        rvDevPlan.setLayoutManager(llm);

        btnGoToIdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();

                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                    ArrayList<UserRealmModel> usr;
                    usr = userRealmHelper.findAllArticle();

                    //SaveKPI(context);

                    ImportDevPlanModel importDevPlanModel = new ImportDevPlanModel();
                    importDevPlanModel.setPaid(kh.getPaId());
                    importDevPlanModel.setPeriode(kh.getTahun());

                    //====================================================
                    ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
                    Call<Void> call2 = apiService.postImportDevPlan(importDevPlanModel,"Bearer "+usr.get(0).getToken());
                    call2.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call2, Response<Void> response2) {

                            ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
                            Call<List<UserListDevPlan>> call = apiService.getuserListDevPlanOne(usr.get(0).getEmpNIK(),kh.getNIK(),"Bearer "+usr.get(0).getToken());
                            call.enqueue(new Callback<List<UserListDevPlan>>() {
                                @Override
                                public void onResponse(Call<List<UserListDevPlan>> call, Response<List<UserListDevPlan>> response) {
                                    int statusCode = response.code();
                                    FragmentManager fm;
                                    Fragment fr;
                                    FragmentTransaction ft;

                                    fr = new DevelopmentPlanDetailFragment();
                                    Bundle bundle3 = new Bundle();
                                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                                    bundle3.putString("empName",kh.getEmpName());
                                    bundle3.putString("empNik", kh.getNIK());
                                    bundle3.putString("jobtitle", kh.getJobTitleName());
                                    bundle3.putString("dept", kh.getDept());
                                    bundle3.putString("GOTOIDP","GOTOIDP");
                                    bundle3.putString("COMPNAME",idpTitle);
                                    bundle3.putString("COMPID", kh.getDevPlanHeaderList().get(position).getCOMPID());
                                    bundle3.putString("hasApprove", response.body().get(0).getFgHasApproveYN());
                                    bundle3.putString("canApprove", response.body().get(0).getFgCanApproveYN());
                                    bundle3.putString("branchName", kh.getBranchName());
                                    bundle3.putString("jobStatus", response.body().get(0).getJobStatus());
                                    bundle3.putString("empPhoto", response.body().get(0).getEmpPhoto());
                                    bundle3.putString("apprseq", response.body().get(0).getAPPRSEQ());

                                    fr.setArguments(bundle3);
                                    fm = ((FragmentActivity)context).getSupportFragmentManager();
                                    ft = fm.beginTransaction();
                                    ft.replace(R.id.fragment_frame, fr);
                                    ft.addToBackStack("DevelopmentPlanDetailFragment");
                                    ft.commit();
                                    Toast.makeText(context, "Development Plan Detail Area", Toast.LENGTH_SHORT).show();

                                }
                                @Override
                                public void onFailure(Call<List<UserListDevPlan>> call, Throwable t) {
                                    //  Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();

                                }
                            });

                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            //  Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();

                        }
                    });
                    //====================================================

                }
                else{
                    Toast.makeText(context,"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                    devPlanDetail.setDEVPLANMETHODID(Integer.toString(spinnerImprovement.getSelectedIndex()));
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
