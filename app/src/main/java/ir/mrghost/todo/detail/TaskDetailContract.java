package ir.mrghost.todo.detail;

import ir.mrghost.todo.BasePresenter;
import ir.mrghost.todo.BaseView;
import ir.mrghost.todo.model.Task;

public interface TaskDetailContract {

    interface View extends BaseView {
        void showTask(Task task);
        void setDeleteButtonVisible(boolean visible);
        void showError(String error);
        void returnResult(int resultCode , Task task);
    }

    interface Presenter extends BasePresenter<View> {
        void deleteTask();
        void saveChange(int importance , String title);
    }

}
