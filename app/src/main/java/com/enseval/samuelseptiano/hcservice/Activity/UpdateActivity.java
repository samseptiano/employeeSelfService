package com.enseval.samuelseptiano.hcservice.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.enseval.samuelseptiano.hcservice.R;

public class UpdateActivity extends AppCompatActivity {

    Button btnUpdate;
    String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        btnUpdate = findViewById(R.id.btnUpdateApp);
        PACKAGE_NAME = getApplicationContext().getPackageName();
        getSupportActionBar().hide();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+PACKAGE_NAME));
                startActivity(intent);
            }
        });

    }
}
