package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
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

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.ListImprovementAreaAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.ListImprovementAreaFullScreenAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevelopmentPlanLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DevelopmentPlanDetailFragment extends Fragment{

    View rootView;
    MaterialSpinner spinnerImprovement;
    Button btnAdd,btnDel;
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
    //======================== Sub Detail ==============================


    static IDPLayoutListModel idpLayoutListModel;

    static List<IDPLayoutListModel> listIDP;
    //=====================================================================

    int ii=0;

    TextView tvEmpName, tvJobtitle, tvDept;
    ImageView imgEmpPhoto;
    String empName, jobTitle, dept, branchName, jobStatus, empPhoto, empNik;
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

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        usr = userRealmHelper.findAllArticle();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            empNik = bundle.getString("empNik");
            empName = bundle.getString("empName");
            jobTitle = bundle.getString("jobTitle");
            dept = bundle.getString("dept");
            jobStatus = bundle.getString("jobStatus");
            branchName = bundle.getString("branchName");
            empPhoto = bundle.getString("empPhoto");
        }


        spinnerImprovement = rootView.findViewById(R.id.spinnerImprovement);
        spinnerImprovement.setBackgroundResource(R.drawable.shapesignup);
        spinnerImprovement.setPadding(25, 10, 25, 10);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnDel = rootView.findViewById(R.id.btnDelete);
        lnDevPlan = rootView.findViewById(R.id.lnAddDevPlan);
        tvEmpName = rootView.findViewById(R.id.tv_employeeName);
        tvJobtitle = rootView.findViewById(R.id.tv_job_title);
        tvDept = rootView.findViewById(R.id.tv_dept);
        imgEmpPhoto = rootView.findViewById(R.id.imgEmpPhoto);
        rvImprovementArea = rootView.findViewById(R.id.rv_improvement_area);


        //============================================ DUMMY =======================================================================
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<KPIHeader> call = apiService.getKPIHeaderPA(empNik, Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<KPIHeader>() {
            @Override
            public void onResponse(Call<KPIHeader> call, Response<KPIHeader> response) {

                int statusCode = response.code();
                try {
                    kpiHeader = new KPIHeader();
                    kpiHeader.setDevPlanHeaderList(response.body().getDevPlanHeaderList());
                    for (int i = 0; i < kpiHeader.getDevPlanHeaderList().size(); i++) {
                        kpiHeader.getDevPlanHeaderList().get(i).setDevPlanDetail(response.body().getDevPlanHeaderList().get(i).getDevPlanDetail());
                    }
                    kpiHeader.setMasterDevelopmentPlanList(response.body().getMasterDevelopmentPlanList());
                    kpiHeader.setBobot(response.body().getBobot());
                    kpiHeader.setSemester("1");
                    kpiHeader.setTahun(response.body().getTahun());
                    kpiHeader.setStatus(response.body().getStatus());
                    kpiHeader.setStrength(response.body().getStrength());
                    kpiHeader.setStatus1(response.body().getStatus1());
                    kpiHeader.setStatus2(response.body().getStatus2());

                    devPlanHeaderList = kpiHeader.getDevPlanHeaderList();


                    listImprovementAreaAdapter = new ListImprovementAreaFullScreenAdapter(devPlanHeaderList,kpiHeader, getContext(), "Y",empPhoto,empName,jobTitle);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());

                    //Setting the adapter
                    rvImprovementArea.setAdapter(listImprovementAreaAdapter);
                    rvImprovementArea.setLayoutManager(llm);
                }
                catch (Exception e){

                }

                //Toast.makeText(getContext(), kh.getTahun(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<KPIHeader> call, Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        //====================================================================================================================


        //================================ foto bawahan =======================
        try {
            //Toast.makeText(context,userList.getEmpPhoto(),Toast.LENGTH_LONG).show();
            byte[] encodeByte = Base64.decode(empPhoto, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if (size.x > 1081) {
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                //Point size = new Point();
                display.getSize(size);
                int width = size.x - (size.x / 3);
                int height = size.y - (size.y / 3);

                int newWidth = width; //this method should return the width of device screen.
                float scaleFactor = (float) newWidth / (float) imageWidth;
                int newHeight = (int) (imageHeight * scaleFactor);

                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
            } else {
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                //Point size = new Point();
                display.getSize(size);
                int width = size.x - (size.x / 3);
                int height = size.y - (size.y / 3);

                int newWidth = width; //this method should return the width of device screen.
                float scaleFactor = (float) newWidth / (float) imageWidth;
                int newHeight = (int) (imageHeight * scaleFactor);

                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
            }

            imgEmpPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.getMessage();
            //return null;
        }
        //===========================================================================

        tvEmpName.setText(empName);
        tvJobtitle.setText(jobTitle);
        tvDept.setText(branchName+"-"+jobStatus);

        List<String> improvement = new ArrayList<String>();
        improvement.add("Dummy 1");
        improvement.add("Dummy 2");
        improvement.add("Dummy 3");
        spinnerImprovement.setItems(improvement);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevPlanHeader devPlanHeader = new DevPlanHeader();
                devPlanHeader.setCOMPNAME(spinnerImprovement.getText().toString());
                devPlanHeader.setChecked(false);
                devPlanHeader.setDevPlanDetail(new ArrayList<DevPlanDetail>());
                devPlanHeader.setPAID("1");
//                for(int i=0;i<kpiHeader.getKpiQuestionsList().size();i++){
//                    if(kpiHeader.getKpiQuestionsList().get(i).getKPIDesc().equals(spinnerImprovement.getText())){
                        devPlanHeader.setCOMPID("1");
                        devPlanHeader.setDEVID("2");

//                    }
//                }
                devPlanHeaderList.add(devPlanHeader);
                listImprovementAreaAdapter.notifyDataSetChanged();
        }});


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<devPlanHeaderList.size();i++){
                    if(devPlanHeaderList.get(i).getChecked()){
                        devPlanHeaderList.remove(i);
                    }
                    listImprovementAreaAdapter.notifyDataSetChanged();

                }
            }
        });

        return rootView;
    }

    private void showIDPDialog(Dialog dialogs, String idpTitle, int DevPlanPosition){
        dialogs = new Dialog(getContext(),android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);

        dialogs.setContentView(R.layout.idp_dialog_fullscreen);
        dialogs.setTitle("Title...");
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.setCanceledOnTouchOutside(false);
        dialogs.setCancelable(false);

        WindowManager manager = (WindowManager) getContext().getSystemService(Activity.WINDOW_SERVICE);
        int width, height;
        ViewGroup.LayoutParams params;

        final boolean[] input = {false};

        final int[] ctr = {1};

        View myView;
        ViewGroup parent;
        LinearLayout formLayout;

//        myView = dialogs.findViewById(R.id.lnIdpDetail);
//        parent = (ViewGroup) myView.getParent();
//        parent.removeView(myView);

        formLayout = (LinearLayout)dialogs.findViewById(R.id.formLayout);
        formLayout.removeAllViews();
        formLayout.setOrientation(LinearLayout.VERTICAL);

        ImageButton closeButton = (ImageButton) dialogs.findViewById(R.id.ib_close);
        TextView tvIdpTitle = dialogs.findViewById(R.id.tvIdpTitle);
        Button btnAdd = dialogs.findViewById(R.id.btnAdd);
        Button btnDelete = dialogs.findViewById(R.id.btnDelete);

        MaterialSpinner spinnerImprovement = dialogs.findViewById(R.id.spinnerImprovement);
        List<View> lnList = new ArrayList<>();

        List<CheckBox> cbList = new ArrayList<>();
        List<TextView> tvNumbList = new ArrayList<>();
        List<TextView> tvTitleList = new ArrayList<>();
        List<EditText>edtDevelopmentActivitiesList = new ArrayList<>();
        List<ImageButton>imgExpandList = new ArrayList<>();
        List<LinearLayout>lnExpandList = new ArrayList<>();
        List<EditText>edtKPIList = new ArrayList<>();
        List<EditText>edtDueDateList = new ArrayList<>();
        List<EditText>edtMentorsList = new ArrayList<>();
        List<EditText>edtAchievementRecommendationList = new ArrayList<>();


        final int[] ii = {0};


        List<String> improvement = new ArrayList<String>();
        improvement.add("Dummy 1");
        improvement.add("Dummy 2");
        improvement.add("Dummy 3");
        spinnerImprovement.setItems(improvement);

        tvIdpTitle.setText(idpTitle);


        try {

            idpLayoutListModel =  new IDPLayoutListModel();
            for (int u=0;u<listIDP.size();u++){
                if(listIDP.get(u).getIdpTitle().equals(idpTitle)){
                    for(int y=0;y<listIDP.get(u).getLnIdpDetail().size();y++){

                        View childs = getLayoutInflater().inflate(R.layout.idp_item_detail, formLayout, false);

                        if(listIDP.get(u).getLnIdpDetail().get(y).getVisibility() == View.VISIBLE) {
                            CheckBox cbIdp = childs.findViewById(R.id.checkBoxIdp);
                            //listIDP.get(u).getCbIdpDetail().get(y);
                            int finalU = u;
                            int finalY = y;
                            cbIdp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        listIDP.get(finalU).getCbIdpDetail().get(finalY).setChecked(true);
                                    } else {
                                        listIDP.get(finalU).getCbIdpDetail().get(finalY).setChecked(true);
                                    }
                                }
                            });


                            EditText edtDueDate = childs.findViewById(R.id.edtDueDate);
                            edtDueDate.setText(listIDP.get(u).getEdtDueDate().get(y).getText());
                            listIDP.get(u).getEdtDueDate().get(y);


                            LinearLayout lnExpand =  childs.findViewById(R.id.lnExpanded);
                            listIDP.get(u).getLnExpanded().get(y);

                            int finalY1 = y;
                            int finalU1 = u;
                            ImageButton imgExpand = childs.findViewById(R.id.imgExpand);
                            imgExpand.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(context,Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();
                                    if(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility() == View.VISIBLE){
                                        lnExpand.setVisibility(View.GONE);
                                        imgExpand.setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_down));
                                        listIDP.get(finalU1).getLnExpanded().get(finalY1).setVisibility(View.GONE);
                                        listIDP.get(finalU1).getImgExpand().get(finalY1).setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_down));
                                        Toast.makeText(getContext(),Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        lnExpand.setVisibility(View.VISIBLE);
                                        imgExpand.setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_up));
                                        listIDP.get(finalU1).getLnExpanded().get(finalY1).setVisibility(View.VISIBLE);
                                        listIDP.get(finalU1).getImgExpand().get(finalY1).setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_up));
                                        Toast.makeText(getContext(),Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                            ImageButton btnCalendar = childs.findViewById(R.id.btnCalendar);


                            btnCalendar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showCalendar(edtDueDate, listIDP.get(finalU1).getEdtDueDate().get(finalY1));
                                    listIDP.get(finalU1).getEdtDueDate().get(finalY1).setText(edtDueDate.getText());

                                }
                            });


                            TextView tvNumber = childs.findViewById(R.id.tvNumber);
                            tvNumber.setText(Integer.toString(ctr[0]) + ". ");
                            listIDP.get(u).getTvIdpNumber().get(y);

                            TextView tvIdpDetailTitle = childs.findViewById(R.id.tvidp_title);
                            tvIdpDetailTitle.setText(listIDP.get(u).getTvIdpTitleDetail().get(y).getText().toString());
                            listIDP.get(u).getTvIdpTitleDetail().get(y);

                            formLayout.addView(childs);
                            ctr[0]++;
                        }
                    }
                }

            }
        }catch (Exception e){}


        Dialog finalDialogs = dialogs;
        final int[] finalCtr = {ctr[0]};
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idpLayoutListModel =  new IDPLayoutListModel();

                TextView tvNumber,tvIdpDetailTitle;
                ImageButton btnCalendar, btnExpand;
                LinearLayout lnExpand;
                EditText edtDevelopmentActivities,edtMentor, edtDueDate,edtKPI,edtAchievementRecommendation;
                CheckBox cbIdp;

                View child = getLayoutInflater().inflate(R.layout.idp_item_detail, formLayout, false);

                cbIdp = child.findViewById(R.id.checkBoxIdp);
                cbIdp.setId(ii[0]);
                cbIdp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                        }
                        else{
                        }
                    }
                });
                cbList.add(cbIdp);

                idpLayoutListModel.setCbIdpDetail(cbList);

                tvNumber = child.findViewById(R.id.tvNumber);
                tvNumber.setText(Integer.toString(finalCtr[0]));
                tvNumber.setId(ii[0]);
                tvNumbList.add(tvNumber);
                idpLayoutListModel.setTvIdpNumber(tvNumbList);

                tvIdpDetailTitle = child.findViewById(R.id.tvidp_title);
                tvIdpDetailTitle.setText(spinnerImprovement.getText());
                tvIdpDetailTitle.setId(ii[0]);
                tvTitleList.add(tvIdpDetailTitle);
                idpLayoutListModel.setTvIdpTitleDetail(tvTitleList);

                edtDevelopmentActivities = child.findViewById(R.id.edtDevelopmentActivities);
                edtDevelopmentActivities.setId(ii[0]);
                edtDevelopmentActivitiesList.add(edtDevelopmentActivities);
                idpLayoutListModel.setEdtDevelopmentActivities(edtDevelopmentActivitiesList);


                btnExpand = child.findViewById(R.id.imgExpand);
                btnExpand.setId(ii[0]);
                imgExpandList.add(btnExpand);
                idpLayoutListModel.setImgExpand(imgExpandList);

                lnExpand = child.findViewById(R.id.lnExpanded);
                lnExpand.setId(ii[0]);
                lnExpandList.add(lnExpand);
                idpLayoutListModel.setLnExpanded(lnExpandList);

                edtKPI = child.findViewById(R.id.edtKPI);
                edtKPI.setId(ii[0]);
                edtKPIList.add(edtKPI);
                idpLayoutListModel.setEdtKPI(edtKPIList);

                edtMentor = child.findViewById(R.id.edtMentor);
                edtMentor.setId(ii[0]);
                edtMentorsList.add(edtMentor);
                idpLayoutListModel.setEdtMentors(edtMentorsList);

                edtDueDate = child.findViewById(R.id.edtDueDate);
                edtDueDate.setId(ii[0]);
                edtDueDateList.add(edtDueDate);
                idpLayoutListModel.setEdtDueDate(edtDueDateList);

                edtAchievementRecommendation = child.findViewById(R.id.edtAchevementRecommendation);
                edtAchievementRecommendation.setId(ii[0]);
                edtAchievementRecommendationList.add(edtAchievementRecommendation);
                idpLayoutListModel.setEdtAchievementRecommendation(edtAchievementRecommendationList);

                btnCalendar = child.findViewById(R.id.btnCalendar);
                btnCalendar.setId(ii[0]);

                formLayout.addView(child);

                lnList.add(child);
                idpLayoutListModel.setIdpTitle(idpTitle);
                idpLayoutListModel.setLnIdpDetail(lnList);
                idpLayoutListModel.setLinLaIdpDetail(formLayout);

                ii[0]++;
                input[0] = true;
                ctr[0] = finalCtr[0]++;


                if(input[0]) {
                    listIDP.add(idpLayoutListModel);
                    input[0] = false;
                }
                finalDialogs.dismiss();
                showIDPDialog(finalDialogs, idpTitle, 0);
                //Toast.makeText(context,Integer.toString(formLayout.getChildCount()),Toast.LENGTH_LONG).show();


            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int u=0;u<listIDP.size();u++){
                    for (int j=0;j<listIDP.get(u).getCbIdpDetail().size();j++) {
                        if(listIDP.get(u).getCbIdpDetail().get(j).isChecked()){
                            listIDP.get(u).getLnIdpDetail().get(j).setVisibility(View.GONE);
                            //Toast.makeText(context,Boolean.toString(listIDP.get(u).getCbIdpDetail().get(j).isChecked()),Toast.LENGTH_LONG).show();

                        }
                    }
                }
                if(input[0]) {
                    listIDP.add(idpLayoutListModel);
                    input[0] = false;
                    //idpLayoutListModel = new IDPLayoutListModel();
                }
                finalDialogs.dismiss();
                showIDPDialog(finalDialogs, idpTitle, 0);
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window

                if(input[0]) {
                    listIDP.add(idpLayoutListModel);
                    input[0] = false;
                    //idpLayoutListModel = new IDPLayoutListModel();
                }
                finalDialogs.dismiss();

            }
        });

        dialogs.show();
    }

    private void showCalendar (EditText editText, EditText editTexts){

        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                try {
                    editText.setText(dateFormatter.format(newDate.getTime()));
                    editTexts.setText(dateFormatter.format(newDate.getTime()));
                    Toast.makeText(getContext(), "Tanggal dipilih : " + dateFormatter.format(newDate.getTime()), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();

                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
