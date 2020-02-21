package com.enseval.samuelseptiano.hcservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.enseval.samuelseptiano.hcservice.Fragment.SubMenuSetting.SettingMenuFragment;
import com.enseval.samuelseptiano.hcservice.R;


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
