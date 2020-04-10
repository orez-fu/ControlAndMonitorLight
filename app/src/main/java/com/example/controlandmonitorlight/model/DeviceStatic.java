package com.example.controlandmonitorlight.model;

import android.text.format.Time;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceStatic {
    private double totalWatt ;
    private long  timeOn ;
    private List<Consumption> records;

    public DeviceStatic() {
    }

    public DeviceStatic(double totalWatt, long timeOn,  List<Consumption> records) {
        this.totalWatt = totalWatt;
        this.timeOn = timeOn;
        this.records = records;

    }

    public double getTotalWatt() {
        return totalWatt;
    }

    public void setTotalWatt(double totalWatt) {
        this.totalWatt = totalWatt;
    }

    public long getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(long timeOn) {
        this.timeOn = timeOn;
    }

    public List<Consumption> getRecords() {
        return records;
    }

    public void setRecords( List<Consumption> records) {
        this.records = records;
    }
}
