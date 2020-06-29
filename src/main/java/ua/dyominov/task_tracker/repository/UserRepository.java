package ua.dyominov.task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dyominov.task_tracker.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);

}
