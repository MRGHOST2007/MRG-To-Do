package ir.mrghost.todo.task;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1 , exportSchema = false , entities = {Task.class})
public abstract class MainDataBase extends RoomDatabase {
    private static MainDataBase mainDataBase;

    public static MainDataBase getMainDataBase(Context context) {
        if (mainDataBase == null)
            mainDataBase = Room.databaseBuilder( context.getApplicationContext() , MainDataBase.class , "mainDataBase")
                    .allowMainThreadQueries()
                    .build();

        return mainDataBase;
    }

    public abstract TaskDAO getTaskDAO();
}
