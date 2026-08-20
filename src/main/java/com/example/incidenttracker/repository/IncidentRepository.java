package com.example.incidenttracker.repository;

import com.example.incidenttracker.model.Incident;
import com.example.incidenttracker.model.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByProjectId(Long projectId);

    List<Incident> findByStatus(IncidentStatus status);
}
