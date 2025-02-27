package com.example.task.controller;

import java.util.List;
import java.util.UUID;

import com.example.task.model.entities.SubTask;
import com.example.task.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.model.request.TodoRequest;
import com.example.task.model.entities.Todo;
import com.example.task.model.response.TodoResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TodoRestController {


  private final TodoService todoService;

  @Operation(summary = "Gets the list of todos")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Fetched Todo List",
      content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Todo.class))})})
  @GetMapping(value="/todos",produces = "application/json")
  public List<TodoResponse> getTodos(){
    return todoService.getAllTodos();
  }

  @Operation(summary = "Get an todo by its id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Get an Todo",
      content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Todo.class))}),
    @ApiResponse(responseCode = "400",description = "Todo not found",content = @Content)})
  @GetMapping(value="/todos/{id}",produces = "application/json")
  public ResponseEntity<TodoResponse> getTodo(@PathVariable("id") String id) {
    TodoResponse todoResponse = todoService.getTodo(UUID.fromString(id));
    return ResponseEntity.status(HttpStatus.OK).body(todoResponse);
  }

  @Operation(summary = "Get an subtask by task id and sub task name")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Get an subtask",
      content = {@Content(mediaType = "application/json",schema = @Schema(implementation = SubTask.class))}),
    @ApiResponse(responseCode = "400",description = "Subtask not found",content = @Content)})
  @GetMapping(value="/todos/{id}/subtask",produces = "application/json")
  public ResponseEntity<?> getSubtask(@PathVariable("id") String id , @RequestParam String name) {
    List<SubTask> subtasks = todoService.getSubtask(UUID.fromString(id),name);
    return ResponseEntity.status(HttpStatus.OK).body(subtasks);
  }


  @Operation(summary = "create an todo ")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Created an Todo",
      content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Todo.class))}),
    @ApiResponse(responseCode = "404",description = "Todo not created",content = @Content)})
  @PostMapping(value="/todos",consumes = "application/json", produces = "application/json")
  public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest todoRequest){
    TodoResponse todoResponse = todoService.createTodo(todoRequest);
    if(null != todoResponse) {
      return ResponseEntity.status(HttpStatus.CREATED).body(todoResponse);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Operation(summary = "Update an todo by its id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Updated an Todo",
      content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Todo.class))}),
    @ApiResponse(responseCode = "400",description = "Todo not found",content = @Content)})
  @PutMapping(value="/todos/{id}",consumes = "application/json", produces = "application/json")
  public ResponseEntity<TodoResponse> updateTodo(@PathVariable("id") String id ,
                                       @RequestBody TodoRequest todoRequest) {
    TodoResponse updatedTodo = todoService.updateTodo(UUID.fromString(id), todoRequest);
    return ResponseEntity.status(HttpStatus.OK).body(updatedTodo);
  }

  @Operation(summary = "Delete an todo by its id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Deleted an Todo",
      content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Todo.class))}),
    @ApiResponse(responseCode = "404",description = "Todo not found",content = @Content)})
  @DeleteMapping(value="/todos/{id}")
  public String deleteTodo(@PathVariable("id") String id) {
    todoService.deleteTodo(UUID.fromString(id));
    return "Todo successfully deleted " ;
  }
}
