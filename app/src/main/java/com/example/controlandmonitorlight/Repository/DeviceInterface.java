package com.example.controlandmonitorlight.Repository;

import com.example.controlandmonitorlight.model.DeviceStatic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DeviceInterface {
    @GET()
    Call<DeviceStatic> getDeviceStatic(@Path("id") int id );
}
