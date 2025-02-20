package com.example.task.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubTaskResponse {

	private String name;

	private String description;

}
