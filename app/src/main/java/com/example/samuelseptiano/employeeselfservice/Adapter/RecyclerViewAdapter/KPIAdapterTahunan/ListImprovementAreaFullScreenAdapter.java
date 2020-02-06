package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswerList;
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
                    saveDevPlan(context);
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
                        saveDevPlan(context);
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

    public void saveDevPlan(Context ctx){

        UserRealmHelper userRealmHelper = new UserRealmHelper(ctx);
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        List<KPIAnswer> kpiAnswerList = new ArrayList<>();
        KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

        int fgSubmit = 0;
        for (int i = 0; i < kh.getKpiQuestionsList().size(); i++) {
            KPIAnswer kpiAnswer = new KPIAnswer();


            if (kh.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                kpiAnswer.setCOMPID(kh.getKpiQuestionsList().get(i).getKpiId());
                kpiAnswer.setKPIID("0");
            } else {
                kpiAnswer.setKPIID(kh.getKpiQuestionsList().get(i).getKpiId());
                kpiAnswer.setCOMPID("0");
            }

            kpiAnswer.setCP(Integer.toString(kh.getKpiQuestionsList().get(i).getCheckedId()));
            kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
            kpiAnswer.setPAID(kh.getKpiQuestionsList().get(i).getPaId());
            kpiAnswer.setCP(kh.getKpiQuestionsList().get(i).getCP());
            kpiAnswer.setKPITYPE(kh.getKpiQuestionsList().get(i).getKPIcategory());
            kpiAnswer.setSEMESTER(kh.getKpiQuestionsList().get(i).getSemester());
            kpiAnswer.setEVIDENCES(kh.getKpiQuestionsList().get(i).getEvidence());
            kpiAnswer.setACTUAL(kh.getKpiQuestionsList().get(i).getActual());
            kpiAnswer.setTARGET(kh.getKpiQuestionsList().get(i).getTarget());
            kpiAnswerList.add(kpiAnswer);
            //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();
        }
        kpiAnswerList1.setNIKBAWAHAN(kh.getNIK());
        kpiAnswerList1.setPAID(kh.getKpiQuestionsList().get(0).getPaId());
        kpiAnswerList1.setSTATUS(kh.getStatus());
        kpiAnswerList1.setSTRENGTH(kh.getStrength());
        kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
        kpiAnswerList1.setDevPlanHeaderList(kh.getDevPlanHeaderList());


        ApiInterface apiService = ApiClient.getClient(ctx).create(ApiInterface.class);
        Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
        call.enqueue(new Callback<KPIAnswerList>() {
            @Override
            public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                Toast.makeText(ctx, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                Toast.makeText(ctx, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
