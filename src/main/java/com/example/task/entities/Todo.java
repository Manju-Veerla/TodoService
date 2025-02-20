package com.example.task.entities;

import java.util.HashSet;
import java.util.Set;

import com.example.task.dto.SubTaskDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	private long id;

	@Column(name = "name")
	@NotNull
	private String name;

	@Column(name = "description")
	private String description;

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id", referencedColumnName="id")
	private Set<SubTask> tasks = new HashSet<>();

	}
