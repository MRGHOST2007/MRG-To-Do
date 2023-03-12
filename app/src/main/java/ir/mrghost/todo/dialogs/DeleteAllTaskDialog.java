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

import ir.mrghost.todo.R;

public class DeleteAllTaskDialog extends DialogFragment {

    private DeleteAllTaskCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (DeleteAllTaskCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_dialog_task , null , false);

        View yes = view.findViewById(R.id.yesBtn);
        View no = view.findViewById(R.id.noBtn);

        yes.setOnClickListener(v -> {
            callback.onDeleteAllTask();
            dismiss();
        });

        no.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();

    }

    public interface DeleteAllTaskCallback{
        void onDeleteAllTask();
    }
}
