package com.annevonwolffen.androidschool.todolist.data.model;

public class TaskModel {
    private long mId;
    private String mLabel;
    private boolean mIsDone;

    public TaskModel(long id, String label) {
        mId = id;
        mLabel = label;
    }

    public TaskModel(long id, String label, boolean isDone) {
        mId = id;
        mLabel = label;
        mIsDone = isDone;

    }

    public long getId() {
        return mId;
    }

    public String getLabel() {
        return mLabel;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setIsDone(boolean isDone) {
        mIsDone = isDone;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "mId=" + mId +
                ", mLabel='" + mLabel + '\'' +
                ", mIsDone=" + mIsDone +
                '}';
    }
}
