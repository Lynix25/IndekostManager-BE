package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.request.TaskCreateRequest;
import com.indekos.dto.request.TaskUpdateRequest;
import com.indekos.model.Task;
import com.indekos.repository.TaskRepository;
import com.indekos.utils.Constant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
	
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    TaskRepository taskRepository;

    public Task getById(String id){
        try {
            return taskRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidRequestIdException("Invalid Task ID");
        }
    }

//    public List<Task> getAll(String requestor) {
//        return taskRepository.findAllByRequestor(requestor);
//    }
    
    public List<Task> getAll(String requestor) {
        return taskRepository.findActiveTaskByRequestor(requestor);
    }

    private void save(String modifierId, Task task){
        try {
            task.update(modifierId);
            taskRepository.save(task);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    public Task update(String id,TaskUpdateRequest request){
        Task task = getById(id);
        task.setStatus(request.getStatus());
        task.setNotes(request.getNotes());
        task.setCharge(request.getCharge());

        save(request.getRequesterId(), task);
        return task;
    }

    public Task register(TaskCreateRequest request){
        modelMapper.typeMap(TaskCreateRequest.class, Task.class).addMappings(mapper -> {
            mapper.map(TaskCreateRequest::getRequesterId, Task::create);
        });

        Task task = modelMapper.map(request, Task.class);
        task.setStatus(Constant.SUBMITTED);

        save(request.getServiceId(), task);
        return task;
    }
}