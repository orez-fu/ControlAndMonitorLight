package com.example.controlandmonitorlight.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserModel {
    private String uid;
    private String name;
    private String email;

    public UserModel() {

    }

    public UserModel(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Exclude
    public Map<String, Object> toMap(String key) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", key);
        result.put("name", name);
        result.put("email", email);

        return result;
    }

}
