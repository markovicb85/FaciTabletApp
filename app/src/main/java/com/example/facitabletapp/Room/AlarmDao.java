package com.example.facitabletapp.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface AlarmDao {

    @Insert
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAllAlarms();

    @Query("SELECT * FROM alarm_table ORDER BY alarmID")
    LiveData<List<Alarm>> getAllAlarms();

    @Query("SELECT EXISTS (SELECT * FROM alarm_table WHERE alarmName = :name)")
    Integer alarmExist(String name);

    @Query("SELECT EXISTS (SELECT * FROM alarm_table WHERE alarmName = :name AND status = :status)")
    Integer checkAlarmStatus(String name, int status);
}
