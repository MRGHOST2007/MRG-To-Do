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

public class EditTaskDialog extends DialogFragment {

    private EditTaskCallback callback;
    private Task task;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (EditTaskCallback) context;
        task = getArguments().getParcelable("task");
        if (task == null){
            dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_dialog_task, null , false);
        final  TextInputEditText titleInput = view.findViewById(R.id.editTask);
        final TextInputLayout titleLayout = view.findViewById(R.id.editTaskLayout);
        titleInput.setText(task.getTitle());
        View save = view.findViewById(R.id.editBtn);

        save.setOnClickListener(v -> {
            if (titleInput.length() > 0){
                task.setTitle(titleInput.getText().toString());
                task.setCompleted(false);
                callback.onEditTask(task);
                dismiss();

            } else titleLayout.setError("Enter a title!");
        });

        builder.setView(view);

        return builder.create();

    }

    public interface EditTaskCallback{
        void onEditTask(Task task);
    }

}
