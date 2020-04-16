package com.example.controlandmonitorlight.model;

import java.util.List;

public class DeviceStaticModel {
    private String deviceId;
    private float totalWatt;
    private int timeOn;
    private List< RecordModel > records;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setTotalWatt(float totalWatt) {
        this.totalWatt = totalWatt;
    }

    public void setTimeOn(int timeOn) {
        this.timeOn = timeOn;
    }

    public void setRecords(List<RecordModel> records) {
        this.records = records;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public float getTotalWatt() {
        return totalWatt;
    }

    public int getTimeOn() {
        return timeOn;
    }

    public List<RecordModel> getRecords() {
        return records;
    }

    public DeviceStaticModel() {
    }

    public DeviceStaticModel(String deviceID, float totalWatt, int timeOn, List<RecordModel> records) {
        this.deviceId = deviceID;
        this.totalWatt = totalWatt;
        this.timeOn = timeOn;
        this.records = records;
    }
}
