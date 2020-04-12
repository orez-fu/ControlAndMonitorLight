package com.example.controlandmonitorlight.model;

import java.util.HashMap;

public class Room {
    private HashMap<String, DeviceModel> devices ;
    private String humidity ;
    private String id ;
    private String lux ;
    private String name ;
    private String temperature;
    private String userId ;
    private int notification;

    public Room() {

    }

    public Room(HashMap<String, DeviceModel> devices, String humidity, String id, String lux, String name, String temperature, String userId, int notification) {
        this.devices = devices;
        this.humidity = humidity;
        this.id = id;
        this.lux = lux;
        this.name = name;
        this.temperature = temperature;
        this.userId = userId;
    }

    public HashMap<String, DeviceModel> getDevices() {
        return devices;
    }

    public void setDevices(HashMap<String, DeviceModel> devices) {
        this.devices = devices;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLux() {
        return lux;
    }

    public void setLux(String lux) {
        this.lux = lux;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNotification() {
        return notification;
    }
}
