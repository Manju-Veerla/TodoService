package com.example.task.model.response;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoResponse {

	private String name;
	private String description;
	private Set<SubTaskResponse> tasks = new HashSet<>();

}
