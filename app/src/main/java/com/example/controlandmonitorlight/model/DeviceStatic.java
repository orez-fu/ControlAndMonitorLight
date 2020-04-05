package com.example.controlandmonitorlight.model;

public class DeviceStatic {
    private float totalWalt ;
    private long  timeOn ;

    public DeviceStatic(float totalWalt, long timeOn) {
        this.totalWalt = totalWalt;
        this.timeOn = timeOn;
    }

    public float getTotalWalt() {
        return totalWalt;
    }

    public void setTotalWalt(float totalWalt) {
        this.totalWalt = totalWalt;
    }

    public long getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(long timeOn) {
        this.timeOn = timeOn;
    }
}
