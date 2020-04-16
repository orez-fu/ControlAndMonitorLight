package com.example.controlandmonitorlight.model;

import java.util.List;

public class StaticModel {
    private int day;
    private int month;
    private int year;
    private List <RoomStaticModel> rooms;

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRooms(List<RoomStaticModel> rooms) {
        this.rooms = rooms;
    }

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

