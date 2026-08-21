package com.example.incidenttracker.controller;

import com.example.incidenttracker.dto.ProjectRequestDto;
import com.example.incidenttracker.dto.ProjectResponseDto;
import com.example.incidenttracker.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    //GET /api/v1/projects -> get all projects
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    //POST /api/v1/projects -> create new project
    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto response = projectService.createProject(projectRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //DELETE /api/v1/projects/{id} -> delete project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }

    //GET /api/v1/projects/{id} -> get project by id
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    //GET /api/v1/projects/project/{projectKey} -> get project by project key
    @GetMapping("/project/{projectKey}")
    public ResponseEntity<ProjectResponseDto> getProjectByProjectKey(@PathVariable String projectKey) {
        return ResponseEntity.ok(projectService.getProjectByProjectKey(projectKey));
    }
}
