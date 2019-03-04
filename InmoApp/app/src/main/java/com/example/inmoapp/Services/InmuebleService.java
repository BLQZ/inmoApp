package com.example.inmoapp.Services;

import com.example.inmoapp.InmuebleDetalladoActivity;
import com.example.inmoapp.Model.Category;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.InmuebleDto;
import com.example.inmoapp.Model.Mine;
import com.example.inmoapp.Model.Photo;
import com.example.inmoapp.Model.PropertyResponseOne;
import com.example.inmoapp.Model.ResponseContainer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET("/categories")
    Call<ResponseContainer<Category>> getListCategories();

    @Multipart
    @POST("/photos")
    Call<ResponseContainer<Photo>> addImgToProperty(@Part MultipartBody.Part picture,
                                                    @Part RequestBody propertyId);

    @DELETE("/properties/{id}")
    Call<ResponseContainer<Inmueble>> deleteProperty(@Path("id") String id);

    @GET("properties/{id}")
    Call<PropertyResponseOne> getInmueble(@Path("id") String id);

    @PUT("properties/{id}")
    Call<Inmueble> editInmueble(@Path("id") String id, @Body InmuebleDto inmueble);

    @GET("properties/mine")
    Call<ResponseContainer<Mine>> getMineInmuebles();

}
