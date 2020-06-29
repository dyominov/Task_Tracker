package ua.dyominov.task_tracker.service;

import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    void remove(Integer id);

    User update(User user);

    List<Task> getTasks(Integer id);

    User getUser(Integer id);

    List<User> getAll(int page);

    User findByUsername(String username);

}
