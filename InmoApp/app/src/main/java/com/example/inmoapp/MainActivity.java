package com.example.inmoapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.inmoapp.Fragments.InmuebleNoAuthFragment;
import com.example.inmoapp.Listener.InmuebleListener;

public class MainActivity extends AppCompatActivity implements InmuebleListener {

    Button btnAccessLogin;
    FrameLayout f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new InmuebleNoAuthFragment())
                .commit();

        f = findViewById(R.id.contenedor);


        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent session = new Intent(getApplicationContext(), SessionActivity.class);
                startActivity(session);
            }
        });

        /*btnAccessLogin = findViewById(R.id.btnAccessLogin);

        btnAccessLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent session = new Intent(getApplicationContext(), SessionActivity.class);
                startActivity(session);
            }
        });*/
    }

    @Override
    public void verInmueble(String inmueble) {

    }
}
