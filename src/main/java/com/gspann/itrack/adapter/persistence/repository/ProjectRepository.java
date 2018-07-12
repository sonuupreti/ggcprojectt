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
public interface ProjectRepository extends JpaRepository<Project, String>, ProjectRepositoryJPA {

	public List<Project> findByTypeCode(String typeCode);
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(p.code, p.name) FROM Project p where p.code = :code") 
    Pair<String, String> findProjectCodeAndName(@Param("code") String code);
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(p.code, p.name) FROM Project p where p.offshoreManager.code = :approverCode") 
    List<Pair<String, String>> findAllProjectCodeAndNameByOffshoreManagerCode(@Param("approverCode") String approverCode);

	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(p.code, p.name) FROM Project p") 
    List<Pair<String, String>> findAllProjectCodeAndNameBy();
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(pt.code, pt.description) FROM ProjectType pt") 
    List<Pair<String, String>> findAllProjectTypeCodeAndDescription();
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(ps.code, ps.description) FROM ProjectStatus ps") 
    List<Pair<String, String>> findAllProjectStatusCodeAndDescription();
	
	@Query("SELECT new com.gspann.itrack.domain.model.common.dto.Pair(ps.code, ps.name) FROM Practice ps") 
    List<Pair<String, String>> findAllPracticeCodeAndDescription();
}
