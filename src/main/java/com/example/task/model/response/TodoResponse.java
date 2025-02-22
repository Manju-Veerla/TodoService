package com.example.task.model.response;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoResponse {

	private String name;
	private String description;
	private Set<SubTaskResponse> tasks = new HashSet<>();


}
