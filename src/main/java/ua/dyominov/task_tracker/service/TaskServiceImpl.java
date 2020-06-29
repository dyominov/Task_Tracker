package ua.dyominov.task_tracker.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.enums.Status;
import ua.dyominov.task_tracker.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(@Autowired TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public Task getTask(Integer id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task create(Task task, Integer id) {
        task.setUserId(id);
        return taskRepository.save(task);
    }


    @Override
    public List<Task> getTasksSortedByIdUsers() {
        return taskRepository.getAllSortedByIdUsers();
    }

    @Override
    public void remove(Integer id) {
        taskRepository.delete(taskRepository.findById(id).get());
    }

    @Override
    public Task update(Task task, Integer id) {
        Optional<Task> option = taskRepository.findById(id);
        if (option.isEmpty()) {
            return taskRepository.save(task);
        }
        Task old = option.get();
        BeanUtils.copyProperties(task, old, "id");
        return taskRepository.save(old);
    }

    @Override
    public Task changeTaskStatus(Integer id, Status status) {
        Task task = taskRepository.findById(id).get();
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    public Task changeTaskAssignmentUser(Integer id, Integer userId) {
        Task task = taskRepository.findById(id).get();
        task.setUserId(userId);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksFilteredByStatus(Status status) {
        return taskRepository.getAllFilteredByStatus(status);
    }
}
