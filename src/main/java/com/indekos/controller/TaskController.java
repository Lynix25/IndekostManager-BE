package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.CreateTask;
import com.indekos.dto.response.Response;
import com.indekos.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody CreateTask createTask){
        taskService.register(createTask);
        return new ResponseEntity(new Response("Sukses", "registered task"), HttpStatus.OK);
    }

    @GetMapping("")
    private ResponseEntity getAllService(){
        return GlobalAcceptions.listData(taskService.getAll(), "All Task Data");
    }
}
