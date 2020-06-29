package ua.dyominov.task_tracker.endpoints;

import net.minidev.json.JSONArray;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.dyominov.task_tracker.service.UserService;
import ua.dyominov.task_tracker.utils.JsonUtils;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dyominov.task_tracker.TestData.user1;

public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;


    @Before
    public void setUp() {
//        service.create(user1);
//        service.create(user2);
//        service.create(user3);
//        service.create(user4);
    }

    @AfterEach
    public void tearDown() {
    }


    @Test
    public void getUser() throws Exception {
        mvc.perform(get("/users/39")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw"))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.writeValue(userService.getUser(39))))
                .andExpect(jsonPath("$.user_id", is(39)))
                .andExpect(jsonPath("$.first_name", is("Ivan")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void create() throws Exception {
        assertEquals("number of users", 3, userService.getAll(0).size());

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw")
                .content("{" +
                        "\"user_id\": 1," +
                        "\"first_name\":\"John\"," +
                        "\"last_name\":\"Dou\"" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name", is("John")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        assertEquals("number of users after user creation", 4, userService.getAll(0).size());
    }

    @Test
    public void createTask() throws Exception {
        assertEquals("number of tasks", 2, userService.getTasks(39).size());

        mvc.perform(post("/users/39/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw")
                .content("{" +
                        "\"id\": 3," +
                        "\"title\":\"title\"," +
                        "\"description\":\"description\"," +
                        "\"status\":\"DONE\"" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        assertEquals("number of users after user creation", 3, userService.getTasks(39).size());
    }

    @Test
    public void update() throws Exception {
        userService.create(user1);
        mvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw")
                .content("{" +
                        "\"user_id\": 1," +
                        "\"first_name\":\"Ivan\"," +
                        "\"last_name\":\"Dou\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", is("Ivan")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        userService.remove(1);

    }

    @Test
    public void getTasks() throws Exception {
        ResultActions action = mvc.perform(get("/users/39/tasks")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        JSONArray array = JsonUtils.readFromJson(action, "$..title");
        Assertions.assertThat(array).hasSameElementsAs(Arrays.asList("title", "title", "title"));
    }

    @Test
    public void remove() throws Exception {
        assertEquals("number of users", 4, userService.getAll(0).size());
        mvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjEwMDAwMTU5MzQyOTQzOCwiaWF0IjoxNTkzNDI5NDM4fQ.sHFuxXoCbtack0ZofimomEuQrQAq-Sp_nfsR-PFXPTQgdSvg-Qoyunewf15TW_vooO2R79l9FSSXchSTZ1CPWw"))
                .andExpect(status().isOk());

        assertEquals("number of users after user creation", 3, userService.getAll(0).size());
    }


}