package com.annevonwolffen.androidschool.todolist.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TodoTasks.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание БД, если она была открыта впервые
        db.execSQL("create table " + TaskSchema.TaskTable.TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                //TaskSchema.TaskTable.Cols.UUID + " text, " +
                TaskSchema.TaskTable.Cols.IS_DONE + " integer default 0, " +
                TaskSchema.TaskTable.Cols.NAME + " text not null) " );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
