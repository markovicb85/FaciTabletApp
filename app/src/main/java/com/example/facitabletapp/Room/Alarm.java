package com.example.facitabletapp.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "alarm_table")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    private int alarmID;
    private String rommID;
    private String groupControler;
    private String group;
    private String alarmName;
    private int status;
    private String suplementData;
    private String radioRFID;
    private String description;
    private String currentTime;

    public Alarm() {
    }

    public int getAlarmID() {
        return alarmID;
    }

    public String getRommID() {
        return rommID;
    }

    public String getGroupControler() {
        return groupControler;
    }

    public String getGroup() {
        return group;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public int getStatus() {
        return status;
    }

    public String getSuplementData() {
        return suplementData;
    }

    public String getRadioRFID() {
        return radioRFID;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public void setRommID(String rommID) {
        this.rommID = rommID;
    }

    public void setGroupControler(String groupControler) {
        this.groupControler = groupControler;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSuplementData(String suplementData) {
        this.suplementData = suplementData;
    }

    public void setRadioRFID(String radioRFID) {
        this.radioRFID = radioRFID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
