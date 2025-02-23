package com.example.task.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy= GenerationType.UUID)
  private UUID id;

  @Column(name = "username")
  @NotNull
  private String userName;

  @Column(name = "email")
  @NotNull
  private String email;

  @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
      CascadeType.PERSIST,
      CascadeType.ALL
    })
   @JoinTable(name = "user_todos",
    joinColumns = { @JoinColumn(name = "user_id") },
    inverseJoinColumns = { @JoinColumn(name = "todo_id") })
  @JsonIgnore
  private Set<Todo> todos = new HashSet<>();


}
