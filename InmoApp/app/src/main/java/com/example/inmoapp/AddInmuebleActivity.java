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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.TipoAutenticacion;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.Generator.UtilUser;
import com.example.inmoapp.Model.Category;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.InmuebleDto;
import com.example.inmoapp.Model.LoginResponse;
import com.example.inmoapp.Model.Photo;
import com.example.inmoapp.Model.ResponseContainer;
import com.example.inmoapp.Services.InmuebleService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private List<Category> categories;
    private String categoryId;
    String propertyIdAdd;

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

        /*getCategories();*/


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
            /*for(Category c : categories) {
                if(c.getName().equalsIgnoreCase("Alquiler"))
                    categoryId = c.getId();
            }*/
            categoryId = "5c6d9b4381f1df001760c7d7";
        }

        if(rbObraNueva.isChecked()){
            /*for(Category c : categories) {
                if(c.getName().equalsIgnoreCase("Obra Nueva"))
                    categoryId = c.getId();
            }*/
            categoryId = "5c6d9b5581f1df001760c7d9";
        }

        if(rbCompra.isChecked()){
            /*for(Category c : categories) {
                if(c.getName().equalsIgnoreCase("Venta"))
                    categoryId = c.getId();
            }*/
            categoryId = "5c6d9b4e81f1df001760c7d8";
        }

        newInmueble = new InmuebleDto(etTitle.getText().toString(), etDescription.getText().toString(),
                Float.parseFloat(etPrice.getText().toString()), Integer.parseInt(etRooms.getText().toString()),
                Float.parseFloat(etSize.getText().toString()), categoryId, etAddress.getText().toString(),
                etZipCode.getText().toString(), etCity.getText().toString(), etProvince.getText().toString(), longitud+", "+latitud);

        InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(this), TipoAutenticacion.JWT);
        Call<Inmueble> call = service.addInmueble(newInmueble);

        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Log.d("Uploaded", "Éxito");
                    Log.d("Uploaded", response.body().toString());
                    propertyIdAdd = response.body().getId();
                } else {
                    Log.e("Upload error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.e("Upload error", t.getMessage());
            }
        });

        addPhoto();

    }

    public void addPhoto() {
        if(uriSelected != null){
            InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(this), TipoAutenticacion.JWT);

            try {
                InputStream inputStream = this.getContentResolver().openInputStream(uriSelected);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                int cantBytes;
                byte[] buffer = new byte[1024*4];

                while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                    baos.write(buffer,0,cantBytes);
                }


                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(this.getContentResolver().getType(uriSelected)), baos.toByteArray());


                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("picture", "picture", requestFile);


                RequestBody propertyId = RequestBody.create(MultipartBody.FORM, propertyIdAdd.trim());

                if (validarString(propertyId)) {

                    Call<ResponseContainer<Photo>> callRegister = service.addImgToProperty(body, propertyId);

                    callRegister.enqueue(new Callback<ResponseContainer<Photo>>() {
                        @Override
                        public void onResponse(Call<ResponseContainer<Photo>> call, Response<ResponseContainer<Photo>> response) {
                            if (response.isSuccessful()) {
                                Log.d("Uploaded", "Éxito");
                                Log.d("Uploaded", response.body().toString());
                                startActivity(new Intent(getParent(), InmoActivity.class));
                            } else {
                                Log.e("Upload error", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseContainer<Photo>> call, Throwable t) {
                            Log.e("Upload error", t.getMessage());
                        }
                    });

                }else {
                    Toast.makeText(ctx, "MAL", Toast.LENGTH_SHORT).show();
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                        .with(imgInmueble.getContext())
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

    public void getCategories(){

        categories = new ArrayList<>();
        InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(this), TipoAutenticacion.JWT);
        Call<ResponseContainer<Category>> call = service.getListCategories();

        call.enqueue(new Callback<ResponseContainer<Category>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Category>> call, Response<ResponseContainer<Category>> response) {

                categories = (ArrayList) Arrays.asList(response.body().getRows());
            }

            @Override
            public void onFailure(Call<ResponseContainer<Category>> call, Throwable t) {
                Log.e("Categories error", t.getMessage());
            }
        });
    }
}
