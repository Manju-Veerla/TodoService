package com.example.task.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.task.dto.TodoDto;
import com.example.task.entities.Todo;
import com.example.task.exception.TodoNotFoundException;
import com.example.task.response.TodoResponse;



public interface TodoService {

	List<Todo> getAllTodos();
	TodoResponse getTodo(long id) throws TodoNotFoundException ;
	TodoResponse createTodo(@RequestBody TodoDto todo);
	TodoResponse updateTodo(long id ,TodoDto todoDetails) throws TodoNotFoundException ;
	void deleteTodo(long id) throws TodoNotFoundException ;
	
	List<?> getSubtask(long id, String name) throws TodoNotFoundException ;
}
