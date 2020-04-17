package com.example.controlandmonitorlight.utils;

import java.util.Calendar;

public class ConvertTime {

    public static long convertDateTimeToUnix(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute, second);
        return calendar.getTimeInMillis();
    }
}
