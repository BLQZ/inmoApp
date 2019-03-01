package com.example.inmoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.inmoapp.Fragments.InmuebleFavFragment;
import com.example.inmoapp.Fragments.InmuebleFragment;
import com.example.inmoapp.Fragments.LoginFragment;
import com.example.inmoapp.Listener.InmuebleListener;

import static android.view.View.INVISIBLE;

public class InmoActivity extends AppCompatActivity implements InmuebleListener {

    private TextView mTextMessage;
    private FloatingActionButton btnToAddInmueble;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new InmuebleFragment())
                            .commit();

                    btnToAddInmueble.show();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new InmuebleFavFragment())
                            .commit();

                    btnToAddInmueble.hide();
                    return true;
                case R.id.navigation_notifications:
                    /*mTextMessage.setText(R.string.title_notifications);*/
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmo);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new InmuebleFragment())
                .commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btnToAddInmueble = findViewById(R.id.btnToAddInmueble);

        btnToAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addInmueble = new Intent(getApplicationContext(), AddInmuebleActivity.class);
                startActivity(addInmueble);
            }
        });
    }

    @Override
    public void verInmueble(String inmueble) {

    }
}
