package com.example.task.service;

import com.example.task.model.request.UserRequest;
import com.example.task.model.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.UUID;


public interface UserService {

	List<UserResponse> getAllUsers();
	UserResponse getUser(UUID id) ;
	UserResponse createUser(@RequestBody @Valid UserRequest userRequest);
	UserResponse updateUser(UUID id , UserRequest userRequest) ;
	/*void deleteTodo(long id) ;
	List<?> getSubtask(long id, String name) ;*/
}
