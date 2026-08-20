package com.example.incidenttracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.incidenttracker.dto.IncidentRequestDto;
import com.example.incidenttracker.dto.IncidentResponseDto;
import com.example.incidenttracker.exception.ResourceNotFoundException;
import com.example.incidenttracker.model.Incident;
import com.example.incidenttracker.model.IncidentStatus;
import com.example.incidenttracker.model.Project;
import com.example.incidenttracker.model.User;
import com.example.incidenttracker.repository.IncidentRepository;
import com.example.incidenttracker.repository.ProjectRepository;
import com.example.incidenttracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public IncidentResponseDto createIncident(IncidentRequestDto dto) {
        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + dto.getProjectId()));

        User assignee = null;
        if(dto.getAssigneeId() != null) {
            assignee = userRepository.findById(dto.getAssigneeId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getAssigneeId()));
        }

        Incident incident = Incident.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .type(dto.getType())
                .priority(dto.getPriority())
                .status(IncidentStatus.OPEN)
                .project(project)
                .assignee(assignee)
                .build();

        Incident savedIncident = incidentRepository.save(incident);
        return mapToDto(savedIncident);
    }

    @Transactional(readOnly = true)
    public List<IncidentResponseDto> getAllIncidents() {
        return incidentRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IncidentResponseDto getIncidentById(Long id) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
        return mapToDto(incident);
    }

    @Transactional
    public IncidentResponseDto updateStatus(Long id, IncidentStatus status) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
        incident.setStatus(status);
        Incident updatedIncident = incidentRepository.save(incident);
        return mapToDto(updatedIncident);
    }

    @Transactional(readOnly = true)
    public List<IncidentResponseDto> getIncidentsByProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project was not found with id: " + projectId);
        }
        return incidentRepository.findByProjectId(projectId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //Private helper to map Entity to DTO
    private IncidentResponseDto mapToDto(Incident incident) {
        return IncidentResponseDto.builder()
                .id(incident.getId())
                .title(incident.getTitle())
                .description(incident.getDescription())
                .type(incident.getType())
                .status(incident.getStatus())
                .priority(incident.getPriority())
                .projectName(incident.getProject().getName())
                .assigneeName(incident.getAssignee() != null ? incident.getAssignee().getFullName() : "Unassigned")
                .createdAt(incident.getCreatedAt())
                .build();
    }
}
