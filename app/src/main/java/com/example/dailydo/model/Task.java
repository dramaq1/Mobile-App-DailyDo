package com.example.dailydo.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "tasks")
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "text")
    public String name;

    @ColumnInfo(name = "text")

    public String description;
    public int iconId;
    public int colorId;
    public Date date;
    public boolean done;

    public Task() {
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && iconId == task.iconId && colorId == task.colorId && done == task.done && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(date, task.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, iconId, colorId, date, done);
    }
    protected Task(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        iconId = in.readInt();
        colorId = in.readInt();
        done = in.readByte() != 0;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(iconId);
        dest.writeInt(colorId);
        dest.writeByte((byte) (done ? 1 : 0));
    }
}


