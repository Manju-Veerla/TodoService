package com.example.task.controller;

import com.example.task.model.request.UserRequest;
import com.example.task.model.response.UserResponse;
import com.example.task.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private UserService userService;

  @Test
  @DisplayName("GET /api/v1/users returns list of users")
  void getUsers_returnsList() throws Exception {
    Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/v1/users"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("GET /api/v1/users/{id} returns user")
  void getUser_returnsUser() throws Exception {
    UserResponse response = new UserResponse();
    Mockito.when(userService.getUser(anyInt())).thenReturn(response);

    mockMvc.perform(get("/api/v1/users/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("POST /api/v1/users creates user")
  void createUser_returnsCreated() throws Exception {
    UserResponse response = new UserResponse();
    Mockito.when(userService.createUser(any(UserRequest.class))).thenReturn(response);

    String json = "{\"name\":\"Test\",\"email\":\"[email protected]\"}";

    mockMvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("POST /api/v1/users returns 404 if user not created")
  void createUser_returnsNotFound() throws Exception {
    Mockito.when(userService.createUser(any(UserRequest.class))).thenReturn(null);

    String json = "{\"name\":\"Test\",\"email\":\"[email protected]\"}";

    mockMvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
      .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("PUT /api/v1/users/{id} updates user")
  void updateUser_returnsUpdated() throws Exception {
    UserResponse response = new UserResponse();
    Mockito.when(userService.updateUser(anyInt(), any(UserRequest.class))).thenReturn(response);

    String json = "{\"name\":\"Test\",\"email\":\"[email protected]\"}";

    mockMvc.perform(put("/api/v1/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
