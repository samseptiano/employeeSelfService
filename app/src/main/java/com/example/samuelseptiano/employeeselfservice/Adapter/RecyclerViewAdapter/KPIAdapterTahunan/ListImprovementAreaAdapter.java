package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.DevelopmentPlanDetailFragment;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Quintus Labs on 15/11/18.
 * www.quintuslabs.com
 */
public class ListImprovementAreaAdapter extends RecyclerView.Adapter<ListImprovementAreaAdapter.ViewHolder> implements ListDevPlanAdapter.EventListener{

    Context context;
    List<DevPlanHeader> steps;
    KPIHeader kh = new KPIHeader();
    String isEditable;
    ArrayList<UserRealmModel> usr;
    String type="";

    boolean isFromIDPLayout=false;

    EventListener listener;

    public interface EventListener {
        boolean getFromIDPLayout();
        void setFromIDPLayout(boolean isFromIDPLayout);
    }

    public ListImprovementAreaAdapter(List<DevPlanHeader> steps, KPIHeader kh, Context context, String isEditable,String type, EventListener listener) {
        this.steps = steps;
        this.context = context;
        this.kh = kh;
        this.isEditable = isEditable;
        this.type=type;
        this.listener = listener;
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
                showIDPDialog(steps.get(i).getCOMPNAME(),kh.getDevPlanHeaderList().get(i).getDevPlanDetail(),i);
            }
        });
//        holder.cbDevPlan.setChecked(false);
        holder.cbDevPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Toast.makeText(context,"cb ke: "+i+" = true",Toast.LENGTH_LONG).show();
                    steps.get(i).setChecked(true);
                }
                else{
                    //Toast.makeText(context,"cb ke: "+i+" = false",Toast.LENGTH_LONG).show();
                    steps.get(i).setChecked(false);

                }
            }
        });

    }

    public List<DevPlanHeader> getStepList() {
        return steps;
    }

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
        return false;
    }



    @Override
    public void setUbah(boolean isUbah) {

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


    public void showIDPDialog(String idpTitle,List<DevPlanDetail> devPlanDetailList, int position){
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
                    ProgressDialog pd = new ProgressDialog(context);
                    pd.setMessage("loading...");
                    pd.setCancelable(false);
                    pd.show();

                    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                    ArrayList<UserRealmModel> usr;
                    usr = userRealmHelper.findAllArticle();

                    SaveKPI(context);

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
                                    pd.dismiss();

                                    KPIKuantitatifTahunanFragment.isFromIDPLAyout=true;

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
                                    pd.dismiss();
                                }
                            });

                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            //  Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();
                            pd.dismiss();

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


 private void SaveKPI(Context ctx){
     UserRealmHelper userRealmHelper = new UserRealmHelper(ctx);
     usr = userRealmHelper.findAllArticle();
     if (type.equals("Approve")) {
         List<KPIAnswer> kpiAnswerList = new ArrayList<>();
         KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

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
             kpiAnswer.setKPITYPE(kh.getKpiQuestionsList().get(i).getKPIcategory());
             kpiAnswer.setSEMESTER(kh.getKpiQuestionsList().get(i).getSemester());
             kpiAnswer.setEVIDENCES(kh.getKpiQuestionsList().get(i).getEvidence());
             kpiAnswer.setACTUAL(kh.getKpiQuestionsList().get(i).getActual());
             kpiAnswer.setTARGET(kh.getKpiQuestionsList().get(i).getTarget());
             kpiAnswerList.add(kpiAnswer);
             //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();

             kpiAnswerList1.setNIKBAWAHAN(kh.getNIK());
             kpiAnswerList1.setPAID(kh.getKpiQuestionsList().get(0).getPaId());
             kpiAnswerList1.setSTATUS(kh.getStatus());
             kpiAnswerList1.setSTRENGTH(kh.getStrength());
             kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
             kpiAnswerList1.setDevPlanHeaderList(kh.getDevPlanHeaderList());


         }


         if (kh.getStrength().length() < 0 ) {
             Toast.makeText(context,"Strength wajib diisi!!!",Toast.LENGTH_LONG).show();
         } else {
             ApiInterface apiService = ApiClient.getClient(ctx).create(ApiInterface.class);
             Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
             call.enqueue(new Callback<KPIAnswerList>() {
                 @Override
                 public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                     int statusCode = response.code();
                     //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                     Toast.makeText(context, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
                     //pd.dismiss();
//                                            lnProgressBar.setVisibility(View.GONE);
//                                            recyclerViewKPI.setVisibility(View.VISIBLE);
//                                            recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));
                 }

                 @Override
                 public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                     Toast.makeText(context, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
                     //pd.dismiss();
//                                            lnProgressBar.setVisibility(View.GONE);
//                                            recyclerViewKPI.setVisibility(View.VISIBLE);
//                                            recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));


                 }
             });
         }

     } else {


         //lnProgressBar.setVisibility(View.VISIBLE);
         ProgressDialog pd = new ProgressDialog(ctx);
         pd.setMessage("Saving...");
         pd.setCancelable(false);
         pd.show();
         KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

//                    recyclerViewKPI.setVisibility(View.INVISIBLE);
//                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.white));

         List<KPIAnswer> kpiAnswerList = new ArrayList<>();
         Toast.makeText(ctx, "Saved!!", Toast.LENGTH_SHORT).show();

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
             kpiAnswer.setKPITYPE(kh.getKpiQuestionsList().get(i).getKPIcategory());
             kpiAnswer.setSEMESTER(kh.getKpiQuestionsList().get(i).getSemester());
             kpiAnswer.setEVIDENCES(kh.getKpiQuestionsList().get(i).getEvidence());
             kpiAnswer.setACTUAL(kh.getKpiQuestionsList().get(i).getActual());
             kpiAnswer.setTARGET(kh.getKpiQuestionsList().get(i).getTarget());
             kpiAnswerList.add(kpiAnswer);
             //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();


             kpiAnswerList1.setNIKBAWAHAN(kh.getNIK());
             kpiAnswerList1.setPAID(kh.getKpiQuestionsList().get(0).getPaId());
             kpiAnswerList1.setSTATUS(kh.getStatus());
             kpiAnswerList1.setSTRENGTH(kh.getStrength());
             kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
             kpiAnswerList1.setDevPlanHeaderList(kh.getDevPlanHeaderList());
         }


         ApiInterface apiService = ApiClient.getClient(ctx).create(ApiInterface.class);
         Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
         call.enqueue(new Callback<KPIAnswerList>() {
             @Override
             public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                 pd.dismiss();
//                                    lnProgressBar.setVisibility(View.GONE);
//                                    recyclerViewKPI.setVisibility(View.VISIBLE);
//                                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));

                 int statusCode = response.code();
                 //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                 Toast.makeText(context, "Saved!!", Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                 pd.dismiss();
                 Toast.makeText(context, "Saved!!", Toast.LENGTH_SHORT).show();
//                                    lnProgressBar.setVisibility(View.GONE);
//                                    recyclerViewKPI.setVisibility(View.VISIBLE);
//                                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));

//                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                 //Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();

             }
         });
//                        Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();
     }
 }
}
