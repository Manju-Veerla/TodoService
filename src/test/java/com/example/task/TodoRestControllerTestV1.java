package com.example.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.task.model.request.TodoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.task.controller.TodoRestController;

import com.example.task.model.response.TodoResponse;
import com.example.task.service.TodoService;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TodoRestController.class)
public class TodoRestControllerTestV1 {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	TodoService todoService;

	TodoResponse todoResponse = new TodoResponse();

	String expected = "{\r\n"
			+ "  \"name\": \"Todo_1\",\r\n"
			+ "  \"description\": \"Task todo one\"}";

	@Test
	public void getTodoTest() throws Exception
	{
		//todoResponse.setId(1);
		todoResponse.setName("Todo_1");
		todoResponse.setDescription("Task todo one");

		Mockito.when(
				todoService.getTodo(UUID.randomUUID())).thenReturn(todoResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/v1/todos/"+UUID.randomUUID()).accept(
						MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
    public void createTodoTest() throws Exception {

		//todoResponse.setId(1);
		todoResponse.setName("Todo_1");
		todoResponse.setDescription("Task todo one");

        Mockito.when(todoService.createTodo(Mockito.any(TodoRequest.class))).thenReturn(todoResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/todos")
                .accept(MediaType.APPLICATION_JSON).content(expected)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }
}
