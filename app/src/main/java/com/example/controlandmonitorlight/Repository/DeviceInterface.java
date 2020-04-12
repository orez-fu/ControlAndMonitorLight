package com.example.controlandmonitorlight.Repository;

import com.example.controlandmonitorlight.model.DeviceStatic;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface DeviceInterface {
    String DAY = "day" ;
    String WEEK = "week";
    @GET("static/device/{id}")
    Call<DeviceStatic> getDeviceStatic(@Path("id") int id,@QueryMap Map<String, String> parameters );
}
