package com.example.encrypto.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.encrypto.models.User;

@Database(entities = User.class, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase database;

    public static synchronized UserDatabase getDatabase(Context context) {

        if (database == null) {
            database = Room.databaseBuilder(context, UserDatabase.class, "user_table").build();
        }

        return database;
    }

    public abstract UserDao getUserDao();

}
