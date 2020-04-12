package com.example.controlandmonitorlight.model;

import java.util.HashMap;
import java.util.List;

public class DeviceModel {
    public static final String STATUS_ON = "1";
    public static final String STATUS_OFF = "0";

    private String id ;
    private String status ;
    private String name ;
    private String type ;

    public DeviceModel() {
    }

    public DeviceModel(String id, String status, String name, String type) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getStatusDevices()
    {
        if(status.contains("0")){
            return "OFF";
        }
        return "ON";
    }
}
