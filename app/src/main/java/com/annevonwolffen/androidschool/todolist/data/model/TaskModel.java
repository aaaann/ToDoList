package com.annevonwolffen.androidschool.todolist.data.model;

public class TaskModel {
    private String mLabel;

    public TaskModel(String label) {
        mLabel = label;
    }

    public String getLabel() {
        return mLabel;
    }
}
