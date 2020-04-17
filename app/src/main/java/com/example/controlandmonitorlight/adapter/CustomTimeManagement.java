package com.example.controlandmonitorlight.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.controlandmonitorlight.viewmodel.TimeManagement;

import java.util.Calendar;

public class CustomTimeManagement extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    TimeManagement timeManagement ;

    public CustomTimeManagement(TimeManagement timeManagement){
        this.timeManagement = timeManagement;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        Calendar calendar =  Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return new DatePickerDialog(getContext(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        timeManagement.onDateSet(datePicker,i,i1,i2);
    }
}
