package com.teambridge.controller;

import com.teambridge.dto.ResponseWrapper;
import com.teambridge.dto.TaskDTO;
import com.teambridge.enums.Status;
import com.teambridge.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.save(taskDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Task successfully created", 201));
    }

    @PutMapping("/{taskCode}")
    public ResponseEntity<ResponseWrapper> updateTask(@PathVariable String taskCode, @RequestBody TaskDTO taskDTO) {
        taskService.update(taskCode, taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper("Task successfully updated"));
    }

    @DeleteMapping("/{taskCode}")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable String taskCode) {
        taskService.delete(taskCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Task successfully deleted"));
    }

    @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper> employeePendingTasks() {
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity
                .ok(new ResponseWrapper("Pending Tasks successfully retrieved", taskDTOList));
    }

}
