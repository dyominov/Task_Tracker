package ua.dyominov.task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.User;
import ua.dyominov.task_tracker.model.enums.Status;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByUserId(Integer userId);

    default List<Task> getAllFilteredByStatus(Status status) {
        return findAll()
                .stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    default List<Task> getAllSortedByIdUsers() {
        List<Task> tasks = findAll();
        tasks.sort(Comparator.comparingInt(Task::getUserId));
        return tasks;
    }

}
