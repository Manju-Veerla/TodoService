package com.example.task.service;

import java.util.List;

import com.example.task.model.entities.SubTask;
import com.example.task.model.request.TodoRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.task.model.response.TodoResponse;



public interface TodoService {

	List<TodoResponse> getAllTodos();
	TodoResponse getTodo(long id) ;
	TodoResponse createTodo(@RequestBody @Valid TodoRequest todoRequest);
	TodoResponse updateTodo(long id , TodoRequest todoRequest) ;
	void deleteTodo(long id) ;
	List<SubTask> getSubtask(long id, String name) ;
}
