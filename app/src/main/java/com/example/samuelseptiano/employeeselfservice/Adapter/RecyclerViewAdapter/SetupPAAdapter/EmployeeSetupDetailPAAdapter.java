package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PAEmployeeDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeDetailModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupPositionDetailModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeSetupDetailPAAdapter extends RecyclerView.Adapter<EmployeeSetupDetailPAAdapter.MyViewHolder> {

    private List<SetupEmployeeDetailModel> setupEmployeeDetailModels;
    private Context context;
    private Activity activity;
    private ArrayList<SetupEmployeeDetailModel> SetupEmployeeDetailModelFilter = new ArrayList<>();
    List<SetupPositionDetailModel> masterSetupPositionDetailModelList = new ArrayList<>();

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    String KPIType;
    EventListener listener;

    public interface EventListener {
        List <SetupEmployeeDetailModel> getsetupEmployeeDetailModels();
        void setsetupEmployeeDetailModels(List <SetupEmployeeDetailModel> setupEmployeeDetailModelList);
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
        public void setCategory(String semester, String category, int position) {
            if(position != 0) {
                if (setupEmployeeDetailModels.get(position - 1).getSemester().equals(semester)) {
                    tvKPICategory.setVisibility(View.GONE);
                    HrLine.setVisibility(View.GONE);
                } else {
                    tvKPICategory.setText(category.substring(0, 1).toUpperCase() + category.substring(1,category.toCharArray().length).toLowerCase()+" Semester "+semester);
                }
            }
            else{
                tvKPICategory.setText(category.substring(0, 1).toUpperCase() + category.substring(1,category.toCharArray().length).toLowerCase()+" Semester "+semester);
            }

//            Toast.makeText(context,Integer.toString(position)+" "+questions.get(position-1).getKPIcategory()+" "+category,Toast.LENGTH_SHORT).show();
        }
    }

    public EmployeeSetupDetailPAAdapter(List<SetupEmployeeDetailModel> setupEmployeeDetailModels, Context context, Activity activity, EventListener listener, String KPIType,List<SetupPositionDetailModel> masterSetupPositionDetailModelList) {
        this.context = context;
        this.setupEmployeeDetailModels = setupEmployeeDetailModels;
        this.activity = activity;
        this.listener = listener;
        this.SetupEmployeeDetailModelFilter.addAll(setupEmployeeDetailModels);
        this.KPIType = KPIType;
        this.masterSetupPositionDetailModelList = masterSetupPositionDetailModelList;
    }

    @Override
    public EmployeeSetupDetailPAAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setup_pa_item_list, parent, false);

        return new EmployeeSetupDetailPAAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmployeeSetupDetailPAAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        SetupEmployeeDetailModel setupEmployeeDetailModel = setupEmployeeDetailModels.get(position);
        if(setupEmployeeDetailModel.isChecked()){
            holder.cbSetup.setChecked(true);
        }
        else{
            holder.cbSetup.setChecked(false);

        }

        holder.lnSetupItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog(setupEmployeeDetailModel.getKPIDesc(),setupEmployeeDetailModel,position,holder.tvNumber.getText().toString());
            }
        });
        holder.tvNumber.setText(setupEmployeeDetailModel.getNumber()+". ");
        holder.tvBobot.setText(setupEmployeeDetailModel.getBobot()+"%");
        holder.tvKPIDesc.setText(setupEmployeeDetailModel.getKPIDesc());
        holder.setCategory(setupEmployeeDetailModel.getSemester(),setupEmployeeDetailModel.getKPIType(),position);
        holder.cbSetup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    setupEmployeeDetailModels.get(position).setChecked(true);
                }
                else{
                    setupEmployeeDetailModels.get(position).setChecked(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return setupEmployeeDetailModels.size();
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

    private void showAddDialog(String title, SetupEmployeeDetailModel setupEmployeeDetailModel, int pos,String numb){
        Dialog dialogs = new Dialog(context);
        String tempNumber=numb.substring(0,numb.length()-2);

        final String[] semester = {"1"};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.edit_kpi_kuantiatif_dialog);
        dialogs.setTitle("Title...");
        TextView tvTitle,tvHint1,tvHint2,tvHint3,tvHint4,tvHint5;
        LinearLayout lnEditKPI;
        EditText edtBobot,edtKPIDesc;
        TextView edtHint1,edtHint2,edtHint3,edtHint4,edtHint5;
        Button btnSaveKPI,btnEditKPI;
        MaterialSpinner spinnerSemester;
        ImageButton btnClose;

//        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);

        tvTitle = dialogs.findViewById(R.id.tvTitle);
        btnSaveKPI = dialogs.findViewById(R.id.btnSaveKPI);
        btnEditKPI = dialogs.findViewById(R.id.btnEditKPI);

        btnClose = dialogs.findViewById(R.id.ib_close);
        spinnerSemester = dialogs.findViewById(R.id.spinnerSemester);
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
        tvHint1.setText(setupEmployeeDetailModel.getKpiHintList().get(0).getKpiGradeName());
        tvHint2.setText(setupEmployeeDetailModel.getKpiHintList().get(1).getKpiGradeName());
        tvHint3.setText(setupEmployeeDetailModel.getKpiHintList().get(2).getKpiGradeName());
        tvHint4.setText(setupEmployeeDetailModel.getKpiHintList().get(3).getKpiGradeName());
        tvHint5.setText(setupEmployeeDetailModel.getKpiHintList().get(4).getKpiGradeName());


        spinnerSemester.setBackgroundResource(R.drawable.shapedropdown);
        spinnerSemester.setPadding(25, 10, 25, 10);
        List<String> setup = new ArrayList<String>();
        setup.add("-ALL-");
        setup.add("SMT 1");
        setup.add("SMT 2");
        spinnerSemester.setItems(setup);

        spinnerSemester.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(spinnerSemester.getText().toString().equals("SMT 1")){
                    semester[0] ="1";
                    listener.setUbah(true);
                }
                else{
                    semester[0] ="2";
                    listener.setUbah(true);

                }
            }
        });

        edtBobot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listener.setUbah(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnEditKPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTitle.setText("Edit KPI "+title);
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
                if(setupEmployeeDetailModel.getSemester().equals("1")){
                    spinnerSemester.setSelectedIndex(1);
                }
                else{
                    spinnerSemester.setSelectedIndex(2);
                }
                edtBobot.setText(setupEmployeeDetailModel.getBobot());
                btnSaveKPI.setVisibility(View.VISIBLE);
                btnEditKPI.setVisibility(View.GONE);


            }
        });
        btnSaveKPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> aaa = new ArrayList<>();
                aaa.add(edtHint1.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());
                aaa.add(edtHint2.getText().toString());


                SetupEmployeeDetailModel setupEmployeeDetailModel = new SetupEmployeeDetailModel();
                setupEmployeeDetailModel.setChecked(false);
                setupEmployeeDetailModel.setId(setupEmployeeDetailModels.get(pos).getId());
                setupEmployeeDetailModel.setPaId(setupEmployeeDetailModels.get(pos).getPaId());
                setupEmployeeDetailModel.setSemester( semester[0]);
                setupEmployeeDetailModel.setNumber(tempNumber);
                setupEmployeeDetailModel.setKPIType("KUANTITATIF");
                setupEmployeeDetailModel.setKPIDesc(setupEmployeeDetailModels.get(pos).getKPIDesc());
                setupEmployeeDetailModel.setBobot(edtBobot.getText().toString());


                List<KPIHint> kpiHints = new ArrayList<>();
                for(int i=0;i<5;i++) {
                    KPIHint kpiHint = new KPIHint();
                    kpiHint.setKpiGradeID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeID());
                    kpiHint.setKpiID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiID());
                    kpiHint.setKpiGradeCode(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeCode());
                    kpiHint.setKpiGradeName(aaa.get(i));
                    kpiHints.add(kpiHint);
                }

                setupEmployeeDetailModel.setKpiHintList(kpiHints);
                setupEmployeeDetailModels.add(setupEmployeeDetailModel);
                Collections.sort(setupEmployeeDetailModels);

                setupEmployeeDetailModels.remove(pos);
                notifyDataSetChanged();
                dialogs.dismiss();


                //=======================================


                List<PAEmployeeDetail>paEmployeeDetails = new ArrayList<>();
                for(int i=0;i<setupEmployeeDetailModels.size();i++){
                    PAEmployeeDetail paEmployeeDetail = new PAEmployeeDetail();
                    paEmployeeDetail.setBobot(setupEmployeeDetailModels.get(i).getBobot());
                    paEmployeeDetail.setHintList(kpiHints);
                    paEmployeeDetail.setKpiID(setupEmployeeDetailModels.get(i).getId());
                    paEmployeeDetail.setKpiname(setupEmployeeDetailModels.get(i).getKPIDesc());
                    paEmployeeDetail.setPaid(setupEmployeeDetailModels.get(i).getPaId());
                    paEmployeeDetail.setSemester(setupEmployeeDetailModels.get(i).getSemester());
                    paEmployeeDetails.add(paEmployeeDetail);
                }

                UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
                Call<Void> call = apiService.postPAKPIEmployee(paEmployeeDetails,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int statusCode = response.code();
                        Toast.makeText(context,"Sukses",Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();

                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();

                    }
                });


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