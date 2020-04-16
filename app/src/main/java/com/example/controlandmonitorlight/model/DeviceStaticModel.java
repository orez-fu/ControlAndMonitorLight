package com.example.controlandmonitorlight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DeviceStaticModel implements Parcelable {
    private String deviceId;
    private float totalWatt;
    private int timeOn;
    private List< RecordModel > records;

    protected DeviceStaticModel(Parcel in) {
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

    public DeviceStaticModel(String deviceID, float totalWatt, int timeOn, List<RecordModel> records) {
        this.deviceId = deviceID;
        this.totalWatt = totalWatt;
        this.timeOn = timeOn;
        this.records = records;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceId);
        dest.writeFloat(totalWatt);
        dest.writeInt(timeOn);
    }
}
