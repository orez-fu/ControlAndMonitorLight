/*viewmodel*/
package com.example.controlandmonitorlight.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.Devices;

import java.util.ArrayList;
import java.util.List;

public class DevicesViewModel extends ViewModel {
    private MutableLiveData<List<Devices>> data = new MutableLiveData<>();
    private Comunication clicked ;
    public MutableLiveData<List<Devices>> getData()
    {
        return  data ;
    }

    public void setData()
    {
        List<Devices> devices = new ArrayList<>();
        devices.add(new Devices("Humidity","On/Off","", R.drawable.water)) ;
        devices.add(new Devices("Light","On/Off","", R.drawable.light)) ;
        devices.add(new Devices("Temperature","On/Off","", R.drawable.temperature)) ;
        this.data.setValue(devices);
    }

}
