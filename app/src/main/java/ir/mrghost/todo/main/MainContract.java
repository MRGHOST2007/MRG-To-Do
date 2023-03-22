package ir.mrghost.todo.main;

import java.util.List;

import ir.mrghost.todo.BasePresenter;
import ir.mrghost.todo.BaseView;
import ir.mrghost.todo.model.Task;

public interface MainContract {

    interface View extends BaseView {
        void showTasks(List<Task> tasks);
        void deleteAll();
        void updateTask(Task task);
        void addTask(Task task);
        void deleteTask(Task task);
        void setEmptyStateVisible(boolean visible);
    }

    interface Presenter extends BasePresenter<View> {
        void onDeleteAllButtonClicked();
        void onSearch(String query);
        void onTaskItemClicked(Task task);
    }

}
