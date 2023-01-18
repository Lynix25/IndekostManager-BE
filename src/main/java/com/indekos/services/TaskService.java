package com.indekos.services;

import com.indekos.dto.request.CreateTask;
import com.indekos.model.Task;
import com.indekos.repository.TaskRepository;
import com.indekos.utils.Utils;
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

    public Task register(CreateTask createTask){
        Task task = modelMapper.map(createTask, Task.class);

        taskRepository.save(task);
        return task;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
