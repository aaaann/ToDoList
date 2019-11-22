package com.annevonwolffen.androidschool.todolist.presentation.view;

public interface ITaskRowView {
    void setTaskLabel(String name);

    void setTaskDone(Boolean isDone);

    void setOnCheckListener(int position);
}
