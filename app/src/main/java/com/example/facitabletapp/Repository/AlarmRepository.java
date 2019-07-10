package com.example.facitabletapp.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.facitabletapp.Room.Alarm;
import com.example.facitabletapp.Room.AlarmDao;
import com.example.facitabletapp.Room.FaciDB;

import java.util.List;


public class AlarmRepository {

    private AlarmDao alarmDao;
    private LiveData<List<Alarm>> allAlarms;

    public AlarmRepository(Application application) {
        FaciDB database = FaciDB.getInstance(application);
        alarmDao = database.alarmDao();
        allAlarms = alarmDao.getAllAlarms();
    }

    public void insert(Alarm alarm) {
        new InsertAlarmAsyncTask(alarmDao).execute(alarm);
    }

    public void update(Alarm alarm) {
        new UpdateAlarmAsyncTask(alarmDao).execute(alarm);
    }

    public void delete(Alarm alarm) {
        new DeleteAlarmAsyncTask(alarmDao).execute(alarm);
    }

    public void deleteAllAlarms() {
        new DeleteAllAlarmAsyncTask(alarmDao).execute();
    }

    public LiveData<List<Alarm>> getAllAlarms() {
        return allAlarms;
    }

    //TODO Ove dve metode proveriti da li blokiraju main thred
    public Boolean checkAlarmStatus(String alarmName, int status) {
        if(alarmDao.checkAlarmStatus(alarmName, status) > 0){
            return false;
        }
        return true;
    }

    public Boolean alarmExist(String alarmName) {
        if(alarmDao.alarmExist(alarmName) > 0){
            return false;
        }
        return true;
    }

    private static class InsertAlarmAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao alarmDao;

        private InsertAlarmAsyncTask(AlarmDao alarmDao) {
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Alarm... alarm) {
            alarmDao.insert(alarm[0]);
            return null;
        }
    }

    private static class UpdateAlarmAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao alarmDao;

        private UpdateAlarmAsyncTask(AlarmDao alarmDao) {
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Alarm... alarm) {
            alarmDao.update(alarm[0]);
            return null;
        }
    }

    private static class DeleteAlarmAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao alarmDao;

        private DeleteAlarmAsyncTask(AlarmDao alarmDao) {
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Alarm... alarm) {
            alarmDao.delete(alarm[0]);
            return null;
        }
    }

    private static class DeleteAllAlarmAsyncTask extends AsyncTask<Void, Void, Void> {
        private AlarmDao alarmDao;

        private DeleteAllAlarmAsyncTask(AlarmDao alarmDao) {
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            alarmDao.deleteAllAlarms();
            return null;
        }
    }
}
