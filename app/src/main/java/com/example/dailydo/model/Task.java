package com.example.dailydo.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dailydo.data.DateConverter;

import java.util.Date;

@Entity(tableName = "tasks")
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "task_name")
    @NonNull
    private String name;

    @ColumnInfo(name = "task_description")
    private String description;

    private int iconId;
    private int colorId;

    @TypeConverters(DateConverter.class)
    private Date date;

    private String timeOfDay;
    private boolean done;
    @Ignore
    public Task(@NonNull String name, String description, int iconId, int colorId, Date date, boolean done) {
        this.name = name;
        setDescription(description);
        setIconId(iconId);
        setColorId(colorId);
        setDate(date);
        setDone(done);
    }

    public Task(String name, String description, int colorId, int iconId, String timeOfDay) {
        this.name = name;
        setDescription(description);
        setColorId(colorId);
        setIconId(iconId);
        setTimeOfDay(timeOfDay);
    }
    protected Task(Parcel in) {
        name = in.readString();
        description = in.readString();
        iconId = in.readInt();
        colorId = in.readInt();
        timeOfDay = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(iconId);
        dest.writeInt(colorId);
    }


}

