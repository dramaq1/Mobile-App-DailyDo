package com.example.dailydo.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "task_name")
    @NonNull
    private String name;

    @ColumnInfo(name = "task_description")
    private String description;

    private int iconId;
    private int colorId;
    private Date date;
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

    public Task(String name, String description, int colorId, int iconId) {
        this.name = name;
        setDescription(description);
        setColorId(colorId);
        setIconId(iconId);
    }

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
}

