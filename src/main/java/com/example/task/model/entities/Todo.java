package com.example.task.model.entities;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todo")
@Getter
@Setter
public class Todo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@OneToMany (cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "task_id", referencedColumnName="id")
	private Set<SubTask> tasks = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
    },
    mappedBy = "todos")
  @JsonIgnore
  private Set<User> users = new HashSet<>();

	}
