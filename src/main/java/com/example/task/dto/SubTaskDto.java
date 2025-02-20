package com.example.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubTaskDto {

	private int id;
	
	@NotNull(message = "The name is required.")
	private String name;
	
	private String description;

}
