package com.example.controlandmonitorlight.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.Repository.MonitorClient;
import com.example.controlandmonitorlight.model.Static;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitorViewModel extends ViewModel {

    public MutableLiveData<Static> data = new MutableLiveData<>();
    public MonitorClient monitorClient;
    public void getData(int id ){
        monitorClient.getINSTANCE().getStatic(id).enqueue(new Callback<Static>() {
            @Override
            public void onResponse(Call<Static> call, Response<Static> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Static> call, Throwable t) {

            }
        });
    }

}
