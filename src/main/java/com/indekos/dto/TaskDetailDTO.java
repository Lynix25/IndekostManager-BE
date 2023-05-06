package com.indekos.dto;

import com.indekos.model.Service;
import com.indekos.model.Task;

import lombok.Data;

@Data
public class TaskDetailDTO {
	
	private RequestorDTO requestor;
	private Task task;
	private Service service;
	
}
