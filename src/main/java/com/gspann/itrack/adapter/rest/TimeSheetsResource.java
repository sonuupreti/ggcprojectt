package com.gspann.itrack.adapter.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gspann.itrack.adapter.rest.error.BadRequestAlertException;
import com.gspann.itrack.adapter.rest.util.HeaderUtil;
import com.gspann.itrack.adapter.rest.util.PaginationUtil;
import com.gspann.itrack.domain.model.common.dto.AccountDTO;
import com.gspann.itrack.domain.model.common.dto.TimeSheetDTO;
import com.gspann.itrack.domain.model.common.dto.TimeSheetSubmissionPageVM;
import com.gspann.itrack.domain.service.api.AccountManagementService;
import com.gspann.itrack.domain.service.api.TimesheetManagementService;

import io.github.jhipster.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing Time sheets.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TimeSheetsResource {

    private final TimesheetManagementService timesheetManagementService;

    public TimeSheetsResource(final TimesheetManagementService timesheetManagementService) {
        this.timesheetManagementService = timesheetManagementService;
    }

    
    @GetMapping("/initSubmission")
    @Timed
    public TimeSheetSubmissionPageVM initTimesheetSubmission(final Principal principal) {
        log.debug("REST request to getTimeSheetSubmissionPageVM() ------>>>");
		String manojResCode = "20001";
        return timesheetManagementService.getTimeSheetSubmissionPageVM(manojResCode);
    }
    /*public ResponseEntity<TimeSheetSubmissionPageVM> getTimeSheetSubmissionPageVM(HttpServletRequest request) {
        log.debug("REST request to getTimeSheetSubmissionPageVM() ------>>>");
		String manojResCode = "20001";
        return new ResponseEntity<TimeSheetSubmissionPageVM>(timesheetManagementService.getTimeSheetSubmissionPageVM(manojResCode), HttpStatus.OK);
    }*/
    
    @PostMapping("/timesheets")
    @Timed
    public ResponseEntity<TimeSheetDTO> createTimeSheet(@Valid @RequestBody TimeSheetDTO timesheet) throws URISyntaxException {
		String manojResCode = "20001";
		timesheet.setResourceCode(manojResCode);
        log.debug("REST request to  create TimeSheet : {}", timesheet);
        System.out.println("input timeSheet -->" + timesheet);
        timesheetManagementService.createTimeSheet(timesheet);
        return new ResponseEntity<TimeSheetDTO>(timesheet, HttpStatus.OK);
//        return ResponseEntity.created(new URI("/api/accounts/" + accountDTO1.getAccountCode()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, accountDTO1.getAccountCode()))
//            .body(accountDTO1);
    }

    /**
     * PUT  /accounts : Updates an existing accounts.
     *
     * @param accountsDTO the accountsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountsDTO,
     * or with status 400 (Bad Request) if the accountsDTO is not valid,
     * or with status 500 (Internal Server Error) if the accountsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timesheets")
    @Timed
    public ResponseEntity<TimeSheetDTO> updateTimesheet(@Valid @RequestBody TimeSheetDTO timesheet) throws URISyntaxException {
        log.debug("REST request to update timesheet : {}", timesheet);
        

        return new ResponseEntity<TimeSheetDTO>(timesheet, HttpStatus.OK);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountDTO1.getAccountCode()))
//            .body(accountDTO1);
    }

    /**
     * GET  /accounts : get all the accounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of accounts in body
     */
    @GetMapping("/timesheets")
    @Timed
    public ResponseEntity<List<AccountDTO>> listTimesheets(Pageable pageable) {
        log.debug("REST request to get a page of Account");
//        Page<AccountDTO> page = accountsManagementService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accounts");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        
        return null;
    }

//    @GetMapping("/accounts/{id}")
//    @Timed
//    public ResponseEntity<> getTimesheet(@PathVariable long id) {
//        log.debug("REST request to get Accounts : {}", id);
//        AccountDTO accountDTO = accountsManagementService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDTO));
//        
//    }
}
