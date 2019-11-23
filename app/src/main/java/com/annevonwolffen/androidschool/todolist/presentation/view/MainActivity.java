package com.annevonwolffen.androidschool.todolist.presentation.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annevonwolffen.androidschool.todolist.R;
import com.annevonwolffen.androidschool.todolist.presentation.presenter.TaskPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements IMainView {

    private static final String TAG = "addTask";

    private TasksAdapter mAdapter;
    private TaskPresenter mPresenter;

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter.loadData();
    }

    private FloatingActionButton mCreateTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providePresenter();
        initRecyclerView();

        mCreateTaskButton = findViewById(R.id.fab_create_task);
        mCreateTaskButton.setOnClickListener(v -> openDialog());
    }

    private void providePresenter() {
        mPresenter = new TaskPresenter(this, this);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mAdapter = new TasksAdapter(mPresenter);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText editText = new EditText(this);
        builder.setView(editText);
        builder.setTitle(R.string.add_task);
        builder.setPositiveButton(R.string.OK, (dialog, which) -> {
            mPresenter.addTask(editText.getText().toString());
            Log.d(TAG, "onClick: " + editText.getText());
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void showData() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void openDeleteDialog(final OnDeleteClicked onDeleteClicked) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(R.string.do_you_want_to_delete);

        dialogBuilder.setPositiveButton(R.string.OK, (dialog, which) -> {
            if (onDeleteClicked.onDelete()) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.root_coordinator),
                        R.string.text_deleted, Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

    }

    public interface OnDeleteClicked {
        boolean onDelete();
    }
}
