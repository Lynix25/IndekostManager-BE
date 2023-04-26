package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.TaskCreateRequest;
import com.indekos.dto.request.TaskUpdateRequest;
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
    private ResponseEntity<?> getAllTask(@RequestParam String status){
        return GlobalAcceptions.listData(taskService.getAll(status), "All Task Data");
    }
    
    @GetMapping("/{id}")
    private ResponseEntity<?> getTask(@PathVariable String id){
    	return GlobalAcceptions.data(taskService.getById(id), "Task Data");
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TaskCreateRequest taskCreateRequest, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.data(taskService.register(taskCreateRequest), "Berhasil menambahkan pengajuan layanan");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable String id,@Valid @RequestBody TaskUpdateRequest taskUpdateRequest, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.data(taskService.update(id,taskUpdateRequest), "Berhasil memperbaharui pengajuan layanan");
    }
}
