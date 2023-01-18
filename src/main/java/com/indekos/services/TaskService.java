package com.indekos.services;

import com.indekos.dto.request.TaskCreateRequest;
import com.indekos.model.Task;
import com.indekos.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TaskRepository taskRepository;

    public Task register(TaskCreateRequest taskCreateRequest){
        Task task = modelMapper.map(taskCreateRequest, Task.class);
        task.create(taskCreateRequest.getRequesterIdUser());
        task.setStatus(1);
        taskRepository.save(task);
        return task;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
