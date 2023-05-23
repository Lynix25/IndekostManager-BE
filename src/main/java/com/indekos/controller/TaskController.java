package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.TaskDTO;
import com.indekos.dto.UserDTO;
import com.indekos.dto.request.TaskCreateRequest;
import com.indekos.dto.request.TaskUpdateRequest;
import com.indekos.model.Task;
import com.indekos.services.TaskService;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ResponseEntity<?> getAllTask(@RequestParam String requestor, @RequestParam String type){
        return GlobalAcceptions.listData(taskService.getAll(requestor, type), "All Task Data");
    }
    
    @GetMapping("/{id}")
    private ResponseEntity<?> getTask(@PathVariable String id){
        Task task = taskService.getById(id);

        TaskDTO taskDTO = new TaskDTO(task, task.getUser().getRoom());
    	return GlobalAcceptions.data(taskDTO, "Task Data");
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TaskCreateRequest taskCreateRequest, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.data(taskService.register(taskCreateRequest), "Berhasil menambahkan pengajuan layanan");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable String id,@RequestBody TaskUpdateRequest taskUpdateRequest){
        Task task = taskService.update(id,taskUpdateRequest);

        TaskDTO taskDTO = new TaskDTO(task, task.getUser().getRoom());
        return GlobalAcceptions.data(taskDTO, "Berhasil memperbaharui pengajuan layanan");
    }
}
