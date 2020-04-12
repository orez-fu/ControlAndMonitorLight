package com.example.controlandmonitorlight.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String email ;
    private String name ;
    private Map<String, Boolean> rooms;
    private String id ;

    public User() {
    }

    public User(String email, String name, Map<String, Boolean> rooms, String id) {
        this.email = email;
        this.name = name;
        this.rooms = rooms;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Boolean> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Boolean> rooms) {
        this.rooms = rooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
