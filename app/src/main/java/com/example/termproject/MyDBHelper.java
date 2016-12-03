package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="schedule.db";
    private static final int DATABASE_VERSION = 1;
    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE schedule (" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "date DATE NULL," +
                "title TEXT NULL," +
                "place TEXT NULL" +
                ");");
    }
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS schedule");
        onCreate(db);
    }
}
