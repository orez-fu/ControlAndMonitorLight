package com.example.controlandmonitorlight.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.controlandmonitorlight.viewmodel.TimeManagement;

import java.util.Calendar;

public class CustomDateManagement extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TimeManagement timeManagement ;

    public CustomDateManagement(TimeManagement timeManagement) {
        this.timeManagement = timeManagement ;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        Calendar calendar =  Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH) ;
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return  new DatePickerDialog(getContext(), this,year,month,day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        timeManagement.onDateSet(view,year,month,dayOfMonth);
    }
}
