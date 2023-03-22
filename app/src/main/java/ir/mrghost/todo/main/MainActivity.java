package ir.mrghost.todo.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.mrghost.todo.R;
import ir.mrghost.todo.detail.TaskDetailActivity;
import ir.mrghost.todo.model.AppDatabase;
import ir.mrghost.todo.model.Task;

public class MainActivity extends AppCompatActivity implements MainContract.View, TaskAdapter.TaskItemEventListener {

    private static final int REQ_CODE = 1234;
    public static final int RES_CODE_ADD_TASK = 1;
    public static final int RES_CODE_DELETE_TASK = 0;
    public static final int RES_CODE_UPDATE_TASK = 2;
    public static final String EXTRA_KEY = "task";
    private MainContract.Presenter presenter;
    private TaskAdapter adapter;
    private View emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(AppDatabase.getAppDatabase(this).getTaskDao());
        adapter = new TaskAdapter(this, this);
        emptyState = findViewById(R.id.emptyState);

        View addNewTask = findViewById(R.id.addNewTaskBtn);
        View deleteAllBtn = findViewById(R.id.deleteAllBtn);

        addNewTask.setOnClickListener(v ->
                startActivityForResult(new Intent(MainActivity.this, TaskDetailActivity.class), REQ_CODE));

        deleteAllBtn.setOnClickListener(v -> {
            presenter.onDeleteAllButtonClicked();
        });

        RecyclerView recyclerView = findViewById(R.id.taskListRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        EditText search = findViewById(R.id.searchEt);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        adapter.setTasks(tasks);
    }

    @Override
    public void deleteAll() {
        adapter.clearItems();
    }

    @Override
    public void updateTask(Task task) {
        adapter.updateItem(task);
    }

    @Override
    public void addTask(Task task) {
        adapter.addItem(task);
    }

    @Override
    public void deleteTask(Task task) {
        adapter.deleteItem(task);
    }

    @Override
    public void setEmptyStateVisible(boolean visible) {
        emptyState.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if ((resultCode == RES_CODE_ADD_TASK || resultCode == RES_CODE_UPDATE_TASK || resultCode == RES_CODE_DELETE_TASK)
                    && data != null) {
                Task task = data.getParcelableExtra(EXTRA_KEY);
                if (task != null) {
                    if (resultCode == RES_CODE_ADD_TASK)
                        adapter.addItem(task);
                    else if (resultCode == RES_CODE_UPDATE_TASK)
                        adapter.updateItem(task);
                    else
                        adapter.deleteItem(task);

                    setEmptyStateVisible(adapter.getItemCount() <= 0);
                }
            }
        }
    }

    @Override
    public void onClick(Task task) {
        presenter.onTaskItemClicked(task);
    }

    @Override
    public void onLongClick(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
        intent.putExtra(EXTRA_KEY, task);
        startActivityForResult(intent, REQ_CODE);
    }
}
