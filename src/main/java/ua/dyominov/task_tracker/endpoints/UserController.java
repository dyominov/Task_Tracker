package ua.dyominov.task_tracker.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.dyominov.task_tracker.model.AuthenticationResponse;
import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.User;
import ua.dyominov.task_tracker.security.JwtTokenUtil;
import ua.dyominov.task_tracker.service.TaskService;
import ua.dyominov.task_tracker.service.UserService;

import java.util.List;

/**
 * Controller that contains user-related endpoints.
 */
@RestController
@RequestMapping(value = "/users")
@Api(value = "User Controller", description = "Controller that contains user-related endpoints")
public class UserController {
    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    /**
     * User service.
     */

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Qualifier("jwtUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    private final UserService userService;
    private final TaskService taskService;

    /**
     * Autowired service field.
     *
     * @param userService user service.
     */
    public UserController(final UserService userService, final TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get user by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "user unique identifier"),
    })
    public User getUser(@PathVariable final Integer id) {
        LOG.info("Getting user by id {}", id);
        return userService.getUser(id);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", required = true, value = "contains user info"),
    })
    public User registrationUser(@RequestBody final User user) {
        LOG.info("creation user {}", user);
        return userService.create(user);
    }

    @PostMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create task by user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "contains user id"),
            @ApiImplicitParam(name = "task", required = true, value = "contains entity of task"),
    })
    public Task createTask(@PathVariable Integer id, @RequestBody final Task task) {
        LOG.info("Creation task {} by user id {}", task, id);
        return taskService.create(task, id);
    }

    @ApiOperation(value = "authentication of user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", required = true, value = "user contains info for authentication")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUserName());
        final String jwt = tokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "getting tasks assignment by user id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "user unique identifier")
    })
    public List<Task> getTasks(@PathVariable final Integer id) {
        LOG.info("getting tasks assignment by user id {}", id);
        return userService.getTasks(id);
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "remove user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "user unique identifier")
    })
    public void remove(@PathVariable final Integer id) {
        LOG.info("remove user by user id {}", id);
        userService.remove(id);
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", required = true, value = "user info fo update")
    })
    public User update(@RequestBody final User user) {
        LOG.info("Getting game list by user id {}", user);
        return userService.update(user);
    }

    @GetMapping(value = "/", params = "page")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "all user with start page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, value = "start page"),
    })
    public List<User> getAllUsers(@RequestParam("page") int page) {
        LOG.info("Getting users from page {}", page);
        return userService.getAll(page);
    }

}
