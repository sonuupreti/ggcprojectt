package com.gspann.itrack.adapter.rest;


import io.github.jhipster.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.gspann.itrack.adapter.rest.error.BadRequestAlertException;
import com.gspann.itrack.adapter.rest.util.HeaderUtil;
import com.gspann.itrack.adapter.rest.util.PaginationUtil;
import com.gspann.itrack.domain.model.common.dto.AddAccountPageVM;
import com.gspann.itrack.domain.model.common.dto.AddProjectPageVM;
import com.gspann.itrack.domain.model.common.dto.ProjectDTO;
import com.gspann.itrack.domain.service.api.ProjectManagementService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Projects.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ProjectResource {

    

    private static final String ENTITY_NAME = "project";

    private final ProjectManagementService projectManagementService;

    public ProjectResource(ProjectManagementService projectManagementService) {
        this.projectManagementService = projectManagementService;
    }
    
    
    
    @GetMapping("/initAddProject")
    @Timed
    public AddProjectPageVM initAddAccountPage(final Principal principal) {
        log.debug("REST request to getAddProjectPageVM() ------>>>");
        return projectManagementService.getAddProjectPageVM();
        
        
        
    }
    
    

    /**
     * POST  /projects : Create a new projects.
     *
     * @param projectsDTO the projectsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectsDTO, or with status 400 (Bad Request) if the projects has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projects")
    @Timed
    public ResponseEntity<ProjectDTO> createProjects(@Valid @RequestBody ProjectDTO projectDTO) throws URISyntaxException {
        log.debug("REST request to save Projects : {}", projectDTO);
        if (projectDTO.getProjectCode() != null) {
            throw new BadRequestAlertException("A new projects cannot already have an Code", ENTITY_NAME, "idexists");
        }
        ProjectDTO result = projectManagementService.addProject(projectDTO);
        return ResponseEntity.created(new URI("/api/projects/" + result.getProjectCode()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getProjectCode()))
            .body(result);
    }

    /**
     * PUT  /projects : Updates an existing projects.
     *
     * @param projectsDTO the projectsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectsDTO,
     * or with status 400 (Bad Request) if the projectsDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projects")
    @Timed
    public ResponseEntity<ProjectDTO> updateProject(@Valid @RequestBody ProjectDTO projectDTO) throws URISyntaxException {
        log.debug("REST request to update Projects : {}", projectDTO);
        if (projectDTO.getProjectCode() == null) {
            return createProjects(projectDTO);
        }
        ProjectDTO result = projectManagementService.updateProject(projectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectDTO.getProjectCode()))
            .body(result);
    }

    /**
     * GET  /projects : get all the projects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     */
    @GetMapping("/projects")
    @Timed
    public ResponseEntity<List<ProjectDTO>> getAllProjects(Pageable pageable) {
        log.debug("REST request to get a page of Projects");
        Page<ProjectDTO> page = projectManagementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projects/:id : get the "id" projects.
     *
     * @param id the id of the projectsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/projects/{id}")
    @Timed
    public ResponseEntity<ProjectDTO> getProjects(@PathVariable String id) {
        log.debug("REST request to get Projects : {}", id);
        ProjectDTO projectDTO = projectManagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectDTO));
    }

    /**
     * DELETE  /projects/:id : delete the "id" projects.
     *
     * @param id the id of the projectsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        log.debug("REST request to delete Projects : {}", id);
        projectManagementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
