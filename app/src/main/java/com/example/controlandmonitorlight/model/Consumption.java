package com.example.controlandmonitorlight.model;

public class Consumption {
    private double timestamp ;
    private float productivity;

    public Consumption() {
    }

    public Consumption(double timestamp, float productivity) {
        this.timestamp = timestamp;
        this.productivity = productivity;
    }

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
}
