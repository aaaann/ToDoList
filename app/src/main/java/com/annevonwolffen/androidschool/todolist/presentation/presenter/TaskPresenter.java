package com.annevonwolffen.androidschool.todolist.presentation.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.annevonwolffen.androidschool.todolist.data.db.TaskRepository;
import com.annevonwolffen.androidschool.todolist.data.model.TaskModel;
import com.annevonwolffen.androidschool.todolist.presentation.view.IMainView;
import com.annevonwolffen.androidschool.todolist.presentation.view.ITaskRowView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TaskPresenter {
    private final static String TAG = "TaskPresenter";

    private final WeakReference<IMainView> mMainActivityWeakReference;
    private final TaskRepository mTaskRepository;
    private List<TaskModel> mTasks = new ArrayList<>();


    public TaskPresenter(@NonNull IMainView mainView, Context context) {
        mMainActivityWeakReference = new WeakReference<>(mainView);
        mTaskRepository = new TaskRepository(context);
    }

    public void loadData() {
        mTaskRepository.getTasks(new TaskRepository.OnDbOperationFinishListener() {
            @Override
            public void onFinish(Long id) {

            }

            @Override
            public void onFinish(List<TaskModel> taskModels) {
                mTasks = taskModels;
                mMainActivityWeakReference.get().showData();
            }

            @Override
            public void onFinish(Integer count) {

            }
        });
    }

    public void addTask(final String taskName) {
        mTaskRepository.insertTask(taskName, new TaskRepository.OnDbOperationFinishListener() {
            @Override
            public void onFinish(Long id) {
                if (id != -1) {
                    mTasks.add(new TaskModel(id, taskName));
                    mMainActivityWeakReference.get().showData();
                } else {
                    Log.d(TAG, "error while inserting data");
                }
            }

            @Override
            public void onFinish(List<TaskModel> taskModels) {

            }

            @Override
            public void onFinish(Integer count) {

            }
        });
    }

    /**
     * called when task is checked as done
     */
    public void onCheckChanged(int position, final boolean isChecked) {
        Log.d(TAG, "onCheckChanged: " + position);
        final TaskModel task = mTasks.get(position);
        mTaskRepository.updateTask(task.getId(), isChecked, new TaskRepository.OnDbOperationFinishListener() {
            @Override
            public void onFinish(Long id) {

            }

            @Override
            public void onFinish(List<TaskModel> taskModels) {

            }

            @Override
            public void onFinish(Integer count) {
                if (count == 1) {
                    task.setIsDone(isChecked);
                    mMainActivityWeakReference.get().showData();
                    Log.d(TAG, "onFinish: update " + task.toString());
                } else {
                    Log.d(TAG, "error while updating data");
                }
            }
        });

    }

    private boolean deleteTask(int position) {
        Log.d(TAG, "deleteTask: " + position);
        final TaskModel task = mTasks.get(position);

        mTaskRepository.deleteTask(task.getId(), new TaskRepository.OnDbOperationFinishListener() {
            @Override
            public void onFinish(Long id) {

            }

            @Override
            public void onFinish(List<TaskModel> taskModels) {

            }

            @Override
            public void onFinish(Integer count) {
                if (count == 1) {
                    mTasks.remove(task);
                    mMainActivityWeakReference.get().showData();
                    Log.d(TAG, "onFinish: " );
                } else {
                    Log.d(TAG, "error while deleting data");
                }
            }
        });

        return mTasks.indexOf(task) == -1;
    }

    public void onLongClick(final int position) {
        mMainActivityWeakReference.get().openDeleteDialog(() -> deleteTask(position));
    }


    public void onBindRecordRowViewAtPosition(int position, ITaskRowView rowView) {
        TaskModel model = mTasks.get(position);
        rowView.setTaskLabel(model.getLabel());
        rowView.setTaskDone(model.isDone());
        rowView.setOnCheckListener(position);
        rowView.setOnLongClickListener(position);
    }

    public int getRecordRowsCount() {
        return mTasks.size();
    }
}
