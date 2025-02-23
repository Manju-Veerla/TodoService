package com.example.task.service;

import java.util.List;
import java.util.UUID;

import com.example.task.model.entities.SubTask;
import com.example.task.model.request.TodoRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.task.model.response.TodoResponse;



public interface TodoService {

	List<TodoResponse> getAllTodos();
	TodoResponse getTodo(UUID id) ;
	TodoResponse createTodo(@RequestBody @Valid TodoRequest todoRequest);
	TodoResponse updateTodo(UUID id , TodoRequest todoRequest) ;
	void deleteTodo(UUID id) ;
	List<SubTask> getSubtask(UUID id, String name) ;
}
