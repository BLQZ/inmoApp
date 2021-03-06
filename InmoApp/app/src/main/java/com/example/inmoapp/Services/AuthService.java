package com.example.inmoapp.Services;

import com.example.inmoapp.Model.LoginResponse;
import com.example.inmoapp.Model.PassDto;
import com.example.inmoapp.Model.User;
import com.example.inmoapp.Model.UserDto;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AuthService {

    @POST("/auth")
    Call<LoginResponse> doLogin(@Header("Authorization") String authorization);

    @PUT("/users/{id}/password")
    Call<User> updatePass(@Header("Authorization") String authorization, @Path("id") String id, @Body PassDto pass);

    @Multipart
    @POST("/users")
    Call<LoginResponse> doRegister(@Part MultipartBody.Part picture,
                                   @Part("email") RequestBody email,
                                   @Part("password") RequestBody password,
                                   @Part("name") RequestBody name);

    @POST("/users")
    Call<LoginResponse> register(@Body UserDto user);

}
