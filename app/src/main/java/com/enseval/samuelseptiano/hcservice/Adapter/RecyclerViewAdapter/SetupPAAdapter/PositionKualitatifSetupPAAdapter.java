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

import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobLvlModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.KPISettingSettingResponse;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.SetupPA.PositionSetupDetailPAFragment;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeaderKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingSettingKualitatif;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PositionKualitatifSetupPAAdapter extends RecyclerView.Adapter<PositionKualitatifSetupPAAdapter.MyViewHolder> {

    private List<PASettingHeaderKualitatif> KPIList;
    private Context context;
    private Activity activity;
    private ArrayList<PASettingHeaderKualitatif> KPIListFilter = new ArrayList<>();
    private List<EmpJobLvlModel> empJobLvlModelList = new ArrayList<>();

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    EventListener listener;

    public interface EventListener {
        List <PASettingHeaderKualitatif> getSetupPositionModels();
        void setSetupPositionModels(List<PASettingHeaderKualitatif> setupPositionModelList);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvtemplateName, tvYear, tvAlertBobot, tvStatusDeploy;
        LinearLayout lnTemp;
        Button btnSetting;
        CheckBox cbPosition;

        public MyViewHolder(View view) {
            super(view);
            tvtemplateName = (TextView) view.findViewById(R.id.tv_templateName);
            tvYear = (TextView) view.findViewById(R.id.tv_year);
            btnSetting = view.findViewById(R.id.btnSetting);
            cbPosition = view.findViewById(R.id.cbPosition);
            tvAlertBobot = view.findViewById(R.id.tv_bobotAlert);
            tvStatusDeploy = view.findViewById(R.id.tv_StatusDeploy);

            lnTemp = view.findViewById(R.id.lnTempName);
        }
    }

    public PositionKualitatifSetupPAAdapter(List<PASettingHeaderKualitatif> KPIList, Context context, Activity activity, EventListener listener, List<EmpJobLvlModel>empJobLvlModelList) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.KPIListFilter.addAll(KPIList);
        this.listener = listener;
        this.empJobLvlModelList = empJobLvlModelList;
    }

    @Override
    public PositionKualitatifSetupPAAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setup_pa_kuantitatif_position_item, parent, false);

        return new PositionKualitatifSetupPAAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PositionKualitatifSetupPAAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        PASettingHeaderKualitatif setupPositionModel = KPIList.get(position);

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


//            if (Float.parseFloat(setupPositionModel.getBobot()) != 100) {
//                holder.tvAlertBobot.setText(Float.parseFloat(setupPositionModel.getBobot())+"%");
//                holder.tvAlertBobot.setTextColor(context.getResources().getColor(R.color.red));
//                //holder.cbPosition.setEnabled(false);
//
//            }
//            else{
//                holder.tvAlertBobot.setText(Float.parseFloat(setupPositionModel.getBobot())+"%");
//                holder.tvAlertBobot.setTextColor(context.getResources().getColor(R.color.black));
//                //holder.cbPosition.setEnabled(true);
//
//            }

        holder.tvtemplateName.setText(setupPositionModel.getTempCompName());
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
                    bundle3.putString("templateName",setupPositionModel.getTempCompName());
                    bundle3.putString("templateId",setupPositionModel.getTempCompID());
                    bundle3.putString("KPIType","KUALITATIF");
                    //bundle3.putSerializable("listSettingHeaderKualitatif",KPIList.get(position));
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
                showSettingDialog(setupPositionModel.getTempCompName(),position);
            }
        });

        holder.cbPosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    KPIList.get(position).setChecked(true);
                    listener.setSetupPositionModels(KPIList);
                }
                else{
                    KPIList.get(position).setChecked(false);
                    listener.setSetupPositionModels(KPIList);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return KPIList.size();
    }


    private void showSettingDialog(String title, int pos){
        Dialog dialogs = new Dialog(context);

        final String[] semester = {"1"};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.setup_position_setting_kualitatif_dialog);
        dialogs.setTitle("Title...");

        TextView tvTitle;
        TagsEditText tgGologan;
        Button btnSave;
        ImageButton btnClose;
        MaterialSpinner spinnerTab;


        tvTitle = dialogs.findViewById(R.id.tvTemplateName);
        btnSave = dialogs.findViewById(R.id.btnSaveSetting);
        tgGologan = dialogs.findViewById(R.id.tagsGolongan);
        spinnerTab = dialogs.findViewById(R.id.spinnerTabDab);
        spinnerTab.setBackgroundResource(R.drawable.shapedropdown);
        spinnerTab.setPadding(25, 10, 25, 10);
        List<String> tab = new ArrayList<String>();
        tab.add("Tanpa Anak Buah");
        tab.add("Dengan Anak Buah");
        // Creating adapter for spinner

        List<String> aaa = new ArrayList<>();
        for(int i=0;i<empJobLvlModelList.size();i++){
            aaa.add(empJobLvlModelList.get(i).getJoblvlcode());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, tab);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerTab.setAdapter(dataAdapter);


        try{

            List<String>aa = new ArrayList<>();
            for (int j=0;j<KPIList.get(pos).getPaSettingSettings().size();j++){
                //Toast.makeText(getContext(),response.body().get(i).getPaSettingSettings().get(j).getJobtitleName(),Toast.LENGTH_SHORT).show();
                if(KPIList.get(pos).getPaSettingSettings().get(j).getJoblvlgroupcode()!=null)
                {
                    aa.add(KPIList.get(pos).getPaSettingSettings().get(j).getJoblvlgroupcode());
                }

            }

            String[] arrayGol= aa.toArray(new String[0]);
            tgGologan.setTags(arrayGol);
        }
        catch (Exception e){
        }


        try{
            if(KPIList.get(pos).getTempCompSubYN().equals("N")){
                spinnerTab.setSelectedIndex(0);
            }
            else{
                spinnerTab.setSelectedIndex(1);
            }
        }
        catch (Exception e){

        }

        tgGologan.setHint("Type Golongan");
        //mTagsEditText.setTagsListener(MainActivity.this);
        tgGologan.setTagsWithSpacesEnabled(true);
        tgGologan.setAdapter(new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, aaa));
        tgGologan.setThreshold(1);


        btnClose = dialogs.findViewById(R.id.ib_close);
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        try{
            if(KPIList.get(pos).getTabDab().contains("Tanpa")){
                spinnerTab.setSelectedIndex(0);
            }
            else{
                spinnerTab.setSelectedIndex(1);
            }

            List<String>aa = new ArrayList<>();
            for (int j=0;j<KPIList.get(pos).getPaSettingSettings().size();j++){
                //Toast.makeText(getContext(),response.body().get(i).getPaSettingSettings().get(j).getJobtitleName(),Toast.LENGTH_SHORT).show();
                if(KPIList.get(pos).getPaSettingSettings().get(j).getJoblvlgroupcode()!=null)
                {
                    aa.add(KPIList.get(pos).getPaSettingSettings().get(j).getJoblvlgroupcode());
                }
            }

            String[] arrayGol = aa.toArray(new String[0]);
            tgGologan.setTags(arrayGol);
        }
        catch (Exception e){
            //Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }

        tvTitle.setText(title);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();


                List<PASettingHeaderKualitatif> aaa = new ArrayList<>();
                PASettingHeaderKualitatif paSettingHeader = new PASettingHeaderKualitatif();
                List<PASettingSettingKualitatif> paSettingSettingList = new ArrayList<>();
                for(int i=0;i<tgGologan.getTags().size();i++){
                    for(int j=0;j<empJobLvlModelList.size();j++) {
                        if (tgGologan.getTags().get(i).equals(empJobLvlModelList.get(j).getJoblvlcode())){
                            PASettingSettingKualitatif paSettingSetting = new PASettingSettingKualitatif();
                            paSettingSetting.setJoblvlgroupcode(empJobLvlModelList.get(j).getJoblvlcode());
                            paSettingSetting.setTempcompid(KPIList.get(pos).getTempCompID());
                            paSettingSettingList.add(paSettingSetting);
                        }
                    }
                }

                paSettingHeader.setChecked(false);
                paSettingHeader.setTempCompID(KPIList.get(pos).getTempCompID());
                paSettingHeader.setYear(KPIList.get(pos).getYear());
                if(spinnerTab.getText().toString().contains("Tanpa")){
                    paSettingHeader.setTabDab(spinnerTab.getText().toString());
                    paSettingHeader.setTempCompSubYN("N");
                    KPIList.get(pos).setTabDab(spinnerTab.getText().toString());
                    KPIList.get(pos).setTempCompSubYN("N");

                }
                else{
                    paSettingHeader.setTabDab(spinnerTab.getText().toString());
                    paSettingHeader.setTempCompSubYN("Y");
                    KPIList.get(pos).setTabDab(spinnerTab.getText().toString());
                    KPIList.get(pos).setTempCompSubYN("Y");

                }
                KPIList.get(pos).setPaSettingSettings(paSettingSettingList);
                paSettingHeader.setTempCompName(KPIList.get(pos).getTempCompName());
                paSettingHeader.setPaSettingDetails(KPIList.get(pos).getPaSettingDetails());
                paSettingHeader.setPaSettingSettings(paSettingSettingList);
                paSettingHeader.setEmpNIK(usr.get(0).getEmpNIK());
                aaa.add(paSettingHeader);

                ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
                Call<List<KPISettingSettingResponse>> call = apiService.postPAKPISettingSettingKualitatif(aaa,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<List<KPISettingSettingResponse>>() {
                    @Override
                    public void onResponse(Call<List<KPISettingSettingResponse>> call, Response<List<KPISettingSettingResponse>> response) {
                        int statusCode = response.code();

                        if(response.body().get(0).result.equals("BERHASIL")){
                            Toast.makeText(context,"Sukses",Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(context,"Tempate dengan job level tersebut sudah pernah dibuat",Toast.LENGTH_LONG).show();
                            Toast.makeText(context,"Tempate dengan job level tersebut sudah pernah dibuat",Toast.LENGTH_LONG).show();
                            paSettingHeader.getPaSettingSettings().remove(paSettingHeader.getPaSettingSettings().size()-1);
                        }

                        notifyDataSetChanged();
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

}