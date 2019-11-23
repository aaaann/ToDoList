package com.annevonwolffen.androidschool.todolist.presentation.view;

public interface IMainView {
    void showData();

    void openDeleteDialog(MainActivity.OnDeleteClicked onDeleteClicked);
}
