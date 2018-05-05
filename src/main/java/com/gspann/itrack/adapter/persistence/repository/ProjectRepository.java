package com.gspann.itrack.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gspann.itrack.domain.model.projects.Project;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ProjectRepository extends JpaRepository<Project, String> {
}
