package com.example.incidenttracker.dto;

import com.example.incidenttracker.model.IncidentPriority;
import com.example.incidenttracker.model.IncidentStatus;
import com.example.incidenttracker.model.IncidentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class IncidentResponseDto {
    private Long id;
    private String title;
    private String description;
    private IncidentType type;
    private IncidentStatus status;
    private IncidentPriority priority;
    private String projectName;
    private String assigneeName;
    private LocalDateTime createdAt;
}
