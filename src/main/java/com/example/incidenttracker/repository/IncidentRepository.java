package com.example.incidenttracker.repository;

import com.example.incidenttracker.model.Incident;
import com.example.incidenttracker.model.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByProjectId(Long projectId);

    List<Incident> findByAssigneeId(Long userId);

    @Modifying
    @Query("DELETE FROM Incident i WHERE i.project.id = :projectId")
    void deleteByProjectId(@Param("projectId") Long projectId);
}
