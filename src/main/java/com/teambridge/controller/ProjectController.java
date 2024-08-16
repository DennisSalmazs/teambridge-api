package com.teambridge.controller;

import com.teambridge.dto.ProjectDTO;
import com.teambridge.dto.ResponseWrapper;
import com.teambridge.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Project Controller", description = "Project API")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @Operation(summary = "Get Projects")
    public ResponseEntity<ResponseWrapper> getProjects() {
        List<ProjectDTO> projectDTOList = projectService.listAllProjects();
        return ResponseEntity
                .ok(new ResponseWrapper("Projects are successfully retrieved", projectDTOList));
    }

    @GetMapping("/{projectCode}")
    @Operation(summary = "Get Project By Project Code")
    public ResponseEntity<ResponseWrapper> getProject(@PathVariable String projectCode) {
        ProjectDTO projectDTO = projectService.findByProjectCode(projectCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Project is successfully retrieved", projectDTO));
    }

    @PostMapping
//    @RolesAllowed({"Admin","Manager"})
    @Operation(summary = "Create Project")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody @Valid ProjectDTO projectDTO) {
        projectService.save(projectDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Project successfully created", 201));
    }

    @PutMapping("/{projectCode}")
    @Operation(summary = "Update Project")
    public ResponseEntity<ResponseWrapper> updateProject(@PathVariable String projectCode, @RequestBody ProjectDTO project) {
        projectService.update(projectCode, project);
        return ResponseEntity
                .ok(new ResponseWrapper("Project successfully updated"));
    }

    @DeleteMapping("/{projectCode}")
    @Operation(summary = "Delete Project")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable String projectCode) {
        projectService.delete(projectCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Project successfully deleted"));
    }

    @GetMapping("/manager/project-status")
    @Operation(summary = "Get Projects By Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager() {
        List<ProjectDTO> projectDTOList = projectService.listAllProjectsDetails();
        return ResponseEntity
                .ok(new ResponseWrapper("Projects are successfully retrieved", projectDTOList));
    }

    @PutMapping("/manager/complete/{projectCode}")
    @Operation(summary = "Complete Project By Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable String projectCode) {
        projectService.complete(projectCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Project successfully completed"));
    }
}
