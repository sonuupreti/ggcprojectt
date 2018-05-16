package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.projects.Project;

/**
 * Spring Data JPA repository for the Project entity.
 */
public interface ProjectRepository extends JpaRepository<Project, String>, ProjectRepositorySpec {

	public List<Project> findByTypeCode(String typeCode);
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(p.code, p.name) FROM Project p where p.code = :code") 
    Pair<String, String> findCodeAndName(@Param("code") String code);
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(p.code, p.name) FROM Project p") 
    List<Pair<String, String>> findAllCodeAndName();
}
