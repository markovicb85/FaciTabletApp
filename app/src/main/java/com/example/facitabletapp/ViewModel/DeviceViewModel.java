package com.example.facitabletapp.ViewModel;

import android.app.Application;
import com.example.facitabletapp.Repository.DeviceRepository;
import com.example.facitabletapp.Room.Device;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DeviceViewModel extends AndroidViewModel {

    private DeviceRepository repository;
    private LiveData<List<Device>> allDevices;

    public DeviceViewModel(@NonNull Application application) {
        super(application);
        repository = new DeviceRepository(application);
        allDevices = repository.getAllDevice();
    }

    public void insert(Device device){
        repository.insert(device);
    }

    public void update(Device device){
        repository.update(device);
    }

    public void delete(Device device){
        repository.delete(device);
    }

    public void deleteAllDevice(){
        repository.deleteAllDevices();
    }

    public LiveData<List<Device>> getAllDevices(){
        return allDevices;
    }
}
