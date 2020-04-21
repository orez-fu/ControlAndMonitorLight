package com.example.controlandmonitorlight.utils;

public class ConvertTimeOn {
    public static String convertTimeOn( int timeOn){
        int hour = timeOn/3600;
        int minute = (timeOn%3600)/60;
        if (hour > 0 )  return (hour+"H:"+minute+"M");
        else return (minute+"M");
    }
}
