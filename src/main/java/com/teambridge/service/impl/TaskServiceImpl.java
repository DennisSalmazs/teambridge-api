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
import com.teambridge.service.KeycloakService;
import com.teambridge.service.ProjectService;
import com.teambridge.service.TaskService;
import com.teambridge.service.UserService;
import org.springframework.context.annotation.Lazy;
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
    private final ProjectService projectService;
    private final KeycloakService keycloakService;

    public TaskServiceImpl(TaskRepository taskRepository, MapperUtil mapperUtil, UserService userService, @Lazy ProjectService projectService, KeycloakService keycloakService) {
        this.taskRepository = taskRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.projectService = projectService;
        this.keycloakService = keycloakService;
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

        task.setAssignedEmployee(userService.findByUserName(task.getAssignedEmployee().getUserName()));
        task.setProject(projectService.findByProjectCode(task.getProject().getProjectCode()));

        Task convertedTask = mapperUtil.convert(task, Task.class);
        taskRepository.save(convertedTask);
    }

    @Override
    public void update(String taskCode, TaskDTO task) {
        // old task information
        Task foundTask = taskRepository.findByTaskCode(taskCode);

        Task convertedTask = mapperUtil.convert(task, Task.class);
        if (foundTask!=null){
            convertedTask.setAssignedDate(foundTask.getAssignedDate());
            convertedTask.setTaskStatus(task.getTaskStatus() == null ? foundTask.getTaskStatus() : task.getTaskStatus());
            convertedTask.setTaskCode(taskCode);
            convertedTask.setId(foundTask.getId());

            convertedTask.setAssignedEmployee(mapperUtil.convert(task.getAssignedEmployee().getUserName(), User.class));
            convertedTask.setProject(mapperUtil.convert(projectService.findByProjectCode(task.getProject().getProjectCode()), Project.class));

            taskRepository.save(convertedTask);
        }
    }

    @Override
    public void delete(String taskCode) {
       Task foundTask = taskRepository.findByTaskCode(taskCode);
       if (foundTask!=null){
           foundTask.setIsDeleted(true);
           taskRepository.save(foundTask);
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
       tasksToDelete.forEach(task -> delete(task.getTaskCode()));
    }

    @Override
    public void completeByProject(ProjectDTO project) {
        List<Task> tasksToComplete = taskRepository.findAllByProject(mapperUtil.convert(project, Project.class));
        tasksToComplete.forEach(task -> {
            TaskDTO taskDTO = mapperUtil.convert(task, TaskDTO.class);
            taskDTO.setTaskStatus(Status.COMPLETE);
            //update status in DB
            update(taskDTO.getTaskCode(), taskDTO);
        });
    }

    // list all the tasks that belong to current logged-in user, for status is not
    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {

        UserDTO loggedInUser = userService.findByUserName(keycloakService.getLoggedInUserName());

        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, mapperUtil.convert(loggedInUser, User.class));

        return tasks.stream().
                map(task -> mapperUtil.convert(task, TaskDTO.class)).
                collect(Collectors.toList());
    }

    // list all the tasks that belong to current logged-in user, for status is
    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {

        UserDTO loggedInUser = userService.findByUserName(keycloakService.getLoggedInUserName());

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

    @Override
    public TaskDTO findByTaskCode(String taskCode) {
        Task task = taskRepository.findByTaskCode(taskCode);
        return mapperUtil.convert(task, TaskDTO.class);
    }
}
