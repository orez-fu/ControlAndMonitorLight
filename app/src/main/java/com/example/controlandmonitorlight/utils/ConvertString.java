package com.example.controlandmonitorlight.utils;

import android.util.Log;

public class ConvertString {

    public ConvertString()
    {}
    public void regexString(String value , String humidity, String temperature, String light)
    {
        String temp="";
        int dem = 0 ;
        String [] words = new String[3];
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
        humidity = words[0];
        temperature= words[1];
        light = words[2];

    }
}
