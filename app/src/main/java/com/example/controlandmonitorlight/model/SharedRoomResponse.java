package com.example.controlandmonitorlight.model;

public class SharedRoomResponse {
    public static final int SUCCESS_OK = 1;
    public static final int SUCCESS_NOT_OK = 0;

    private int success;
    private String id;
    private String imageUrl;
    private String name;

    public SharedRoomResponse() {
    }

    public SharedRoomResponse(int success, String id, String imageUrl, String name) {
        this.success = success;
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
