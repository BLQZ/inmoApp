package com.example.inmoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestBuilder;
import com.example.inmoapp.Adapter.ViewPagerAdapter;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.ResponseContainer;
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
    Inmueble inmueble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmueble_detallado);

        viewPager = findViewById(R.id.viewPager);
        tvTittle = (TextView) findViewById(R.id.tvTittle);
        tvDescription = findViewById(R.id.tvDescription);
        tvAddress = findViewById(R.id.tvAddress);
        tvRooms = findViewById(R.id.tvRooms);
        tvPrice = findViewById(R.id.tvPrice);
        tvCityProvince = findViewById(R.id.tvCityProvince);
        position = findViewById(R.id.position);
        tvOwner = findViewById(R.id.tvOwner);

        Bundle extras = getIntent().getExtras();
        idProyec = extras.getString("id");

        /*Intent i = getIntent();
        idProyec = (String) i.getSerializableExtra("id");*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*tvAddress.setText(inmueble.getAdress());
        tvTittle.setText(inmueble.getTitle());
        tvCityProvince.setText(inmueble.getCity()+"("+inmueble.getProvince()+")");
        tvDescription.setText(inmueble.getDescription());
        tvPrice.setText(String.valueOf(inmueble.getPrice()));
        tvRooms.setText(String.valueOf(inmueble.getRooms()));
        tvOwner.setText(inmueble.getOwnerId().getName());
        imagenes = Arrays.asList(inmueble.getPhotos());
        *//*imagenes.add("https://i.imgur.com/0UbI7MB.png");
        imagenes.add("https://imgs.nestimg.com/casa_venta_granada_capital_san_francisco_javier_110864311921321328.jpg");*//*
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(InmuebleDetalladoActivity.this, imagenes, position);
        viewPager.setAdapter(viewPagerAdapter);*/


        InmuebleService service = ServiceGenerator.createService(InmuebleService.class);
        Call<Inmueble> call = service.getInmueble(idProyec);


        call.enqueue(new Callback<Inmueble>() {

            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.code() != 200) {
                    Toast.makeText(InmuebleDetalladoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    inmueble = response.body();
                    tvAddress.setText(inmueble.getAdress());
                    tvTittle.setText(inmueble.getTitle());
                    tvCityProvince.setText(inmueble.getCity()+"("+inmueble.getProvince()+")");
                    tvDescription.setText(inmueble.getDescription());
                    tvPrice.setText(String.valueOf(inmueble.getPrice()));
                    tvRooms.setText(String.valueOf(inmueble.getRooms()));
                    /*tvOwner.setText(inmueble.getOwnerId().getName());*/
                    /*imagenes = Arrays.asList(inmueble.getPhotos());*/
                    imagenes.add("https://i.imgur.com/0UbI7MB.png");
                    imagenes.add("https://imgs.nestimg.com/casa_venta_granada_capital_san_francisco_javier_110864311921321328.jpg");
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(InmuebleDetalladoActivity.this, imagenes, position);
                    viewPager.setAdapter(viewPagerAdapter);
                    /*tweetDefault = "Disfrutando de la ExpoFP 2019 con " + proyec.getNombre();*/

                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(InmuebleDetalladoActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
