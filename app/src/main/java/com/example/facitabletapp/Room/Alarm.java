package com.example.facitabletapp.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "alarm_table")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    private int alarmID;

    private String alarmName;

    public Alarm(String alarmName) {
        this.alarmName = alarmName;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public int getAlarmID() {
        return alarmID;
    }
}
