package ir.mrghost.todo.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ir.mrghost.todo.R;
import ir.mrghost.todo.task.Task;

public class AddTaskDialog extends DialogFragment {

    private AddNewTaskCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (AddNewTaskCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_dialog_task, null , false);
        TextInputEditText titleInput = view.findViewById(R.id.inputTask);
        TextInputLayout titleLayout = view.findViewById(R.id.inputTaskLayout);
        View save = view.findViewById(R.id.saveBtn);

        save.setOnClickListener(v -> {
            if (titleInput.length() > 0){

                Task task = new Task();
                task.setTitle(titleInput.getText().toString());
                task.setCompleted(false);
                callback.onNewTask(task);
                dismiss();

            } else titleLayout.setError("Enter a title!");
        });

        builder.setView(view);

        return builder.create();

    }

    public interface AddNewTaskCallback{
        void onNewTask(Task task);
    }

}
