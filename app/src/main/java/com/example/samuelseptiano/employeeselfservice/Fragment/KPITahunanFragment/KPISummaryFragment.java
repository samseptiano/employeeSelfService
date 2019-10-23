package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;


public class KPISummaryFragment extends Fragment {

    private static final String TAG = "";
    TextView name,nik, EmpStatus, status, dept, company,location, tvNilai, tvNilaiTotal, tvAprname1, tvAprname2, tvAprStatus1,tvAprStatus2;
RatingBar rbResult;
Button btnApprove;
    View rootView;
    LinearLayout lnProgressbar;
    KPIUserAnswerList kpiUserAnswerList;
    EditText edtKelebihan,edtKekurangan,edtRencanaTindaklanjut;

    protected boolean mIsVisibleToUser;

    static KPIHeader smt1 = new KPIHeader();
    static KPIHeader smt2 = new KPIHeader();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if(b != null) {
            smt1 = (KPIHeader) b.getSerializable("smt1");
            smt2 = (KPIHeader) b.getSerializable("smt2");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;

//        lnProgressbar.setVisibility(View.VISIBLE);
        if (isResumed()) { // fragment have created
            if (mIsVisibleToUser) {
                onVisible();
                lnProgressbar.setVisibility(View.GONE);
            } else {
                onInVisible();
                lnProgressbar.setVisibility(View.VISIBLE);

            }
        }
    }

    public void onVisible() {
        KPIKuantitatifTahunanFragment kp = new KPIKuantitatifTahunanFragment();
        kpiUserAnswerList.setKpiUserAnswerList1(kp.getQuestionSmt1().getKpiQuestionsList());
        kpiUserAnswerList.setKpiUserAnswerList2(kp.getQuestionSmt2().getKpiQuestionsList());
        kpiUserAnswerList.setEmpNIK(kp.getQuestionSmt1().getNIK());
        kpiUserAnswerList.setKPIType(kp.type);
        kpiUserAnswerList.setStatus1(kp.getQuestionSmt1().getStatus());
        kpiUserAnswerList.setDept(kp.getQuestionSmt1().getDept());
        kpiUserAnswerList.setKelebihan("kelebihan");
        kpiUserAnswerList.setKekurangan("kekurangan");
        kpiUserAnswerList.setRencanaTindaklanjut("rencanaTindaklanjut");

        //Toast.makeText(getActivity(), TAG + "visible", Toast.LENGTH_SHORT).show();
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
       if(kp.getInitKh().getEmpName().equals("")){
           ArrayList<UserRealmModel> usr;
           usr = userRealmHelper.findAllArticle();
           String names = usr.get(0).getUsername();
           String niks = usr.get(0).getEmpNIK();
           name.setText(names);
           nik.setText(niks);
       }
       else {
           name.setText(kp.getInitKh().getEmpName());
           nik.setText(kp.getInitKh().getNIK());
       }

        try {
           rbResult.setRating(kpiUserAnswerList.getKpiUserAnswerList1().get(0).getCheckedId());
            //set rbresult dengan value
        } catch (Exception e) {
        }
        if (rbResult.getRating() == 0) {
            try {
                status.setText("N/A");
                btnApprove.setVisibility(View.GONE);
                tvNilai.setText("Nilai: 0");
                tvNilaiTotal.setText("Nilai Akhir:" + Integer.toString(kpiUserAnswerList.getKpiUserAnswerList1().get(0).getCheckedId()));
            }
            catch (Exception e){

            }
        } else {
            btnApprove.setVisibility(View.VISIBLE);
        }


       if(kp.typeStatic.equals("Approve")) {
           edtKelebihan.setText(kpiUserAnswerList.getKelebihan());
           edtKekurangan.setText(kpiUserAnswerList.getKekurangan());
           edtRencanaTindaklanjut.setText(kpiUserAnswerList.getRencanaTindaklanjut());
           Toast.makeText(getActivity(), kp.typeStatic, Toast.LENGTH_SHORT).show();
       }
        else if(!kp.typeStatic.equals("Approve")) {
//            edtKelebihan.setText("");
//            edtKekurangan.setText("");
//            edtRencanaTindaklanjut.setText("");
            //Toast.makeText(getActivity(), kp.typeStatic, Toast.LENGTH_SHORT).show();
        }


    }

    public void onInVisible() {
        //Toast.makeText(getActivity(), TAG + "invisible", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_kpi_summary, container, false);

        //KPISummaryFragment test = (KPISummaryFragment) getFragmentManager().findFragmentById(R.id.f).findFragmentByTag("testID");

         kpiUserAnswerList = new KPIUserAnswerList();


        name = rootView.findViewById(R.id.tvEmpName);
        EmpStatus = rootView.findViewById(R.id.tvStatuss);
        status = rootView.findViewById(R.id.tv_result_status);
        nik = rootView.findViewById(R.id.tvNIK);
        dept = rootView.findViewById(R.id.tvDept);
        location = rootView.findViewById(R.id.tvLocation);
        company = rootView.findViewById(R.id.tvCompany);
        rbResult = rootView.findViewById(R.id.ratingBarResult);
        btnApprove = rootView.findViewById(R.id.btn_approve_result);
        lnProgressbar = rootView.findViewById(R.id.linlaHeaderProgress);
        edtKelebihan = rootView.findViewById(R.id.edtResultkelebihan);
        edtKekurangan = rootView.findViewById(R.id.edtResultKekurangan);
        edtRencanaTindaklanjut = rootView.findViewById(R.id.edtResultRencanatindaklanjut);
        tvNilai = rootView.findViewById(R.id.tv_score);
        tvNilaiTotal = rootView.findViewById(R.id.tv_final_score);
        tvAprname1 = rootView.findViewById(R.id.tv_approver_name_1);
        tvAprname2 = rootView.findViewById(R.id.tv_approver_name_2);
        tvAprStatus1 = rootView.findViewById(R.id.tv_approver_status_1);
        tvAprStatus2 = rootView.findViewById(R.id.tv_approver_status_2);

        btnApprove.setVisibility(View.VISIBLE);

                lnProgressbar.setVisibility(View.VISIBLE);

        KPIKuantitatifTahunanFragment kp = new KPIKuantitatifTahunanFragment();

        try {
            name.setText(kp.getInitKh().getEmpName());
            nik.setText(kp.getInitKh().getNIK());
            EmpStatus.setText(kp.getInitKh().getStatus());
            dept.setText(kp.getInitKh().getDept());
            company.setText(kp.getInitKh().getCompany());
            location.setText(kp.getInitKh().getLocationName());
            tvAprStatus2.setText(kp.getInitKh().getStatus2());
            tvAprStatus1.setText(kp.getInitKh().getStatus1());
            tvAprname1.setText(kp.getInitKh().getAtasanLangsung());
            tvAprname2.setText(kp.getInitKh().getAtasanTakLangsung());


            if(kp.getInitKh().getStatus2().equals("Approved")){
              tvAprStatus2.setTextColor(Color.GREEN);
            }
            else{
                tvAprStatus2.setTextColor(Color.RED);
            }
            if(kp.getInitKh().getStatus1().equals("Approved")){
                tvAprStatus1.setTextColor(Color.GREEN);
            }
            else{
                tvAprStatus1.setTextColor(Color.RED);
            }
            if(kp.getInitKh().getStatus().equals("Approved")){
                EmpStatus.setTextColor(Color.GREEN);
            }
            else{
                EmpStatus.setTextColor(Color.RED);
            }

            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        name.setText(kp.getQuestionSmt1().getEmpName());
//                        nik.setText(kp.getQuestionSmt1().getNIK());
//                        Toast.makeText(getContext(), kp.getQuestionSmt1().getEmpName(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        catch(Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        return rootView;
    }
}
