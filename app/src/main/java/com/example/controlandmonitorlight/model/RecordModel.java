package com.example.controlandmonitorlight.model;

public class RecordModel {
    private double timestamp;
    private float productivity;
    private int timeOn;

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public float getProductivity() {
        return productivity;
    }

    public void setProductivity(float productivity) {
        this.productivity = productivity;
    }

    public int getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(int timeOn) {
        this.timeOn = timeOn;
    }

    public RecordModel(double timestamp, float productivity, int timeOn) {
        this.timestamp = timestamp;
        this.productivity = productivity;
        this.timeOn = timeOn;
    }

    public RecordModel() {
    }
}
