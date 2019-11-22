package com.annevonwolffen.androidschool.todolist.presentation.view;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
                        .inflate(R.layout.task_item, parent, false), mPresenter);
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
        private CheckBox mIsDone;
        private TaskPresenter mPresenter;

        public TaskViewHolder(@NonNull View itemView, TaskPresenter taskPresenter) {
            super(itemView);

            mPresenter = taskPresenter;
            mLabel = itemView.findViewById(R.id.tv_task_name);
            mIsDone = itemView.findViewById(R.id.chbx_done);
        }

        @Override
        public void setTaskLabel(String name) {
            mLabel.setText(name);
        }

        @Override
        public void setTaskDone(Boolean isDone) {
            mIsDone.setChecked(isDone);
            mLabel.setPaintFlags(isDone ? mLabel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                    : mLabel.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        @Override
        public void setOnCheckListener(final int position) {
            mIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mPresenter.onCheckChanged(position, isChecked);
                }
            });
        }
    }
}
