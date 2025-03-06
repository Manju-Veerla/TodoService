package com.example.task.mapper;

import com.example.task.model.entities.User;
import com.example.task.model.request.UserRequest;
import com.example.task.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "name", source = "userName")
  UserResponse toUserResponse(User user);
  @Mapping(target = "userName", source = "name")
  User toUser(UserRequest userRequest);
}
