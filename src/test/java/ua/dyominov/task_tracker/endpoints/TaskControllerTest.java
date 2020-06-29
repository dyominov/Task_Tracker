package ua.dyominov.task_tracker.endpoints;

import net.minidev.json.JSONArray;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.dyominov.task_tracker.model.Task;
import ua.dyominov.task_tracker.model.enums.Status;
import ua.dyominov.task_tracker.service.TaskService;
import ua.dyominov.task_tracker.utils.JsonUtils;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dyominov.task_tracker.TestData.task3;
import static ua.dyominov.task_tracker.TestData.task4;

public class TaskControllerTest extends AbstractControllerTest {

    @Autowired
    private TaskService taskService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTaskById() throws Exception {
        mvc.perform(get("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw"))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.writeValue(taskService.getTask(1))))
                .andExpect(jsonPath("$.status", is("VIEW")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getTasksFilteredByStatusView() throws Exception {
        ResultActions action = mvc.perform(get("/tasks/filtered/view")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        JSONArray array = JsonUtils.readFromJson(action, "$..status");
        Assertions.assertThat(array).hasSameElementsAs(Arrays.asList("VIEW", "VIEW", "VIEW"));
    }

    @Test
    public void getTasksFilteredByStatusInProgress() throws Exception {
        ResultActions action = mvc.perform(get("/tasks/filtered/in-progress")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        JSONArray array = JsonUtils.readFromJson(action, "$..status");
        Assertions.assertThat(array).hasSameElementsAs(Collections.singletonList("IN_PROGRESS"));
    }

    @Test
    public void getTasksFilteredByStatusDone() throws Exception {
        ResultActions action = mvc.perform(get("/tasks/filtered/done")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        JSONArray array = JsonUtils.readFromJson(action, "$..status");
        Assertions.assertThat(array).hasSameElementsAs(Collections.singletonList("DONE"));
    }

    @Test
    public void getTasksSortedByIdUsers() {
    }

    @Test
    public void remove() throws Exception {
        assertEquals("number of users", 5, taskService.getTasksSortedByIdUsers().size());
        mvc.perform(delete("/tasks/3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw"))
                .andExpect(status().isOk());

        assertEquals("number of users after user creation", 4, taskService.getTasksSortedByIdUsers().size());
    }

    @Test
    public void update() throws Exception {
        taskService.create(task3, 39);
        mvc.perform(put("/tasks/3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw")
                .content("{" +
                        "\"title\":\"Title\"," +
                        "\"description\":\"update\"," +
                        "\"status\":\"DONE\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("update")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        taskService.remove(3);
    }

    @Test
    public void changeTaskStatus() throws Exception {
        taskService.create(task4, 39);
        Task task = taskService.getTask(4);
        Assert.assertEquals(task.getStatus(), Status.IN_PROGRESS);
        mvc.perform(put("/tasks/4/status")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw")
                .content("{" +
                        "\"status\":\"DONE\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("DONE")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Task taskChangedStatus = taskService.getTask(4);
        Assert.assertEquals(taskChangedStatus.getStatus(), Status.DONE);
        taskService.remove(4);
    }

    @Test
    public void changeTaskAssignmentUser() throws Exception {
        taskService.create(task4, 39);
        Task task = taskService.getTask(4);
        Assert.assertEquals(task.getUserId().intValue(), 39);
        mvc.perform(put("/tasks/4/assignment")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw")
                .content("{" +
                        "\"user_id\":\"29\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id", is(29)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Task taskChangedStatus = taskService.getTask(4);
        Assert.assertEquals(taskChangedStatus.getUserId().intValue(), 29);
        taskService.remove(4);
    }
}