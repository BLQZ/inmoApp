package com.example.inmoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.inmoapp.Fragments.InmuebleFragment;
import com.example.inmoapp.Fragments.LoginFragment;
import com.example.inmoapp.Listener.InmuebleListener;

public class InmoActivity extends AppCompatActivity implements InmuebleListener {

    private TextView mTextMessage;

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
                    return true;
                case R.id.navigation_dashboard:
                    /*mTextMessage.setText(R.string.title_dashboard);*/
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
    }

    @Override
    public void verInmueble(String inmueble) {

    }
}
