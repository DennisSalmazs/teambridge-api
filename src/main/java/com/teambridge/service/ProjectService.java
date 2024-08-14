package com.teambridge.service;

import com.teambridge.dto.ProjectDTO;
import com.teambridge.dto.UserDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO findByProjectCode(String projectCode);
    List<ProjectDTO> listAllProjects();
    void save(ProjectDTO project);
    void update(ProjectDTO project);
    void delete(String project);
    void complete(String project);
    List<ProjectDTO> listAllProjectsDetails();
    List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO assignedManager);
}
