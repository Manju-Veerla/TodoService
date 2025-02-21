package com.example.task.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubTaskRequest {

	private int id;

	@NotNull(message = "The name is required.")
	private String name;

	private String description;

}
