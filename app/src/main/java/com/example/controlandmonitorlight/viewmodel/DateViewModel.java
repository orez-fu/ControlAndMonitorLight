package com.example.controlandmonitorlight.viewmodel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.model.Time;

import java.util.ArrayList;
import java.util.List;

public class DateViewModel extends ViewModel  {

    private MutableLiveData<List<Time>> data = new MutableLiveData<>();
    private TimeManagement mTimeManagement ;
    public MutableLiveData<List<Time>> getData(){
        return data ;
    }
    public void onTimeSet(String time ) {
        List<Time> mTime = new ArrayList<>();
        mTime.add(new Time(time,""));
        this.data.setValue(mTime);
    }
}
