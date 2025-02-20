package com.example.task.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoDto {

	private int id;
	
	@NotNull(message = "The name is required.")
	private String name;
	
	private String description;
	
	@Valid
	private Set<SubTaskDto> tasks = new HashSet<>();

}
