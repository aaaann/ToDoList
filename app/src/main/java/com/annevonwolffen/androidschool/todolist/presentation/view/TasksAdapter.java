package com.annevonwolffen.androidschool.todolist.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.annevonwolffen.androidschool.todolist.R;
import com.annevonwolffen.androidschool.todolist.presentation.presenter.TaskPresenter;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private final TaskPresenter mPresenter;

    public TasksAdapter(TaskPresenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        mPresenter.onBindRecordRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getRecordRowsCount();
    }


    static class TaskViewHolder extends RecyclerView.ViewHolder implements ITaskRowView{
        private TextView mLabel;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            mLabel = itemView.findViewById(R.id.tv_task_name);


        }

        @Override
        public void setTaskLabel(String name) {
            mLabel.setText(name);
        }
    }
}
