package ua.dyominov.task_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"ua.dyominov.task_tracker.*", "spring.configuration.*"})
public class TaskTrackerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TaskTrackerApplication.class, args);
    }
}
