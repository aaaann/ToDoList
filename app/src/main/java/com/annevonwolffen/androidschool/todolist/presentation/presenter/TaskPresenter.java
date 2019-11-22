package com.annevonwolffen.androidschool.todolist.presentation.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
        });

    }

    public void addTask(final String taskName) {
        mTaskRepository.insertTask(taskName, new TaskRepository.OnDbOperationFinishListener() {
            @Override
            public void onFinish(Long id) {
                if (id != -1) {
                    mTasks.add(new TaskModel(id, taskName));
                } else {
                    Log.d(TAG, "error while inserting data");
                }
                mMainActivityWeakReference.get().showData();
            }

            @Override
            public void onFinish(List<TaskModel> taskModels) {

            }
        });

    }

    public void deleteTask(String taskName) { //todo: vielleicht taskID

    }


    /**
     * called when task is checked as done
     */
    public void checkTaskAsDone() {

    }

    public void onBindRecordRowViewAtPosition(int position, ITaskRowView rowView) {
        TaskModel model = mTasks.get(position);
        rowView.setTaskLabel(model.getLabel());
    }

    public int getRecordRowsCount() {
        return mTasks.size();
    }
}
