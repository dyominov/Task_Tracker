package ua.dyominov.task_tracker.service;

import ua.dyominov.task_tracker.model.enums.Status;
import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.User;

import java.util.List;

public interface TaskService {
    Task getTask(Integer id);

    Task create(Task task, Integer id);

    List<Task> getTasksSortedByIdUsers();

    void remove(Integer id);

    Task update(Task task, Integer id);

    Task changeTaskStatus(Integer id, Status status);

    Task changeTaskAssignmentUser(Integer id, Integer userId);

    List<Task> getTasksFilteredByStatus(Status status);
}
