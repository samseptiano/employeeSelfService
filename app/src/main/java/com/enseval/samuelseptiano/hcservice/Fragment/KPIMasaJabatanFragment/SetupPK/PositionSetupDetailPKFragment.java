package com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment.SetupPK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPKAdapter.PositionSetupDetailKualitatifPKAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.SetupPKAdapter.PositionSetupDetailPKAdapter;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHint;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.MKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.MKPIPK;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingDetail;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingDetailKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingHeaderKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.SetupPositionDetailPKModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PositionSetupDetailPKFragment extends Fragment implements PositionSetupDetailPKAdapter.EventListener, PositionSetupDetailKualitatifPKAdapter.EventListener {

    View rootView;
    RecyclerView rvSetupEmployeeDetail;
    TextView tvEmpName,tvJobTitle, tvNoSem1, tvNoSem2;
    ImageView tvNoResult;
    CircleImageView imgEmpPhoto;
    Button btnAdd,btnDelete;
    String idKPI = "";
    List<SetupPositionDetailPKModel> setupPositionDetailModelList = new ArrayList<>();
    List<SetupPositionDetailPKModel> masterSetupPositionDetailModelList = new ArrayList<>();
    PositionSetupDetailPKAdapter positionSetupDetailPAAdapter;
    PositionSetupDetailKualitatifPKAdapter positionSetupDetailKualitatifPAAdapter;

    boolean isUbah;

    String tempName = "", tempId="";
    String KPIType="";
    //String tempKPIID,tempCOMPID;
    List<PKSettingHeader> paSettingHeaderList = new ArrayList<>();
    List<PKSettingHeaderKualitatif> paSettingHeaderKualitatifList = new ArrayList<>();
    ArrayList<UserRealmModel> usr = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_position_setup_detail_pa, container, false);
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        usr = userRealmHelper.findAllArticle();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tempName = bundle.getString("templateName");
            tempId = bundle.getString("templateId");
            KPIType = bundle.getString("KPIType");
            //paSettingHeaderList.add((PKSettingHeader) bundle.getSerializable("listSettingHeader"));
            //paSettingHeaderKualitatifList.add((PASettingHeaderKualitatif) bundle.getSerializable("listSettingHeaderKualitatif"));

        }

        tvEmpName = rootView.findViewById(R.id.tv_TemplateName);
        tvNoResult = rootView.findViewById(R.id.tvNoResult);
        tvNoSem1 = rootView.findViewById(R.id.tvNotCompletedSem1);
        tvNoSem2 = rootView.findViewById(R.id.tvNotCompletedSem2);

        btnAdd = rootView.findViewById(R.id.btnAddKPI);
        btnDelete = rootView.findViewById(R.id.btnDelKPI);



        tvEmpName.setText(tempName);

        rvSetupEmployeeDetail = rootView.findViewById(R.id.rv_setup_pos_pa);
        if(KPIType.equals("KUANTITATIF")) {
            paSettingHeaderList = new ArrayList<>();
            prepareDataPositionKuantitatifPA();
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddDialog(positionSetupDetailPAAdapter);
                }
            });
        }
        else{
            paSettingHeaderKualitatifList = new ArrayList<>();
            prepareDataPositionKualitatifPA();
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddDialogKualitatif(positionSetupDetailKualitatifPAAdapter);
                }
            });
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(KPIType.equals("KUANTITATIF")){
                try {

                    List<PKSettingHeader> aaa = new ArrayList<>();
                    aaa = getPASettingHeader();
                    Collections.sort(aaa.get(0).getPaSettingDetails());

                    setupPositionDetailModelList = getSetupPositionDetailModels();
                    Collections.sort(setupPositionDetailModelList);
                    //aaa.get(0).getPaSettingDetails().size()


                    Iterator<SetupPositionDetailPKModel> iter = setupPositionDetailModelList.iterator();

                    Iterator<PKSettingDetail> iter2 = aaa.get(0).getPaSettingDetails().iterator();

                    while (iter2.hasNext()) {
                        PKSettingDetail p2 = iter2.next();
                            if (!p2.isChecked()){
                                    iter2.remove();
                            }
                      //  }
                    }

                    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                    ArrayList<UserRealmModel> usr;
                    usr = userRealmHelper.findAllArticle();
                    ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                    Call<Void> call = apiService.deletePAKPISettingDetailPK(aaa, "Bearer " + usr.get(0).getToken());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int statusCode = response.code();
                            Toast.makeText(getContext(), "Sukses Delete", Toast.LENGTH_SHORT).show();
                            prepareDataPositionKuantitatifPA();
//                            List<PASettingDetail> bbb= new ArrayList<>();
//                            while(iter2.hasNext()){
//                                bbb.add(iter2.next());
//                            }
//                            paSettingHeaderList.get(0).setPaSettingDetails(bbb);
//                          PositionSetupDetailPAAdapter positionSetupDetailPAAdapter = new PositionSetupDetailPAAdapter(setupPositionDetailModelList,getContext(),getActivity(),PositionSetupDetailPAFragment.this,paSettingHeaderList,"KUANTITATIF");
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                            rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
//                            rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
//                            rvSetupEmployeeDetail.setAdapter(positionSetupDetailPAAdapter);
//                            positionSetupDetailPAAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

                        }
                    });

                    positionSetupDetailPAAdapter.notifyDataSetChanged();
                }
            catch (Exception e){}

            }
            else{
                try {

                    List<PKSettingHeaderKualitatif> aaa = new ArrayList<>();
                    aaa = getPASettingHeaderKualitatifList();
                    Collections.sort(aaa.get(0).getPaSettingDetails());

                    setupPositionDetailModelList = getSetupPositionDetailModels();
                    Collections.sort(setupPositionDetailModelList);
                    //aaa.get(0).getPaSettingDetails().size()


                    Iterator<SetupPositionDetailPKModel> iter = setupPositionDetailModelList.iterator();

                    Iterator<PKSettingDetailKualitatif> iter2 = aaa.get(0).getPaSettingDetails().iterator();

//                    while (iter2.hasNext()) {
//                        SetupPositionDetailModel p = iter.next();
//                        PASettingDetailKualitatif p2 = iter2.next();
//
//                        if (!p.isChecked()){
//                            if(p.getId().equals(p2.getCompID())) {
//                                iter2.remove();
//                            }
//                        }
//                        else{
//                            iter.remove();
//                        }
//                    }

                    while (iter2.hasNext()) {
                        PKSettingDetailKualitatif p2 = iter2.next();
                        if (!p2.isChecked()){

                            iter2.remove();

                        }
                        //  }
                    }

                    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                    ArrayList<UserRealmModel> usr;
                    usr = userRealmHelper.findAllArticle();
                    ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                    Call<Void> call = apiService.deletePAKPISettingDetailKualitatifPK(aaa, "Bearer " + usr.get(0).getToken());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int statusCode = response.code();
                            Toast.makeText(getContext(), "Sukses Delete", Toast.LENGTH_SHORT).show();
                            prepareDataPositionKualitatifPA();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                    //positionSetupDetailKualitatifPAAdapter.notifyDataSetChanged();

                }
                catch (Exception e){}
            }
            }
        });

        return  rootView;
    }

    private void prepareDataPositionKuantitatifPA() {
        setupPositionDetailModelList = new ArrayList<>();
        masterSetupPositionDetailModelList = new ArrayList<>();
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Load Data...");
        pd.setCancelable(false);
        pd.show();

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        //=======================================================================
        ApiInterface apiService2 = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
        Call<List<PKSettingHeader>> call2 = apiService2.getPAKPISettingHeaderPKOne(tempId,"Bearer "+usr.get(0).getToken());
        call2.enqueue(new Callback<List<PKSettingHeader>>() {
            @Override
            public void onResponse(Call<List<PKSettingHeader>> call2, Response<List<PKSettingHeader>> response) {
                int statusCode = response.code();
                paSettingHeaderList= response.body();
                if(paSettingHeaderList.get(0).getStatusDeployYN().equals("Y")){
                    btnAdd.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.GONE);
                }


                Collections.sort(paSettingHeaderList.get(0).getPaSettingDetails());
                Collections.sort(setupPositionDetailModelList);
                if(paSettingHeaderList.get(0).getPaSettingDetails().size()==0){
                    tvNoResult.setVisibility(View.VISIBLE);
                   // tvNoSem1.setVisibility(View.GONE);
                   // tvNoSem2.setVisibility(View.GONE);
                    rvSetupEmployeeDetail.setVisibility(View.GONE);
                }
                else{
                     tvNoResult.setVisibility(View.GONE);
                            //.setVisibility(View.GONE);
                           // tvNoSem2.setVisibility(View.GONE);
                            rvSetupEmployeeDetail.setVisibility(View.VISIBLE);


                }


                positionSetupDetailPAAdapter = new PositionSetupDetailPKAdapter(setupPositionDetailModelList,getContext(),getActivity(), PositionSetupDetailPKFragment.this,paSettingHeaderList, "KUANTITATIF");
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
                rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
                rvSetupEmployeeDetail.setAdapter(positionSetupDetailPAAdapter);

                pd.dismiss();

                ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                Call<List<MKPIPK>> call = apiService.getMKPIPK(tempId,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<List<MKPIPK>>() {
                    @Override
                    public void onResponse(Call<List<MKPIPK>> call, Response<List<MKPIPK>> response) {
                        int statusCode = response.code();
                        int j=0;
                        for(int i=0;i<response.body().size();i++){
                            SetupPositionDetailPKModel ka = new SetupPositionDetailPKModel();
                            ka.setPaId(response.body().get(i).getKPIID());
                            ka.setBobot(response.body().get(i).getBOBOT());
                            ka.setId(response.body().get(i).getKPIID());
                            ka.setTemplateJobTitle("JOBTITLE");
                            ka.setTemplateId(tempId);          //MASIH HARDCODE
                            ka.setTemplateOrganisasi("ORGANISASI");
                            ka.setKPIDesc(response.body().get(i).getKPINAME());
                            ka.setKPIType("KUANTITATIF");
                            ka.setKPIperspective("KUANTITATIF");
                            ka.setNumber(Integer.toString(i+1));

                            ka.setKpiHintList(response.body().get(i).getpK_ViewTransGrades());
                            setupPositionDetailModelList.add(ka);


                            Collections.sort(paSettingHeaderList.get(0).getPaSettingDetails());
                            Collections.sort(setupPositionDetailModelList);



                            positionSetupDetailPAAdapter = new PositionSetupDetailPKAdapter(setupPositionDetailModelList,getContext(),getActivity(), PositionSetupDetailPKFragment.this,paSettingHeaderList, "KUANTITATIF");
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
                            rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
                            rvSetupEmployeeDetail.setAdapter(positionSetupDetailPAAdapter);
                        }

                        call = apiService.getMKPIALLPK("Bearer "+usr.get(0).getToken());
                        call.enqueue(new Callback<List<MKPIPK>>() {
                            @Override
                            public void onResponse(Call<List<MKPIPK>> call, Response<List<MKPIPK>> response) {
                                int statusCode = response.code();
                                for(int i=0;i<response.body().size();i++){
                                    SetupPositionDetailPKModel ka = new SetupPositionDetailPKModel();
                                    ka.setPaId(response.body().get(i).getKPIID());
                                    ka.setBobot(response.body().get(i).getBOBOT());
                                    ka.setId(response.body().get(i).getKPIID());
                                    ka.setTemplateJobTitle("JOBTITLE");
                                    ka.setTemplateId(tempId);          //MASIH HARDCODE
                                    ka.setTemplateOrganisasi("ORGANISASI");
                                    ka.setKPIDesc(response.body().get(i).getKPINAME());
                                    ka.setKPIType("KUANTITATIF");
                                    ka.setKPIperspective("KUANTITATIF");

                                    ka.setKpiHintList(response.body().get(i).getpK_ViewTransGrades());
                                    masterSetupPositionDetailModelList.add(ka);
                                }


                            }
                            @Override
                            public void onFailure(Call<List<MKPIPK>> call, Throwable t) {
                                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                    @Override
                    public void onFailure(Call<List<MKPIPK>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                    }
                });

                Collections.sort(paSettingHeaderList.get(0).getPaSettingDetails());
                Collections.sort(setupPositionDetailModelList);
                positionSetupDetailPAAdapter = new PositionSetupDetailPKAdapter(setupPositionDetailModelList,getContext(),getActivity(), PositionSetupDetailPKFragment.this,paSettingHeaderList, "KUANTITATIF");
                mLayoutManager = new LinearLayoutManager(getContext());
                rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
                rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
                rvSetupEmployeeDetail.setAdapter(positionSetupDetailPAAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<PKSettingHeader>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                });
        //=======================================================================

    }

    private void prepareDataPositionKualitatifPA() {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Load Data...");
        pd.setCancelable(false);
        pd.show();

        masterSetupPositionDetailModelList = new ArrayList<>();
        setupPositionDetailModelList = new ArrayList<>();
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
        Call<List<PKSettingHeaderKualitatif>> call2 = apiService.getPAKualitatifSettingHeaderPKOne(tempId,"Bearer "+usr.get(0).getToken());
        call2.enqueue(new Callback<List<PKSettingHeaderKualitatif>>() {
            @Override
            public void onResponse(Call<List<PKSettingHeaderKualitatif>> call2, Response<List<PKSettingHeaderKualitatif>> response) {
                int statusCode = response.code();
                paSettingHeaderKualitatifList = response.body();
                if(paSettingHeaderKualitatifList.get(0).getStatusDeployYN().equals("Y")){
                    btnAdd.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.GONE);
                }

                if(paSettingHeaderKualitatifList.get(0).getPaSettingDetails().size()==0){
                    tvNoResult.setVisibility(View.VISIBLE);
                    rvSetupEmployeeDetail.setVisibility(View.GONE);
                }
                else{
                            tvNoResult.setVisibility(View.GONE);
                            rvSetupEmployeeDetail.setVisibility(View.VISIBLE);
                }


                Collections.sort(paSettingHeaderKualitatifList.get(0).getPaSettingDetails());
                Collections.sort(setupPositionDetailModelList);
//                positionSetupDetailKualitatifPAAdapter = new PositionSetupDetailKualitatifPKAdapter(setupPositionDetailModelList,getContext(),getActivity(), PositionSetupDetailPKFragment.this,paSettingHeaderKualitatifList,"KUALITATIF");
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
//                rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
//                rvSetupEmployeeDetail.setAdapter(positionSetupDetailKualitatifPAAdapter);

                pd.dismiss();

                ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                Call<List<MKualitatif>> call = apiService.getMKualitatif(tempId,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<List<MKualitatif>>() {
                    @Override
                    public void onResponse(Call<List<MKualitatif>> call, Response<List<MKualitatif>> response) {
                        int statusCode = response.code();
                        int j=0;
                        for(int i=0;i<response.body().size();i++){
                            SetupPositionDetailPKModel ka = new SetupPositionDetailPKModel();
                            ka.setPaId(response.body().get(i).getCOMPID());
                            ka.setBobot(response.body().get(i).getBOBOT());
                            ka.setId(response.body().get(i).getCOMPID());
                            ka.setTemplateJobTitle("JOBTITLE");
                            ka.setTemplateId(paSettingHeaderKualitatifList.get(0).getTempCompID());          //MASIH HARDCODE
                            ka.setTemplateOrganisasi("ORGANISASI");
                            ka.setKPIDesc(response.body().get(i).getKPINAME());
                            ka.setKPIType("KUALITATIF");
                            ka.setCr(Integer.parseInt(response.body().get(i).getCR()));
                            ka.setKPIperspective("KUALITATIF");
                            if(response.body().get(i).getSEMESTER().equals("2")){
                               ka.setNumber(Integer.toString(j+1));
                               j++;
                            }
                            else{
                                ka.setNumber(Integer.toString(i+1));
                            }
                            ka.setKpiHintList(response.body().get(i).getpA_ViewTransGrades());
                            setupPositionDetailModelList.add(ka);


                            Collections.sort(paSettingHeaderKualitatifList.get(0).getPaSettingDetails());
                            Collections.sort(setupPositionDetailModelList);



                            positionSetupDetailKualitatifPAAdapter = new PositionSetupDetailKualitatifPKAdapter(setupPositionDetailModelList,getContext(),getActivity(), PositionSetupDetailPKFragment.this,paSettingHeaderKualitatifList,"KUALITATIF");
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
                            rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
                            rvSetupEmployeeDetail.setAdapter(positionSetupDetailKualitatifPAAdapter);
                        }

                        call = apiService.getMKualitatifALL("Bearer "+usr.get(0).getToken());
                        call.enqueue(new Callback<List<MKualitatif>>() {
                            @Override
                            public void onResponse(Call<List<MKualitatif>> call, Response<List<MKualitatif>> response) {
                                int statusCode = response.code();
                                for(int i=0;i<response.body().size();i++){
                                    SetupPositionDetailPKModel ka = new SetupPositionDetailPKModel();
                                    ka.setPaId(response.body().get(i).getCOMPID());
                                    ka.setBobot(response.body().get(i).getBOBOT());
                                    ka.setId(response.body().get(i).getCOMPID());
                                    ka.setTemplateJobTitle("JOBTITLE");
                                    ka.setTemplateId(tempId);          //MASIH HARDCODE
                                    ka.setTemplateOrganisasi("ORGANISASI");
                                    ka.setKPIDesc(response.body().get(i).getKPINAME());
                                    ka.setKPIType("KUALITATIF");
                                    ka.setKPIperspective("KUALITATIF");

                                    ka.setKpiHintList(response.body().get(i).getpA_ViewTransGrades());
                                    masterSetupPositionDetailModelList.add(ka);
                                }


                            }
                            @Override
                            public void onFailure(Call<List<MKualitatif>> call, Throwable t) {
                                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                    @Override
                    public void onFailure(Call<List<MKualitatif>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                    }
                });
                    }
                    @Override
                    public void onFailure(Call<List<PKSettingHeaderKualitatif>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                });
    }

    private void showAddDialog(PositionSetupDetailPKAdapter positionSetupDetailPAAdapter){
        Dialog dialogs = new Dialog(getContext());
        final boolean[] isAll = {true};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.add_pk_kpi_kuantiatif_dialog);
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Window window = dialogs.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogs.setTitle("Title...");
        TextView tvTitle;
        EditText edtBobot,edtKPIDesc;
        TextView edtHint1,edtHint2,edtHint3,edtHint4,edtHint5;
        Button btnSaveKPI;
        AutoCompleteTextView autoCompleteKPIDesc;
        MaterialSpinner spinnerKPIDesc;
        ImageButton btnClose;
        LinearLayout lnProgress;

//        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);

        tvTitle = dialogs.findViewById(R.id.tvTitle);
        btnSaveKPI = dialogs.findViewById(R.id.btnSaveKPI);
        lnProgress = dialogs.findViewById(R.id.linlaHeaderProgress);

        btnClose = dialogs.findViewById(R.id.ib_close);
        spinnerKPIDesc = dialogs.findViewById(R.id.spinnerKPIDesc);
        autoCompleteKPIDesc = dialogs.findViewById(R.id.autoCompleteKPIDesc);
        edtBobot = dialogs.findViewById(R.id.edtBobot);
        edtHint1 = dialogs.findViewById(R.id.edtHint1);
        edtHint2 = dialogs.findViewById(R.id.edtHint2);
        edtHint3 = dialogs.findViewById(R.id.edtHint3);
        edtHint4 = dialogs.findViewById(R.id.edtHint4);
        edtHint5 = dialogs.findViewById(R.id.edtHint5);
        lnProgress.setVisibility(View.VISIBLE);
        spinnerKPIDesc.setBackgroundResource(R.drawable.shapedropdown);
        spinnerKPIDesc.setPadding(25, 10, 25, 10);
        List<String> setup2 = new ArrayList<String>();
        for(int i=0;i<masterSetupPositionDetailModelList.size();i++){
            setup2.add(masterSetupPositionDetailModelList.get(i).getKPIDesc());
        }
        Collections.sort(setup2);
        setup2.add(0,"- PILIH -");
        spinnerKPIDesc.setItems(setup2);
        lnProgress.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, setup2);

        autoCompleteKPIDesc.setThreshold(1);
        autoCompleteKPIDesc.setAdapter(adapter);
        autoCompleteKPIDesc.setTextColor(Color.BLACK);

        autoCompleteKPIDesc.setVisibility(View.GONE);
        autoCompleteKPIDesc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),adapter.getItem(i), Toast.LENGTH_LONG).show();
                for (int ii=0;ii<masterSetupPositionDetailModelList.size();ii++){
                    if(adapter.getItem(i).equals(masterSetupPositionDetailModelList.get(ii).getKPIDesc())){

                        try{edtHint1.setText(masterSetupPositionDetailModelList.get(ii).getKpiHintList().get(4).getKpiGradeName());
                            edtHint2.setText(masterSetupPositionDetailModelList.get(ii).getKpiHintList().get(3).getKpiGradeName());
                            edtHint3.setText(masterSetupPositionDetailModelList.get(ii).getKpiHintList().get(2).getKpiGradeName());
                            edtHint4.setText(masterSetupPositionDetailModelList.get(ii).getKpiHintList().get(1).getKpiGradeName());
                            edtHint5.setText(masterSetupPositionDetailModelList.get(ii).getKpiHintList().get(0).getKpiGradeName());
                            idKPI = masterSetupPositionDetailModelList.get(ii).getPaId();}
                        catch (Exception e){
                            edtHint1.setText("");
                            edtHint2.setText("");
                            edtHint3.setText("");
                            edtHint4.setText("");
                            edtHint5.setText("");
                        }
                        break;
                    }
                    else{
                        edtHint1.setText("");
                        edtHint2.setText("");
                        edtHint3.setText("");
                        edtHint4.setText("");
                        edtHint5.setText("");
                    }
                }
            }
        });

        spinnerKPIDesc.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                for (int i=0;i<masterSetupPositionDetailModelList.size();i++){
                    if(spinnerKPIDesc.getText().equals(masterSetupPositionDetailModelList.get(i).getKPIDesc())){

                        try{edtHint1.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(4).getKpiGradeName());
                        edtHint2.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(3).getKpiGradeName());
                        edtHint3.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(2).getKpiGradeName());
                        edtHint4.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(1).getKpiGradeName());
                        edtHint5.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(0).getKpiGradeName());
                        idKPI = masterSetupPositionDetailModelList.get(i).getPaId();}
                        catch (Exception e){
                            edtHint1.setText("");
                            edtHint2.setText("");
                            edtHint3.setText("");
                            edtHint4.setText("");
                            edtHint5.setText("");
                        }
                        break;
                    }
                    else{
                        edtHint1.setText("");
                        edtHint2.setText("");
                        edtHint3.setText("");
                        edtHint4.setText("");
                        edtHint5.setText("");
                    }
                }
            }
        });

        btnSaveKPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(getContext());
                pd.setMessage("Saving...");
                pd.setCancelable(false);
                pd.show();

                    List<PKSettingHeader> paSettingHeaders = new ArrayList<>();

                    if(!edtHint1.getText().toString().equals("") &&
                            !edtBobot.getText().toString().equals("") &&
                            !spinnerKPIDesc.getText().toString().equals("- PILIH -")){

                        for(int x=0;x<2;x++){

                            if(!edtHint1.getText().toString().equals("") &&
                                    !edtBobot.getText().toString().equals("") &&
                                    !spinnerKPIDesc.getText().toString().equals("- Pilih -")){}

                            List<String> aaa = new ArrayList<>();
                            aaa.add(edtHint5.getText().toString());
                            aaa.add(edtHint4.getText().toString());
                            aaa.add(edtHint3.getText().toString());
                            aaa.add(edtHint2.getText().toString());
                            aaa.add(edtHint1.getText().toString());


                            SetupPositionDetailPKModel setupPositionDetailModel = new SetupPositionDetailPKModel();
                            setupPositionDetailModel.setChecked(false);
                            setupPositionDetailModel.setId(idKPI);
                            setupPositionDetailModel.setKPIType("KUANTITATIF");
                            setupPositionDetailModel.setKPIDesc(spinnerKPIDesc.getText().toString());
                            setupPositionDetailModel.setBobot(edtBobot.getText().toString());
//                            List<KPIHint> kpiHints = new ArrayList<>();
//                            for (int i = 0; i < 5; i++) {
//                                KPIHint kpiHint = new KPIHint();
//                                kpiHint.setKpiGradeID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeID());
//                                kpiHint.setKpiID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiID());
//                                kpiHint.setKpiGradeCode(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeCode());
//                                kpiHint.setKpiGradeName(aaa.get(i));
//                                kpiHints.add(kpiHint);
//                            }

//                            setupPositionDetailModel.setKpiHintList(kpiHints);
                            setupPositionDetailModelList.add(setupPositionDetailModel);
                            //Collections.sort(setupPositionDetailModelList);
//                positionSetupDetailPAAdapter.notifyDataSetChanged();

                            List<PKSettingDetail> paSettingDetailss = new ArrayList<>();
                            for (int i = 0; i < setupPositionDetailModelList.size(); i++) {
                                PKSettingDetail paSettingDetail = new PKSettingDetail();
                                paSettingDetail.setBobot(setupPositionDetailModelList.get(i).getBobot());
                                paSettingDetail.setKpiID(setupPositionDetailModelList.get(i).getId());
                                paSettingDetailss.add(paSettingDetail);
                            }
                            PKSettingHeader paSettingHeader = new PKSettingHeader();
                            paSettingHeader.setYear(paSettingHeaderList.get(0).getYear());
                            paSettingHeader.setTempKPIName(paSettingHeaderList.get(0).getTempKPIName());
                            paSettingHeader.setTempKPIID(paSettingHeaderList.get(0).getTempKPIID());
                            paSettingHeader.setChecked(paSettingHeaderList.get(0).isChecked());
                            paSettingHeader.setStatusDeployYN(paSettingHeaderList.get(0).getStatusDeployYN());
                            //paSettingHeader.setPaSettingSettings(paSettingHeaderList.get(0).getPaSettingSettings());
                            paSettingHeader.setEmpNIK(usr.get(0).getEmpNIK());
                            paSettingHeader.setPaSettingDetails(paSettingDetailss);
                            paSettingHeaders.add(paSettingHeader);

                            setPaSettingHeaderList(paSettingHeaders);
                            setSetupPositionDetailModels(setupPositionDetailModelList);
                        }
                        paSettingHeaders.remove(0);

                        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                        ArrayList<UserRealmModel> usr;
                        usr = userRealmHelper.findAllArticle();
                        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                        Call<Void> call = apiService.postPAKPISettingHeaderPK(paSettingHeaders, "Bearer " + usr.get(0).getToken());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int statusCode = response.code();
                                Toast.makeText(getContext(), "Sukses Input", Toast.LENGTH_SHORT).show();
//                            setSetupPositionDetailModels(setupPositionDetailModelList);
//                            setPaSettingHeaderList(paSettingHeaders);
//
//                            PositionSetupDetailPAAdapter positionSetupDetailPAAdapter = new PositionSetupDetailPAAdapter(setupPositionDetailModelList, getContext(), getActivity(), PositionSetupDetailPAFragment.this, paSettingHeaderList, "KUANTITATIF");
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                            rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
//                            rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
//                            rvSetupEmployeeDetail.setAdapter(positionSetupDetailPAAdapter);
                                pd.dismiss();
                                prepareDataPositionKuantitatifPA();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        });

                    }

                    else{
                        Toast.makeText(getContext(),"Masih Ada Field yang Kosong!",Toast.LENGTH_LONG).show();
                        pd.dismiss();

                    }

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

    private void showAddDialogKualitatif(PositionSetupDetailKualitatifPKAdapter positionSetupDetailKualitatifPAAdapter){
        Dialog dialogs = new Dialog(getContext());

        final boolean[] isAll = {true};

        final String[] semester = {"1"};
        //Toast.makeText(context,devPlanDetailList.get(0).getDevplanMethodDesk(),Toast.LENGTH_SHORT).show();
        dialogs.setContentView(R.layout.add_pk_kpi_kuantiatif_dialog);
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Window window = dialogs.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogs.setTitle("Title...");
        TextView tvTitle;
        EditText edtBobot,edtKPIDesc;
        TextView edtHint1,edtHint2,edtHint3,edtHint4,edtHint5;
        Button btnSaveKPI;
        MaterialSpinner spinnerKPIDesc, spinnerCR;
        ImageButton btnClose;

//        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);

        tvTitle = dialogs.findViewById(R.id.tvTitle);
        btnSaveKPI = dialogs.findViewById(R.id.btnSaveKPI);

        btnClose = dialogs.findViewById(R.id.ib_close);
        spinnerKPIDesc = dialogs.findViewById(R.id.spinnerKPIDesc);
        spinnerCR = dialogs.findViewById(R.id.spinnerCR);

        edtBobot = dialogs.findViewById(R.id.edtBobot);
        edtHint1 = dialogs.findViewById(R.id.edtHint1);
        edtHint2 = dialogs.findViewById(R.id.edtHint2);
        edtHint3 = dialogs.findViewById(R.id.edtHint3);
        edtHint4 = dialogs.findViewById(R.id.edtHint4);
        edtHint5 = dialogs.findViewById(R.id.edtHint5);


        if(KPIType.equals("KUALITATIF")){
            tvTitle.setText("Add Faktor Kualitatif");
            spinnerCR.setVisibility(View.VISIBLE);
        }

        spinnerCR.setBackgroundResource(R.drawable.shapedropdown);
        spinnerCR.setPadding(25, 10, 25, 10);
        List<String> setup3 = new ArrayList<String>();
        setup3.add("- PILIH CR -");
        for(int i=0;i<5;i++){
            setup3.add(Integer.toString(i+1));
        }
        spinnerCR.setItems(setup3);

        spinnerKPIDesc.setBackgroundResource(R.drawable.shapedropdown);
        spinnerKPIDesc.setPadding(25, 10, 25, 10);
        List<String> setup2 = new ArrayList<String>();
        for(int i=0;i<masterSetupPositionDetailModelList.size();i++){
            setup2.add(masterSetupPositionDetailModelList.get(i).getKPIDesc());
        }
        Collections.sort(setup2);
        setup2.add(0,"- PILIH -");

        spinnerKPIDesc.setItems(setup2);

        spinnerKPIDesc.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                for (int i=0;i<masterSetupPositionDetailModelList.size();i++){
                    if(spinnerKPIDesc.getText().equals(masterSetupPositionDetailModelList.get(i).getKPIDesc())){
                        edtHint1.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(4).getKpiGradeName());
                        edtHint2.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(3).getKpiGradeName());
                        edtHint3.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(2).getKpiGradeName());
                        edtHint4.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(1).getKpiGradeName());
                        edtHint5.setText(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(0).getKpiGradeName());
                        idKPI = masterSetupPositionDetailModelList.get(i).getPaId();
                        break;
                    }
                }
            }
        });

        btnSaveKPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog pd = new ProgressDialog(getContext());
                pd.setMessage("Saving...");
                pd.setCancelable(false);
                pd.show();


                if(isAll[0]){

                    if(!edtHint1.getText().toString().equals("") &&
                            (!edtBobot.getText().toString().equals("0") && !edtBobot.getText().toString().equals("")) &&
                            !spinnerKPIDesc.getText().toString().equals("- PILIH -")) {
                        List<PKSettingHeaderKualitatif> paSettingHeaders = new ArrayList<>();
                        for (int x = 0; x < 2; x++) {
                            List<String> aaa = new ArrayList<>();
                            aaa.add(edtHint5.getText().toString());
                            aaa.add(edtHint4.getText().toString());
                            aaa.add(edtHint3.getText().toString());
                            aaa.add(edtHint2.getText().toString());
                            aaa.add(edtHint1.getText().toString());

                            SetupPositionDetailPKModel setupPositionDetailModel = new SetupPositionDetailPKModel();
                            setupPositionDetailModel.setChecked(false);
                            setupPositionDetailModel.setId(idKPI);
                            setupPositionDetailModel.setPaId(idKPI);
                            if (spinnerCR.getText().toString().length() > 2) {
                                setupPositionDetailModel.setCr(3);
                            } else {
                                setupPositionDetailModel.setCr(Integer.parseInt(spinnerCR.getText().toString()));
                            }
                            setupPositionDetailModel.setKPIType("KUALITATIF");
                            setupPositionDetailModel.setKPIperspective("KUALITATIF");
                            setupPositionDetailModel.setKPIDesc(spinnerKPIDesc.getText().toString());
                            setupPositionDetailModel.setBobot(edtBobot.getText().toString());
                            setupPositionDetailModel.setTemplateId(paSettingHeaderKualitatifList.get(0).getTempCompID());
                            List<KPIHint> kpiHints = new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                KPIHint kpiHint = new KPIHint();
                                kpiHint.setKpiGradeID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeID());
                                kpiHint.setKpiID(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiID());
                                kpiHint.setKpiGradeCode(masterSetupPositionDetailModelList.get(i).getKpiHintList().get(i).getKpiGradeCode());
                                kpiHint.setKpiGradeName(aaa.get(i));
                                kpiHints.add(kpiHint);
                            }

                            setupPositionDetailModel.setKpiHintList(kpiHints);
                            setupPositionDetailModelList.add(setupPositionDetailModel);

                            //Collections.sort(setupPositionDetailModelList);
//                positionSetupDetailPAAdapter.notifyDataSetChanged();

                            List<PKSettingDetailKualitatif> paSettingDetailss = new ArrayList<>();
                            for (int i = 0; i < setupPositionDetailModelList.size(); i++) {
                                PKSettingDetailKualitatif paSettingDetail = new PKSettingDetailKualitatif();
                                paSettingDetail.setBobot(setupPositionDetailModelList.get(i).getBobot());
                                paSettingDetail.setSemester("0");
                                paSettingDetail.setCompID(setupPositionDetailModelList.get(i).getId());
                                paSettingDetail.setCr(Integer.toString(setupPositionDetailModelList.get(i).getCr()));
                                paSettingDetailss.add(paSettingDetail);
                            }


                            PKSettingHeaderKualitatif paSettingHeader = new PKSettingHeaderKualitatif();
                            paSettingHeader.setYear(paSettingHeaderKualitatifList.get(0).getYear());
                            paSettingHeader.setTempCompName(paSettingHeaderKualitatifList.get(0).getTempCompName());
                            paSettingHeader.setTempCompID(paSettingHeaderKualitatifList.get(0).getTempCompID());
                            paSettingHeader.setChecked(paSettingHeaderKualitatifList.get(0).isChecked());
                            paSettingHeader.setTabDab(paSettingHeaderKualitatifList.get(0).getTabDab());
                            paSettingHeader.setTempCompSubYN(paSettingHeaderKualitatifList.get(0).getTempCompSubYN());
                            paSettingHeader.setPaSettingSettings(paSettingHeaderKualitatifList.get(0).getPaSettingSettings());
                            paSettingHeader.setPaSettingDetails(paSettingDetailss);
                            paSettingHeaders.add(paSettingHeader);
                            paSettingHeader.setEmpNIK(usr.get(0).getEmpNIK());

                            setPaSettingHeaderKualitatifList(paSettingHeaders);
                            setSetupPositionDetailModels(setupPositionDetailModelList);

                        }
                        paSettingHeaders.remove(0);

                        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                        ArrayList<UserRealmModel> usr;
                        usr = userRealmHelper.findAllArticle();
                        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                        Call<Void> call = apiService.postPAKPISettingHeaderKualitatifPK(paSettingHeaders, "Bearer " + usr.get(0).getToken());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int statusCode = response.code();
                                Toast.makeText(getContext(), "Sukses Input", Toast.LENGTH_SHORT).show();
//                            //positionSetupDetailKualitatifPAAdapter.notifyDataSetChanged();
//                            setSetupPositionDetailModels(setupPositionDetailModelList);
//                            setPaSettingHeaderKualitatifList(paSettingHeaderKualitatifList);
//
//                            PositionSetupDetailKualitatifPAAdapter positionSetupDetailKualitatifPAAdapter = new PositionSetupDetailKualitatifPAAdapter(setupPositionDetailModelList, getContext(), getActivity(), PositionSetupDetailPAFragment.this, paSettingHeaderKualitatifList, "KUALITATIF");
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                            rvSetupEmployeeDetail.setLayoutManager(mLayoutManager);
//                            rvSetupEmployeeDetail.setItemAnimator(new DefaultItemAnimator());
//                            rvSetupEmployeeDetail.setAdapter(positionSetupDetailKualitatifPAAdapter);
                                pd.dismiss();
                                prepareDataPositionKualitatifPA();


                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        });
                    }

                    else{
                        Toast.makeText(getContext(),"Masih Ada Field Yang Kosong!",Toast.LENGTH_LONG).show();
                        pd.dismiss();

                    }
                }
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

    @Override
    public List<SetupPositionDetailPKModel> getSetupPositionDetailModels() {
        return setupPositionDetailModelList;
    }

    @Override
    public void setSetupPositionDetailModels(List<SetupPositionDetailPKModel> setupPositionDetailModelList1) {
        this.setupPositionDetailModelList = setupPositionDetailModelList1;
    }

    @Override
    public List<PKSettingHeaderKualitatif> getPASettingHeaderKualitatifList() {
        return paSettingHeaderKualitatifList;
    }

    @Override
    public void setPaSettingHeaderKualitatifList(List<PKSettingHeaderKualitatif> paSettingHeaderList) {
        this.paSettingHeaderKualitatifList=paSettingHeaderList;
    }

    @Override
    public void refreshDataKualitatif() {
        prepareDataPositionKualitatifPA();
    }

    @Override
    public List<PKSettingHeader> getPASettingHeader() {
        return paSettingHeaderList;
    }

    @Override
    public void setPaSettingHeaderList(List<PKSettingHeader> paSettingHeaderList) {
            this.paSettingHeaderList = paSettingHeaderList;
    }

    @Override
    public void setUbah(boolean isUbah) {
        this.isUbah = isUbah;
    }

    @Override
    public boolean getUbah() {
        return isUbah;
    }

    @Override
    public void refreshData() {
        prepareDataPositionKuantitatifPA();
    }


}
