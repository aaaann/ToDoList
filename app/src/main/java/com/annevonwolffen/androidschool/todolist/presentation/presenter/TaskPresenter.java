package com.annevonwolffen.androidschool.todolist.presentation.presenter;

import android.content.Context;

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

    private final WeakReference<IMainView> mMainActivityWeakReference;
    private final TaskRepository mTaskRepository;
    private List<TaskModel> mTasks = new ArrayList<>();


    public TaskPresenter(@NonNull IMainView mainView, Context context) {
        mMainActivityWeakReference = new WeakReference<>(mainView);
        mTaskRepository = new TaskRepository(context);
    }

    public void addTask(String taskName) {

        // todo: call of some db repository for insert

        mMainActivityWeakReference.get().showData();
    }

    public void deleteTask(String taskName) { //todo: vielleicht taskID

    }

    /**
     * called when task is checked as done
     */
    public void checkTaskAsDone() {

    }

    public void loadData() {
        // todo: initialize mTasks from db
    }

    public void onBindRecordRowViewAtPosition(int position, ITaskRowView rowView) {
        TaskModel model = mTasks.get(position);
        rowView.setTaskLabel(model.getLabel());
    }

    public int getRecordRowsCount() {
        return mTasks.size();
    }
}
