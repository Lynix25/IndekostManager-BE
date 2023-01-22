package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.TaskCreateRequest;
import com.indekos.model.Task;
import com.indekos.model.User;
import com.indekos.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TaskRepository taskRepository;

    public Task register(TaskCreateRequest taskCreateRequest){
        Task task = modelMapper.map(taskCreateRequest, Task.class);
        task.create(taskCreateRequest.getRequesterIdUser());
        task.setRequestedBy(taskCreateRequest.getRequesterIdUser());
        task.setStatus(0);
        taskRepository.save(task);
        return task;
    }

    public Task getById(String id){
        try {
            return taskRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidRequestIdException("Invalid Task ID");
        }
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
