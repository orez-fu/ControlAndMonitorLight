package com.example.controlandmonitorlight.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.Repository.DeviceClient;
import com.example.controlandmonitorlight.model.Consumption;
import com.example.controlandmonitorlight.model.DeviceStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceStaticViewModel extends ViewModel {

    public MutableLiveData<DeviceStatic> deviceData = new MutableLiveData<>();
    public DeviceClient deviceClient;
    DeviceStatic deviceStatic;
    List<Consumption> a = new ArrayList<>();
    public void getData(int id, Map<String, String> parameters , final Context context )
    {
        deviceStatic = new DeviceStatic(1,1,a);
        deviceClient.getInstance().getDeviceStatic(id, parameters).enqueue(new Callback<DeviceStatic>() {
            @Override
            public void onResponse(Call<DeviceStatic> call, Response<DeviceStatic> response) {

                DeviceStatic device = response.body();
                Log.d("record= ",device.getRecords().size()+" : "+device.getTotalWatt()+" : "+device.getTimeOn());
                //Toast.makeText(context,device.getRecords().size()+"",Toast.LENGTH_SHORT).show();
                deviceData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<DeviceStatic> call, Throwable t) {

            }
        });
    }
}
