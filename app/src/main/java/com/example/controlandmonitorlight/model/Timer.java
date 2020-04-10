package com.example.controlandmonitorlight.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Timer {
    public static final int STATUS_ON = 1;
    public static final int STATUS_OFF = 0;
    public static final String STRING_ON = "On";
    public static final String STRING_OFF = "Off";
    public static final String REPEAT_ONCE = "ONCE";
    public static final String REPEAT_EVERY = "EVERY";
    public static final String TYPE_ON = "TURN_ON";
    public static final String TYPE_OFF = "TURN_OFF";

    private String id;

    private int hour;

    private int minute;

    private String repeat;

    private String label;

    private String type;

    private int status;

    public Timer() {

    }

    public Timer(String id, int hour, int minute, String repeat, String label, String type, int status) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.label = label;
        this.type = type;
        this.status = status;
    }

    public Timer(int hour, int minute, String repeat, String label, String type, int status) {
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.label = label;
        this.type = type;
        this.status = status;
    }

    public String getId() {
        return id;
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

    @Exclude
    public Map<String, Object> toMap(String key) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", key);
        result.put("hour", hour);
        result.put("minute", minute);
        result.put("label", label);
        result.put("repeat", repeat);
        result.put("status", status);
        result.put("type", type);

        return result;
    }
}
