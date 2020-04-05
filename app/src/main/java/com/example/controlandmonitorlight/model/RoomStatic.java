package com.example.controlandmonitorlight.model;

public class RoomStatic {
    private int totalWatt ;

    public RoomStatic(int totalWatt) {
        this.totalWatt = totalWatt;
    }

    public int getTotalWatt() {
        return totalWatt;
    }

    public void setTotalWatt(int totalWatt) {
        this.totalWatt = totalWatt;
    }
}
