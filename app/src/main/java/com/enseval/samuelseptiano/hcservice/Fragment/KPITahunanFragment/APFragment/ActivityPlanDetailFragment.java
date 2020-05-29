package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.APFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityPlanDetailFragment extends Fragment {

    View rootView;

    ImageView imgPhoto;
    TextView tvEmpName,tvEmpJobTitle,tvAtasan1,tvAtasan2,tvJobTitleAtasan1,tvjobTitleAtasan2;

    ImageButton imgExpand;
    LinearLayout lnEmpDetail;

    Boolean isExpanded=false;

    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
    ArrayList<UserRealmModel> usr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_activity_plan_detail, container, false);
        imgPhoto = rootView.findViewById(R.id.imgEmpPhoto);
        tvEmpName = rootView.findViewById(R.id.tv_employeeName);
        tvEmpJobTitle = rootView.findViewById(R.id.tv_dept);
        tvAtasan1 = rootView.findViewById(R.id.tvAtasan1);
        tvAtasan2 = rootView.findViewById(R.id.tvAtasan2);
        tvJobTitleAtasan1 = rootView.findViewById(R.id.tvJobTitleAtasan1);
        tvjobTitleAtasan2 = rootView.findViewById(R.id.tvJobTitleAtasan2);
        imgExpand = rootView.findViewById(R.id.imgExpand);
        lnEmpDetail = rootView.findViewById(R.id.lnEmpDetail);


        usr = userRealmHelper.findAllArticle();

        lnEmpDetail.setVisibility(View.GONE);
        imgExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpanded){
                    lnEmpDetail.setVisibility(View.GONE);
                    isExpanded=false;
                    imgExpand.setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_down));
                }
                else if(!isExpanded){
                    lnEmpDetail.setVisibility(View.VISIBLE);
                    isExpanded=true;
                    imgExpand.setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_up));

                }
            }
        });

        tvEmpName.setText(usr.get(0).getEmpName());
        tvEmpJobTitle.setText(usr.get(0).getJobTitleName()+" "+usr.get(0).getOrgName());
        tvAtasan1.setText(usr.get(0).getNamaAtasanLangsung());
        tvAtasan2.setText(usr.get(0).getNamaAtasanTakLangsung());
        tvJobTitleAtasan1.setText(usr.get(0).getJobTitleAtasan1());
        tvjobTitleAtasan2.setText(usr.get(0).getJobTitleAtasan2());
        Picasso.get()
                .load(usr.get(0).getEmpPhoto())
                .placeholder(R.drawable.user)
                .error(R.drawable.imgalt)
                .into(imgPhoto);
        return rootView;
    }

}
