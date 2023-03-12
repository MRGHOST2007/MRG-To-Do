package ir.mrghost.todo.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.mrghost.todo.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks = new ArrayList<>();
    private TaskItemEventListener eventListener;

    public TaskAdapter(TaskItemEventListener eventListener){
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindTask(tasks.get(position));
    }

    public void addItem(Task task) {
        tasks.add( 0 , task);
        notifyItemInserted(0);
    }

    public void deleteItem(Task task){
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()){
                tasks.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void deleteAll(){
        this.tasks.clear();
        notifyDataSetChanged();
    }

    public void updateItem(Task task){
        for (int i = 0; i < tasks.size(); i++) {
            if (task.getId() == tasks.get(i).getId()){
                tasks.set( i , task);
                notifyItemChanged(i);
            }
        }
    }

    public void addItems(List<Task> tasks) {
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

/////////////////////////////////////////////////////////////////

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private View deleteBtn;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
        public void bindTask(Task task){
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(task.isCompleted());
            checkBox.setText(task.getTitle());
            deleteBtn.setOnClickListener( v -> {
                eventListener.onDeleteClicked(task);
            });
            itemView.setOnLongClickListener(v -> {
                eventListener.onItemLongPress(task);
                return false;
            });

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.setCompleted(isChecked);
                eventListener.onItemChecked(task);
            });

        }

    }
    public interface TaskItemEventListener{
        void onDeleteClicked(Task task);
        void onItemLongPress(Task task);
        void onItemChecked(Task task);
    }
}


