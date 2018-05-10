package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gspann.itrack.domain.model.projects.Project;

/**
 * Spring Data JPA repository for the Project entity.
 */
public interface ProjectRepository extends JpaRepository<Project, String>, ProjectRepositorySpec {

	public List<Project> findByTypeCode(String typeCode);
}
