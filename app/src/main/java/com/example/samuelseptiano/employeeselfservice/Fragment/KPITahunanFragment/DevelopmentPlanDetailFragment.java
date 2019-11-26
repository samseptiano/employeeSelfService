package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
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

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevelopmentPlanLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPModel;
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


public class DevelopmentPlanDetailFragment extends Fragment implements DevelopmentPlanSubDetailFragment.EventListener{

    View rootView;
    MaterialSpinner spinnerImprovement;
    Button btnAdd,btnDel;
    LinearLayout lnDevPlan;

    LayoutInflater inflate;
    DatePickerDialog  datePickerDialog;
    SimpleDateFormat dateFormatter;

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    //======================== Sub Detail ==============================
    static List<View> lnList;
    static List<CheckBox> cbList;
    static List<TextView> tvNumbList;
    static List<TextView> tvTitleList;
    static List<EditText>edtDevelopmentActivitiesList;
    static List<EditText>edtKPIList;
    static  List<EditText>edtDueDateList;
    static List<EditText>edtMentorsList;
    static List<EditText>edtAchievementRecommendationList;
    static List<ImageButton>imgExpandList;
    static List<LinearLayout>lnExpandList;

    static List<LinearLayout> lnDevplansDetail;

    static IDPLayoutListModel idpLayoutListModel;

    static List<IDPLayoutListModel> listIDP;
    //=====================================================================

    //============== Detail =========================
    static List<Button> btnevplan;
    static List<CheckBox> cbDevplan;
    static List<TextView> tvDevplan;
    static List<LinearLayout> lnDevplans;
    static DevelopmentPlanLayoutListModel developmentPlanLayoutListModel;

    static List<DevelopmentPlanLayoutListModel> listDevPlan;
    //==================================================

    int ii=0;

    CheckBox cb;
    LinearLayout ln;
    Button btn;
    TextView tv;


    TextView tvEmpName, tvJobtitle, tvDept;
    ImageView imgEmpPhoto;
    String empName, jobTitle, dept, branchName, jobStatus, empPhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            //======================== Sub Detail ==============================
            lnList = new ArrayList<>();
            cbList = new ArrayList<>();
            tvNumbList = new ArrayList<>();
            tvTitleList = new ArrayList<>();
            edtDevelopmentActivitiesList = new ArrayList<>();
            edtKPIList = new ArrayList<>();
            edtDueDateList = new ArrayList<>();
            edtMentorsList = new ArrayList<>();
            edtAchievementRecommendationList = new ArrayList<>();
            imgExpandList = new ArrayList<>();
            lnExpandList = new ArrayList<>();
            lnDevplansDetail = new ArrayList<>();
            idpLayoutListModel = new IDPLayoutListModel();
            listIDP = new ArrayList<>();
            //=====================================================================

            //============== Detail =========================
            btnevplan = new ArrayList<>();
            cbDevplan = new ArrayList<>();
            tvDevplan = new ArrayList<>();
            lnDevplans = new ArrayList<>();
            developmentPlanLayoutListModel = new DevelopmentPlanLayoutListModel();
            listDevPlan = new ArrayList<>();
            //==================================================
        } else {

        }
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


        Bundle bundle = this.getArguments();
        if (bundle != null) {

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

        tvEmpName.setText(empName);
        tvJobtitle.setText(jobTitle);
        tvDept.setText(dept+"-"+branchName+"-"+jobStatus);

        List<String> improvement = new ArrayList<String>();
        improvement.add("Dummy 1");
        improvement.add("Dummy 2");
        improvement.add("Dummy 3");
        spinnerImprovement.setItems(improvement);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View child = getActivity().getLayoutInflater().inflate(R.layout.improvement_area_development_plan, null);

//                        int done = 0;
                for (int i=0;i<tvDevplan.size();i++){
                    if(tvDevplan.get(i).getText().equals(spinnerImprovement.getText())){
//                                done=1;
                    }
                }
//                    if(done==0){
               // idpModel = new IDPModel();


                ln = child.findViewById(R.id.lnDevPlan);
                ln.setId(ii);
                lnDevplans.add(ln);
                cb = child.findViewById(R.id.cbDevPlan);
                cb.setId(ii);
                cbDevplan.add(cb);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                        }
                        else{
                        }
                    }
                });

                tv = child.findViewById(R.id.tvDevPlan);
                tv.setId(ii);
                tv.setText(spinnerImprovement.getText());
//                    tahuns.remove(spinnerDevPlan.getText());

                tvDevplan.add(tv);
                btn =  child.findViewById(R.id.btnIdp);
                btn.setId(ii);
                btnevplan.add(btn);
                final int index = ii;
                btnevplan.get(ii).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //INTENT TO IDP LAYOUT
//                        Dialog aa= new Dialog(getContext());
//                        showIDPDialog(aa,tvDevplan.get(index).getText().toString(),ii);
                        //==================================================================
                        if(ConnectivityReceiver.isConnected()) {
                            // code block
                            fr = new DevelopmentPlanSubDetailFragment();
                            Bundle bundle3 = new Bundle();
                            bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                            bundle3.putSerializable("lnList", (Serializable) lnList);
                            bundle3.putString("idpTitleDetail", tvDevplan.get(index).getText().toString());
                            bundle3.putSerializable("cbList", (Serializable) cbList);
                            bundle3.putSerializable("tvNumbList", (Serializable) tvNumbList);
                            bundle3.putSerializable("tvTitleList", (Serializable) tvTitleList);
                            bundle3.putSerializable("edtDevelopmentActivitiesList", (Serializable) edtDevelopmentActivitiesList);
                            bundle3.putSerializable("edtKPIList", (Serializable) edtKPIList);
                            bundle3.putSerializable("edtDueDateList", (Serializable) edtDueDateList);
                            bundle3.putSerializable("edtMentorsList", (Serializable) edtMentorsList);
                            bundle3.putSerializable("edtAchievementRecommendationList", (Serializable) edtAchievementRecommendationList);
                            bundle3.putSerializable("imgExpandList", (Serializable) imgExpandList);
                            bundle3.putSerializable("lnExpandList", (Serializable) lnExpandList);
                            bundle3.putSerializable("lnDevplansDetail", (Serializable) lnDevplansDetail);
                            bundle3.putSerializable("idpLayoutListModel", (Serializable) idpLayoutListModel);
                            bundle3.putSerializable("listIDP", (Serializable) listIDP);
                            bundle3.putSerializable("btnevplan", (Serializable) btnevplan);
                            bundle3.putSerializable("cbDevplan", (Serializable) cbDevplan);
                            bundle3.putSerializable("tvDevplan", (Serializable) tvDevplan);
                            bundle3.putSerializable("lnDevplans", (Serializable) lnDevplans);
                            bundle3.putSerializable("developmentPlanLayoutListModel", (Serializable) developmentPlanLayoutListModel);
                            bundle3.putSerializable("listDevPlan", (Serializable) listDevPlan);

                            fr.setArguments(bundle3);
                            fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                            ft = fm.beginTransaction();
                            ft.add(R.id.fragment_frame, fr);
                            ft.addToBackStack("DevelopmentPlanSubDetailFragment");
                            ft.commit();
                            Toast.makeText(getContext(), "Development Plan SubDetail Area", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }





                    }
                });
                lnDevPlan.addView(child);


                //Insert IDP to object
//                    idpModel.setEmpNIK(usr.get(0).getEmpNIK());
//                    idpModel.setId("");
//                    idpModel.setIDPTitle(tv.getText().toString());
//                    idpModel.setPaTransId(questions.get(0).getIdKPI());
//                    idpModel.setIdpDetailModelList();
//                    idpModelList.add(idpModel);

                ii++;
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xx = cbDevplan.size();
//                    View child = activity.getLayoutInflater().inflate(R.layout.improvement_area_development_plan, null);
                for (int j=0;j<lnDevplans.size();j++) {
                    if(cbDevplan.get(j).isChecked()){
//                            lnDevPlan.removeView(lnDevplans.get(j));
                        lnDevplans.get(j).setVisibility(View.GONE);
//                            tahuns.add(tvDevplan.get(j).getText().toString());
                        //Toast.makeText(context,Boolean.toString(cbDevplan.get(j).isChecked()),Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public List<CheckBox> getCbList() {
        return cbList;
    }

    @Override
    public void setCbList(List<CheckBox> cbList) {
        this.cbList = cbList;
    }

    @Override
    public List<TextView> getTvNumbList() {
        return tvNumbList;
    }

    @Override
    public void setTvNumbList(List<TextView> tvNumbList) {
        this.tvNumbList = tvNumbList;
    }

    @Override
    public List<TextView> getTvTitleList() {
        return tvTitleList;
    }

    @Override
    public void setTvTitleList(List<TextView> tvTitleList) {
            this.tvTitleList = tvTitleList;
    }

    @Override
    public List<EditText> getEdtDevelopmentActivitiesList() {
        return edtDevelopmentActivitiesList;
    }

    @Override
    public void setEdtDevelopmentActivitiesList(List<EditText> edtDevelopmentActivitiesList) {
        this.edtDevelopmentActivitiesList = edtDevelopmentActivitiesList;
    }

    @Override
    public List<ImageButton> getImgExpandList() {
        return imgExpandList;
    }

    @Override
    public void setImgExpandList(List<ImageButton> imgExpandList) {
            this.imgExpandList = imgExpandList;
    }

    @Override
    public List<LinearLayout> getLnExpandList() {
        return lnExpandList;
    }

    @Override
    public void setLnExpandList(List<LinearLayout> lnExpandList) {
                this.lnExpandList=lnExpandList;
    }

    @Override
    public List<EditText> getEdtKPIList() {
        return edtKPIList;
    }

    @Override
    public void setEdtKPIList(List<EditText> edtKPIList) {
            this.edtKPIList = edtKPIList;
    }

    @Override
    public List<EditText> getEdtDueDateList() {
        return edtDueDateList;
    }

    @Override
    public void setEdtDueDateList(List<EditText> edtDueDateList) {
            this.edtDueDateList = edtDueDateList;
    }

    @Override
    public List<EditText> getEdtMentorsList() {
        return edtMentorsList;
    }

    @Override
    public void setEdtMentorsList(List<EditText> edtMentorsList) {
            this.edtMentorsList = edtMentorsList;
    }

    @Override
    public List<EditText> getEdtAchievementRecommendationList() {
        return edtAchievementRecommendationList;
    }

    @Override
    public void setEdtAchievementRecommendationList(List<EditText> edtAchievementRecommendationList) {
                this.edtAchievementRecommendationList = edtAchievementRecommendationList;
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
