package com.example.facitabletapp.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Device.class}, version = 1)
public abstract class FaciDB extends RoomDatabase {

    private static FaciDB instance;

    public abstract DeviceDao deviceDao();

    public static synchronized FaciDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FaciDB.class, "faci_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
