package ir.mrghost.todo.task;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    long add(Task task);

    @Delete
    int delete(Task task);

    @Update
    int update(Task task);

    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE title LIKE '%'||:query||'%' ")
    List<Task> search(String query);

    @Query("DELETE FROM taskTable")
    void deleteAll();

}
