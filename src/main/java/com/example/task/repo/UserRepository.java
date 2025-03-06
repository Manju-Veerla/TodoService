package com.example.task.repo;


import com.example.task.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>{

  List<User> findUsersByTodos_Id(Integer todo_id);

}
