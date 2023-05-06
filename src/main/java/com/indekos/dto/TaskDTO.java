package com.indekos.dto;

import com.indekos.model.Task;

import lombok.Data;

@Data
public class TaskDTO {
	private Task task;
	private SimpleUserDTO user;
}
