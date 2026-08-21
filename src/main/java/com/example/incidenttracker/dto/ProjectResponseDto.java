package com.example.incidenttracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String projectKey;
    private String description;
}
