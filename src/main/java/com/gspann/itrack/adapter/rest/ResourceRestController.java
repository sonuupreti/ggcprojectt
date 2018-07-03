package com.gspann.itrack.adapter.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gspann.itrack.adapter.rest.error.BadRequestAlertException;
import com.gspann.itrack.adapter.rest.util.HeaderUtil;
import com.gspann.itrack.adapter.rest.util.PaginationUtil;
import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.common.dto.ResourceDTO;
import com.gspann.itrack.domain.model.common.dto.ResourceOnBoardingDTO;
import com.gspann.itrack.domain.model.common.dto.ResourceOnLoadVM;
import com.gspann.itrack.domain.model.common.dto.ResourceSearchDTO;
import com.gspann.itrack.domain.service.api.ResourceManagementService;

import io.github.jhipster.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class ResourceRestController {

    private static final String ENTITY_NAME = "resource";
    private final ResourceManagementService resourceManagementService;

    public ResourceRestController(ResourceManagementService resourceManagementService) {
        this.resourceManagementService = resourceManagementService;
    }

    @GetMapping("/initAddResource")
    @Timed
    public ResourceOnLoadVM initResourceOnLodPage(final Principal principal) {
        log.debug("<<<<<----- Resource Page onLoad ------>>>");

        return resourceManagementService.resourceOnLodPage();

    }

    @PostMapping("/resource")
    @Timed
    public ResponseEntity<ResourceDTO> createResource(@Valid @RequestBody ResourceDTO resourceDTO)
            throws URISyntaxException {
        log.debug("REST request to save Resource : {}", resourceDTO);
        if (resourceDTO.getResourceCode() != null) {
            throw new BadRequestAlertException("A new resource code cannot  have an already existed reesource ID",
                    ENTITY_NAME, "idexists");
        }
        ResourceDTO resourceDTO1 = resourceManagementService.addResource(resourceDTO);

        return ResponseEntity.created(new URI("/api/resource/" + resourceDTO1.getResourceCode()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, resourceDTO1.getResourceCode()))
                .body(resourceDTO1);
    }

    @GetMapping("/resource")
    @Timed
    public ResponseEntity<List<ResourceDTO>> getAllResources(Pageable pageable) {
        log.debug("REST request to get a page of Resource");
        Page<ResourceDTO> page = resourceManagementService.getAllResources(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resource");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/resource/search")
    @Timed
    public ResponseEntity<List<ResourceSearchDTO>> getAllResourcesBySearchParameter(
            @RequestParam("searchParam") String searchParam) {
        log.debug("START :: ResourceRestController :: getAllResourcesBySearchParameter ");
        List<ResourceSearchDTO> resourceSearchDTO = resourceManagementService
                .getAllResourcesBySearchParameter(searchParam);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resourceSearchDTO));
    }

    /**
     * This method is used to get Resource object by Id.
     * 
     * @param id
     * @return
     */
    @GetMapping("/resource/{id}")
    @Timed
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable String id) {
        log.debug("START :: ResourceRestController :: getResourceById ");
        ResourceDTO resourceDTO = resourceManagementService.findById(id);
        log.debug("END :: ResourceRestController :: getResourceById");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resourceDTO));
    }

    /**
     * 
     * @param resourceDTO
     * @return
     */
    @PutMapping("/resources")
    @Timed
    public ResponseEntity<ResourceDTO> updateResource(@Valid @RequestBody ResourceDTO resourceDTO) {
        log.debug("START :: ResourceRestController :: updateResource ");
        ResourceDTO updatedResourceDTO = resourceManagementService.updateResource(resourceDTO);

        log.debug("END :: ResourceRestController :: updateResource ");

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, updatedResourceDTO.getResourceCode()))
                .body(updatedResourceDTO);
    }
    
    /**
     * 
     * @param resourceDTO
     * @return
     */
    @PutMapping("/onBoarding")
    @Timed
    public ResponseEntity<ResourceOnBoardingDTO> processOnBoarding(@Valid @RequestBody ResourceOnBoardingDTO resourceOnBoardingDTO) {
        log.debug("START :: ResourceRestController :: processOnBoarding ");
        ResourceOnBoardingDTO updatedResourceDTO = resourceManagementService.processOnBoard(resourceOnBoardingDTO);
        log.debug("END :: ResourceRestController :: processOnBoarding ");

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, updatedResourceDTO.getResourceCode()))
                .body(updatedResourceDTO);
    }

}
