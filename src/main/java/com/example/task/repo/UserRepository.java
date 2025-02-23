package com.example.task.repo;


import com.example.task.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{

  List<User> findUsersByTodos_Id(UUID todo_id);

}
