package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevelopmentPlanLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DevelopmentPlanSubDetailFragment extends Fragment {

    String title;
    View rootView;

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    DatePickerDialog  datePickerDialog;
    SimpleDateFormat dateFormatter;

    //======================== Sub Detail ==============================
    List<View> lnList = new ArrayList<>();
    List<CheckBox> cbList = new ArrayList<>();
    List<TextView> tvNumbList = new ArrayList<>();
    List<TextView> tvTitleList = new ArrayList<>();
    List<EditText>edtDevelopmentActivitiesList  = new ArrayList<>();
    List<EditText>edtKPIList  = new ArrayList<>();
    List<EditText>edtDueDateList  = new ArrayList<>();
    List<EditText>edtMentorsList  = new ArrayList<>();
    List<EditText>edtAchievementRecommendationList  = new ArrayList<>();
    List<ImageButton>imgExpandList  = new ArrayList<>();
    List<LinearLayout>lnExpandList  = new ArrayList<>();

    List<LinearLayout> lnDevplansDetail  = new ArrayList<>();

    IDPLayoutListModel idpLayoutListModel  = new IDPLayoutListModel();

    List<IDPLayoutListModel> listIDP  = new ArrayList<>();
    //=====================================================================

    //============== Detail =========================
    List<Button> btnevplan = new ArrayList<>();
    List<CheckBox> cbDevplan  = new ArrayList<>();
    List<TextView> tvDevplan  = new ArrayList<>();
    List<LinearLayout> lnDevplans  = new ArrayList<>();
    DevelopmentPlanLayoutListModel developmentPlanLayoutListModel =new DevelopmentPlanLayoutListModel();

    List<DevelopmentPlanLayoutListModel> listDevPlan  = new ArrayList<>();
    //==================================================

    public interface EventListener {
        public List<CheckBox> getCbList();

        public void setCbList(List<CheckBox> cbList);

        public List<TextView> getTvNumbList();

        public void setTvNumbList(List<TextView> tvNumbList);

        public List<TextView> getTvTitleList();

        public void setTvTitleList(List<TextView> tvTitleList);

        public List<EditText> getEdtDevelopmentActivitiesList();

        public void setEdtDevelopmentActivitiesList(List<EditText> edtDevelopmentActivitiesList);

        public List<ImageButton> getImgExpandList();

        public void setImgExpandList(List<ImageButton> imgExpandList);

        public List<LinearLayout> getLnExpandList();

        public void setLnExpandList(List<LinearLayout> lnExpandList);

        public List<EditText> getEdtKPIList();

        public void setEdtKPIList(List<EditText> edtKPIList);

        public List<EditText> getEdtDueDateList();

        public void setEdtDueDateList(List<EditText> edtDueDateList);

        public List<EditText> getEdtMentorsList();

        public void setEdtMentorsList(List<EditText> edtMentorsList);

        public List<EditText> getEdtAchievementRecommendationList();

        public void setEdtAchievementRecommendationList(List<EditText> edtAchievementRecommendationList);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            title = bundle.getString("idpTitleDetail");
            lnList = (List<View>) bundle.getSerializable("lnList");
            cbList = (List<CheckBox>) bundle.getSerializable("cbList");
            tvNumbList = (List<TextView>) bundle.getSerializable("tvNumbList");
            tvTitleList = (List<TextView>) bundle.getSerializable("tvTitleList");
            edtDevelopmentActivitiesList  = (List<EditText>) bundle.getSerializable("edtDevelopmentActivitiesList");
            edtKPIList  = (List<EditText>) bundle.getSerializable("edtKPIList");
            edtDueDateList  = (List<EditText>) bundle.getSerializable("edtDueDateList");;
            edtMentorsList  = (List<EditText>) bundle.getSerializable("edtMentorsList");
            edtAchievementRecommendationList  = (List<EditText>) bundle.getSerializable("edtAchievementRecommendationList");
            imgExpandList  = (List<ImageButton>) bundle.getSerializable("imgExpandList");
            lnExpandList  = (List<LinearLayout>) bundle.getSerializable("lnExpandList");
            lnDevplansDetail  = (List<LinearLayout>) bundle.getSerializable("lnDevplansDetail");
            idpLayoutListModel  = (IDPLayoutListModel) bundle.getSerializable("idpLayoutListModel");
            listIDP  = (List<IDPLayoutListModel>) bundle.getSerializable("listIDP");
            //=====================================================================

            //============== Detail =========================
            btnevplan = (List<Button>) bundle.getSerializable("btnevplan");
            cbDevplan  = (List<CheckBox>) bundle.getSerializable("cbDevplan");
            tvDevplan  = (List<TextView>) bundle.getSerializable("tvDevplan");
            lnDevplans  = (List<LinearLayout>) bundle.getSerializable("lnDevplans");
            developmentPlanLayoutListModel = (DevelopmentPlanLayoutListModel) bundle.getSerializable("developmentPlanLayoutListModel");
            listDevPlan  = (List<DevelopmentPlanLayoutListModel>) bundle.getSerializable("listDevPlan");
        }
//        Toast.makeText(getContext(),Integer.toString(lnList.size()),Toast.LENGTH_LONG).show();
//        Toast.makeText(getContext(),title,Toast.LENGTH_LONG).show();
        rootView =  inflater.inflate(R.layout.fragment_development_plan_sub_detail, container, false);



        final boolean[] input = {false};

        final int[] ctr = {1};

        View myView;
        ViewGroup parent;
        LinearLayout formLayout;

//        myView = dialogs.findViewById(R.id.lnIdpDetail);
//        parent = (ViewGroup) myView.getParent();
//        parent.removeView(myView);

        formLayout = (LinearLayout)rootView.findViewById(R.id.formLayout);
        formLayout.removeAllViews();
        formLayout.setOrientation(LinearLayout.VERTICAL);

        TextView tvIdpTitle = rootView.findViewById(R.id.tvIdpTitle);
        Button btnAdd = rootView.findViewById(R.id.btnAdd);
        Button btnDelete = rootView.findViewById(R.id.btnDelete);

        MaterialSpinner spinnerImprovement = rootView.findViewById(R.id.spinnerImprovement);
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

        tvIdpTitle.setText(title);


        try {

            idpLayoutListModel =  new IDPLayoutListModel();
            for (int u=0;u<listIDP.size();u++){
                if(listIDP.get(u).getIdpTitle().equals(title)){
                    for(int y=0;y<listIDP.get(u).getLnIdpDetail().size();y++){

                        View childs = inflater.inflate(R.layout.idp_item_detail, formLayout, false);

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

                View child = inflater.inflate(R.layout.idp_item_detail, formLayout, false);

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
                idpLayoutListModel.setIdpTitle(title);
                idpLayoutListModel.setLnIdpDetail(lnList);
                idpLayoutListModel.setLinLaIdpDetail(formLayout);

                ii[0]++;
                input[0] = true;
                ctr[0] = finalCtr[0]++;


                if(input[0]) {
                    Toast.makeText(getContext(),Integer.toString(idpLayoutListModel.getTvIdpTitleDetail().size()),Toast.LENGTH_LONG).show();
                    listIDP.add(idpLayoutListModel);
                    input[0] = false;

                }


                fr = new DevelopmentPlanSubDetailFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle3.putSerializable("lnList", (Serializable) lnList);
                bundle3.putString("idpTitleDetail", title);
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

                android.app.FragmentTransaction transaction = getActivity().getFragmentManager()
                        .beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    transaction.setReorderingAllowed(false);
                }

                //======================= REFRESH FRAGMENT ===================================
                FragmentTransaction ftr = getFragmentManager().beginTransaction();
                ftr.detach(DevelopmentPlanSubDetailFragment.this).attach(DevelopmentPlanSubDetailFragment.this).commit();
                //============================================================================

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
                //======================= REFRESH FRAGMENT ===================================
                FragmentTransaction ftr = getFragmentManager().beginTransaction();
                ftr.detach(DevelopmentPlanSubDetailFragment.this).attach(DevelopmentPlanSubDetailFragment.this).commit();
                //============================================================================
            }
        });




        return rootView;
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
