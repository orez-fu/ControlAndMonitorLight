package com.example.controlandmonitorlight.model;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import java.util.HashMap;
import java.util.List;

public class Room {
    private HashMap<String,ObjectDevices> devices ;
    private String humidity ;
    private String id ;
    private String lux ;
    private String name ;
    private String temperature;
    private String userId ;
    public Room() {

    }

    public Room(HashMap<String,ObjectDevices> devices, String humidity, String id, String lux, String name, String temperature, String userId) {
        this.devices = devices;
        this.humidity = humidity;
        this.id = id;
        this.lux = lux;
        this.name = name;
        this.temperature = temperature;
        this.userId = userId;
    }

    public HashMap<String,ObjectDevices> getDevices() {
        return devices;
    }

    public void setDevices(HashMap<String,ObjectDevices> devices) {
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
}
