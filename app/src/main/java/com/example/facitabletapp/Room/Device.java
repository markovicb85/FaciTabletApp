package com.example.facitabletapp.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "device_table")
public class Device {

    @PrimaryKey(autoGenerate = true)
    private int deviceID;

    private String deviceName;

    private boolean status;

    public Device(String deviceName, boolean status) {
        this.deviceName = deviceName;
        this.status = status;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public boolean getStatus() {
        return status;
    }

    public int getDeviceID() {
        return deviceID;
    }
}
