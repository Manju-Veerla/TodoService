package com.example.task.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task.model.entities.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long>{

  List<Todo> findTodosByUsers_Id(long userid);

}
