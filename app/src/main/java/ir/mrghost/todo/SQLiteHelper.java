package ir.mrghost.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.mrghost.todo.task.Task;

public class SQLiteHelper extends SQLiteOpenHelper {

    private final String TABLE_TASK = "taskTable";

    public SQLiteHelper(@Nullable Context context) {
        super(context, "app_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE "+TABLE_TASK+" (id INTEGER PRIMARY KEY AUTOINCREMENT , title TEXT , isCompleted BOOLEAN); ");
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title" , task.getTitle());
        values.put("isCompleted" , task.isCompleted());
        long res = sqLiteDatabase.insert(TABLE_TASK , null , values);
        sqLiteDatabase.close();
        return res;
    }

    public List<Task> getTasks(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TASK, null);
        List<Task> tasks = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(cursor.getLong(0));
                task.setTitle(cursor.getString(1));
                task.setCompleted(cursor.getInt(2) == 1);
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return tasks;
    }

    public List<Task> searchInTasks(String query){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_TASK + " WHERE title LIKE '%"+query+"%'" , null);
        List<Task> tasks = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(cursor.getLong(0));
                task.setTitle(cursor.getString(1));
                task.setCompleted(cursor.getInt(2) == 1);
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return tasks;

    }

    public int deleteTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int res = sqLiteDatabase.delete(TABLE_TASK , "id = ?" , new String[]{String.valueOf(task.getId())});
        sqLiteDatabase.close();
        return res;
    }

    public void deleteAll(){
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.execSQL("DELETE FROM "+TABLE_TASK);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int updateTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title" , task.getTitle());
        values.put("isCompleted" , task.isCompleted());
        int res = sqLiteDatabase.update(TABLE_TASK , values , "id = ?" , new String[]{String.valueOf(task.getId())});
        sqLiteDatabase.close();
        return res;
    }

}
