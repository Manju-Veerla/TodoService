package com.example.task.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo,Long>{


}
