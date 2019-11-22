package com.annevonwolffen.androidschool.todolist.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class TaskRepository {
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;


    public TaskRepository(Context context) {
        mDbHelper = new DBHelper(context);
    }

    public void insertTask(String taskName, OnDbOperationFinishListener onDbOperationFinishListener) {
        InsertAsyncTask insertTaskAsyncTask = new InsertAsyncTask(taskName, onDbOperationFinishListener);
        insertTaskAsyncTask.execute();
    }


    private long insert(String taskName) {
        mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskSchema.TaskTable.Cols.UUID, "12309038493"); // todo: call java method for generate UUID
        values.put(TaskSchema.TaskTable.Cols.NAME,taskName);

        return mDb.insert(TaskSchema.TaskTable.TABLE_NAME, null, values);
    }

//    private long delete() {
//
//
//    }

//    private long update() {
//
//    }


    private class InsertAsyncTask extends AsyncTask<Void, Void, Long> {

        private final OnDbOperationFinishListener mOnDbOperationFinishListener;
        private final String mTaskName;

        private InsertAsyncTask(String taskName, OnDbOperationFinishListener onDbOperationFinishListener) {
            mTaskName = taskName;
            mOnDbOperationFinishListener = onDbOperationFinishListener;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return insert(mTaskName);
        }

        @Override
        protected void onPostExecute(Long id) {
            super.onPostExecute(id);

            mOnDbOperationFinishListener.onFinish(id);
        }
    }

    public interface OnDbOperationFinishListener {
        void onFinish(Long id);
    }

}
