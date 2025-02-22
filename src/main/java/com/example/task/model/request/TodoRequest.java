package com.example.task.model.request;

import java.util.HashSet;
import java.util.Set;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequest {

	@NotNull(message = "The name is required.")
	private String name;

	private String description;

	@Valid
	private Set<SubTaskRequest> tasks = new HashSet<>();

}
