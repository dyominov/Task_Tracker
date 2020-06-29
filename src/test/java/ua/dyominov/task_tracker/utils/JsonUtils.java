package ua.dyominov.task_tracker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

/**
 * Class that contains utils method for working with json.
 */
public class JsonUtils {

    private JsonUtils() {}

    /**
     * Transform java object into json.
     */
    public static <T> String writeValue(T obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    /**
     * Reads a specific field from json and returns it as an object.
     * @param action contains content of response
     * @param jsonPath path to a specific field
     * @return object extracted from json
     */
    public static <T> T readFromJson(ResultActions action, String jsonPath) throws UnsupportedEncodingException {
        String content = action
                .andReturn()
                .getResponse()
                .getContentAsString();
        return JsonPath.read(content, jsonPath);
    }
}
