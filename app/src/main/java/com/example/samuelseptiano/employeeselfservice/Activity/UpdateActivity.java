package com.example.samuelseptiano.employeeselfservice.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.R;

public class UpdateActivity extends AppCompatActivity {

    Button btnUpdate;
    String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        btnUpdate = findViewById(R.id.btnUpdateApp);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+PACKAGE_NAME));
                startActivity(intent);
            }
        });

    }
}
