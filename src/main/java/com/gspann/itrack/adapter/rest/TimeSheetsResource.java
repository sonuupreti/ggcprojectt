package com.gspann.itrack.adapter.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gspann.itrack.adapter.rest.util.HeaderUtil;
import com.gspann.itrack.domain.model.common.dto.AccountDTO;
import com.gspann.itrack.domain.model.common.dto.DayDTO;
import com.gspann.itrack.domain.model.common.dto.TimeEntryDTO;
import com.gspann.itrack.domain.model.common.dto.TimeSheetDTO;
import com.gspann.itrack.domain.model.common.dto.TimeSheetMetaDataVM;
import com.gspann.itrack.domain.model.common.dto.TimeSheetStatusType;
import com.gspann.itrack.domain.model.common.dto.TimeSheetVM;
import com.gspann.itrack.domain.model.common.dto.TimeSheetActionType;
import com.gspann.itrack.domain.model.common.dto.WeekDTO;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.service.api.TimesheetManagementService;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing Time sheets.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TimeSheetsResource {

	private static final String ENTITY_NAME = "timesheet";

	private final TimesheetManagementService timesheetManagementService;

	public TimeSheetsResource(final TimesheetManagementService timesheetManagementService) {
		this.timesheetManagementService = timesheetManagementService;
	}

	@GetMapping("/initSubmission/{resourceCode}")
	@Timed
	public ResponseEntity<TimeSheetMetaDataVM> initTimesheetSubmission(@PathVariable String resourceCode, final Principal principal) {
		log.debug("REST request to getTimeSheetSubmissionPageVM() ------>>>");
//		String manojResCode = "20001";
		return new ResponseEntity<TimeSheetMetaDataVM>(timesheetManagementService.getTimeSheetMetaData(resourceCode),
				HttpStatus.OK);
	}
	/*
	 * public ResponseEntity<TimeSheetSubmissionPageVM>
	 * getTimeSheetSubmissionPageVM(HttpServletRequest request) {
	 * log.debug("REST request to getTimeSheetSubmissionPageVM() ------>>>"); String
	 * manojResCode = "20001"; return new
	 * ResponseEntity<TimeSheetSubmissionPageVM>(timesheetManagementService.
	 * getTimeSheetSubmissionPageVM(manojResCode), HttpStatus.OK); }
	 */

	@PostMapping("/timesheets")
	@Timed
	public ResponseEntity<TimeSheetVM> saveOrSubmitTimeSheet(@Valid @RequestBody TimeSheetDTO timesheet)
			throws URISyntaxException {
		log.debug("REST request to  create TimeSheet : {}", timesheet);
		System.out.println("input timeSheet -->" + timesheet);
		for(var v: timesheet.getWeek().getDailyEntries()) {
			System.out.println(v.getDate());
		}
		TimeSheetMetaDataVM timeSheetMetaDataVM = timesheetManagementService.getTimeSheetMetaData(timesheet.getResourceCode());
		
		Optional<WeeklyTimeSheet> weeklyTimeSheet = timesheetManagementService.saveOrSubmitTimeSheet(timesheet, timeSheetMetaDataVM.getResourceAllocationSummary().getProjects());
		if (weeklyTimeSheet.isPresent()) {
			WeeklyTimeSheet createdTimesheet = weeklyTimeSheet.get();
			TimeSheetVM timeSheetVM = new TimeSheetVM();
			timeSheetVM.setWeeklyTimeSheetId(createdTimesheet.id());
			timeSheetVM.setStatus(createdTimesheet.status() == TimesheetStatus.SAVED ? TimeSheetStatusType.SAVED
					: TimeSheetStatusType.SUBMITTED);
			Set<DayDTO> dailyEntries = new TreeSet<>((x, y) -> x.getDate().compareTo(y.getDate()));
			
			for (var dailyEntry : createdTimesheet.dailyTimeSheets()) {
				Set<TimeEntryDTO> timeEntries = new LinkedHashSet<>(5);
				for (var entry : dailyEntry.entries()) {
					timeEntries.add(
							TimeEntryDTO.of(entry.project().code(), (int) entry.hours.toHours(), entry.comments()));
				}
				dailyEntries.add(DayDTO.of(dailyEntry.date(), dailyEntry.dailyComments(), timeEntries));
			}
			WeekDTO weekDTO = WeekDTO.of(createdTimesheet.week().startingFrom(), dailyEntries);
			timeSheetVM.setWeekDetails(weekDTO);
			
			timeSheetMetaDataVM.setActions(createdTimesheet.status() == TimesheetStatus.SUBMITTED
					? new TimeSheetActionType[] { TimeSheetActionType.NONE }
					: new TimeSheetActionType[] { TimeSheetActionType.SUBMIT });
			timeSheetVM.setTimesheetMetaData(timeSheetMetaDataVM);
			return ResponseEntity.created(new URI("/api/timesheets/" + timeSheetVM.getWeeklyTimeSheetId()))
					.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "" + timeSheetVM.getWeeklyTimeSheetId()))
					.body(timeSheetVM);
		} else {
			return null;
		}
	}

	/**
	 * PUT /accounts : Updates an existing accounts.
	 *
	 * @param accountsDTO
	 *            the accountsDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         accountsDTO, or with status 400 (Bad Request) if the accountsDTO is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         accountsDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/timesheets")
	@Timed
	public ResponseEntity<TimeSheetDTO> updateTimesheet(@Valid @RequestBody TimeSheetDTO timesheet)
			throws URISyntaxException {
		log.debug("REST request to update timesheet : {}", timesheet);

		return new ResponseEntity<TimeSheetDTO>(timesheet, HttpStatus.OK);
		// return ResponseEntity.ok()
		// .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
		// accountDTO1.getAccountCode()))
		// .body(accountDTO1);
	}

	/**
	 * GET /accounts : get all the accounts.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of accounts in
	 *         body
	 */
	@GetMapping("/timesheets")
	@Timed
	public ResponseEntity<List<AccountDTO>> listTimesheets(Pageable pageable) {
		log.debug("REST request to get a page of Account");
		// Page<AccountDTO> page = accountsManagementService.findAll(pageable);
		// HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
		// "/api/accounts");
		// return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

		return null;
	}

	// @GetMapping("/accounts/{id}")
	// @Timed
	// public ResponseEntity<> getTimesheet(@PathVariable long id) {
	// log.debug("REST request to get Accounts : {}", id);
	// AccountDTO accountDTO = accountsManagementService.findOne(id);
	// return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDTO));
	//
	// }
}
