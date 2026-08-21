package com.example.incidenttracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Project key is required")
    @Size(min = 2, max = 10, message = "Key must be between 2 and 10 characters")
    private String projectKey;

    private String description;
}
