package com.example.incidenttracker.dto;

import com.example.incidenttracker.model.IncidentPriority;
import com.example.incidenttracker.model.IncidentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IncidentRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Incident type is required")
    private IncidentType type;

    @NotNull(message = "Priority is required")
    private IncidentPriority priority;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assigneeId;
}
