package com.example.dailydo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import androidx.room.Room;
import androidx.room.TypeConverters;


import com.example.dailydo.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class TaskDatabase  extends RoomDatabase {
    private static final String DATABASE_NAME = "tasks_database";
    private static volatile TaskDatabase INSTANCE;
    public abstract TaskDao taskDao();

    public static TaskDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TaskDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
