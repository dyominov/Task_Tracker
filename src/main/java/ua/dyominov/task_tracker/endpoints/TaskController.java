package ua.dyominov.task_tracker.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.enums.Status;
import ua.dyominov.task_tracker.service.TaskService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/tasks")
@Api(value = "Task Controller", description = "Controller that contains task-related endpoints")
public class TaskController {


    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);


    private final TaskService service;


    public TaskController(final TaskService service) {
        this.service = service;
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get task by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "task unique identifier"),
    })
    public Task getTaskById(@PathVariable("id") final Integer id) {
        LOG.info("Getting task by id {}", id);
        return service.getTask(id);
    }


    @GetMapping(value = "/filtered/view", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "getting task filtered by status View")
    public List<Task> getTasksFilteredByStatusView() {
        LOG.info("getting task filtered by status View");
        return service.getTasksFilteredByStatus(Status.VIEW);
    }


    @GetMapping(value = "/filtered/in-progress", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "getting task filtered by status IN_PROGRESS")
    public List<Task> getTasksFilteredByStatusInProgress() {
        LOG.info("getting task filtered by status IN_PROGRESS");
        return service.getTasksFilteredByStatus(Status.IN_PROGRESS);
    }

    @GetMapping(value = "/filtered/done", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "getting task filtered by status DONE")
    public List<Task> getTasksFilteredByStatusDone() {
        LOG.info("getting task filtered by status DONE");
        return service.getTasksFilteredByStatus(Status.DONE);
    }

    @GetMapping(value = "/sorted", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "getting tasks sorted by user id")
    public List<Task> getTasksSortedByIdUsers() {
        LOG.info("getting tasks sorted by user id");
        return service.getTasksSortedByIdUsers();
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "delete task by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "task unique identifier")
    })
    public void remove(@PathVariable("id") final Integer id) {
        LOG.info("delete task by id {}", id);
        service.remove(id);
    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update task by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "task unique identifier"),
            @ApiImplicitParam(name = "task", required = true, value = "update  information for task")
    })
    public Task update(@PathVariable Integer id, @RequestBody final Task task) {
        LOG.info("update task by id {} with new info {}", id, task);
        return service.update(task, id);
    }

    @PutMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "change status task")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "task unique identifier"),
            @ApiImplicitParam(name = "jsonMap", required = true, value = "contains new status")
    })
    public Task changeTaskStatus(@PathVariable final Integer id, @RequestBody final Map<String, String> jsonMap) {
        Status status = Status.valueOf(jsonMap.get("status"));
        LOG.info("change status task by id {} with new status {}", id, status);
        return service.changeTaskStatus(id, status);
    }


    @PutMapping(value = "/{id}/assignment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "assignment new user to task")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "task unique identifier"),
            @ApiImplicitParam(name = "jsonMap", required = true, value = "contains new user")
    })
    public Task changeTaskAssignmentUser(@PathVariable final Integer id, @RequestBody final Map<String, Integer> jsonMap) {
        Integer userId = jsonMap.get("user_id");
        LOG.info("assignment new user to task id {} new user {}", id, userId);
        return service.changeTaskAssignmentUser(id, userId);
    }

}
