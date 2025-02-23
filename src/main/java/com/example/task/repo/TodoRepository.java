package com.example.task.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task.model.entities.Todo;

import java.util.List;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID>{

  List<Todo> findTodosByUsers_Id(UUID userid);

}
