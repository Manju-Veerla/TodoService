package com.example.task.mapper;

import com.example.task.model.entities.SubTask;
import com.example.task.model.request.SubTaskRequest;
import com.example.task.model.response.SubTaskResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubTaskMapper {

  SubTaskResponse toSubTaskResponse(SubTask subTask);

  SubTask toSubTask(SubTaskRequest  subTaskRequest);


}
