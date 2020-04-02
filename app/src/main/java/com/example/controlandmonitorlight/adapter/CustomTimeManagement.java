package com.example.controlandmonitorlight.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.controlandmonitorlight.viewmodel.TimeManagement;

import java.util.Calendar;

public class CustomTimeManagement extends DialogFragment implements TimePickerDialog.OnTimeSetListener  {

    TimeManagement timeManagement ;

    public CustomTimeManagement(TimeManagement timeManagement){
        this.timeManagement = timeManagement;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        Calendar calendar =  Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getContext(),this,hour,minute, DateFormat.is24HourFormat(getContext()));
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeManagement.onSetTime(view,hourOfDay,minute);
    }
}
