package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;


public class ProfileDataFragment extends Fragment {

    TextView tvUserName;
    TextView tvEmail,tvNIK;
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
//        tvToken = (TextView) rootView.findViewById(R.id.tvBirthDate);

        tvUserName.setText(usr.get(0).getUsername());
        tvEmail.setText(usr.get(0).getEmpEmail());
        tvNIK.setText(usr.get(0).getEmpNIK());

        return rootView;
    }
}
