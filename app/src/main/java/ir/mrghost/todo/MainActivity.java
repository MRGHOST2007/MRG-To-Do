package ir.mrghost.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ir.mrghost.todo.dialogs.AddTaskDialog;
import ir.mrghost.todo.dialogs.DeleteAllTaskDialog;
import ir.mrghost.todo.dialogs.EditTaskDialog;
import ir.mrghost.todo.task.Task;
import ir.mrghost.todo.task.TaskAdapter;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.AddNewTaskCallback ,
        TaskAdapter.TaskItemEventListener ,
        EditTaskDialog.EditTaskCallback ,
        DeleteAllTaskDialog.DeleteAllTaskCallback {
    private SQLiteHelper sqLiteHelper;

    TaskAdapter adapter = new TaskAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchInput = findViewById(R.id.search);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    List<Task> tasks = sqLiteHelper.searchInTasks(s.toString());
                    adapter.setTasks(tasks);
                } else {
                    List<Task> tasks = sqLiteHelper.getTasks();
                    adapter.setTasks(tasks);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL , false));
        recyclerView.setAdapter(adapter);

        sqLiteHelper = new SQLiteHelper(this);
        List<Task> tasks = sqLiteHelper.getTasks();
        adapter.addItems(tasks);
        FloatingActionButton addTaskBtn = findViewById(R.id.addBtn);
        View clearAll = findViewById(R.id.clearAllBtn);

        addTaskBtn.setOnClickListener(v ->{
            AddTaskDialog dialog = new AddTaskDialog();
            dialog.show(getSupportFragmentManager() , null );
        });

        clearAll.setOnClickListener(v -> {
            DeleteAllTaskDialog dialog = new DeleteAllTaskDialog();
            dialog.show(getSupportFragmentManager() , null);
        });

    }

    @Override
    public void onDeleteAllTask() {
        sqLiteHelper.deleteAll();
        adapter.deleteAll();
    }

    @Override
    public void onNewTask(Task task) {
        long taskId = sqLiteHelper.addTask(task);
        if (taskId != -1){
            task.setId(taskId);
            adapter.addItem(task);
        }
    }

    @Override
    public void onDeleteClicked(Task task) {
        int res = sqLiteHelper.deleteTask(task);
        if (res > 0){
            adapter.deleteItem(task);
        }
    }



    @Override
    public void onItemLongPress(Task task) {
        EditTaskDialog dialog = new EditTaskDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task" , task);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager() , null);
    }

    @Override
    public void onEditTask(Task task) {
        int res = sqLiteHelper.updateTask(task);
        if (res > 0){
            adapter.updateItem(task);
        }
    }

    @Override
    public void onItemChecked(Task task) {
        sqLiteHelper.updateTask(task);
    }
}