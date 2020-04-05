package com.example.controlandmonitorlight.Repository;

import com.example.controlandmonitorlight.model.DeviceStatic;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeviceClient  {
    private String BASE_URL = "";
    private static DeviceInterface deviceInterface ;
    private DeviceClient Instance ;

    public DeviceClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        deviceInterface = retrofit.create(DeviceInterface.class);
    }

    public DeviceClient getInstance() {
        if (Instance == null){
            Instance = new DeviceClient();
        }
        return Instance ;
    }
    public Call<DeviceStatic> getDeviceStatic(int id)
    {
        return deviceInterface.getDeviceStatic(id);
    }
}
