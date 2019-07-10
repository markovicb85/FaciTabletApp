package com.example.facitabletapp.Tools;

import android.arch.lifecycle.LiveData;

import com.example.facitabletapp.Room.Alarm;
import com.example.facitabletapp.Room.Device;
import com.example.facitabletapp.ViewModel.AlarmViewModel;
import com.example.facitabletapp.ViewModel.DeviceViewModel;

import java.util.List;

public class Globals {

    private static Globals instance;

    private LiveData<List<Alarm>> alarms;
    private LiveData<List<Device>> devices;

    public LiveData<List<Alarm>> getAlarms(AlarmViewModel alarmViewModel) {
        return alarmViewModel.getAllAlarms();
    }

    public LiveData<List<Device>> getDevices(DeviceViewModel deviceViewModel) {
        return deviceViewModel.getAllDevices();
    }

    public void setAlarms(LiveData<List<Alarm>> alarms) {
        this.alarms = alarms;
    }

    public void setDevices(LiveData<List<Device>> devices) {
        this.devices = devices;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
