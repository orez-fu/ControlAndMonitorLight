package com.example.controlandmonitorlight.model;

import java.util.List;

public class StaticModel {
    private int day;
    private int month;
    private int year;
    private List <RoomStaticModel> rooms;

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public List<RoomStaticModel> getRooms() {
        return rooms;
    }

    public StaticModel() {
    }

    public StaticModel(int day, int month, int year, List<RoomStaticModel> rooms) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.rooms = rooms;
    }
}
class RecordModel {
    private long timestamp;
    private float productivity;
    private int timeOn;

    public long getTimestamp() {
        return timestamp;
    }

    public float getProductivity() {
        return productivity;
    }

    public int getTimeOn() {
        return timeOn;
    }

    public RecordModel() {
    }
    public RecordModel(long timestamp, float productivity, int timeOn) {
        this.timestamp = timestamp;
        this.productivity = productivity;
        this.timeOn = timeOn;
    }
}
class DeviceStaticModel {
    private String deviceId;
    private Float totalWatt;
    private int timeOn;
    private List < RecordModel > records;

    public String getDeviceId() {
        return deviceId;
    }

    public Float getTotalWatt() {
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

    public DeviceStaticModel(String deviceID, Float totalWatt, int timeOn, List<RecordModel> records) {
        this.deviceId = deviceID;
        this.totalWatt = totalWatt;
        this.timeOn = timeOn;
        this.records = records;
    }
}
class RoomStaticModel {
    private String roomId;
    private float totalWatt;
    private List < DeviceStaticModel > devices;

    public String getRoomId() {
        return roomId;
    }

    public float getTotalWatt() {
        return totalWatt;
    }

    public List<DeviceStaticModel> getDevices() {
        return devices;
    }

    public RoomStaticModel() {
    }

    public RoomStaticModel(String roomId, float totalWatt, List<DeviceStaticModel> devices) {
        this.roomId = roomId;
        this.totalWatt = totalWatt;
        this.devices = devices;
    }
}
