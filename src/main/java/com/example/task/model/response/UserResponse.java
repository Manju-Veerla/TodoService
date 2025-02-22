package com.example.task.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
  private String name;
  private String email;

  private Set<TodoResponse> todos = new HashSet<>();

}
