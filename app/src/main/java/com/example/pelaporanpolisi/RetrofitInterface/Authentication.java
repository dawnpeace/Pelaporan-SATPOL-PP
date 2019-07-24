package com.example.pelaporanpolisi.RetrofitInterface;

import com.example.pelaporanpolisi.Model.AuthenticationModel;
import com.example.pelaporanpolisi.Model.ProfileModel;
import com.example.pelaporanpolisi.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Authentication {
    @FormUrlEncoded
    @POST("auth/login")
    Call<AuthenticationModel> login(@Field("nomor_identitas") String username, @Field("password") String password);

    @GET("auth/me")
    Call<UserModel> getLoggedInUser();

    @GET("auth/user")
    Call<ProfileModel> getProfil();
}
