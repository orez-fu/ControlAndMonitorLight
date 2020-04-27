package com.example.controlandmonitorlight.model;

public class SharedRoomRequest {
    private String token;

    public SharedRoomRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SharedRoomRequest(String token) {
        this.token = token;
    }
}
