package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPKAdapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHint;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingDetailKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeaderKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.SetupPositionDetailModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingDetailKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingHeaderKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.SetupPositionDetailPKModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PositionSetupDetailKualitatifPKAdapter extends RecyclerView.Adapter<PositionSetupDetailKualitatifPKAdapter.MyViewHolder> {

    private List<SetupPositionDetailPKModel> setupPositionDetailModelList;
    private Context context;
    private Activity activity;
    private ArrayList<SetupPositionDetailPKModel> setupPositionDetailModelListFilter = new ArrayList<>();
    private List<PKSettingHeaderKualitatif> paSettingHeaderList = new ArrayList<>();


    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    EventListener listener;
    String type;

    public interface EventListener {
        List <SetupPositionDetailPKModel> getSetupPositionDetailModels();
        void setSetupPositionDetailModels(List<SetupPositionDetailPKModel> setupPositionDetailModels);
        List <PKSettingHeaderKualitatif> getPASettingHeaderKualitatifList();
        void setPaSettingHeaderKualitatifList(List<PKSettingHeaderKualitatif> paSettingHeaderList);
        void refreshDataKualitatif();

        void setUbah(boolean isUbah);
        boolean getUbah();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNumber, tvKPIDesc,tvBobot, tvKPICategory;
        LinearLayout lnSetupItem;
        View HrLine;
        CheckBox cbSetup;

        public MyViewHolder(View view) {
            super(view);
            tvNumber = (TextView) view.findViewById(R.id.tvNumber);
            tvKPIDesc = (TextView) view.findViewById(R.id.tvSetupKPI);
            tvBobot =  view.findViewById(R.id.tvSetupBobot);
            lnSetupItem = view.findViewById(R.id.lnSetup);
            cbSetup = view.findViewById(R.id.cbSetupPa);
            tvKPICategory = view.findViewById(R.id.tvKPICategory);
            HrLine = view.findViewById(R.id.HrLine2);
        }
        public void setCategory(String category, int position) {
                tvKPICategory.setText(category.substring(0, 1).toUpperCase() + category.substring(1,category.toCharArray().length).toLowerCase());
//            Toast.makeText(context,Integer.toString(position)+" "+questions.get(position-1).getKPIcategory()+" "+category,Toast.LENGTH_SHORT).show();
        }
    }

    public PositionSetupDetailKualitatifPKAdapter(List<SetupPositionDetailPKModel> setupPositionDetailModelList, Context context, Activity activity, EventListener listener, List<PKSettingHeaderKualitatif> paSettingHeaderList, String type ) {
        this.context = context;
        this.setupPositionDetailModelList = setupPositionDetailModelList;
        this.activity = activity;
        this.listener = listener;
        this.setupPositionDetailModelListFilter.addAll(setupPositionDetailModelList);
        this.paSettingHeaderList = paSettingHeaderList;
        this.type=type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setup_pa_item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        SetupPositionDetailPKModel setupPositionDetailModel = setupPositionDetailModelList.get(position);
        if(setupPositionDetailModel.isChecked()){
            holder.cbSetup.setChecked(true);
        }
        else{
            holder.cbSetup.setChecked(false);

        }


        //if(paSettingHeaderList.get(0).getStatusDeployYN().equals("N")) {
            holder.lnSetupItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddDialog(setupPositionDetailModel.getKPIDesc(), setupPositionDetailModel, position, holder.tvNumber.getText().toString());
                }
            });
        //}
        holder.tvNumber.setText(setupPositionDetailModel.getNumber()+". ");
        try {
            holder.tvBobot.setText(setupPositionDetailModel.getBobot() + "%");
        }catch (Exception e){
            holder.tvBobot.setText("0%");
        }
        holder.tvKPIDesc.setText(setupPositionDetailModel.getKPIDesc());
        holder.setCategory(setupPositionDetailModel.getKPIType(),position);
        holder.cbSetup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    setupPositionDetailModelList.get(position).setChecked(true);
                    paSettingHeaderList.get(0).getPaSettingDetails().get(position).setChecked(true);
                    listener.setSetupPositionDetailModels(setupPositionDetailModelList);
                    listener.setPaSettingHeaderKualitatifList(paSettingHeaderList);
                    listener.setUbah(true);

                }
                else{
                    setupPositionDetailModelList.get(position).setChecked(false);
                    paSettingHeaderList.get(0).getPaSettingDetails().get(position).setChecked(false);
                    listener.setSetupPositionDetailModels(setupPositionDetailModelList);
                    listener.setPaSettingHeaderKualitatifList(paSettingHeaderList);
                    listener.setUbah(true);
                }
            }
        });

        holder.HrLine.setVisibility(View.GONE);
        holder.tvKPICategory.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return setupPositionDetailModelList.size();
    }

    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        KPIList.clear();
//        if (charText.length() == 0) {
//            KPIList.addAll(KPIListFilter);
//        }
//        else
//        {
//            for (SetupEmployeeModel wp : KPIListFilter) {
//                if (wp.getEmpName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getNIK().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    KPIList.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
}

    private void showAddDialog(String title, SetupPositionDetailPKModel setupEmployeeDetailModel, int pos, String numb){
        Dialog dialogs = new Dialog(context);

        String tempNumber=numb.substring(0,numb.length()-2);

        final String[] semester = {"1"};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.edit_pk_kpi_kuantiatif_dialog);
        dialogs.setTitle("Title...");
        TextView tvTitle,tvHint1,tvHint2,tvHint3,tvHint4,tvHint5;
        LinearLayout lnEditKPI;
        EditText edtBobot,edtKPIDesc;
        TextView edtHint1,edtHint2,edtHint3,edtHint4,edtHint5;
        Button btnSaveKPI,btnEditKPI;
        MaterialSpinner spinnerCR;
        ImageButton btnClose;

//        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);

        tvTitle = dialogs.findViewById(R.id.tvTitle);
        btnSaveKPI = dialogs.findViewById(R.id.btnSaveKPI);
        btnEditKPI = dialogs.findViewById(R.id.btnEditKPI);

        btnClose = dialogs.findViewById(R.id.ib_close);
        spinnerCR = dialogs.findViewById(R.id.spinnerCR);

        spinnerCR.setVisibility(View.VISIBLE);
        edtBobot = dialogs.findViewById(R.id.edtBobot);

        lnEditKPI = dialogs.findViewById(R.id.lnEditKPI);

        edtHint1 = dialogs.findViewById(R.id.edtHint1);
        edtHint2 = dialogs.findViewById(R.id.edtHint2);
        edtHint3 = dialogs.findViewById(R.id.edtHint3);
        edtHint4 = dialogs.findViewById(R.id.edtHint4);
        edtHint5 = dialogs.findViewById(R.id.edtHint5);

        tvHint1 = dialogs.findViewById(R.id.tvKPIHint1);
        tvHint2 = dialogs.findViewById(R.id.tvKPIHint2);
        tvHint3 = dialogs.findViewById(R.id.tvKPIHint3);
        tvHint4 = dialogs.findViewById(R.id.tvKPIHint4);
        tvHint5 = dialogs.findViewById(R.id.tvKPIHint5);

        tvTitle.setText(title);
        tvHint1.setText(setupEmployeeDetailModel.getKpiHintList().get(4).getKpiGradeName());
        tvHint2.setText(setupEmployeeDetailModel.getKpiHintList().get(3).getKpiGradeName());
        tvHint3.setText(setupEmployeeDetailModel.getKpiHintList().get(2).getKpiGradeName());
        tvHint4.setText(setupEmployeeDetailModel.getKpiHintList().get(1).getKpiGradeName());
        tvHint5.setText(setupEmployeeDetailModel.getKpiHintList().get(0).getKpiGradeName());

        if(paSettingHeaderList.get(0).getStatusDeployYN().equals("Y")) {
            btnEditKPI.setVisibility(View.GONE);
            btnSaveKPI.setVisibility(View.GONE);
        }
        spinnerCR.setBackgroundResource(R.drawable.shapedropdown);
        spinnerCR.setPadding(25, 10, 25, 10);
        List<String> setupCR = new ArrayList<String>();
        setupCR.add("-PILIH CR-");
        for(int ii=0;ii<5;ii++){
            setupCR.add(Integer.toString(ii+1));
        }
        spinnerCR.setItems(setupCR);

        spinnerCR.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                listener.setUbah(true);
            }
        });

        btnEditKPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTitle.setText("Edit "+title);
                tvHint1.setVisibility(View.GONE);
                tvHint2.setVisibility(View.GONE);
                tvHint3.setVisibility(View.GONE);
                tvHint4.setVisibility(View.GONE);
                tvHint5.setVisibility(View.GONE);
                edtHint1.setVisibility(View.VISIBLE);
                edtHint2.setVisibility(View.VISIBLE);
                edtHint3.setVisibility(View.VISIBLE);
                edtHint4.setVisibility(View.VISIBLE);
                edtHint5.setVisibility(View.VISIBLE);
                lnEditKPI.setVisibility(View.VISIBLE);

                edtHint1.setText(tvHint1.getText());
                edtHint2.setText(tvHint2.getText());
                edtHint3.setText(tvHint3.getText());
                edtHint4.setText(tvHint4.getText());
                edtHint5.setText(tvHint5.getText());
                spinnerCR.setSelectedIndex(setupEmployeeDetailModel.getCr());
                edtBobot.setText(setupEmployeeDetailModel.getBobot());
                btnSaveKPI.setVisibility(View.VISIBLE);
                btnEditKPI.setVisibility(View.GONE);


            }
        });
        btnSaveKPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ctr = 0;

                List<PKSettingHeaderKualitatif> paSettingHeaders = new ArrayList<>();
                PKSettingHeaderKualitatif paSettingHeader = new PKSettingHeaderKualitatif();

                List<String> aaa = new ArrayList<>();
                aaa.add(edtHint1.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());

                SetupPositionDetailPKModel setupPositionDetailModel = new SetupPositionDetailPKModel();
                setupPositionDetailModel.setChecked(false);
                setupPositionDetailModel.setKPIType("KUALITATIF");
                setupPositionDetailModel.setNumber(tempNumber);
                setupPositionDetailModel.setId(setupPositionDetailModelList.get(pos).getId());
                setupPositionDetailModel.setKPIDesc(title);

                setupPositionDetailModel.setCr(Integer.parseInt(spinnerCR.getText().toString()));

                setupPositionDetailModel.setBobot(edtBobot.getText().toString());
                List<KPIHint> kpiHints = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    KPIHint kpiHint = new KPIHint();
                    kpiHint.setKpiGradeID("");
                    kpiHint.setKpiID("");
                    kpiHint.setKpiGradeCode("");
                    kpiHint.setKpiGradeName(aaa.get(i));
                    kpiHints.add(kpiHint);
                }


//                    for (int i = 0; i < setupPositionDetailModelList.size(); i++) {
//                        if (setupPositionDetailModelList.get(i).getKPIDesc().equals(setupPositionDetailModel.getKPIDesc())) {
//                            Toast.makeText(context, "Template Sudah Ada", Toast.LENGTH_SHORT).show();
//                            ctr++;
//                        }
//                    }

                if(ctr==0){
                setupPositionDetailModel.setKpiHintList(kpiHints);
                setupPositionDetailModelList.add(setupPositionDetailModel);

                Collections.sort(setupPositionDetailModelList);
                setupPositionDetailModelList.remove(pos);

                //=========================================
                UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();

                List<PKSettingDetailKualitatif> paSettingDetailss = new ArrayList<>();
                //for (int i = 0; i < setupPositionDetailModelList.size(); i++) {
                    PKSettingDetailKualitatif paSettingDetail = new PKSettingDetailKualitatif();
                    paSettingDetail.setBobot(edtBobot.getText().toString());
                    paSettingDetail.setSemester("0");
                    paSettingDetail.setCompID(setupEmployeeDetailModel.getId());
                    paSettingDetail.setCr(spinnerCR.getText().toString());
                    paSettingDetailss.add(paSettingDetail);

                //}
                paSettingHeader.setYear(paSettingHeaderList.get(0).getYear());
                paSettingHeader.setTempCompName(paSettingHeaderList.get(0).getTempCompName());
                paSettingHeader.setTempCompID(paSettingHeaderList.get(0).getTempCompID());
                paSettingHeader.setChecked(paSettingHeaderList.get(0).isChecked());
                paSettingHeader.setTempCompSubYN(paSettingHeaderList.get(0).getTempCompSubYN());
                paSettingHeader.setTabDab(paSettingHeaderList.get(0).getTabDab());
                paSettingHeader.setPaSettingSettings(paSettingHeaderList.get(0).getPaSettingSettings());
                paSettingHeader.setPaSettingDetails(paSettingDetailss);
                paSettingHeaders.add(paSettingHeader);

                ApiInterface apiService = ApiClient.getClientTest(context).create(ApiInterface.class);
                Call<Void> call = apiService.postPAKPISettingHeaderKualitatifPKOne(paSettingHeaders, "Bearer " + usr.get(0).getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int statusCode = response.code();
                        Toast.makeText(context, "Sukses", Toast.LENGTH_LONG).show();
                        listener.refreshDataKualitatif();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                //=========================================
                notifyDataSetChanged();
                dialogs.dismiss();
            }
                     else{
                    Toast.makeText(context,"Template Sudah Ada",Toast.LENGTH_SHORT).show();
                    }
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