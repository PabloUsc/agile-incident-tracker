package com.example.incidenttracker.repository;

import com.example.incidenttracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectKey(String projectKey);
    boolean existsByProjectKey(String projectKey);
}
