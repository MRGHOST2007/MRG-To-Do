package ir.mrghost.todo;

public interface BasePresenter<T extends BaseView> {
    void onAttach(T view);
    void onDetach();
}
