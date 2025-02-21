package com.example.task.service;

import java.util.List;
import com.example.task.model.request.TodoRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.task.exception.TodoNotFoundException;
import com.example.task.model.response.TodoResponse;



public interface TodoService {

	List<TodoResponse> getAllTodos();
	TodoResponse getTodo(long id) ;
	TodoResponse createTodo(@RequestBody @Valid TodoRequest todo);
	TodoResponse updateTodo(long id , TodoRequest todoDetails) ;
	void deleteTodo(long id) ;

	List<?> getSubtask(long id, String name) ;
}
