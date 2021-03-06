package com.example.inmoapp.Services;

import com.example.inmoapp.Model.PassDto;
import com.example.inmoapp.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @PUT("users/{id}")
    Call<User> updateNamePictureUser(@Body User user);

    @PUT("users/{id}/password")
    Call<User> updatePassword(@Header("Authorization") String credentials, @Path("id") String idUser, @Body PassDto newPassword);

    @GET("users/{id}")
    Call<User> getDataUser(@Path("id") String id);
}
