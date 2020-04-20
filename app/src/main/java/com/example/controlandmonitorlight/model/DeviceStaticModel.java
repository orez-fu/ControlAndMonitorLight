package com.example.controlandmonitorlight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DeviceStaticModel implements Parcelable {
    private String deviceId;
    private float totalWatt;
    private int timeOn;
    private String name;
    private List< RecordModel > records;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected DeviceStaticModel(Parcel in) {
        name = in.readString();
        deviceId = in.readString();
        totalWatt = in.readFloat();
        timeOn = in.readInt();
    }

    public static final Creator<DeviceStaticModel> CREATOR = new Creator<DeviceStaticModel>() {
        @Override
        public DeviceStaticModel createFromParcel(Parcel in) {
            return new DeviceStaticModel(in);
        }

        @Override
        public DeviceStaticModel[] newArray(int size) {
            return new DeviceStaticModel[size];
        }
    };

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setTotalWatt(float totalWatt) {
        this.totalWatt = totalWatt;
    }

    public void setTimeOn(int timeOn) {
        this.timeOn = timeOn;
    }

    public void setRecords(List<RecordModel> records) {
        this.records = records;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public float getTotalWatt() {
        return totalWatt;
    }

    public int getTimeOn() {
        return timeOn;
    }

    public List<RecordModel> getRecords() {
        return records;
    }

    public DeviceStaticModel() {
    }

    public DeviceStaticModel(String deviceId, float totalWatt, int timeOn, String name, List<RecordModel> records) {
        this.deviceId = deviceId;
        this.totalWatt = totalWatt;
        this.timeOn = timeOn;
        this.name = name;
        this.records = records;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(deviceId);
        dest.writeFloat(totalWatt);
        dest.writeInt(timeOn);
    }
}
