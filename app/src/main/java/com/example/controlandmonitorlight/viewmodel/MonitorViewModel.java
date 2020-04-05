package com.example.controlandmonitorlight.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.Repository.MonitorClient;
import com.example.controlandmonitorlight.model.RoomStatic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitorViewModel extends ViewModel {

    public MutableLiveData<RoomStatic> data = new MutableLiveData<>();
    public MonitorClient monitorClient;
    public void getData(int id ){
        monitorClient.getINSTANCE().getStatic(id).enqueue(new Callback<RoomStatic>() {
            @Override
            public void onResponse(Call<RoomStatic> call, Response<RoomStatic> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RoomStatic> call, Throwable t) {

            }
        });
    }

}
