package com.example.facitabletapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.text.BoringLayout;

import com.example.facitabletapp.Repository.AlarmRepository;
import com.example.facitabletapp.Repository.DeviceRepository;
import com.example.facitabletapp.Room.Alarm;
import com.example.facitabletapp.Room.Device;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository repository;
    private LiveData<List<Alarm>> allAlarms;

    public AlarmViewModel(@NonNull Application application) {
        super(application);
        repository = new AlarmRepository(application);
        allAlarms = repository.getAllAlarms();
    }

    public void insert(Alarm alarm){
        repository.insert(alarm);
    }

    public void update(Alarm alarm){
        repository.update(alarm);
    }

    public void delete(Alarm alarm){
        repository.delete(alarm);
    }

    public void deleteAlarm(Alarm alarm){
        repository.deleteAlarm(alarm);
    }

    public void deleteAllAlarms(){
        repository.deleteAllAlarms();
    }

    public LiveData<List<Alarm>> getAllAlarms(){
        return allAlarms;
    }

    public Boolean alarmExist(String alarmName){
        return repository.alarmExist(alarmName);
    }

    public Boolean checkAlarmStatus(String alarmName, int status){
        return repository.checkAlarmStatus(alarmName, status);
    }
}
