package com.teambridge.controller;

import com.teambridge.dto.ResponseWrapper;
import com.teambridge.dto.TaskDTO;
import com.teambridge.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getTasks() {
        List<TaskDTO> taskDTOList = taskService.listAllTasks();
        return ResponseEntity
                .ok(new ResponseWrapper("Tasks successfully retrieved", taskDTOList));
    }

    @GetMapping("/{taskCode}")
    public ResponseEntity<ResponseWrapper> getTask(@PathVariable String taskCode) {
        TaskDTO taskDTO = taskService.findByTaskCode(taskCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Task successfully retrieved", taskDTO));
    }





}
