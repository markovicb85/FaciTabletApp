package com.example.facitabletapp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.facitabletapp.Room.Device;
import com.example.facitabletapp.Room.FaciDB;
import com.example.facitabletapp.Room.DeviceDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DeviceRepository {

    private DeviceDao deviceDao;
    private LiveData<List<Device>> allDevice;

    public DeviceRepository(Application application) {
        FaciDB database = FaciDB.getInstance(application);
        deviceDao = database.deviceDao();
        allDevice = deviceDao.getAllDevices();
    }

    public void insert(Device device) {
        new InsertDeviceAsyncTask(deviceDao).execute(device);
    }

    public void update(Device device) {
        new UpdateDeviceAsyncTask(deviceDao).execute(device);
    }

    public void delete(Device device) {
        new DeleteDeviceAsyncTask(deviceDao).execute(device);
    }

    public void deleteAllDevices() {
        new DeleteAllDeviceAsyncTask(deviceDao).execute();
    }

    public LiveData<List<Device>> getAllDevice() {
        return allDevice;
    }

    private static class InsertDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DeviceDao noteDao;

        private InsertDeviceAsyncTask(DeviceDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Device... device) {
            noteDao.insert(device[0]);
            return null;
        }
    }

    private static class UpdateDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DeviceDao noteDao;

        private UpdateDeviceAsyncTask(DeviceDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Device... device) {
            noteDao.update(device[0]);
            return null;
        }
    }

    private static class DeleteDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DeviceDao noteDao;

        private DeleteDeviceAsyncTask(DeviceDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Device... device) {
            noteDao.delete(device[0]);
            return null;
        }
    }

    private static class DeleteAllDeviceAsyncTask extends AsyncTask<Void, Void, Void> {
        private DeviceDao noteDao;

        private DeleteAllDeviceAsyncTask(DeviceDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllDevices();
            return null;
        }
    }
}
