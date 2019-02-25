package com.example.inmoapp;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAccessLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAccessLogin = findViewById(R.id.btnAccessLogin);

        btnAccessLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent session = new Intent(getApplicationContext(), SessionActivity.class);
                startActivity(session);
            }
        });
    }
}
