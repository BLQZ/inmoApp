package com.example.inmoapp.Services;

import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InmuebleService {


    @GET("/properties")
    Call<ResponseContainer<Inmueble>> getListInmuble();

}
