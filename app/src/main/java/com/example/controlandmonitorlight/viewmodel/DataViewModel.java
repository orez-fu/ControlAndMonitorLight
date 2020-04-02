/*viewmodel*/
package com.example.controlandmonitorlight.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.Data;
import com.example.controlandmonitorlight.utils.ConvertString;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends ViewModel {

    private MutableLiveData<List<Data>> data = new MutableLiveData<>() ;
    public MutableLiveData<List<Data>> getData()
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
        List<Data> d = new ArrayList<>();
        d.add(new Data(humidity,"Humidity", R.drawable.water)) ;
        d.add(new Data(temperature,"Temp", R.drawable.temperature)) ;
        d.add(new Data(light,"Light", R.drawable.light)) ;
        this.data.setValue(d);
    }
}
