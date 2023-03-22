package ir.mrghost.todo.main;

import java.util.List;

import ir.mrghost.todo.model.Task;
import ir.mrghost.todo.model.TaskDao;

public class MainPresenter implements MainContract.Presenter {

    private TaskDao taskDao;
    private List<Task> tasks;
    private MainContract.View view;

    public MainPresenter(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.tasks = taskDao.getAll();
    }

    @Override
    public void onDeleteAllButtonClicked() {
        taskDao.deleteAll();
        view.deleteAll();
        view.setEmptyStateVisible(true);
    }

    @Override
    public void onSearch(String query) {
        List<Task> tasks;
        if (!query.isEmpty()) {
            tasks = taskDao.search(query);
        } else {
            tasks = taskDao.getAll();
        }
        view.showTasks(tasks);
    }

    @Override
    public void onTaskItemClicked(Task task) {
        task.setCompleted(!task.isCompleted());
        int res = taskDao.update(task);
        if (res > 0)
            view.updateTask(task);
    }

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;
        if (!tasks.isEmpty()) {
            view.showTasks(tasks);
            view.setEmptyStateVisible(false);
        } else view.setEmptyStateVisible(true);
    }

    @Override
    public void onDetach() {

    }

}
