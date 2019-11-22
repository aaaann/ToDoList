package com.annevonwolffen.androidschool.todolist.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.BaseColumns;

import com.annevonwolffen.androidschool.todolist.data.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    public TaskRepository(Context context) {
        mDbHelper = new DBHelper(context);
    }

    public void getTasks(OnDbOperationFinishListener onDbOperationFinishListener) {
        GetAsyncTask getTasksAsyncTask = new GetAsyncTask(onDbOperationFinishListener);
        getTasksAsyncTask.execute();
    }

    public void insertTask(String taskName, OnDbOperationFinishListener onDbOperationFinishListener) {
        InsertAsyncTask insertTaskAsyncTask = new InsertAsyncTask(taskName, onDbOperationFinishListener);
        insertTaskAsyncTask.execute();
    }

    public void updateTask(long id, boolean isDone, OnDbOperationFinishListener onDbOperationFinishListener) {
        UpdateAsyncTask updateTaskAsyncTask = new UpdateAsyncTask(id, isDone, onDbOperationFinishListener);
        updateTaskAsyncTask.execute();
    }

    private List<TaskModel> findAll() {
        mDb = mDbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                TaskSchema.TaskTable.Cols.NAME,
                TaskSchema.TaskTable.Cols.IS_DONE
        };

        Cursor cursor = mDb.query(
                TaskSchema.TaskTable.TABLE_NAME, // Название таблиы
                projection, // Какие колонки нужны - columns
                null, // Условие
                null, // Значения условия
                null, // группировка – groupBy
                null, // фильтр (после группировки) - having
                null // сортировка
        );

        List<TaskModel> tasks = new ArrayList<>();
        try {
            while(cursor.moveToNext()) {
                tasks.add(new TaskModel(cursor.getLong(cursor.getColumnIndex(BaseColumns._ID)),
                        cursor.getString(cursor.getColumnIndex(TaskSchema.TaskTable.Cols.NAME)),
                        cursor.getInt(cursor.getColumnIndex(TaskSchema.TaskTable.Cols.IS_DONE)) == 1));
            }
        } finally {
            cursor.close();
        }

        return tasks;
    }

    private long insert(String taskName) {
        mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(TaskSchema.TaskTable.Cols.UUID, "12309038493"); // todo: call java method for generate UUID
        values.put(TaskSchema.TaskTable.Cols.NAME, taskName);

        return mDb.insert(TaskSchema.TaskTable.TABLE_NAME, null, values);
    }

    private int update(long id, boolean isDone) {
        mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskSchema.TaskTable.Cols.IS_DONE, isDone ? 1 : 0);

        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { "" + id };
        return mDb.update(
                TaskSchema.TaskTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

//    private long delete() {
//
//

//    }


    private class GetAsyncTask extends AsyncTask<Void, Void, List<TaskModel>> {
        private final OnDbOperationFinishListener mOnDbOperationFinishListener;

        private GetAsyncTask(OnDbOperationFinishListener onDbOperationFinishListener) {
            mOnDbOperationFinishListener = onDbOperationFinishListener;
        }

        @Override
        protected List<TaskModel> doInBackground(Void... voids) {
            return findAll();
        }

        @Override
        protected void onPostExecute(List<TaskModel> taskModels) {
            super.onPostExecute(taskModels);

            mOnDbOperationFinishListener.onFinish(taskModels);
        }
    }
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

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Integer> {

        private final OnDbOperationFinishListener mOnDbOperationFinishListener;
        private final long mId;
        private final boolean mIsDone;

        private UpdateAsyncTask(long id, boolean isDone, OnDbOperationFinishListener onDbOperationFinishListener) {
            mId = id;
            mIsDone = isDone;
            mOnDbOperationFinishListener = onDbOperationFinishListener;
        }


        @Override
        protected Integer doInBackground(Void... voids) {
            return update(mId, mIsDone);
        }

        @Override
        protected void onPostExecute(Integer count) {
            super.onPostExecute(count);

            mOnDbOperationFinishListener.onFinish(count);
        }
    }

    public interface OnDbOperationFinishListener {
        void onFinish(Long id);

        void onFinish(List<TaskModel> taskModels);

        void onFinish(Integer count);
    }



}
