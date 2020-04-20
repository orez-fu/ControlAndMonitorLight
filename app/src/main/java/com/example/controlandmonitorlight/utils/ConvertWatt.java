package com.example.controlandmonitorlight.utils;

public class ConvertWatt {
    public static String convertWatt( float valueWatt){
        valueWatt = (float)Math.round(valueWatt*100)/100;
        return (valueWatt+ " Wh");
    }
}
