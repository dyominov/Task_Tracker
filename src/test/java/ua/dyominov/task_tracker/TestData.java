package ua.dyominov.task_tracker;

import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.User;
import ua.dyominov.task_tracker.model.enums.Status;

public class TestData {


    public static User user1 = new User(1, "Ivan", "Dyominov", "dema.ivan@gmail.com");
    public static User user2 = new User(2, "Ivan", "Dyominov", "dema.ivan@gmail.com");
    public static User user3 = new User(3, "Ivan", "Dyominov", "dema.ivan@gmail.com");
    public static User user4 = new User(4, "Ivan", "Dyominov", "dema.ivan@gmail.com");


    public static Task task1 = new Task(1, "Title", "desc", Status.VIEW);
    public static Task task2 = new Task(2, "Title", "desc", Status.VIEW);
    public static Task task3 = new Task(3, "Title", "desc", Status.DONE);
    public static Task task4 = new Task(4, "Title", "desc", Status.IN_PROGRESS);
    public static Task task5 = new Task(5, "Title", "desc", Status.IN_PROGRESS);
    public static Task task6 = new Task(6, "Title", "desc", Status.DONE);
}
