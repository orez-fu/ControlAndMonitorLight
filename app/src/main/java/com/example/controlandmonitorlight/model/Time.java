package com.example.controlandmonitorlight.model;

public class Time {
    private String time ;
    private String date;

    public Time() {
    }

    public Time(String time , String date) {
        this.time = time;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}