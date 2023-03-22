package ir.mrghost.todo.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import ir.mrghost.todo.R;
import ir.mrghost.todo.main.MainActivity;
import ir.mrghost.todo.model.AppDatabase;
import ir.mrghost.todo.model.Task;


public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View {
    private int selectedImportance = Task.IMPORTANCE_NORMAL;
    private ImageView lastSelectedImportanceIv;
    private TaskDetailContract.Presenter presenter;
    private EditText editText;
    private TextView textView;
    private View deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        View backBtn = findViewById(R.id.backBtn);
        presenter = new TaskDetailPresenter(AppDatabase.getAppDatabase(this).getTaskDao() ,
                (Task) getIntent().getParcelableExtra(MainActivity.EXTRA_KEY));
        editText = findViewById(R.id.taskEt);

        backBtn.setOnClickListener(v -> finish());

        Button saveChangesBtn = findViewById(R.id.saveChangesBtn);
        saveChangesBtn.setOnClickListener(v -> presenter.saveChange(selectedImportance, editText.getText().toString()));

        View normalImportanceBtn = findViewById(R.id.normalImportanceBtn);
        lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.normalImportanceCheckIv);

        textView = findViewById(R.id.toolbarTitleTv);
        deleteBtn = findViewById(R.id.deleteTaskBtn);
        deleteBtn.setOnClickListener(v -> presenter.deleteTask());

        View highImportanceBtn = findViewById(R.id.highImportanceBtn);
        highImportanceBtn.setOnClickListener(v -> {
            if (selectedImportance != Task.IMPORTANCE_HIGH) {
                lastSelectedImportanceIv.setImageResource(0);
                ImageView imageView = v.findViewById(R.id.highImportanceCheckIv);
                imageView.setImageResource(R.drawable.ic_check_white_24dp);
                selectedImportance = Task.IMPORTANCE_HIGH;

                lastSelectedImportanceIv = imageView;
            }
        });
        View lowImportanceBtn = findViewById(R.id.lowImportanceBtn);
        lowImportanceBtn.setOnClickListener(v -> {
            if (selectedImportance != Task.IMPORTANCE_LOW) {
                lastSelectedImportanceIv.setImageResource(0);
                ImageView imageView = v.findViewById(R.id.lowImportanceCheckIv);
                imageView.setImageResource(R.drawable.ic_check_white_24dp);
                selectedImportance = Task.IMPORTANCE_LOW;

                lastSelectedImportanceIv = imageView;
            }
        });

        normalImportanceBtn.setOnClickListener(v -> {
            if (selectedImportance != Task.IMPORTANCE_NORMAL) {
                lastSelectedImportanceIv.setImageResource(0);
                ImageView imageView = v.findViewById(R.id.normalImportanceCheckIv);
                imageView.setImageResource(R.drawable.ic_check_white_24dp);
                selectedImportance = Task.IMPORTANCE_NORMAL;

                lastSelectedImportanceIv = imageView;
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
    public void showTask(Task task) {
        editText.setText(task.getTitle());
        switch (task.getImportance()){
            case Task.IMPORTANCE_HIGH:
                findViewById(R.id.highImportanceBtn).performClick();
                break;
            case Task.IMPORTANCE_LOW:
                findViewById(R.id.lowImportanceBtn).performClick();
                break;
            case Task.IMPORTANCE_NORMAL:
                findViewById(R.id.normalImportanceBtn).performClick();
                break;
        }
    }

    @Override
    public void setDeleteButtonVisible(boolean visible) {
        deleteBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(findViewById(R.id.taskRoot) , error , Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void returnResult(int resultCode, Task task) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_KEY , task);
        setResult(resultCode , intent);
        finish();
    }
}
