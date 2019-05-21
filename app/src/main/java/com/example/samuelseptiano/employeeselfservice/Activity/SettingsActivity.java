package com.example.samuelseptiano.employeeselfservice.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuSetting.SettingMenuFragment;
import com.example.samuelseptiano.employeeselfservice.R;


public class SettingsActivity extends AppCompatActivity {
    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar mToolbar= (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(),"Hello", Toast.LENGTH_SHORT).show();


            }
        });

        fr = new SettingMenuFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame_setting, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.commit();
        Toast.makeText(getApplicationContext(), "Setting Menu Area", Toast.LENGTH_SHORT).show();

}}
