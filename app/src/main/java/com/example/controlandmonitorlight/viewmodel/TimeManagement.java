package com.example.controlandmonitorlight.viewmodel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

public interface TimeManagement {

    void onSetTime(TimePicker view, int hourOfDay, int minute);
    void onDateSet(DatePicker view, int year, int month, int dayOfMonth);

}
