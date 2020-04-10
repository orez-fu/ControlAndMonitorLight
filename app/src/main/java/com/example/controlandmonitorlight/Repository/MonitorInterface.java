package com.example.controlandmonitorlight.Repository;

import com.example.controlandmonitorlight.model.RoomStatic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MonitorInterface {

    @GET("static/device/{id}")
    Call<RoomStatic> getStatic(@Path("id") int id );
}
