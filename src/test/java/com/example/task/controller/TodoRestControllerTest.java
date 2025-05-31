package com.example.task.controller;

import com.example.task.model.entities.SubTask;
import com.example.task.model.request.TodoRequest;
import com.example.task.model.response.TodoResponse;
import com.example.task.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoRestController.class)
class TodoRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private TodoService todoService;

  @Test
  @DisplayName("GET /api/v1/todos - should return list of todos")
  void getTodos_shouldReturnTodoList() throws Exception {
    Mockito.when(todoService.getAllTodos()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/v1/todos"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("GET /api/v1/todos/{id} - should return a todo")
  void getTodo_shouldReturnTodo() throws Exception {
    TodoResponse todoResponse = new TodoResponse();
    Mockito.when(todoService.getTodo(anyInt())).thenReturn(todoResponse);

    mockMvc.perform(get("/api/v1/todos/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("GET /api/v1/todos/{id}/subtask?name=foo - should return subtasks")
  void getSubtask_shouldReturnSubtasks() throws Exception {
    List<SubTask> subTasks = Collections.emptyList();
    Mockito.when(todoService.getSubtask(anyInt(), anyString())).thenReturn(subTasks);

    mockMvc.perform(get("/api/v1/todos/1/subtask").param("name", "foo"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("POST /api/v1/todos - should create a todo")
  void createTodo_shouldCreateTodo() throws Exception {
    TodoResponse todoResponse = new TodoResponse();
    Mockito.when(todoService.createTodo(any(TodoRequest.class))).thenReturn(todoResponse);

    String requestBody = "{\"title\":\"Test Todo\",\"description\":\"Test Desc\"}";

    mockMvc.perform(post("/api/v1/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("POST /api/v1/todos - should return 404 if not created")
  void createTodo_shouldReturnNotFoundIfNull() throws Exception {
    Mockito.when(todoService.createTodo(any(TodoRequest.class))).thenReturn(null);

    String requestBody = "{\"title\":\"Test Todo\",\"description\":\"Test Desc\"}";

    mockMvc.perform(post("/api/v1/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("PUT /api/v1/todos/{id} - should update a todo")
  void updateTodo_shouldUpdateTodo() throws Exception {
    TodoResponse updatedTodo = new TodoResponse();
    Mockito.when(todoService.updateTodo(anyInt(), any(TodoRequest.class))).thenReturn(updatedTodo);

    String requestBody = "{\"title\":\"Updated Todo\",\"description\":\"Updated Desc\"}";

    mockMvc.perform(put("/api/v1/todos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("DELETE /api/v1/todos/{id} - should delete a todo")
  void deleteTodo_shouldDeleteTodo() throws Exception {
    Mockito.doNothing().when(todoService).deleteTodo(anyInt());

    mockMvc.perform(delete("/api/v1/todos/1"))
      .andExpect(status().isOk())
      .andExpect(content().string("Todo successfully deleted "));
  }
}
