package com.example.task.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubTaskRequest {

	@NotNull(message = "The name is required.")
	private String name;

	private String description;

}
