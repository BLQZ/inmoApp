package com.example.inmoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.TipoAutenticacion;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.Generator.UtilUser;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.InmuebleDto;
import com.example.inmoapp.Services.InmuebleService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInmuebleActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    private EditText etTitle, etDescription, etAddress, etRooms, etPrice, etSize, etCity, etProvince, etZipCode;
    private RadioGroup radioGroup;
    private RadioButton rbAlquiler, rbCompra, rbObraNueva;
    private Button btnAddInmueble, subirImagen;
    private ImageView imgInmueble;
    private String loc;
    private double latitud, longitud;
    Uri uriSelected;
    Context ctx;
    InmuebleDto newInmueble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inmueble);

        etTitle = findViewById(R.id.editTextTitle);
        etDescription = findViewById(R.id.editTextDescription);
        etAddress = findViewById(R.id.editTextAddress);
        etRooms = findViewById(R.id.editTextRooms);
        etPrice = findViewById(R.id.editTextPrice);
        etSize = findViewById(R.id.editTextSize);
        etCity = findViewById(R.id.editTextCity);
        etProvince = findViewById(R.id.editTextProvince);
        etZipCode = findViewById(R.id.editTextZipcode);

        radioGroup = findViewById(R.id.category);
        rbAlquiler = findViewById(R.id.rbAlquiler);
        rbCompra = findViewById(R.id.rbVenta);
        rbObraNueva = findViewById(R.id.rbObraNueva);

        imgInmueble = findViewById(R.id.imageViewPreImg);

        btnAddInmueble = findViewById(R.id.btnAddInmueble);

        subirImagen = findViewById(R.id.buttonSubirImagen);

        subirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

        btnAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInmueble();
            }
        });


    }

    /**
     * Obtener Latitud y longitud a partir de ADDRESS
     * Geocoder coder = new Geocoder(this);
     *     try {
     *         ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName("Your Address", 50);
     *         for(Address add : adresses){
     *             if (statement) {//Controls to ensure it is right address such as country etc.
     *                 double longitude = add.getLongitude();
     *                 double latitude = add.getLatitude();
     *             }
     *         }
     *     } catch (IOException e) {
     *         e.printStackTrace();
     *     }
     */

    public void addInmueble(){

        // TODO: AÑADIR CHECK A LOS RADIOBUTTON, Y AÑADIR FOTO DESPUÉS DE LLAMAR A LA PETICION DE AÑADIR INMUEBLE

        // Obtener Latitud y longitud a partir de ADDRESS
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(etAddress.getText().toString(), 1);
            for(Address add : adresses){
                latitud = add.getLongitude();
                longitud = add.getLatitude();
                /*if (statement) {//Controls to ensure it is right address such as country etc.
                    latitud = add.getLongitude();
                    longitud = add.getLatitude();
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(rbAlquiler.isChecked()){

        }

        if(rbObraNueva.isChecked()){

        }

        if(rbCompra.isChecked()){

        }

        newInmueble = new InmuebleDto(etTitle.getText().toString(), etDescription.getText().toString(),
                Float.parseFloat(etPrice.getText().toString()), Integer.parseInt(etRooms.getText().toString()),
                Float.parseFloat(etSize.getText().toString()), "5c6d9b4381f1df001760c7d7", etAddress.getText().toString(),
                etZipCode.getText().toString(), etCity.getText().toString(), etProvince.getText().toString(), longitud+", "+latitud);

        InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(this), TipoAutenticacion.JWT);
        Call<Inmueble> call = service.addInmueble(newInmueble);

        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Log.d("Uploaded", "Éxito");
                    Log.d("Uploaded", response.body().toString());
                    startActivity(new Intent(getApplicationContext(), InmoActivity.class));
                } else {
                    Log.e("Upload error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.e("Upload error", t.getMessage());
            }
        });

    }

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
                //showImage(uri);
                Glide
                        .with(this)
                        .load(uri)
                        .into(imgInmueble);
                uriSelected = uri;
                btnAddInmueble.setEnabled(true);
            }
        }
    }

    Boolean validarString (RequestBody texto) {
        return texto != null && texto.toString().length() >0;
    }
}
