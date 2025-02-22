package com.example.task.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserRequest {

  private String name;
  private String email;

  private Set<TodoRequest> todos = new HashSet<>();
}
