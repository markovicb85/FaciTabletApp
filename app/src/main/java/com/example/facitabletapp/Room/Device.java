package com.example.facitabletapp.Room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "device_table")
public class Device {

    @PrimaryKey(autoGenerate = true)
    private int deviceID;

    private String deviceName;

    private int status;

    public Device(String deviceName, int status) {
        this.deviceName = deviceName;
        this.status = status;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getStatus() {
        return status;
    }
}
