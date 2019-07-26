package com.example.pelaporanpolisi.RetrofitInterface;

import com.example.pelaporanpolisi.Model.AuthenticationModel;
import com.example.pelaporanpolisi.Model.ProfileModel;
import com.example.pelaporanpolisi.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Authentication {
    @FormUrlEncoded
    @POST("auth/login")
    Call<AuthenticationModel> login(@Field("nomor_identitas") String username, @Field("password") String password);

    @GET("auth/me")
    Call<UserModel> getLoggedInUser();

    @GET("auth/user")
    Call<ProfileModel> getProfil();

    @FormUrlEncoded
    @PUT("auth/store-fcm-token")
    Call<Void> storeFirebaseToken(@Field("fcm_token") String token);

    @PUT("auth/destroy-fcm-token")
    Call<Void> destroyFirebaseToken();

}
