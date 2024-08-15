package com.teambridge.service.impl;

import com.teambridge.dto.ProjectDTO;
import com.teambridge.dto.UserDTO;
import com.teambridge.entity.Project;
import com.teambridge.entity.User;
import com.teambridge.enums.Status;
import com.teambridge.mapper.MapperUtil;
import com.teambridge.repository.ProjectRepository;
import com.teambridge.service.ProjectService;
import com.teambridge.service.TaskService;
import com.teambridge.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, MapperUtil mapperUtil, UserService userService, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.taskService = taskService;
    }


    @Override
    public ProjectDTO findByProjectCode(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        return mapperUtil.convert(project, ProjectDTO.class);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> list = projectRepository.findAll(Sort.by("projectCode"));
        return list.stream().
                map(project -> mapperUtil.convert(project, ProjectDTO.class)).
                collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO project) {
        project.setProjectStatus(Status.OPEN);
        project.setAssignedManager(userService.findByUserName(project.getAssignedManager().getUserName()));
        Project convertedProject = mapperUtil.convert(project, Project.class);
        projectRepository.save(convertedProject);

    }

    @Override
    public void update(String projectCode, ProjectDTO project) {
        Project foundProject = projectRepository.findByProjectCode(projectCode);

        Project convertedProject = mapperUtil.convert(project, Project.class);
        convertedProject.setId(foundProject.getId());
        convertedProject.setProjectStatus(foundProject.getProjectStatus());
        convertedProject.setProjectCode(projectCode);

        User assignedManager = mapperUtil.convert(userService.findByUserName(project.getAssignedManager().getUserName()), User.class);
        convertedProject.setAssignedManager(assignedManager);

        projectRepository.save(convertedProject);
    }

    @Override
    public void delete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setIsDeleted(true);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId()); // SP00-1 ==> so that we can reuse same projectCode
        projectRepository.save(project);

        //once project is deleted, the tasks belonging to that project should also be deleted
        taskService.deleteByProject(mapperUtil.convert(project, ProjectDTO.class));
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        //once project is completed, the tasks belonging to that project should also be completed
        taskService.completeByProject(mapperUtil.convert(project, ProjectDTO.class));
    }

    // list all the projects that belong to current logged-in user
    @Override
    public List<ProjectDTO> listAllProjectsDetails() {

        UserDTO currentUser = userService.findByUserName("harold@manager.com");
        User user = mapperUtil.convert(currentUser, User.class);

        List<Project> list = projectRepository.findAllByAssignedManager(user);

       return list.stream().map(project -> {
            ProjectDTO dto = mapperUtil.convert(project, ProjectDTO.class);
            dto.setUnfinishedTaskCounts(taskService.totalNonCompletedTasks(project.getProjectCode()));
            dto.setCompleteTaskCounts(taskService.totalCompletedTasks(project.getProjectCode()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO assignedManager) {
        List<Project> projects = projectRepository.findAllByProjectStatusIsNotAndAssignedManager(Status.COMPLETE, mapperUtil.convert(assignedManager, User.class));

        return projects.stream().
                map(project -> mapperUtil.convert(project, ProjectDTO.class)).
                collect(Collectors.toList());
    }
}
