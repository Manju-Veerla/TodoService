package com.example.task.response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoResponse {

	private long id;
	private String name;
	private String description;

	private Set<SubTaskResponse> tasks = new HashSet<>();



}
