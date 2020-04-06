package com.example.controlandmonitorlight.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.Repository.DeviceClient;
import com.example.controlandmonitorlight.model.DeviceStatic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceStaticViewModel extends ViewModel {

    public MutableLiveData<DeviceStatic> deviceData = new MutableLiveData<>();
    public DeviceClient deviceClient;
    public void getData(int id )
    {
        deviceClient.getInstance().getDeviceStatic(id).enqueue(new Callback<DeviceStatic>() {
            @Override
            public void onResponse(Call<DeviceStatic> call, Response<DeviceStatic> response) {
                deviceData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<DeviceStatic> call, Throwable t) {

            }
        });
    }
}
