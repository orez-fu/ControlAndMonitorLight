package com.example.controlandmonitorlight.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConvertFormatDate {
    public static String getFormatDate(int dayOfMonth, int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(calendar.getTime());
    }
}
