package com.example.controlandmonitorlight.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Timer {

    private int hour;

    private int minute;

    private String repeat;

    private String label;

    private String type;

    private int status;

    public Timer() {

    }

    public Timer(int hour, int minute, String repeat, String label, String type, int status) {
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.label = label;
        this.type = type;
        this.status = status;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getRepeat() {
        return repeat;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public String getTime() {
        return String.valueOf(hour) + ":" + String.valueOf(minute);
    }
}
