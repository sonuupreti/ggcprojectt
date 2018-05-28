package com.gspann.itrack.domain.service.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gspann.itrack.domain.model.common.dto.AddProjectPageVM;
import com.gspann.itrack.domain.model.common.dto.ProjectDTO;


public interface ProjectManagementService {


	
	public void assignOffShoreManager();

	public void assignOnShoreManager();
	
	public void allocateResource();

	public void allocateResources();

	public void scheduleAllocation();
	
	
	
	
	/**
     * save a project.
     *
     * @param projectDTO the entity to save
     * @return the persisted entity
     */
	public ProjectDTO addProject(ProjectDTO projectDTO);

	
    /**
     * Get all the projects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProjectDTO> findAll(Pageable pageable);

    /**
     * Get the "id" project.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProjectDTO findOne(String id);

    /**
     * Delete the "id" project.
     *
     * @param id the id of the entity
     */
    void delete(String id);
    
    /**
     * update a project.
     *
     * @param projectDTO the entity to save
     * @return the persisted entity
     */

	public ProjectDTO updateProject(@Valid ProjectDTO projectDTO);

	public AddProjectPageVM getAddProjectPageVM();
}
