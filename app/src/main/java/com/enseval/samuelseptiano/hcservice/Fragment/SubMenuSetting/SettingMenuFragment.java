package com.enseval.samuelseptiano.hcservice.Fragment.SubMenuSetting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.enseval.samuelseptiano.hcservice.Activity.MainActivity;
import com.enseval.samuelseptiano.hcservice.Activity.SettingsActivity;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.Session.SessionManagement;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;


public class SettingMenuFragment extends Fragment {
    View rootView;

    TextView tvAboutApp,tvResetPassword,tvResetGuideline;


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
        tvResetGuideline = rootView.findViewById(R.id.tvResetGuideline);
        tvResetPassword = rootView.findViewById(R.id.tvResetPassword);

        tvAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutApp();
            }
        });

        tvResetGuideline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGuidresetGuideline();
            }
        });

        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        return rootView;
    }

    private void aboutApp(){

        PackageManager manager = getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;

        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                .setMessage(getContext().getResources().getString(R.string.app_name)+" Ver "+version+" © February 2020")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        //
                    }
                }).show();

        //2. now setup to change color of the button
        alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));
    }

    private void resetPassword(){

        PackageManager manager = getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;

        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                .setMessage("Silahkan mengubungi HR Service untuk melakukan reset password atau email ke hr.service@enseval.com")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        //
                    }
                }).show();

        //2. now setup to change color of the button
        alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));
    }

    private void resetGuidresetGuideline(){

        PackageManager manager = getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;

        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                .setMessage("Apakah anda ingin melakukan reset guideline aplikasi ini?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        SessionManagement session = new SessionManagement(getContext());
                        session.updateRanBefore(false);
                        MaterialShowcaseView.resetAll(getContext());

                        //
                    }
                }).show();

        //2. now setup to change color of the button
        alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));
    }

}
