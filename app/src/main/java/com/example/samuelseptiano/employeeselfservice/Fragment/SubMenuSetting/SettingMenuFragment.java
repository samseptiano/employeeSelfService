package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuSetting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.DevelopmentPlanDetailFragment;
import com.example.samuelseptiano.employeeselfservice.R;


public class SettingMenuFragment extends Fragment {
    View rootView;

    TextView tvAboutApp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setting_menu, container, false);

        tvAboutApp = rootView.findViewById(R.id.tvAboutApp);
        tvAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutApp();
            }
        });

        return rootView;
    }

    private void aboutApp(){
        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                .setMessage("HCKiosK Ver 1.1 © February 2020")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        //
                    }
                }).show();

        //2. now setup to change color of the button
        alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));
    }

}
