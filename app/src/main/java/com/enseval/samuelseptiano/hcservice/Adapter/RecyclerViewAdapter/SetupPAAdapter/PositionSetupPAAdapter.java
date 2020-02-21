package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPAAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.KPISettingSettingResponse;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.PositionSetupDetailPAFragment;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobTtlModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpOrgModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingSetting;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.SetupPositionModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PositionSetupPAAdapter extends RecyclerView.Adapter<PositionSetupPAAdapter.MyViewHolder> {

    private List<PASettingHeader> paSettingHeaderList;
    private List<PASettingHeader> paSettingHeaderListFilter = new ArrayList<>();

    private List<EmpOrgModel>   empOrgModelList;
    private List<EmpJobTtlModel> empJobTtlModelList;

    private Context context;
    private Activity activity;



    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    EventListener listener;

    public interface EventListener {
        List <SetupPositionModel> getSetupPositionModels();
        void setSetupPositionModels(List <SetupPositionModel> setupPositionModelList);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvtemplateName, tvAlertBobot, tvYear,tvStatusDeploy;
        Button btnSetting;

        CheckBox cbPosition;
        LinearLayout lnTemp;

        public MyViewHolder(View view) {
            super(view);
            tvtemplateName = (TextView) view.findViewById(R.id.tv_templateName);
            tvAlertBobot = (TextView) view.findViewById(R.id.tv_bobotAlert);
            tvYear = (TextView) view.findViewById(R.id.tv_year);
            btnSetting = view.findViewById(R.id.btnSetting);
            cbPosition = view.findViewById(R.id.cbPosition);
            lnTemp = view.findViewById(R.id.lnTempName);
            tvStatusDeploy = view.findViewById(R.id.tv_StatusDeploy);

        }
    }

    public PositionSetupPAAdapter(List<PASettingHeader> paSettingHeaderList, Context context, Activity activity,EventListener listener,List<EmpJobTtlModel>empJobTtlModelList,List<EmpOrgModel>empOrgModelList) {
        this.context = context;
        this.activity = activity;
        this.listener = listener;
        this.empJobTtlModelList = empJobTtlModelList;
        this.empOrgModelList = empOrgModelList;
        this.paSettingHeaderList = paSettingHeaderList;
        this.paSettingHeaderListFilter.addAll(paSettingHeaderList);
    }

    @Override
    public PositionSetupPAAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setup_pa_kuantitatif_position_item, parent, false);

        return new PositionSetupPAAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PositionSetupPAAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        PASettingHeader setupPositionModel = paSettingHeaderList.get(position);

        float bobot=0;
        for(int i=0;i<setupPositionModel.getPaSettingDetails().size();i++){
            bobot+=Float.parseFloat(setupPositionModel.getPaSettingDetails().get(i).getBobot().replace(",","."));
        }
        if(bobot==100) {
            holder.tvAlertBobot.setText(Float.toString(bobot)+"%");
        }
        else{
            holder.tvAlertBobot.setText(Float.toString(bobot)+"%");
            holder.tvAlertBobot.setTextColor(context.getResources().getColor(R.color.red));
        }
//            if (Float.parseFloat(setupPositionModel.getBobot().replace(",",". ")) != 100) {
//                holder.tvAlertBobot.setText(Float.parseFloat(setupPositionModel.getBobot())+"%");
//                holder.tvAlertBobot.setTextColor(context.getResources().getColor(R.color.red));
//                //holder.cbPosition.setEnabled(false);
//            }
//            else{
//                holder.tvAlertBobot.setText(Float.parseFloat(setupPositionModel.getBobot())+"%");
//                holder.tvAlertBobot.setTextColor(context.getResources().getColor(R.color.black));
//                //holder.cbPosition.setEnabled(true);
//            }
        holder.tvtemplateName.setText(setupPositionModel.getTempKPIName());

        holder.tvtemplateName.setPaintFlags( holder.tvtemplateName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.tvYear.setText(setupPositionModel.getYear());

        holder.tvStatusDeploy.setText(setupPositionModel.getStatusDeployYN());

        holder.cbPosition.setChecked(setupPositionModel.isChecked());

        holder.lnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new PositionSetupDetailPAFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    bundle3.putString("templateName",setupPositionModel.getTempKPIName());
                    bundle3.putString("templateId",setupPositionModel.getTempKPIID());
                    bundle3.putString("KPIType","KUANTITATIF");
                    //bundle3.putSerializable("listSettingHeader",paSettingHeaderList.get(position));

                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity)context).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("PositionSetupDetailPAFragment");
                    ft.commit();
                    Toast.makeText(context, "Position Detail Setup Area", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showSettingDialog(setupPositionModel.getTempKPIName(), position);
                }
                catch (Exception e){
                    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.cbPosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    paSettingHeaderList.get(position).setChecked(true);
                }
                else{
                    paSettingHeaderList.get(position).setChecked(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return paSettingHeaderList.size();
    }


    private void showSettingDialog(String title, int pos){
        Dialog dialogs = new Dialog(context);

        final String[] semester = {"1"};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.setup_position_setting_dialog);
        dialogs.setTitle("Title...");

        TextView tvTitle;
        TagsEditText tgOrganisasi, tgJabatan;
        Button btnSave;
        ImageButton btnClose;

        List<String> aaa= new ArrayList<>();
        for (int i=0;i<empOrgModelList.size();i++){
            aaa.add(empOrgModelList.get(i).getOrgName());
        }

        tvTitle = dialogs.findViewById(R.id.tvTemplateName);
        btnSave = dialogs.findViewById(R.id.btnSaveSetting);
        tgOrganisasi = dialogs.findViewById(R.id.tagsOrganisasi);
        tgOrganisasi.setHint("Type Organization Name");
        //mTagsEditText.setTagsListener(MainActivity.this);
        tgOrganisasi.setTagsWithSpacesEnabled(true);
        tgOrganisasi.setAdapter(new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, aaa));
        tgOrganisasi.setThreshold(1);



        List<String> bbb= new ArrayList<>();
        for (int i=0;i<empJobTtlModelList.size();i++){
            bbb.add(empJobTtlModelList.get(i).getJobttlname());
        }

        tgJabatan = dialogs.findViewById(R.id.tagsJabatan);
        tgJabatan.setHint("Type Job Title Name");
        //mTagsEditText.setTagsListener(MainActivity.this);
        tgJabatan.setTagsWithSpacesEnabled(true);
        tgJabatan.setAdapter(new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, bbb));
        tgJabatan.setThreshold(1);

        btnClose = dialogs.findViewById(R.id.ib_close);
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        try{

            List<String>aa = new ArrayList<>();
            List<String>bb = new ArrayList<>();
            for (int j=0;j<paSettingHeaderList.get(pos).getPaSettingSettings().size();j++){
                //Toast.makeText(getContext(),response.body().get(i).getPaSettingSettings().get(j).getJobtitleName(),Toast.LENGTH_SHORT).show();
                if(paSettingHeaderList.get(pos).getPaSettingSettings().get(j).getJobtitleName()!=null)
                {
                    aa.add(paSettingHeaderList.get(pos).getPaSettingSettings().get(j).getJobtitleName());
                }
                if(paSettingHeaderList.get(pos).getPaSettingSettings().get(j).getOrgName()!=null) {
                    bb.add(paSettingHeaderList.get(pos).getPaSettingSettings().get(j).getOrgName());
               }
            }

            Set<String> set = new HashSet<>(aa);
            aa.clear();
            aa.addAll(set);

            Set<String> set2 = new HashSet<>(bb);
            bb.clear();
            bb.addAll(set2);

            String[] arrayJabatan = aa.toArray(new String[0]);
            String[] arrayOrg = bb.toArray(new String[0]);
            tgOrganisasi.setTags(arrayOrg);
            tgJabatan.setTags(arrayJabatan);

        }
        catch (Exception e){
        }

        tvTitle.setText(title);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();

                List<PASettingHeader> aaa = new ArrayList<>();
                PASettingHeader paSettingHeader = new PASettingHeader();
                List<PASettingSetting> paSettingSettingList = new ArrayList<>();
                for(int i=0;i<tgOrganisasi.getTags().size();i++){
                    for(int j=0;j<empOrgModelList.size();j++) {
                        if (tgOrganisasi.getTags().get(i).equals(empOrgModelList.get(j).getOrgName())){
                            PASettingSetting paSettingSetting = new PASettingSetting();
                            paSettingSetting.setOrgName(tgOrganisasi.getTags().get(i));
                            paSettingSetting.setTempOrgCode(empOrgModelList.get(j).getOrgCode());
                            paSettingSetting.setJobtitleName("");
                            paSettingSetting.setTempJobTitleCode("");
                            paSettingSettingList.add(paSettingSetting);
                        }
                    }
                }

                for(int i=0;i<tgJabatan.getTags().size();i++){
                    for(int j=0;j<empJobTtlModelList.size();j++) {
                        if (tgJabatan.getTags().get(i).equals(empJobTtlModelList.get(j).getJobttlname())){
                            PASettingSetting paSettingSetting = new PASettingSetting();
                            paSettingSetting.setOrgName("");
                            paSettingSetting.setTempOrgCode("");
                            paSettingSetting.setJobtitleName(tgJabatan.getTags().get(i));
                            paSettingSetting.setTempJobTitleCode(empJobTtlModelList.get(j).getJobttlcode());
                            paSettingSettingList.add(paSettingSetting);
                        }
                    }

                }
                paSettingHeader.setChecked(false);
                paSettingHeader.setTempKPIID(paSettingHeaderList.get(pos).getTempKPIID());
                paSettingHeader.setYear(paSettingHeaderList.get(pos).getYear());
                paSettingHeader.setStatusDeployYN(paSettingHeaderList.get(pos).getStatusDeployYN());
                paSettingHeader.setTempKPIName(paSettingHeaderList.get(pos).getTempKPIName());
                paSettingHeader.setPaSettingDetails(paSettingHeaderList.get(pos).getPaSettingDetails());


                Set<PASettingSetting> set = new HashSet<>(paSettingSettingList);
                paSettingSettingList.clear();
                paSettingSettingList.addAll(set);

                paSettingHeader.setPaSettingSettings(paSettingSettingList);
                paSettingHeader.setEmpNIK(usr.get(0).getEmpNIK());
                aaa.add(paSettingHeader);

                ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
                Call<List<KPISettingSettingResponse>> call = apiService.postPAKPISettingSetting(aaa,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<List<KPISettingSettingResponse>>() {
                    @Override
                    public void onResponse(Call<List<KPISettingSettingResponse>> call, Response<List<KPISettingSettingResponse>> response) {
                        int statusCode = response.code();
                        if(response.body().size()>0) {
                            if (response.body().get(0).result.equals("BERHASIL")) {
                                Toast.makeText(context, "Template berhasil disimpan", Toast.LENGTH_LONG).show();
                                paSettingHeaderList.get(pos).setPaSettingSettings(aaa.get(0).getPaSettingSettings());
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Kombinasi template sudah pernah dibuat", Toast.LENGTH_LONG).show();
                                // paSettingHeaderList.get(pos).setPaSettingSettings(aaa.get(0).getPaSettingSettings());
                                notifyDataSetChanged();
                            }
                        }
                        else{
                            Toast.makeText(context, "Template berhasil disimpan", Toast.LENGTH_LONG).show();
                            paSettingHeaderList.get(pos).setPaSettingSettings(aaa.get(0).getPaSettingSettings());
                            notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<KPISettingSettingResponse>> call, Throwable t) {
                        Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();

                    }
                });


                    dialogs.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });

        dialogs.show();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        paSettingHeaderList.clear();
        if (charText.length() == 0) {
            paSettingHeaderList.addAll(paSettingHeaderListFilter);
        }
        else
        {
            for (PASettingHeader wp : paSettingHeaderListFilter) {
                if (wp.getTempKPIName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    paSettingHeaderList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}