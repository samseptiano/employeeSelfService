package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;


public class ProfileDataFragment extends Fragment {

    TextView tvUserName;
    TextView tvEmail,tvNIK,tvName,tvJobTitle,tvDept,tvOrgName;
    TextView tvToken;
    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View rootView =  inflater.inflate(R.layout.fragment_profile_data, container, false);

        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        tvUserName = (TextView) rootView.findViewById(R.id.tvUsername);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);
        tvNIK = (TextView) rootView.findViewById(R.id.tvNik);
        tvName = (TextView) rootView.findViewById(R.id.tvEmpName);
        tvDept = (TextView) rootView.findViewById(R.id.tvDepartment);
        tvJobTitle = (TextView) rootView.findViewById(R.id.tvJobTitleName);
        tvOrgName = (TextView) rootView.findViewById(R.id.tvcompanyName);
//        tvToken = (TextView) rootView.findViewById(R.id.tvBirthDate);

        tvUserName.setText(usr.get(0).getUsername());
        tvEmail.setText(usr.get(0).getEmpEmail());
        tvNIK.setText(usr.get(0).getEmpNIK());

        tvName.setText(usr.get(0).getEmpName());
        tvDept.setText(usr.get(0).getOrgName());
        tvOrgName.setText(usr.get(0).getCompanyName());
        tvJobTitle.setText(usr.get(0).getJobTitleName());

        return rootView;
    }
}
