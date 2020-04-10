package com.example.controlandmonitorlight.Repository;

import android.util.Log;

import com.example.controlandmonitorlight.model.DeviceStatic;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeviceClient  {
    private String BASE_URL = "https://us-central1-luxremote-zm.cloudfunctions.net/webApi/api/v1/";
    private static DeviceInterface deviceInterface ;
    private static DeviceClient Instance ;

    public DeviceClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        deviceInterface = retrofit.create(DeviceInterface.class);
    }

    public static DeviceClient getInstance() {

        if (Instance == null){

            Instance = new DeviceClient();
        }
        return Instance ;
    }
    public Call<DeviceStatic> getDeviceStatic(int id , String during )
    {
        return this.deviceInterface.getDeviceStatic(id,during);
    }
}
