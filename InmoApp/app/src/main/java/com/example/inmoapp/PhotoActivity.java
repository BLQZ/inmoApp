package com.example.inmoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmoapp.Fragments.PhotoFragment;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.TipoAutenticacion;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.Model.Photo;
import com.example.inmoapp.Model.ResponseContainer;
import com.example.inmoapp.Services.InmuebleService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoActivity extends AppCompatActivity {

    private FrameLayout f;
    private FloatingActionButton addPhoto;
    Uri uriSelected;
    Context ctx;
    String id;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        /*Toolbar toolbar = findViewById(R.id.toolbar);*/
        /*setSupportActionBar(toolbar);*/

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Bundle b = getIntent().getExtras();
        id = b.getString("id");
        Bundle args = new Bundle();

        args.putString("idB", id);
        PhotoFragment p = new PhotoFragment();
        p.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, p)
                .commit();

        f = findViewById(R.id.contenedor);

        addPhoto = findViewById(R.id.addPhoto);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto(id);
            }
        });

        /*PhotoFragment p = new PhotoFragment();
        p.setArguments(getIntent().getExtras());*/




    }

    public String mandarId(){
        Bundle b = getIntent().getExtras();
        String id = b.getString("id");

        return id;
    }

    public void addPhoto(String id) {
        performFileSearch();
        if(uriSelected != null){
            InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(PhotoActivity.this), TipoAutenticacion.JWT);

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
                        MultipartBody.Part.createFormData("photo", "photo", requestFile);


                RequestBody propertyId = RequestBody.create(MultipartBody.FORM, id.trim());

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
                /*Glide
                        .with(imgInmueble.getContext())
                        .load(uri)
                        .into(imgInmueble);
                uriSelected = uri;
                btnAddInmueble.setEnabled(true);*/
            }
        }
    }

    Boolean validarString (RequestBody texto) {
        return texto != null && texto.toString().length() >0;
    }

}
