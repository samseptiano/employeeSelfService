package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.DevelopmentPlanAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.ListDevPlanAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.ListImprovementAreaFullScreenAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.HomeFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.PostApproveDevPlan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.UserListDevPlan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.EmailSentModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DevelopmentPlanDetailFragment extends Fragment implements ListImprovementAreaFullScreenAdapter.EventListener, ListDevPlanAdapter.EventListener {

    View rootView;
    MaterialSpinner spinnerImprovement;
    Button btnAdd,btnDel, btnApproveDevplan,btnSaveDevPlan;
    LinearLayout lnDevPlan;
    ListImprovementAreaFullScreenAdapter listImprovementAreaAdapter;
    LayoutInflater inflate;
    DatePickerDialog  datePickerDialog;
    SimpleDateFormat dateFormatter;
    ArrayList<UserRealmModel> usr;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    List<DevPlanHeader> devPlanHeaderList = new ArrayList<>();
    KPIHeader kpiHeader;
    List<KPIQuestions> question;
    String isEditable = "Y";
    LinearLayout lnSpinnerDevPlan, lnImprovementArea;
    SwipeRefreshLayout pullToRefresh;

    boolean diUbah=false;
    //======================== Sub Detail ==============================


    static IDPLayoutListModel idpLayoutListModel;

    static List<IDPLayoutListModel> listIDP;
    //=====================================================================

    int ii=0;

    TextView tvEmpName, tvJobtitle, tvDept;
    CircleImageView imgEmpPhoto;
    String empName, jobTitle, dept, branchName, jobStatus, empPhoto, empNik, hasApprove,canApprove,apprseq,compid="",fromGotoIDP="",compName="", orgname="";
    RecyclerView rvImprovementArea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList("lnList", new ArrayList<Department>(lnList.getList()));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_development_plan_detail, container, false);

        pullToRefresh = rootView.findViewById(R.id.swipeContainer);

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        usr = userRealmHelper.findAllArticle();

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_back_arrow);
        actionBar.setTitle("");





        actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    //================================================
                    if(!diUbah) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    else{
                        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                .setMessage("Apakah anda ingin menyimpan IDP yang telah diisi sebelum keluar dari menu ini??")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Context context = getContext();
                                        saveDevPlan(context);
                                        getActivity().getSupportFragmentManager().popBackStack();
                                        //close();
                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        getActivity().getSupportFragmentManager().popBackStack();
                                    }
                                })
                                .show();
                        alertbox.setOnShowListener( new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey));
                                alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

                            }
                        });
                    }
                    //================================================
                }
                catch (Exception e){
                    //Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            empNik = bundle.getString("empNik");
            empName = bundle.getString("empName");
            jobTitle = bundle.getString("jobTitle");
            dept = bundle.getString("dept");
            jobStatus = bundle.getString("jobStatus");
            branchName = bundle.getString("branchName");
            empPhoto = bundle.getString("empPhoto");
            hasApprove = bundle.getString("hasApprove");
            canApprove = bundle.getString("canApprove");
            orgname = bundle.getString("orgName");
            compid = bundle.getString("COMPID");
            compName = bundle.getString("COMPNAME");
            apprseq = bundle.getString("apprseq");
            fromGotoIDP = bundle.getString("GOTOIDP");
        }


        spinnerImprovement = rootView.findViewById(R.id.spinnerImprovement);
        spinnerImprovement.setBackgroundResource(R.drawable.shapedropdown);
        spinnerImprovement.setPadding(25, 10, 25, 10);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnDel = rootView.findViewById(R.id.btnDelete);
        lnDevPlan = rootView.findViewById(R.id.lnAddDevPlan);
        tvEmpName = rootView.findViewById(R.id.tv_employeeName);
        tvJobtitle = rootView.findViewById(R.id.tv_job_title);
        tvDept = rootView.findViewById(R.id.tv_dept);
        imgEmpPhoto = rootView.findViewById(R.id.imgEmpPhoto);
        rvImprovementArea = rootView.findViewById(R.id.rv_improvement_area);
        btnApproveDevplan = rootView.findViewById(R.id.btnApproveDevPlan);
        btnSaveDevPlan = rootView.findViewById(R.id.btnSaveDevPlan);
        lnSpinnerDevPlan = rootView.findViewById(R.id.linlaSpinnerDevPlanProgress);
        lnImprovementArea = rootView.findViewById(R.id.linlaImprovementAreaProgress);



        if(hasApprove.equals("Y")){
            btnAdd.setVisibility(View.GONE);
            btnDel.setVisibility(View.GONE);
            btnSaveDevPlan.setVisibility(View.GONE);
            btnApproveDevplan.setVisibility(View.GONE);
            spinnerImprovement.setVisibility(View.GONE);
            isEditable="N";
        }

        Picasso.get()
                .load(empPhoto)
                .placeholder(R.drawable.user)
                .error(R.drawable.imgalt)
                .into(imgEmpPhoto);

        //============================================ DUMMY =======================================================================
        lnImprovementArea.setVisibility(View.VISIBLE);
        lnSpinnerDevPlan.setVisibility(View.VISIBLE);
        try {
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    prepareData();

                    pullToRefresh.setRefreshing(false);
                }
            });
            prepareData();
        }
        catch (Exception e){

        }

//        Call<KPIHeader> call = apiService.getKPIHeaderPA(empNik, Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
//        call.enqueue(new Callback<KPIHeader>() {
//            @Override
//            public void onResponse(Call<KPIHeader> call, Response<KPIHeader> response) {
//
//                int statusCode = response.code();
//                try {
//                    kpiHeader = new KPIHeader();
//                    kpiHeader.setDevPlanHeaderList(response.body().getDevPlanHeaderList());
//                    for (int i = 0; i < kpiHeader.getDevPlanHeaderList().size(); i++) {
//                        kpiHeader.getDevPlanHeaderList().get(i).setDevPlanDetail(response.body().getDevPlanHeaderList().get(i).getDevPlanDetail());
//                    }
//                    kpiHeader.setMasterDevelopmentPlanList(response.body().getMasterDevelopmentPlanList());
//                    kpiHeader.setBobot(response.body().getBobot());
//                    kpiHeader.setSemester("1");
//                    kpiHeader.setTahun(response.body().getTahun());
//                    kpiHeader.setStatus(response.body().getStatus());
//                    kpiHeader.setStrength(response.body().getStrength());
//                    kpiHeader.setStatus1(response.body().getStatus1());
//                    kpiHeader.setStatus2(response.body().getStatus2());
//
//                    devPlanHeaderList = kpiHeader.getDevPlanHeaderList();
//
//
//                    listImprovementAreaAdapter = new ListImprovementAreaFullScreenAdapter(devPlanHeaderList,kpiHeader, getContext(), "Y",empPhoto,empName,jobTitle);
//                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
//
//                    //Setting the adapter
//                    rvImprovementArea.setAdapter(listImprovementAreaAdapter);
//                    rvImprovementArea.setLayoutManager(llm);
//                }
//                catch (Exception e){
//
//                }
//
//                //Toast.makeText(getContext(), kh.getTahun(), Toast.LENGTH_LONG).show();
//            }
//            @Override
//            public void onFailure(Call<KPIHeader> call, Throwable t) {
//                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
//            }
//        });

        //====================================================================================================================


        //================================ foto bawahan =======================
//        try {
//            //Toast.makeText(context,userList.getEmpPhoto(),Toast.LENGTH_LONG).show();
//            byte[] encodeByte = Base64.decode(empPhoto, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//
//            Display display = getActivity().getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//
//            if (size.x > 1081) {
//                int imageWidth = bitmap.getWidth();
//                int imageHeight = bitmap.getHeight();
//
//                //Display display = getActivity().getWindowManager().getDefaultDisplay();
//                //Point size = new Point();
//                display.getSize(size);
//                int width = size.x - (size.x / 3);
//                int height = size.y - (size.y / 3);
//
//                int newWidth = width; //this method should return the width of device screen.
//                float scaleFactor = (float) newWidth / (float) imageWidth;
//                int newHeight = (int) (imageHeight * scaleFactor);
//
//                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
//                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
//            } else {
//                int imageWidth = bitmap.getWidth();
//                int imageHeight = bitmap.getHeight();
//
//                //Display display = getActivity().getWindowManager().getDefaultDisplay();
//                //Point size = new Point();
//                display.getSize(size);
//                int width = size.x - (size.x / 3);
//                int height = size.y - (size.y / 3);
//
//                int newWidth = width; //this method should return the width of device screen.
//                float scaleFactor = (float) newWidth / (float) imageWidth;
//                int newHeight = (int) (imageHeight * scaleFactor);
//
//                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
//                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
//            }
//
//            imgEmpPhoto.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            e.getMessage();
//            //return null;
//        }
        //===========================================================================

        tvEmpName.setText(empName);
        tvJobtitle.setText(jobTitle);
        tvDept.setText(branchName);

        return rootView;
    }


    private void prepareData(){
        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Observable<KPIHeader> observable = apiService.getKPIHeaderPA(empNik,  Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<KPIHeader>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(KPIHeader response) {
                        try {
                            kpiHeader = new KPIHeader();
                            kpiHeader=response;
                            kpiHeader.setKpiQuestionsList(response.getKpiQuestionsList());
                            kpiHeader.setDevPlanHeaderList(response.getDevPlanHeaderList());
                            for (int i = 0; i < kpiHeader.getDevPlanHeaderList().size(); i++) {
                                kpiHeader.getDevPlanHeaderList().get(i).setDevPlanDetail(response.getDevPlanHeaderList().get(i).getDevPlanDetail());
                            }
                            kpiHeader.setMasterDevelopmentPlanList(response.getMasterDevelopmentPlanList());
                            kpiHeader.setBobot(response.getBobot());
                            kpiHeader.setSemester("1");
                            kpiHeader.setTahun(response.getTahun());
                            kpiHeader.setStatus(response.getStatus());
                            kpiHeader.setStrength(response.getStrength());
                            kpiHeader.setStatus1(response.getStatus1());
                            kpiHeader.setStatus2(response.getStatus2());

                            devPlanHeaderList = kpiHeader.getDevPlanHeaderList();
                            Collections.sort(devPlanHeaderList);

                            listImprovementAreaAdapter = new ListImprovementAreaFullScreenAdapter(devPlanHeaderList,kpiHeader, getContext(), "Y",empPhoto,empName,branchName,DevelopmentPlanDetailFragment.this,getActivity());
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());

                            //Setting the adapter
                            rvImprovementArea.setAdapter(listImprovementAreaAdapter);
                            rvImprovementArea.setLayoutManager(llm);
                            for(int i=0;i<devPlanHeaderList.size();i++){
                                if(devPlanHeaderList.get(i).getCOMPID().equals(compid) && (devPlanHeaderList.get(i).getCOMPNAME().equals(compName))){
                                    listImprovementAreaAdapter.showIDPDialog(empPhoto, empName, jobTitle, compName,devPlanHeaderList.get(i).getDevPlanDetail(),i ,fromGotoIDP);
                                }

                            }



                            lnImprovementArea.setVisibility(View.GONE);
                            lnSpinnerDevPlan.setVisibility(View.GONE);
                            List<String> improvement = new ArrayList<String>();
                            for(int i=0;i<kpiHeader.getKpiQuestionsList().size();i++){
                                if(kpiHeader.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")){
                                    improvement.add(kpiHeader.getKpiQuestionsList().get(i).getKPIDesc());
                                }
                            }
                            spinnerImprovement.setItems(improvement);

                            btnAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    int ctr=0;
                                    if(!spinnerImprovement.getText().toString().equals("- Pilih  -")){
                                        DevPlanHeader devPlanHeader = new DevPlanHeader();
                                        devPlanHeader.setCOMPNAME(spinnerImprovement.getText().toString());
                                        devPlanHeader.setChecked(false);
                                        devPlanHeader.setDevPlanDetail(new ArrayList<DevPlanDetail>());
                                        devPlanHeader.setPAID(kpiHeader.getKpiQuestionsList().get(0).getPaId());
                                        for(int i=0;i<kpiHeader.getKpiQuestionsList().size();i++){
                                            if(kpiHeader.getKpiQuestionsList().get(i).getKPIDesc().equals(spinnerImprovement.getText())){
                                                devPlanHeader.setCOMPID(kpiHeader.getKpiQuestionsList().get(i).getKpiId());
                                                devPlanHeader.setDEVID(kpiHeader.getKpiQuestionsList().get(i).getKpiId());

                                            }
                                        }
                                        for(int i=0;i<kpiHeader.getDevPlanHeaderList().size();i++) {
                                            if (spinnerImprovement.getText().toString().equals(kpiHeader.getDevPlanHeaderList().get(i).getCOMPNAME())) {
                                                ctr++;
                                                Toast.makeText(getContext(),"Anda Sudah memilih Improvement Area Ini",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        if(ctr==0) {
                                            devPlanHeaderList.add(devPlanHeader);
                                            listImprovementAreaAdapter.notifyDataSetChanged();
                                            listImprovementAreaAdapter.getData();
                                        }

                                    }




                                }
                            });


                            btnDel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for(int i=0;i<devPlanHeaderList.size();i++){
                                        if(devPlanHeaderList.get(i).getChecked()){
                                            devPlanHeaderList.remove(i);
                                        }
                                        listImprovementAreaAdapter.notifyDataSetChanged();
                                        listImprovementAreaAdapter.getData();

                                    }
                                }
                            });

                            btnApproveDevplan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    int ctr=0;

                                    PostApproveDevPlan postApproveDevPlan = new PostApproveDevPlan();

                                    postApproveDevPlan.setPAID(kpiHeader.getKpiQuestionsList().get(0).getPaId());
                                    postApproveDevPlan.setAPPRSEQ(apprseq);
                                    postApproveDevPlan.setEMPNIK(usr.get(0).getEmpNIK());

                                    List<KPIAnswer> kpiAnswerList = new ArrayList<>();
                                    KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

                                    int fgSubmit = 0;
                                    for (int i = 0; i < kpiHeader.getKpiQuestionsList().size(); i++) {
                                        KPIAnswer kpiAnswer = new KPIAnswer();


                                        if (kpiHeader.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                                            kpiAnswer.setCOMPID(kpiHeader.getKpiQuestionsList().get(i).getKpiId());
                                            kpiAnswer.setKPIID("0");
                                        } else {
                                            kpiAnswer.setKPIID(kpiHeader.getKpiQuestionsList().get(i).getKpiId());
                                            kpiAnswer.setCOMPID("0");
                                        }



                                        kpiAnswer.setCP(Integer.toString(kpiHeader.getKpiQuestionsList().get(i).getCheckedId()));
                                        kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                                        kpiAnswer.setCP(kpiHeader.getKpiQuestionsList().get(i).getCP());
                                        kpiAnswer.setPAID(kpiHeader.getKpiQuestionsList().get(i).getPaId());
                                        kpiAnswer.setKPITYPE(kpiHeader.getKpiQuestionsList().get(i).getKPIcategory());
                                        kpiAnswer.setSEMESTER(kpiHeader.getKpiQuestionsList().get(i).getSemester());
                                        kpiAnswer.setEVIDENCES(kpiHeader.getKpiQuestionsList().get(i).getEvidence());
                                        kpiAnswer.setACTUAL(kpiHeader.getKpiQuestionsList().get(i).getActual());
                                        kpiAnswer.setTARGET(kpiHeader.getKpiQuestionsList().get(i).getTarget());
                                        kpiAnswerList.add(kpiAnswer);
                                        //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();

                                        kpiAnswerList1.setNIKBAWAHAN(kpiHeader.getNIK());
                                        kpiAnswerList1.setPAID(kpiHeader.getKpiQuestionsList().get(0).getPaId());
                                        kpiAnswerList1.setSTATUS(kpiHeader.getStatus());
                                        kpiAnswerList1.setSTRENGTH(kpiHeader.getStrength());
                                        kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                                        kpiAnswerList1.setDevPlanHeaderList(kpiHeader.getDevPlanHeaderList());

                                    }

                                    try {
                                        for (int i = 0; i < kpiHeader.getDevPlanHeaderList().size(); i++) {
                                            for (int j = 0; j < kpiHeader.getDevPlanHeaderList().get(i).getDevPlanDetail().size(); j++) {
                                                if (kpiHeader.getDevPlanHeaderList().get(i).getDevPlanDetail().get(j).getDEVPLANACTIVITIES().equals("") ||
                                                        kpiHeader.getDevPlanHeaderList().get(i).getDevPlanDetail().get(j).getDEVPLANDUEDATE().equals("") ||
                                                        kpiHeader.getDevPlanHeaderList().get(i).getDevPlanDetail().get(j).getDEVPLANKPI().equals("")) {
                                                    ctr++;
                                                }
                                            }
                                        }
                                    }
                                    catch (Exception e){

                                    }



                                    if(ctr==0){

                                        Call<KPIAnswerList> call2 = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
                                        call2.enqueue(new Callback<KPIAnswerList>() {
                                            @Override
                                            public void onResponse(Call<KPIAnswerList> call2, Response<KPIAnswerList> response) {
                                                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                                                Call<Void> call = apiService.approveDevPlan(postApproveDevPlan,"Bearer "+usr.get(0).getToken());
                                                call.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        int statusCode = response.code();
                                                        Toast.makeText(getContext(),"Data berhasil diapprove",Toast.LENGTH_LONG).show();
                                                        EmailSentModel emailSentModel = new EmailSentModel();
//                                                        emailSentModel.SUBJECT="[HCKiosk] IDP karyawan atas nama "+empName+" menunggu approval";
//                                                        emailSentModel.MESSAGE="<br /> <br /> Bersama ini kami informasikan, IDP untuk karyawan dibawah ini menunggu approval Bapak/Ibu</p>\n" +
//                                                                "<table style=\"border: 0px;\">\n" +
//                                                                "<tbody>\n" +
//                                                                "<tr>\n" +
//                                                                "<td style=\"border: 0px;\">Nama</td>\n" +
//                                                                "<td style=\"border: 0px;\">: "+empNik+"</td>\n" +
//                                                                "</tr>\n" +
//                                                                "<tr>\n" +
//                                                                "<td style=\"border: 0px;\">Jabatan</td>\n" +
//                                                                "<td style=\"border: 0px;\">: "+empName+"</td>\n" +
//                                                                "</tr>\n" +
//                                                                "<tr>\n" +
//                                                                "<td style=\"border: 0px;\">Organisasi</td>\n" +
//                                                                "<td style=\"border: 0px;\">: "+orgname+"</td>\n" +
//                                                                "</tr>\n" +
//                                                                "</tbody>\n" +
//                                                                "</table>\n" +
//                                                                "<p><br /> Approval IDP dapat dilakukan melalui aplikasi HC Kiosk yang dapat diakses <strong>melalui HC Kiosk web <a href=\"#\">(klik disini)</a> atau HC Kiosk Mobile. </strong> <br /> <br /> Terima kasih <br /> <br /> <em>(Note: email ini adalah hasil generate otomatis dari aplikasi HC Kiosk)</em></p>";
//                                                        emailSentModel.SENDERNAME=empName;
//                                                        emailSentModel.SENDERNIK=empNik;
//                                                        emailSentModel.SENDEREMAIL="hr.service@enseval.com";
//                                                        emailSentModel.MAILTYPE="IDP-"+Integer.toString(Integer.parseInt(apprseq)+1);
//                                                        emailSentModel.RECEIVERNAME=kpiHeader.getAtasanLangsung();
//                                                        emailSentModel.RECEIVERNIK=kpiHeader.getNIKAtasanLangsung();
//                                                        emailSentModel.RECEIVEREMAIL=kpiHeader.getEmailAtasan1();
//
//                                                        sendEmail(emailSentModel);

                                                        getActivity().getSupportFragmentManager().popBackStack();


                                                    }
                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                                                    }
                                                });
//

                                            }
                                            @Override
                                            public void onFailure(Call<KPIAnswerList> call2, Throwable t) {
                                                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                                                Call<Void> call = apiService.approveDevPlan(postApproveDevPlan,"Bearer "+usr.get(0).getToken());
                                                call.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        int statusCode = response.code();
                                                        Toast.makeText(getContext(),"Data berhasil diapprove",Toast.LENGTH_LONG).show();
                                                        EmailSentModel emailSentModel = new EmailSentModel();
//                                                        emailSentModel.SUBJECT="[HCKiosk] IDP karyawan atas nama "+empName+" menunggu approval";
//                                                        emailSentModel.MESSAGE="<br /> <br /> Bersama ini kami informasikan, IDP untuk karyawan dibawah ini menunggu approval Bapak/Ibu</p>\n" +
//                                                                "<table style=\"border: 0px;\">\n" +
//                                                                "<tbody>\n" +
//                                                                "<tr>\n" +
//                                                                "<td style=\"border: 0px;\">Nama</td>\n" +
//                                                                "<td style=\"border: 0px;\">: "+empNik+"</td>\n" +
//                                                                "</tr>\n" +
//                                                                "<tr>\n" +
//                                                                "<td style=\"border: 0px;\">Jabatan</td>\n" +
//                                                                "<td style=\"border: 0px;\">: "+empName+"</td>\n" +
//                                                                "</tr>\n" +
//                                                                "<tr>\n" +
//                                                                "<td style=\"border: 0px;\">Organisasi</td>\n" +
//                                                                "<td style=\"border: 0px;\">: "+orgname+"</td>\n" +
//                                                                "</tr>\n" +
//                                                                "</tbody>\n" +
//                                                                "</table>\n" +
//                                                                "<p><br /> Approval IDP dapat dilakukan melalui aplikasi HC Kiosk yang dapat diakses <strong>melalui HC Kiosk web <a href=\"#\">(klik disini)</a> atau HC Kiosk Mobile. </strong> <br /> <br /> Terima kasih <br /> <br /> <em>(Note: email ini adalah hasil generate otomatis dari aplikasi HC Kiosk)</em></p>";
//                                                        emailSentModel.SENDERNAME=empName;
//                                                        emailSentModel.SENDERNIK=empNik;
//                                                        emailSentModel.SENDEREMAIL="hr.service@enseval.com";
//                                                        emailSentModel.MAILTYPE="IDP-"+Integer.toString(Integer.parseInt(apprseq)+1);
//                                                        emailSentModel.RECEIVERNAME=kpiHeader.getAtasanLangsung();
//                                                        emailSentModel.RECEIVERNIK=kpiHeader.getNIKAtasanLangsung();
//                                                        emailSentModel.RECEIVEREMAIL=kpiHeader.getEmailAtasan1();
//
//                                                        sendEmail(emailSentModel);

                                                        getActivity().getSupportFragmentManager().popBackStack();


                                                    }
                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                            }
                                        });
                                    }
                                    else{
                                        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                                .setMessage("Data Individual Development Plan belum lengkap, silakan melengkapi sebelum approve")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                    // do something when the button is clicked
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        arg0.dismiss();

                                                    }
                                                })
                                                .show();
                                    }

                                }
                            });
                            btnSaveDevPlan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    saveDevPlan(getContext());
                                }
                            });



                        }
                        catch (Exception e){

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "An error occurred in the Retrofit request. Perhaps no response/cache", Toast.LENGTH_SHORT).show();
                        lnImprovementArea.setVisibility(View.GONE);
                        lnSpinnerDevPlan.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        lnImprovementArea.setVisibility(View.GONE);
                        lnSpinnerDevPlan.setVisibility(View.GONE);
                    }
                });

    }

//    private void showIDPDialog(Dialog dialogs, String idpTitle, int DevPlanPosition){
//        dialogs = new Dialog(getContext(),android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
//
//        dialogs.setContentView(R.layout.idp_dialog_fullscreen);
//        dialogs.setTitle("Title...");
//        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialogs.setCanceledOnTouchOutside(false);
//        dialogs.setCancelable(false);
//
//        WindowManager manager = (WindowManager) getContext().getSystemService(Activity.WINDOW_SERVICE);
//        int width, height;
//        ViewGroup.LayoutParams params;
//
//        final boolean[] input = {false};
//
//        final int[] ctr = {1};
//
//        View myView;
//        ViewGroup parent;
//        LinearLayout formLayout;
//
////        myView = dialogs.findViewById(R.id.lnIdpDetail);
////        parent = (ViewGroup) myView.getParent();
////        parent.removeView(myView);
//
//        formLayout = (LinearLayout)dialogs.findViewById(R.id.formLayout);
//        formLayout.removeAllViews();
//        formLayout.setOrientation(LinearLayout.VERTICAL);
//
//        ImageButton closeButton = (ImageButton) dialogs.findViewById(R.id.ib_close);
//        TextView tvIdpTitle = dialogs.findViewById(R.id.tvIdpTitle);
//        Button btnAdd = dialogs.findViewById(R.id.btnAdd);
//        Button btnDelete = dialogs.findViewById(R.id.btnDelete);
//
//        MaterialSpinner spinnerImprovement = dialogs.findViewById(R.id.spinnerImprovement);
//        spinnerImprovement.setBackgroundResource(R.drawable.shapedropdown);
//
//        List<View> lnList = new ArrayList<>();
//
//        List<CheckBox> cbList = new ArrayList<>();
//        List<TextView> tvNumbList = new ArrayList<>();
//        List<TextView> tvTitleList = new ArrayList<>();
//        List<EditText>edtDevelopmentActivitiesList = new ArrayList<>();
//        List<ImageButton>imgExpandList = new ArrayList<>();
//        List<LinearLayout>lnExpandList = new ArrayList<>();
//        List<EditText>edtKPIList = new ArrayList<>();
//        List<EditText>edtDueDateList = new ArrayList<>();
//        List<EditText>edtMentorsList = new ArrayList<>();
//        List<EditText>edtAchievementRecommendationList = new ArrayList<>();
//
//
//        final int[] ii = {0};
//
//
//        List<String> improvement = new ArrayList<String>();
//        improvement.add("Dummy 1");
//        improvement.add("Dummy 2");
//        improvement.add("Dummy 3");
//        spinnerImprovement.setItems(improvement);
//
//        tvIdpTitle.setText(idpTitle);
//
//
//        try {
//
//            idpLayoutListModel =  new IDPLayoutListModel();
//            for (int u=0;u<listIDP.size();u++){
//                if(listIDP.get(u).getIdpTitle().equals(idpTitle)){
//                    for(int y=0;y<listIDP.get(u).getLnIdpDetail().size();y++){
//
//                        View childs = getLayoutInflater().inflate(R.layout.idp_item_detail, formLayout, false);
//
//                        if(listIDP.get(u).getLnIdpDetail().get(y).getVisibility() == View.VISIBLE) {
//                            CheckBox cbIdp = childs.findViewById(R.id.checkBoxIdp);
//                            //listIDP.get(u).getCbIdpDetail().get(y);
//                            int finalU = u;
//                            int finalY = y;
//                            cbIdp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                @Override
//                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                    if (isChecked) {
//                                        listIDP.get(finalU).getCbIdpDetail().get(finalY).setChecked(true);
//                                    } else {
//                                        listIDP.get(finalU).getCbIdpDetail().get(finalY).setChecked(true);
//                                    }
//                                }
//                            });
//
//
//                            EditText edtDueDate = childs.findViewById(R.id.edtDueDate);
//                            edtDueDate.setText(listIDP.get(u).getEdtDueDate().get(y).getText());
//                            listIDP.get(u).getEdtDueDate().get(y);
//
//
//                            LinearLayout lnExpand =  childs.findViewById(R.id.lnExpanded);
//                            listIDP.get(u).getLnExpanded().get(y);
//
//                            int finalY1 = y;
//                            int finalU1 = u;
//                            ImageButton imgExpand = childs.findViewById(R.id.imgExpand);
//                            imgExpand.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    //Toast.makeText(context,Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();
//                                    if(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility() == View.VISIBLE){
//                                        lnExpand.setVisibility(View.GONE);
//                                        imgExpand.setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_down));
//                                        listIDP.get(finalU1).getLnExpanded().get(finalY1).setVisibility(View.GONE);
//                                        listIDP.get(finalU1).getImgExpand().get(finalY1).setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_down));
//                                        Toast.makeText(getContext(),Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();
//
//                                    }
//                                    else{
//                                        lnExpand.setVisibility(View.VISIBLE);
//                                        imgExpand.setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_up));
//                                        listIDP.get(finalU1).getLnExpanded().get(finalY1).setVisibility(View.VISIBLE);
//                                        listIDP.get(finalU1).getImgExpand().get(finalY1).setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_up));
//                                        Toast.makeText(getContext(),Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();
//
//                                    }
//                                }
//                            });
//
//                            ImageButton btnCalendar = childs.findViewById(R.id.btnCalendar);
//
//
//                            btnCalendar.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    showCalendar(edtDueDate, listIDP.get(finalU1).getEdtDueDate().get(finalY1));
//                                    listIDP.get(finalU1).getEdtDueDate().get(finalY1).setText(edtDueDate.getText());
//
//                                }
//                            });
//
//
//                            TextView tvNumber = childs.findViewById(R.id.tvNumber);
//                            tvNumber.setText(Integer.toString(ctr[0]) + ". ");
//                            listIDP.get(u).getTvIdpNumber().get(y);
//
//                            TextView tvIdpDetailTitle = childs.findViewById(R.id.tvidp_title);
//                            tvIdpDetailTitle.setText(listIDP.get(u).getTvIdpTitleDetail().get(y).getText().toString());
//                            listIDP.get(u).getTvIdpTitleDetail().get(y);
//
//                            formLayout.addView(childs);
//                            ctr[0]++;
//                        }
//                    }
//                }
//
//            }
//        }catch (Exception e){}
//
//
//        Dialog finalDialogs = dialogs;
//        final int[] finalCtr = {ctr[0]};
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                idpLayoutListModel =  new IDPLayoutListModel();
//
//                TextView tvNumber,tvIdpDetailTitle;
//                ImageButton btnCalendar, btnExpand;
//                LinearLayout lnExpand;
//                EditText edtDevelopmentActivities,edtMentor, edtDueDate,edtKPI,edtAchievementRecommendation;
//                CheckBox cbIdp;
//
//                View child = getLayoutInflater().inflate(R.layout.idp_item_detail, formLayout, false);
//
//                cbIdp = child.findViewById(R.id.checkBoxIdp);
//                cbIdp.setId(ii[0]);
//                cbIdp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if(isChecked){
//                        }
//                        else{
//                        }
//                    }
//                });
//                cbList.add(cbIdp);
//
//                idpLayoutListModel.setCbIdpDetail(cbList);
//
//                tvNumber = child.findViewById(R.id.tvNumber);
//                tvNumber.setText(Integer.toString(finalCtr[0]));
//                tvNumber.setId(ii[0]);
//                tvNumbList.add(tvNumber);
//                idpLayoutListModel.setTvIdpNumber(tvNumbList);
//
//                tvIdpDetailTitle = child.findViewById(R.id.tvidp_title);
//                tvIdpDetailTitle.setText(spinnerImprovement.getText());
//                tvIdpDetailTitle.setId(ii[0]);
//                tvTitleList.add(tvIdpDetailTitle);
//                idpLayoutListModel.setTvIdpTitleDetail(tvTitleList);
//
//                edtDevelopmentActivities = child.findViewById(R.id.edtDevelopmentActivities);
//                edtDevelopmentActivities.setId(ii[0]);
//                edtDevelopmentActivitiesList.add(edtDevelopmentActivities);
//                idpLayoutListModel.setEdtDevelopmentActivities(edtDevelopmentActivitiesList);
//
//
//                btnExpand = child.findViewById(R.id.imgExpand);
//                btnExpand.setId(ii[0]);
//                imgExpandList.add(btnExpand);
//                idpLayoutListModel.setImgExpand(imgExpandList);
//
//                lnExpand = child.findViewById(R.id.lnExpanded);
//                lnExpand.setId(ii[0]);
//                lnExpandList.add(lnExpand);
//                idpLayoutListModel.setLnExpanded(lnExpandList);
//
//                edtKPI = child.findViewById(R.id.edtKPI);
//                edtKPI.setId(ii[0]);
//                edtKPIList.add(edtKPI);
//                idpLayoutListModel.setEdtKPI(edtKPIList);
//
//                edtMentor = child.findViewById(R.id.edtMentor);
//                edtMentor.setId(ii[0]);
//                edtMentorsList.add(edtMentor);
//                idpLayoutListModel.setEdtMentors(edtMentorsList);
//
//                edtDueDate = child.findViewById(R.id.edtDueDate);
//                edtDueDate.setId(ii[0]);
//                edtDueDateList.add(edtDueDate);
//                idpLayoutListModel.setEdtDueDate(edtDueDateList);
//
//                edtAchievementRecommendation = child.findViewById(R.id.edtAchevementRecommendation);
//                edtAchievementRecommendation.setId(ii[0]);
//                edtAchievementRecommendationList.add(edtAchievementRecommendation);
//                idpLayoutListModel.setEdtAchievementRecommendation(edtAchievementRecommendationList);
//
//                btnCalendar = child.findViewById(R.id.btnCalendar);
//                btnCalendar.setId(ii[0]);
//
//                formLayout.addView(child);
//
//                lnList.add(child);
//                idpLayoutListModel.setIdpTitle(idpTitle);
//                idpLayoutListModel.setLnIdpDetail(lnList);
//                idpLayoutListModel.setLinLaIdpDetail(formLayout);
//
//                ii[0]++;
//                input[0] = true;
//                ctr[0] = finalCtr[0]++;
//
//
//                if(input[0]) {
//                    listIDP.add(idpLayoutListModel);
//                    input[0] = false;
//                }
//                finalDialogs.dismiss();
//                showIDPDialog(finalDialogs, idpTitle, 0);
//                //Toast.makeText(context,Integer.toString(formLayout.getChildCount()),Toast.LENGTH_LONG).show();
//
//
//            }
//        });
//
//
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int u=0;u<listIDP.size();u++){
//                    for (int j=0;j<listIDP.get(u).getCbIdpDetail().size();j++) {
//                        if(listIDP.get(u).getCbIdpDetail().get(j).isChecked()){
//                            listIDP.get(u).getLnIdpDetail().get(j).setVisibility(View.GONE);
//                            //Toast.makeText(context,Boolean.toString(listIDP.get(u).getCbIdpDetail().get(j).isChecked()),Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//                }
//                if(input[0]) {
//                    listIDP.add(idpLayoutListModel);
//                    input[0] = false;
//                    //idpLayoutListModel = new IDPLayoutListModel();
//                }
//                finalDialogs.dismiss();
//                showIDPDialog(finalDialogs, idpTitle, 0);
//            }
//        });
//
//
//        closeButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                // Dismiss the popup window
//
//                if(input[0]) {
//                    listIDP.add(idpLayoutListModel);
//                    input[0] = false;
//                    //idpLayoutListModel = new IDPLayoutListModel();
//                }
//                finalDialogs.dismiss();
//
//            }
//        });
//
//        dialogs.show();
//    }

//    private void showCalendar (EditText editText, EditText editTexts){
//
//        Calendar newCalendar = Calendar.getInstance();
//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//
//
//        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                try {
//                    editText.setText(dateFormatter.format(newDate.getTime()));
//                    editTexts.setText(dateFormatter.format(newDate.getTime()));
//                    Toast.makeText(getContext(), "Tanggal dipilih : " + dateFormatter.format(newDate.getTime()), Toast.LENGTH_SHORT).show();
//                }catch (Exception e){
//                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//        datePickerDialog.show();
//    }

    @Override
    public void onEvent(int position, List<KPIQuestions> questions, int action) {
        kpiHeader.setKpiQuestionsList(questions);
    }

    @Override
    public void setDetail(List<DevPlanDetail> steps) {

    }

    @Override
    public List<DevPlanDetail> getDetail() {
        return null;
    }

    @Override
    public List<ImageUploadModelPA> onResult() {
        return null;
    }

    @Override
    public void redirect(int tabPos) {

    }

    @Override
    public String isEditables() {
        return isEditable;
    }

    @Override
    public String getSemster() {
        return null;
    }

    @Override
    public void setQuestion(KPIHeader a, int semester) {

    }

    @Override
    public void setEditables(String edit, KPIHeaderPJ kh) {

    }

    @Override
    public void setSemester(String semester, KPIHeaderPJ kh) {

    }

    @Override
    public KPIHeader getQuestionSmt1() {
        return null;
    }

    @Override
    public KPIHeader getQuestionSmt2() {
        return null;
    }

    @Override
    public void getQuestion() {
    }

    @Override
    public boolean getUbah() {
        return diUbah;
    }

    @Override
    public void setUbah(boolean isUbah) {
        diUbah=isUbah;
    }

    public void saveDevPlan(Context ctx){
        List<KPIAnswer> kpiAnswerList = new ArrayList<>();
        KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

        int fgSubmit = 0;
        for (int i = 0; i < kpiHeader.getKpiQuestionsList().size(); i++) {
            KPIAnswer kpiAnswer = new KPIAnswer();


            if (kpiHeader.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                kpiAnswer.setCOMPID(kpiHeader.getKpiQuestionsList().get(i).getKpiId());
                kpiAnswer.setKPIID("0");
            } else {
                kpiAnswer.setKPIID(kpiHeader.getKpiQuestionsList().get(i).getKpiId());
                kpiAnswer.setCOMPID("0");
            }

            kpiAnswer.setCP(Integer.toString(kpiHeader.getKpiQuestionsList().get(i).getCheckedId()));
            kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
            kpiAnswer.setPAID(kpiHeader.getKpiQuestionsList().get(i).getPaId());
            kpiAnswer.setCP(kpiHeader.getKpiQuestionsList().get(i).getCP());
            kpiAnswer.setKPITYPE(kpiHeader.getKpiQuestionsList().get(i).getKPIcategory());
            kpiAnswer.setSEMESTER(kpiHeader.getKpiQuestionsList().get(i).getSemester());
            kpiAnswer.setEVIDENCES(kpiHeader.getKpiQuestionsList().get(i).getEvidence());
            kpiAnswer.setACTUAL(kpiHeader.getKpiQuestionsList().get(i).getActual());
            kpiAnswer.setTARGET(kpiHeader.getKpiQuestionsList().get(i).getTarget());
            kpiAnswerList.add(kpiAnswer);
            //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();
        }
        kpiAnswerList1.setNIKBAWAHAN(kpiHeader.getNIK());
        kpiAnswerList1.setPAID(kpiHeader.getKpiQuestionsList().get(0).getPaId());
        kpiAnswerList1.setSTATUS(kpiHeader.getStatus());
        kpiAnswerList1.setSTRENGTH(kpiHeader.getStrength());
        kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
        kpiAnswerList1.setDevPlanHeaderList(kpiHeader.getDevPlanHeaderList());


        ApiInterface apiService = ApiClient.getClient(ctx).create(ApiInterface.class);
        Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
        call.enqueue(new Callback<KPIAnswerList>() {
            @Override
            public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                Toast.makeText(ctx, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                diUbah = false;
            }

            @Override
            public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                Toast.makeText(ctx, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                diUbah = false;

            }
        });

    }

    private void sendEmail(EmailSentModel emailSentModel){
        List<EmailSentModel> emailSentModelList = new ArrayList<>();
        emailSentModelList.add(emailSentModel);

        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<Void> call2 = apiService.sendEmailtoPIC(emailSentModelList,"Bearer " + usr.get(0).getToken());
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                Toast.makeText(getContext(),"Email berhasil dikirim",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"Email berhasil dikirim",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
