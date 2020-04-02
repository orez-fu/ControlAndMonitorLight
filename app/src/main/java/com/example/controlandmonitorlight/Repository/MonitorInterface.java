package com.example.controlandmonitorlight.Repository;

import com.example.controlandmonitorlight.model.Static;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MonitorInterface {

    @GET("static/device/{id}")
    Call<Static> getStatic(@Path("id") int id );
}
