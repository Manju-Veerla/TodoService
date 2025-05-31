package com.example.task.service;

import com.example.task.exception.TodoNotFoundException;
import com.example.task.mapper.SubTaskMapper;
import com.example.task.mapper.TodoMapper;
import com.example.task.model.entities.SubTask;
import com.example.task.model.entities.Todo;
import com.example.task.model.request.SubTaskRequest;
import com.example.task.model.request.TodoRequest;
import com.example.task.model.response.TodoResponse;
import com.example.task.repo.SubtaskRepository;
import com.example.task.repo.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

  @Mock
  private TodoRepository todoRepo;
  @Mock
  private SubtaskRepository subtaskRepo;
  @Mock
  private TodoMapper todoMapper;
  @Mock
  private SubTaskMapper subTaskMapper;

  @InjectMocks
  private TodoService todoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllTodos_shouldReturnList() {
    List<Todo> todos = Arrays.asList(new Todo(), new Todo());
    List<TodoResponse> todoResponses = Arrays.asList(new TodoResponse(), new TodoResponse());

    when(todoRepo.findAll()).thenReturn(todos);
    when(todoMapper.toTodoResponse(any(Todo.class))).thenReturn(new TodoResponse());

    List<TodoResponse> result = todoService.getAllTodos();

    assertEquals(todos.size(), result.size());
    verify(todoRepo).findAll();
  }

  @Test
  void getTodo_found_shouldReturnResponse() {
    int id = 1;
    Todo todo = new Todo();
    TodoResponse response = new TodoResponse();

    when(todoRepo.findById(id)).thenReturn(Optional.of(todo));
    when(todoMapper.toTodoResponse(todo)).thenReturn(response);

    TodoResponse result = todoService.getTodo(id);

    assertEquals(response, result);
    verify(todoRepo).findById(id);
  }

  @Test
  void getTodo_notFound_shouldThrow() {
    int id = 1;
    when(todoRepo.findById(id)).thenReturn(Optional.empty());

    assertThrows(TodoNotFoundException.class, () -> todoService.getTodo(id));
  }

  @Test
  void createTodo_shouldSaveAndReturnResponse() {
    TodoRequest request = mock(TodoRequest.class);
    Todo todo = new Todo();
    Todo savedTodo = new Todo();
    TodoResponse response = new TodoResponse();

    when(todoMapper.toTodoEntity(request)).thenReturn(todo);
    when(todoRepo.save(todo)).thenReturn(savedTodo);
    when(todoMapper.toTodoResponse(savedTodo)).thenReturn(response);

    TodoResponse result = todoService.createTodo(request);

    assertEquals(response, result);
    verify(todoRepo).save(todo);
  }

  @Test
  void updateTodo_allFields_shouldUpdateAndReturn() {
    Integer id = 1;
    TodoRequest request = mock(TodoRequest.class);
    Todo todo = new Todo();
    todo.setTasks(new HashSet<>());
    TodoResponse updatedResponse = new TodoResponse();

    when(todoRepo.findById(id)).thenReturn(Optional.of(todo));
    when(request.getName()).thenReturn("New Name");
    when(request.getDescription()).thenReturn("New Desc");
    Set<SubTaskRequest> subTaskRequests = new HashSet<>();
    SubTaskRequest subTaskRequest = mock(SubTaskRequest.class);
    subTaskRequests.add(subTaskRequest);
    when(request.getTasks()).thenReturn(subTaskRequests);

    SubTask subTask = new SubTask();
    when(subTaskMapper.toSubTask(subTaskRequest)).thenReturn(subTask);
    when(subtaskRepo.save(subTask)).thenReturn(subTask);
    when(todoRepo.save(todo)).thenReturn(todo);
    when(todoMapper.toTodoResponse(todo)).thenReturn(updatedResponse);

    TodoResponse result = todoService.updateTodo(id, request);

    assertEquals(updatedResponse, result);
    verify(todoRepo).save(todo);
  }

  @Test
  void updateTodo_notFound_shouldThrow() {
    Integer id = 1;
    TodoRequest request = mock(TodoRequest.class);

    when(todoRepo.findById(id)).thenReturn(Optional.empty());

    assertThrows(TodoNotFoundException.class, () -> todoService.updateTodo(id, request));
  }

  @Test
  void deleteTodo_found_shouldDelete() {
    Integer id = 1;
    Todo todo = new Todo();

    when(todoRepo.findById(id)).thenReturn(Optional.of(todo));
    doNothing().when(todoRepo).deleteById(id);

    assertDoesNotThrow(() -> todoService.deleteTodo(id));
    verify(todoRepo).deleteById(id);
  }

  @Test
  void deleteTodo_notFound_shouldThrow() {
    Integer id = 1;
    when(todoRepo.findById(id)).thenReturn(Optional.empty());

    assertThrows(TodoNotFoundException.class, () -> todoService.deleteTodo(id));
  }

  @Test
  void getSubtask_found_shouldReturnList() {
    Integer id = 1;
    String name = "sub";
    Todo todo = new Todo();
    List<SubTask> subTasks = Arrays.asList(new SubTask());

    when(todoRepo.findById(id)).thenReturn(Optional.of(todo));
    when(subtaskRepo.findBySubtaskName(id, name)).thenReturn(subTasks);

    List<SubTask> result = todoService.getSubtask(id, name);

    assertEquals(subTasks, result);
    verify(subtaskRepo).findBySubtaskName(id, name);
  }

  @Test
  void getSubtask_todoNotFound_shouldThrow() {
    Integer id = 1;
    String name = "sub";
    when(todoRepo.findById(id)).thenReturn(Optional.empty());

    assertThrows(TodoNotFoundException.class, () -> todoService.getSubtask(id, name));
  }
}
