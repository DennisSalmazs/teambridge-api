package com.teambridge.service.impl;

import com.teambridge.dto.ProjectDTO;
import com.teambridge.dto.TaskDTO;
import com.teambridge.dto.UserDTO;
import com.teambridge.entity.Project;
import com.teambridge.entity.Task;
import com.teambridge.entity.User;
import com.teambridge.enums.Status;
import com.teambridge.mapper.MapperUtil;
import com.teambridge.repository.TaskRepository;
import com.teambridge.service.TaskService;
import com.teambridge.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, MapperUtil mapperUtil, UserService userService) {
        this.taskRepository = taskRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
    }

    @Override
    public TaskDTO findById(Long id) {
       Optional<Task> task = taskRepository.findById(id);
       if (task.isPresent()){
           return mapperUtil.convert(task.get(), TaskDTO.class);
       }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().
                map(task -> mapperUtil.convert(task, TaskDTO.class)).
                collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO task) {
        task.setAssignedDate(LocalDate.now());
        task.setTaskStatus(Status.OPEN);
        Task convertedTask = mapperUtil.convert(task, Task.class);
        taskRepository.save(convertedTask);
    }

    @Override
    public void update(TaskDTO task) {
        // old task information
        Optional<Task> foundTask = taskRepository.findById(task.getId());

        Task convertedTask = mapperUtil.convert(task, Task.class);
        if (foundTask.isPresent()){
            convertedTask.setAssignedDate(foundTask.get().getAssignedDate());
            convertedTask.setTaskStatus(task.getTaskStatus() == null ? foundTask.get().getTaskStatus() : task.getTaskStatus());
            taskRepository.save(convertedTask);
        }
    }

    @Override
    public void delete(Long id) {
       Optional<Task> foundTask = taskRepository.findById(id);
       if (foundTask.isPresent()){
           foundTask.get().setIsDeleted(true);
           taskRepository.save(foundTask.get());
       }
    }

    @Override
    public int totalNonCompletedTasks(String projectCode) {
      return  taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO project) {
       List<Task> tasksToDelete = taskRepository.findAllByProject(mapperUtil.convert(project, Project.class));
       tasksToDelete.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO project) {
        List<Task> tasksToComplete = taskRepository.findAllByProject(mapperUtil.convert(project, Project.class));
        tasksToComplete.forEach(task -> {
            TaskDTO taskDTO = mapperUtil.convert(task, TaskDTO.class);
            taskDTO.setTaskStatus(Status.COMPLETE);
            //update status in DB
            update(taskDTO);
        });
    }

    // list all the tasks that belong to current logged-in user, for status is not
    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {

        UserDTO loggedInUser = userService.findByUserName("john@employee.com");

        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, mapperUtil.convert(loggedInUser, User.class));

        return tasks.stream().
                map(task -> mapperUtil.convert(task, TaskDTO.class)).
                collect(Collectors.toList());
    }

    // list all the tasks that belong to current logged-in user, for status is
    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {

        UserDTO loggedInUser = userService.findByUserName("john@employee.com");

        List<Task> tasks = taskRepository.findAllByTaskStatusAndAssignedEmployee(status, mapperUtil.convert(loggedInUser, User.class));

        return tasks.stream().
                map(task -> mapperUtil.convert(task, TaskDTO.class)).
                collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO employee) {

        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE,mapperUtil.convert(employee, User.class));

        return tasks.stream().
                map(task -> mapperUtil.convert(task, TaskDTO.class)).
                collect(Collectors.toList());
    }
}
