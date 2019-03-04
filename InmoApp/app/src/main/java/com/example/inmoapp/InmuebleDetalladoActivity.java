package com.example.inmoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestBuilder;
import com.example.inmoapp.Adapter.ViewPagerAdapter;
import com.example.inmoapp.Fragments.PhotoFragment;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.Generator.UtilUser;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.PropertyResponseOne;
import com.example.inmoapp.Model.ResponseContainer;
import com.example.inmoapp.Model.Rows;
import com.example.inmoapp.Services.InmuebleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalladoActivity extends AppCompatActivity {

    ViewPager viewPager;
    TextView tvTittle, tvDescription, tvAddress, tvRooms, tvPrice, tvCityProvince, position, tvOwner;
    List<String> imagenes = new ArrayList<>();
    List<RequestBuilder<Bitmap>> imagenesBitMap;
    Intent tweetIntent;
    String tweetDefault, idProyec;
    ImageView btnTwitter;
    Rows inmueble;
    FloatingActionButton btnEditar, btnPhotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmueble_detallado);

        viewPager = findViewById(R.id.viewPager);
        tvTittle = (TextView) findViewById(R.id.tvTittle);
        tvDescription = findViewById(R.id.tvDescription);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvRooms = findViewById(R.id.tvRooms);
        tvPrice = findViewById(R.id.tvPrice);
        tvCityProvince = findViewById(R.id.tvCityProvince);
        position = findViewById(R.id.position);
        tvOwner = findViewById(R.id.tvOwner);

        btnEditar = findViewById(R.id.btnEditProperty);
        btnPhotos = findViewById(R.id.btnPhotos);
        /*btnPhotos.hide();*/





        Bundle extras = getIntent().getExtras();
        idProyec = extras.getString("id");

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InmuebleDetalladoActivity.this, AddInmuebleActivity.class);
                i.putExtra("id", idProyec);
                startActivity(i);
            }
        });



        btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Intent i = new Intent(InmuebleDetalladoActivity.this, PhotoActivity.class);
                b.putString("id", idProyec);
                /*PhotoFragment p = new PhotoFragment();*/
                /*p.setArguments(b);*/

                /*Intent i = new Intent(InmuebleDetalladoActivity.this, PhotoActivity.class);*/
                i.putExtras(b);
                startActivity(i);


            }
        });

        /*Intent i = getIntent();
        inmueble = (Inmueble) i.getSerializableExtra("inmueble");*/

        findViewById(R.id.btnTwitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, tvTittle.getText().toString() + "\n-" + tvDescription.getText().toString() + "\n\tCompartido con InmoApp.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        InmuebleService service = ServiceGenerator.createService(InmuebleService.class);
        Call<PropertyResponseOne> call = service.getInmueble(idProyec);


        call.enqueue(new Callback<PropertyResponseOne>() {

            /*private Inmueble inmueble;*/

            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<PropertyResponseOne> call, Response<PropertyResponseOne> response) {
                if (response.code() != 200) {
                    Toast.makeText(InmuebleDetalladoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    inmueble = response.body().getRows();
                    Log.d("el response", response.toString());
                    tvAddress.setText(inmueble.getAddress());
                    tvTittle.setText(inmueble.getTitle());
                    tvCityProvince.setText(inmueble.getCity()+"("+inmueble.getProvince()+")");
                    tvDescription.setText(inmueble.getDescription());
                    tvPrice.setText(String.valueOf(inmueble.getPrice()));
                    tvRooms.setText(String.valueOf(inmueble.getRooms()));
                    tvOwner.setText(inmueble.getOwnerId().getName() + " - " + inmueble.getOwnerId().getEmail());
                    /*if(inmueble.getPhotos().length==0){
                        imagenes.add("https://i.imgur.com/0UbI7MB.png");
                    } else {
                        imagenes = Arrays.asList(inmueble.getPhotos());
                    }*/
                    imagenes = Arrays.asList(inmueble.getPhotos());

                    /*imagenes.add("https://i.imgur.com/0UbI7MB.png");*/
                    /*imagenes.add("https://imgs.nestimg.com/casa_venta_granada_capital_san_francisco_javier_110864311921321328.jpg");*/
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(InmuebleDetalladoActivity.this, imagenes, position);
                    viewPager.setAdapter(viewPagerAdapter);
                    /*tweetDefault = "Disfrutando de la ExpoFP 2019 con " + proyec.getNombre();*/

                    /*if(!UtilUser.getId(InmuebleDetalladoActivity.this).equals(inmueble.getOwnerId().getId())){
                        btnPhotos.setVisibility(View.INVISIBLE);
                        *//*btnPhotos.setVisibility(View.INVISIBLE);*//*
                    } else {
                        btnPhotos.setVisibility(View.VISIBLE);
                    }*/

                }
            }

            @Override
            public void onFailure(Call<PropertyResponseOne> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(InmuebleDetalladoActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
