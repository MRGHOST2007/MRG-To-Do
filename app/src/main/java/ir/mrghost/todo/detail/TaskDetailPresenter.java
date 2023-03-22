package ir.mrghost.todo.detail;

import ir.mrghost.todo.main.MainActivity;
import ir.mrghost.todo.model.Task;
import ir.mrghost.todo.model.TaskDao;

public class TaskDetailPresenter implements TaskDetailContract.Presenter{

    private TaskDao taskDao;
    private TaskDetailContract.View view;
    private Task task;

    public TaskDetailPresenter(TaskDao taskDao , Task task){
        this.taskDao = taskDao;
        this.task = task;
    }

    @Override
    public void onAttach(TaskDetailContract.View view) {
        this.view = view;
        if (task != null){
            view.setDeleteButtonVisible(true);
            view.showTask(task);
        }
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void deleteTask() {
        if (task != null)
            if (taskDao.delete(task) > 0){
                view.returnResult(MainActivity.RES_CODE_DELETE_TASK , task);
            }
    }

    @Override
    public void saveChange(int importance, String title) {
        if (title.isEmpty()){
            view.showError("Enter Title!");
            return;
        }

        if (task == null){
            Task task = new Task();
            task.setTitle(title);
            task.setImportance(importance);
            long id  = taskDao.add(task);
            task.setId(id);

            view.returnResult(MainActivity.RES_CODE_ADD_TASK, task);
        } else {
            task.setTitle(title);
            task.setImportance(importance);
            int res = taskDao.update(task);
            if (res > 0){
                view.returnResult(MainActivity.RES_CODE_UPDATE_TASK , task);
            }
        }

    }
}
