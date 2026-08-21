package com.example.incidenttracker.service;

import com.example.incidenttracker.dto.ProjectRequestDto;
import com.example.incidenttracker.dto.ProjectResponseDto;
import com.example.incidenttracker.exception.ResourceNotFoundException;
import com.example.incidenttracker.model.Project;
import com.example.incidenttracker.repository.IncidentRepository;
import com.example.incidenttracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final IncidentRepository incidentRepository;

    @Transactional
    public ProjectResponseDto createProject(ProjectRequestDto dto) {
        if (projectRepository.existsByProjectKey(dto.getProjectKey())) {
            throw new ResourceNotFoundException("Project with key " + dto.getProjectKey() + " already exists");
        }
        Project project = Project.builder()
                .name(dto.getName())
                .projectKey(dto.getProjectKey().toUpperCase())
                .description(dto.getDescription())
                .build();
        Project createdProject = projectRepository.save(project);
        return mapToDto(createdProject);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
        return mapToDto(project);
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectByProjectKey(String projectKey) {
        Project project = projectRepository.findByProjectKey(projectKey)
                .orElseThrow(() -> new ResourceNotFoundException("Project with key " + projectKey + " not found"));
        return mapToDto(project);
    }

    @Transactional
    public void deleteProjectById(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project with id " + id + " not found");
        }

        //Delete incidents related to project
        incidentRepository.deleteByProjectId(id);

        projectRepository.deleteById(id);
    }

    //Private helper to map Entity to ProjectDTO
    private ProjectResponseDto mapToDto(Project project) {
        return ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .projectKey(project.getProjectKey())
                .description(project.getDescription() != null && !project.getDescription().isEmpty() ? project.getDescription() : "No description")
                .build();
    }
}
