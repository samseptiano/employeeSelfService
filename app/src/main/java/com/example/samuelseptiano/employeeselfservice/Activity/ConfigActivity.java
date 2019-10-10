package com.example.samuelseptiano.employeeselfservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.samuelseptiano.employeeselfservice.Helper.ConfigRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.ConfigModel;
import com.example.samuelseptiano.employeeselfservice.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ConfigActivity extends AppCompatActivity {

    Button btnSetup;
    EditText edtUrl;
    ConfigModel cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        ConfigRealmHelper configRealmHelper = new ConfigRealmHelper(getApplicationContext());

        btnSetup = findViewById(R.id.btnSetup);
        edtUrl = findViewById(R.id.edtURL);

        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configRealmHelper.deleteAllData();
                if(edtUrl.getText().equals(null) || edtUrl.getText().equals("")) {

                    cm = new ConfigModel("1", "https://e-recruitment.enseval.com/e-Kiosk/api/");
                }
                else{
                    cm = new ConfigModel("1", edtUrl.getText().toString());
                }

                configRealmHelper.addArticle(cm);
                Toast.makeText(getApplicationContext(),"Setup URL Successfully!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ConfigActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
}
