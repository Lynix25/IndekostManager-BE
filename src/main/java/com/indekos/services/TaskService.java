package com.indekos.services;

import com.indekos.dto.request.CreateTask;
import com.indekos.model.Task;
import com.indekos.repository.TaskRepository;
import com.indekos.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Task register(CreateTask createTask){
        Task task = new Task();

        task.setService_id(createTask.getVariant());
        task.setService_date(createTask.getDateTime());
        task.setSummary(createTask.getSummary());
        task.setNotes(createTask.getNotes());
        task.setCreatedBy(createTask.getCreatedBy());
        task.setLastModifiedBy(createTask.getCreatedBy());

        taskRepository.save(task);

        return task;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
