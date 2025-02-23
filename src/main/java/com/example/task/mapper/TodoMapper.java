package com.example.task.mapper;

import com.example.task.model.entities.Todo;
import com.example.task.model.request.TodoRequest;
import com.example.task.model.response.TodoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",config = UUIDMapperConfig.class)
public interface TodoMapper {
  TodoResponse toTodoResponse(Todo todo);
  Todo toTodoEntity(TodoRequest todoRequest);
}
