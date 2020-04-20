package com.example.controlandmonitorlight.model;

import java.util.List;

public class RoomStaticModel {
    private String roomId;
    private float totalWatt;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List< DeviceStaticModel > devices;

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setTotalWatt(float totalWatt) {
        this.totalWatt = totalWatt;
    }

    public void setDevices(List<DeviceStaticModel> devices) {
        this.devices = devices;
    }

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

    public RoomStaticModel(String roomId, float totalWatt, String name, List<DeviceStaticModel> devices) {
        this.roomId = roomId;
        this.totalWatt = totalWatt;
        this.name = name;
        this.devices = devices;
    }
}
