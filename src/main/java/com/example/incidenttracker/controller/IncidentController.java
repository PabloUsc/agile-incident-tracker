package com.example.incidenttracker.controller;

import com.example.incidenttracker.dto.IncidentRequestDto;
import com.example.incidenttracker.dto.IncidentResponseDto;
import com.example.incidenttracker.model.IncidentPriority;
import com.example.incidenttracker.model.IncidentStatus;
import com.example.incidenttracker.service.IncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    //POST /api/v1/incidents -> create new incident
    @PostMapping
    public ResponseEntity<IncidentResponseDto> createIncident(@Valid @RequestBody IncidentRequestDto dto) {
        IncidentResponseDto response = incidentService.createIncident(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //GET /api/v1/incidents -> get all incidents
    @GetMapping
    public ResponseEntity<List<IncidentResponseDto>> getAllIncidents() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    //DELETE /api/v1/incidents/{id} -> delete incident
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }

    //GET /api/v1/incidents/{id} -> get incident by id
    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponseDto> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.getIncidentById(id));
    }

    //PATCH /api/v1/incidents/{id}/status -> change status of incident
    @PatchMapping("/{id}/status")
    public ResponseEntity<IncidentResponseDto> updateStatus(@PathVariable Long id, @RequestParam IncidentStatus status) {
        return ResponseEntity.ok(incidentService.updateStatus(id,status));
    }

    //GET /api/v1/incidents/project/{projectId} -> get incidents of a project
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<IncidentResponseDto>> getIncidentsByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(incidentService.getIncidentsByProject(projectId));
    }

    //PATCH /api/v1/incidents/{id}/assignee -> change assignee of incident
    @PatchMapping("/{id}/assignee")
    public ResponseEntity<IncidentResponseDto> assignIncident(@PathVariable Long id, @RequestParam Long assigneeId) {
        return ResponseEntity.ok(incidentService.updateAssignee(id,assigneeId));
    }

    //PATCH /api/v1/incidents/{id}/priority -> change incident priority
    @PatchMapping("/{id}/priority")
    public ResponseEntity<IncidentResponseDto> updateIncidentPriority(@PathVariable Long id, @RequestParam IncidentPriority priority) {
        return ResponseEntity.ok(incidentService.updatePriority(id, priority));
    }
}
