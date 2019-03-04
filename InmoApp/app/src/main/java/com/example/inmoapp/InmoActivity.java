package com.example.inmoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.inmoapp.Fragments.AjustesFragment;
import com.example.inmoapp.Fragments.InmuebleFavFragment;
import com.example.inmoapp.Fragments.InmuebleFragment;
import com.example.inmoapp.Fragments.LoginFragment;
import com.example.inmoapp.Fragments.MyInmuebleFragment;
import com.example.inmoapp.Generator.UtilUser;
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

                    btnToAddInmueble.hide();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new InmuebleFavFragment())
                            .commit();

                    btnToAddInmueble.hide();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new AjustesFragment())
                            .commit();

                    btnToAddInmueble.hide();
                    return true;
                case R.id.navigation_mineProperties:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new MyInmuebleFragment())
                            .commit();

                    btnToAddInmueble.show();
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
        btnToAddInmueble.hide();

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

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        // Defino qué quiero hacer cuando el usuario pulse el botón
        // volver o atrás del móvil

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2.1. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        // 2.2. Añadir botones al diálogo
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                UtilUser.clearSharedPreferences(InmoActivity.this);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

}
