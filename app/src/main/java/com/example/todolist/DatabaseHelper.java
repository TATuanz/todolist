package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ToDoList";

    private static final String ID_COLUMN_NAME = "task_id";

    private static final String NAME_COLUMN_NAME = "name";

    private static final String DEADLINE_COLUMN_NAME = "deadline";

    private static final String DURATION_COLUMN_NAME = "duration";

    private static final String DESCRIPTION_COLUMN_NAME = "description";

    private SQLiteDatabase database;

    private static final String DATABASE_CREATE_QUERY = String.format(
            "Create Table %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s INTEGER, " +
                    "%s INTEGER, " +
                    "%s TEXT )",
            DATABASE_NAME, ID_COLUMN_NAME, NAME_COLUMN_NAME, DEADLINE_COLUMN_NAME, DURATION_COLUMN_NAME, DESCRIPTION_COLUMN_NAME
    );

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        Log.w(this.getClass().getName(), DATABASE_NAME + "database upgrade to version" + newVersion + "old data lost");
    }

    public void insertTask (String name, Date deadline, int duration, String description) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN_NAME, name);
        rowValues.put(DEADLINE_COLUMN_NAME, deadline.getTime());
        rowValues.put(DURATION_COLUMN_NAME, duration);
        rowValues.put(DESCRIPTION_COLUMN_NAME, description);

        database.insertOrThrow(DATABASE_NAME, null, rowValues);
    }

    public void updateTask (int id, String name, Date deadline, int duration, String description) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN_NAME, name);
        rowValues.put(DEADLINE_COLUMN_NAME, deadline.getTime());
        rowValues.put(DURATION_COLUMN_NAME, duration);
        rowValues.put(DESCRIPTION_COLUMN_NAME, description);
        database.update(DATABASE_NAME, rowValues, ID_COLUMN_NAME + " = ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask (int id) {
        database.delete(DATABASE_NAME, ID_COLUMN_NAME + " = ?", new String[] {String.valueOf(id)});
    }

    public ArrayList<Task> readTask () {
        ArrayList<Task> task = new ArrayList<Task>();

        Cursor result = database.query(DATABASE_NAME,
                new String[] {ID_COLUMN_NAME, NAME_COLUMN_NAME, DEADLINE_COLUMN_NAME, DURATION_COLUMN_NAME, DESCRIPTION_COLUMN_NAME},
                null, null, null, null, null
        );

        result.moveToFirst();
        while (!result.isAfterLast()) {
            int id = result.getInt(0);
            String name = result.getString(1);
            Date deadline = new Date(result.getLong(2));
            int duration = result.getInt(3);
            String description = result.getString(4);

            task.add(new Task(id, name, deadline, duration, description));
            result.moveToNext();
        };

        return task;
    }

    public Task readTask (int id) {
        Task task = new Task();
        Cursor result = database.query(DATABASE_NAME,
                new String[] {ID_COLUMN_NAME, NAME_COLUMN_NAME, DEADLINE_COLUMN_NAME, DURATION_COLUMN_NAME, DESCRIPTION_COLUMN_NAME},
                ID_COLUMN_NAME + " = ?", new String[] { String.valueOf(id) }, null, null, null
        );

        result.moveToFirst();

        String name = result.getString(1);
        Date deadline = new Date(result.getLong(2));
        int duration = result.getInt(3);
        String description = result.getString(4);

        task = new Task(id, name, deadline, duration, description);
        
        return task;
    }
}
