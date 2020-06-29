package ua.dyominov.task_tracker.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.User;
import ua.dyominov.task_tracker.repository.TaskRepository;
import ua.dyominov.task_tracker.repository.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final Integer pageLimit;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserServiceImpl(@Autowired UserRepository userRepository, @Autowired TaskRepository taskRepository, @Value("${app.pageLimit}") Integer pageLimit) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.pageLimit = pageLimit;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Integer id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    @Override
    public User update(User user) {
        Optional<User> option = userRepository.findById(user.getId());
        if (option.isEmpty()) {
            return userRepository.save(user);
        }
        User old = option.get();
        BeanUtils.copyProperties(user, old, "id");
        return userRepository.save(old);
    }

    @Override
    public List<Task> getTasks(Integer id) {
        return taskRepository.findAllByUserId(id);
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAll(int page) {
        return userRepository.findAll(PageRequest.of(page, pageLimit)).getContent();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }
}
