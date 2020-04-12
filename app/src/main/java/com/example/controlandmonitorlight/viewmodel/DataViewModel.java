/*viewmodel*/
package com.example.controlandmonitorlight.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceDataModel;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends ViewModel {

    private MutableLiveData<List<DeviceDataModel>> data = new MutableLiveData<>() ;
    public MutableLiveData<List<DeviceDataModel>> getData()
    {
        return  data ;
    }
    public void setData(String value)
    {
        String [] words = new String[3];
        int dem =0 ;
        String temp="";
        for(int i =0 ; i < value.length() ; i++)
        {
            if(value.charAt(i) != ' ')
            {
                temp+= value.charAt(i);
            }else{
                words[dem] = temp ;
                dem++;
                temp ="";
            }
        }
        words[dem] = temp;
        dem++;
        String humidity = words[0];
        String temperature= words[1];
        String light = words[2];
        List<DeviceDataModel> d = new ArrayList<>();
        d.add(new DeviceDataModel(humidity,"Humidity", R.drawable.water)) ;
        d.add(new DeviceDataModel(temperature,"Temp", R.drawable.temperature)) ;
        d.add(new DeviceDataModel(light,"Light", R.drawable.light)) ;
        this.data.setValue(d);
    }
}
