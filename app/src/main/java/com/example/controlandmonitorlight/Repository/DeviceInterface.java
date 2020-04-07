package com.example.controlandmonitorlight.Repository;

import com.example.controlandmonitorlight.model.DeviceStatic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceInterface {
    String DAY = "day" ;
    String WEEK = "week";
    @GET("static/device/{id}")
    Call<DeviceStatic> getDeviceStatic(@Path("id") int id, @Query("duration") String duration);
}
