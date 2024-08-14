package com.teambridge.service;

import com.teambridge.dto.ProjectDTO;
import com.teambridge.dto.TaskDTO;
import com.teambridge.dto.UserDTO;
import com.teambridge.enums.Status;

import java.util.List;

public interface TaskService {

    TaskDTO findById(Long id);
    List<TaskDTO> listAllTasks();
    void save(TaskDTO taskDTO);
    void update(TaskDTO taskDTO);
    void delete(Long id);
    int totalNonCompletedTasks(String projectCode);
    int totalCompletedTasks(String projectCode);
    void deleteByProject(ProjectDTO project);
    void completeByProject(ProjectDTO project);
    List<TaskDTO> listAllTasksByStatusIsNot(Status status);
    List<TaskDTO> listAllTasksByStatus(Status status);
    List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO employee);
}
