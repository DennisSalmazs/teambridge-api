package com.teambridge.controller;

import com.teambridge.dto.ProjectDTO;
import com.teambridge.dto.ResponseWrapper;
import com.teambridge.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getProjects() {
        List<ProjectDTO> projectDTOList = projectService.listAllProjects();
        return ResponseEntity
                .ok(new ResponseWrapper("Projects are successfully retrieved", projectDTOList));
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> getProject(@PathVariable String projectCode) {
        ProjectDTO projectDTO = projectService.findByProjectCode(projectCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Project is successfully retrieved", projectDTO));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO) {
        projectService.save(projectDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Project successfully created", 201));
    }

    @PutMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> updateProjectCode(@PathVariable String projectCode, @RequestBody ProjectDTO project) {
        projectService.update(projectCode, project);
        return ResponseEntity
                .ok(new ResponseWrapper("Project successfully updated"));
    }
}
