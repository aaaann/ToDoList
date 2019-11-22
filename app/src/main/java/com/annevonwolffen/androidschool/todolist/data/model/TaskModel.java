package com.annevonwolffen.androidschool.todolist.data.model;

public class TaskModel {
    private long mId;
    private String mLabel;
    private boolean mIsDone;
    private boolean mIsDeleted;

    public TaskModel(long id, String label) {
        mId = id;
        mLabel = label;
    }

    public TaskModel(long id, String label, boolean isDone, boolean isDeleted) {
        mId = id;
        mLabel = label;
        mIsDone = isDone;
        mIsDeleted = isDeleted;
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

    public boolean isDeleted() {
        return mIsDeleted;
    }

    public void setIsDone(boolean isDone) {
        mIsDone = isDone;
    }

    public void setIsDeleted(boolean isDeleted) {
        mIsDeleted = isDeleted;
    }
}
