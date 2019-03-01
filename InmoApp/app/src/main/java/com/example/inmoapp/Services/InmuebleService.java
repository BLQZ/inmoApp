package com.example.inmoapp.Services;

import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.InmuebleDto;
import com.example.inmoapp.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InmuebleService {


    @GET("/properties")
    Call<ResponseContainer<Inmueble>> getListInmuble();

    @GET("/properties/auth")
    Call<ResponseContainer<Inmueble>> getListInmuebleAuth();

    @GET("/properties/fav")
    Call<ResponseContainer<Inmueble>> getListFavsInmueble();

    @POST("/properties/fav/{id}")
    Call<ResponseContainer<Inmueble>> addToFavsProperties(@Path("id") String id);

    @DELETE("properties/fav/{id}")
    Call<ResponseContainer<Inmueble>> deleteToFavsProperties(@Path("id") String id);

    @POST("/properties")
    Call<Inmueble> addInmueble(@Body InmuebleDto newInmueble);

}
