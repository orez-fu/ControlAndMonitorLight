package com.example.controlandmonitorlight.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.Repository.DeviceClient;
import com.example.controlandmonitorlight.model.StaticModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaticTotalViewModel extends ViewModel {
    public MutableLiveData<StaticModel> dataStatic = new MutableLiveData<>();
    public MutableLiveData<Calendar> mCalendar = new MutableLiveData<>();

    public void getStaticData(String userId, int day, int month, int year){
        Log.d("STATIC_FRAGMENT","Date: " + day + "/" + month + "/" + year);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day);
        mCalendar.setValue(calendar);

        Map< String, String > parameter = new HashMap<>();
        parameter.put("day", String.valueOf(day));
        parameter.put("month", String.valueOf(month+1));
        parameter.put("year", String.valueOf(year));
        Log.d("STATIC_FRAGMENT", "Params: " + parameter.toString());

        DeviceClient.getInstance().getStaticModel(userId, parameter).enqueue(new Callback<StaticModel>() {
            @Override
            public void onResponse(Call<StaticModel> call, Response<StaticModel> response) {
                Log.d("STATIC_FRAGMENT",response.body().toString());
                StaticModel statics = response.body();
                //            Toast.makeText(getActivity(), "Called API",Toast.LENGTH_SHORT).show();
                dataStatic.setValue(statics);
            }

            @Override
            public void onFailure(Call<StaticModel> call, Throwable t) {
                Log.d("STATIC_FRAGMENT", "Failed" + t.getMessage().toString());
                //  Toast.makeText(getActivity(), "Called fail API",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
