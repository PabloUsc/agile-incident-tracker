package com.example.incidenttracker.service;

import com.example.incidenttracker.dto.IncidentRequestDto;
import com.example.incidenttracker.dto.IncidentResponseDto;
import com.example.incidenttracker.exception.ResourceNotFoundException;
import com.example.incidenttracker.model.*;
import com.example.incidenttracker.repository.IncidentRepository;
import com.example.incidenttracker.repository.ProjectRepository;
import com.example.incidenttracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IncidentService incidentService;

    private Project mockProject;
    private User mockUser;
    private Incident mockIncident;
    private IncidentRequestDto mockRequestDto;

    @BeforeEach
    void setUp() {
        //Initial test data before any test
        mockProject = Project.builder()
                .id(1L)
                .name("Mairimashita School Platform")
                .projectKey("MAIRU")
                .build();

        mockUser = User.builder()
                .id(1L)
                .username("suziru")
                .fullName("Suzuki Iruma")
                .build();

        mockIncident = Incident.builder()
                .id(100L)
                .title("Fix Assignment Bug")
                .description("Assignment cannot be created")
                .type(IncidentType.BUG)
                .status(IncidentStatus.OPEN)
                .priority(IncidentPriority.HIGH)
                .project(mockProject)
                .assignee(mockUser)
                .createdAt(LocalDateTime.now())
                .build();

        mockRequestDto = new IncidentRequestDto();
        mockRequestDto.setTitle("Fix Assignment Bug");
        mockRequestDto.setDescription("Assignment cannot be created");
        mockRequestDto.setType(IncidentType.BUG);
        mockRequestDto.setPriority(IncidentPriority.HIGH);
        mockRequestDto.setProjectId(1L);
        mockRequestDto.setAssigneeId(1L);
    }

    @Test
    @DisplayName("Should succesfully create an incident when project and user exist")
    void createIncident_Success() {
        //GIVEN
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(incidentRepository.save(any(Incident.class))).thenReturn(mockIncident);

        //WHEN
        IncidentResponseDto response = incidentService.createIncident(mockRequestDto);

        //THEN
        assertNotNull(response);
        assertEquals("Fix Assignment Bug",response.getTitle());
        assertEquals(IncidentStatus.OPEN,response.getStatus());
        assertEquals("Mairimashita School Platform",response.getProjectName());
        assertEquals("Suzuki Iruma",response.getAssigneeName());

        //Verify save action was called only once
        verify(incidentRepository, times(1)).save(any(Incident.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when Project ID does not exist")
    void createIncident_ProjectNotFound_ThrowsException() {
        //GIVEN
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN and THEN
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> incidentService.createIncident(mockRequestDto)
        );
        assertEquals("Project not found with id: 1", exception.getMessage());
        //Verify no save was attempted
        verify(incidentRepository, never()).save(any(Incident.class));
    }

    @Test
    @DisplayName("Should return incident by ID when it exists")
    void getIncidentById_Success() {
        //GIVEN
        when(incidentRepository.findById(100L)).thenReturn(Optional.of(mockIncident));

        //WHEN
        IncidentResponseDto response = incidentService.getIncidentById(100L);

        //THEN
        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("Fix Assignment Bug", response.getTitle());
    }

    @Test
    @DisplayName("Should update incident status succesfully")
    void updateStatus_Success() {
        //GIVEN
        when(incidentRepository.findById(100L)).thenReturn(Optional.of(mockIncident));
        when(incidentRepository.save(any(Incident.class))).thenReturn(mockIncident);

        //WHEN
        IncidentResponseDto response = incidentService.updateStatus(100L,IncidentStatus.IN_PROGRESS);

        //THEN
        assertNotNull(response);
        assertEquals(IncidentStatus.IN_PROGRESS,response.getStatus());
        //Verify save attempt was done only once
        verify(incidentRepository, times(1)).save(mockIncident);
    }
}
