package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.TaskCreateRequest;
import com.indekos.dto.request.TaskUpdateRequest;
import com.indekos.dto.response.Response;
import com.indekos.model.Task;
import com.indekos.services.TaskService;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/task")
public class TaskController {
	
    @Autowired
    TaskService taskService;
    
    @GetMapping
    private ResponseEntity<?> getAllTask(){
        return GlobalAcceptions.listData(taskService.getAll(), "All Task Data");
    }
    
    @GetMapping("/{id}")
    private ResponseEntity<?> getTask(@PathVariable String id){
        Task task = taskService.getById(id);

        return GlobalAcceptions.data(task,"");
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TaskCreateRequest taskCreateRequest, Errors errors){
        Validated.request(errors);

        taskService.register(taskCreateRequest);

        return new ResponseEntity<>(new Response<>("Sukses", "registered task"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable String id,@Valid @RequestBody TaskUpdateRequest taskUpdateRequest, Errors errors){
        Validated.request(errors);

        Task task = taskService.update(id,taskUpdateRequest);

        return GlobalAcceptions.data(task, "Update Task Status");
    }
}
