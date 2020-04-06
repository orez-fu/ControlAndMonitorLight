package com.example.controlandmonitorlight.model;

public class DeviceStatic {
    private double totalWatt ;
    private long  timeOn ;

    public DeviceStatic(double totalWalt, long timeOn) {
        this.totalWatt = totalWalt;
        this.timeOn = timeOn;
    }

    public double getTotalWalt() {
        return totalWatt;
    }

    public void setTotalWalt(double totalWalt) {
        this.totalWatt = totalWalt;
    }

    public long getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(long timeOn) {
        this.timeOn = timeOn;
    }
}
