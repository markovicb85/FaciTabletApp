package com.example.facitabletapp.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.example.facitabletapp.Room.Device;
import com.example.facitabletapp.Room.FaciDB;
import com.example.facitabletapp.Room.DeviceDao;
import java.util.List;


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
        private DeviceDao deviceDao;

        private InsertDeviceAsyncTask(DeviceDao noteDao) {
            this.deviceDao = noteDao;
        }

        @Override
        protected Void doInBackground(Device... device) {
            deviceDao.insert(device[0]);
            return null;
        }
    }

    private static class UpdateDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DeviceDao deviceDao;

        private UpdateDeviceAsyncTask(DeviceDao noteDao) {
            this.deviceDao = noteDao;
        }

        @Override
        protected Void doInBackground(Device... device) {
            deviceDao.update(device[0]);
            return null;
        }
    }

    private static class DeleteDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DeviceDao deviceDao;

        private DeleteDeviceAsyncTask(DeviceDao noteDao) {
            this.deviceDao = deviceDao;
        }

        @Override
        protected Void doInBackground(Device... device) {
            deviceDao.delete(device[0]);
            return null;
        }
    }

    private static class DeleteAllDeviceAsyncTask extends AsyncTask<Void, Void, Void> {
        private DeviceDao deviceDao;

        private DeleteAllDeviceAsyncTask(DeviceDao noteDao) {
            this.deviceDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            deviceDao.deleteAllDevices();
            return null;
        }
    }
}
