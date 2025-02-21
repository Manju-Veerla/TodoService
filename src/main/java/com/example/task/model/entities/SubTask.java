package com.example.task.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subtask")
@Getter
@Setter
public class SubTask {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;


	@Column(name = "name")
	@NotNull
	private String name;

	@Column(name = "description")
	private String description;

}

