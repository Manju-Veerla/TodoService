package com.example.task.controller;

import com.example.task.model.request.UserRequest;
import com.example.task.model.response.UserResponse;
import com.example.task.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(value="/users",produces = "application/json")
  public List<UserResponse> getUsers(){
    return userService.getAllUsers();
  }

  @GetMapping(value="/users/{id}",produces = "application/json")
  public ResponseEntity<?> getUser(@PathVariable("id") String id) {
    UserResponse userResponse = userService.getUser(UUID.fromString(id));
    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
  }

  @PostMapping(value="/users",consumes = "application/json", produces = "application/json")
  public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user){
    UserResponse userResponse = userService.createUser(user);
    if(null != userResponse) {
      return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  public ResponseEntity <?> updateUser(@PathVariable("id") String id ,
                                       @RequestBody UserRequest userRequest) {
    UserResponse updatedUser = userService.updateUser(UUID.fromString(id), userRequest);
    return ResponseEntity.status(HttpStatus.OK).body( updatedUser);
  }


}
