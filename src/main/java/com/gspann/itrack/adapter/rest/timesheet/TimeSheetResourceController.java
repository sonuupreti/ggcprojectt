package com.gspann.itrack.adapter.rest.timesheet;

import static com.gspann.itrack.adapter.rest.timesheet.TimeSheetLinks.*;
import static com.gspann.itrack.common.constants.RestConstant.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gspann.itrack.adapter.rest.util.HeaderUtil;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.dto.TimeSheetDTO;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResource;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceList;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;
import com.gspann.itrack.domain.service.api.TimesheetManagementService;
import com.gspann.itrack.infra.config.ApplicationProperties;

import io.github.jhipster.web.util.ResponseUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing Time sheets.
 */
@RestController
@RequestMapping(TIMESHEET)
@ExposesResourceFor(TimeSheetResource.class)
@RequiredArgsConstructor
@Slf4j
public class TimeSheetResourceController {

	@NonNull
	private final TimesheetManagementService timesheetManagementService;

	@NonNull
	private final ApplicationProperties applicationProperties;

	@NonNull
	private final TimeSheetLinks timesheetLinks;

	// public TimeSheetResourceController(final TimesheetManagementService
	// timesheetManagementService,
	// final ApplicationProperties applicationProperties) {
	// this.timesheetManagementService = timesheetManagementService;
	// this.applicationProperties = applicationProperties;
	// }

	private Week getInputWeek(final Optional<LocalDate> date) {
		Week currentWeek = Week.current(applicationProperties.timeSheet().WEEK_START_DAY(),
				applicationProperties.timeSheet().WEEK_END_DAY());
		LocalDate systemStartDate = applicationProperties.timeSheet().SYSTEM_START_DATE();

		Week forWeek = null;
		if (date.isPresent()) {
			if (date.get().isAfter(currentWeek.endingOn()) || date.get().isBefore(systemStartDate)) {
				throw new IllegalArgumentException("input date path variable : " + date.get() + " must be between : "
						+ systemStartDate + " and " + currentWeek.endingOn() + ", both ends inclusive");
			} else {
				forWeek = Week.containingDate(date.get(), applicationProperties.timeSheet().WEEK_START_DAY(),
						applicationProperties.timeSheet().WEEK_END_DAY());
			}
		} else {
			forWeek = Week.current(applicationProperties.timeSheet().WEEK_START_DAY(),
					applicationProperties.timeSheet().WEEK_END_DAY());
		}
		return forWeek;
	}

	private void updateNextAndPreviousWeeks(final TimeSheetResource timeSheetResource) {
		Week forWeek = Week.of(timeSheetResource.getWeekDetails().getWeekStartDate(),
				timeSheetResource.getWeekDetails().getWeekEndDate());
		Map<Week, TimeSheetWeekStatusVM> nextAndPreviousWeekTimesheetStatuses = timesheetManagementService
				.getWeeklyStatusesForNextAndPreviousWeeks(timeSheetResource.getResource().getKey(), forWeek);
		timeSheetResource.setNextWeek(nextAndPreviousWeekTimesheetStatuses.get(forWeek.next()));
		timeSheetResource.setPreviousWeek(nextAndPreviousWeekTimesheetStatuses.get(forWeek.previous()));
	}

	@GetMapping(TIMESHEET_WEEKLY)
	@Timed
	public ResponseEntity<TimeSheetResource> weekly(
			@RequestParam(value = "date", required = false) final Optional<LocalDate> date, final Principal principal) {
		System.out.println("????? " + principal.getName());
		log.debug("REST request to getTimeSheetSubmissionPageVM() ------>>>");
		String resourceCode = principal.getName();
		Week forWeek = getInputWeek(date);
		Optional<TimeSheetResource> timeSheetResource = timesheetManagementService
				.getTimeSheetVMByResourceAndWeek(resourceCode, forWeek);

		if (timeSheetResource.isPresent()) {
			// TODO: Think about making the call parallel
			TimeSheetResource timesheet = timeSheetResource.get();
			updateNextAndPreviousWeeks(timesheet);
			try {
				timesheet.add(timesheetLinks.getLinks(timesheet));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<TimeSheetResource>(timeSheetResource.get(), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(TIMESHEET_WEEKLY + SLASH + PATH_VARIABLE_ID)
	@Timed
	public ResponseEntity<TimeSheetResource> weekly(@PathVariable final long id, final Principal principal) {
		log.debug("REST request to getTimeSheetSubmissionPageVM() ------>>>");
		String resourceCode = principal.getName();

		Optional<TimeSheetResource> timeSheetResource = timesheetManagementService.getTimeSheetVMByIdAndResourceCode(id,
				resourceCode);
		if (timeSheetResource.isPresent()) {
			// TODO: Think about making the call parallel
			TimeSheetResource timesheet = timeSheetResource.get();
			updateNextAndPreviousWeeks(timesheet);
			try {
				timesheet.add(timesheetLinks.getLinks(timesheet));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<TimeSheetResource>(timeSheetResource.get(), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@Timed
	public ResponseEntity<Void> saveOrSubmitTimeSheet(@Valid @RequestBody TimeSheetDTO timesheet,
			final Principal principal) throws URISyntaxException {
		log.debug("REST request to  create TimeSheet : {}", timesheet);
		System.out.println("input timeSheet -->" + timesheet);
		String resourceCode = principal.getName();

		Optional<WeeklyTimeSheet> weeklyTimeSheet = timesheetManagementService.saveOrSubmitTimeSheet(resourceCode,
				timesheet);
		if (weeklyTimeSheet.isPresent()) {
			return ResponseEntity.created(new URI(TIMESHEET + SLASH + weeklyTimeSheet.get().id()))
					.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "" + weeklyTimeSheet.get().id()))
					.build();
		} else {
			return null;
		}
	}

	@GetMapping(SLASH + PATH_VARIABLE_ID)
	@Timed
	public ResponseEntity<TimeSheetResource> getTimesheetById(@PathVariable long id, final Principal principal) {
		log.debug("REST request to getTimesheet : {}", id);
		String resourceCode = principal.getName();
		// TODO: Apply role here, only the timesheet owner resource or approver should
		// be able to view

		return ResponseUtil
				.wrapOrNotFound(timesheetManagementService.getTimeSheetVMByIdAndResourceCode(id, resourceCode));

	}

	@GetMapping(SLASH + TIMESHEET_RECENT)
	@Timed
	public ResponseEntity<TimeSheetResourceList> recentTimeSheets(final Principal principal) {
		log.debug("REST request to recentTimeSheets ");
		String resourceCode = principal.getName();
		// TODO: Apply role here, only the timesheet owner resource or approver should
		// be able to view
		TimeSheetResourceList timeSheetResourceList = timesheetManagementService.listRecentTimeSheetVMsByResource(
				resourceCode, applicationProperties.timeSheet().RECENT_TIMESHEETS_PAGE_SIZE());

		for (var timesheet : timeSheetResourceList.getTimesheets()) {
			timesheet.add(timesheetLinks.getSelfLink(timesheet));
		}

		return ResponseEntity.ok(timeSheetResourceList);
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
	// @PutMapping
	// @Timed
	// public ResponseEntity<TimeSheetDTO> updateTimesheet(@Valid @RequestBody
	// TimeSheetDTO timesheet)
	// throws URISyntaxException {
	// log.debug("REST request to update timesheet : {}", timesheet);
	//
	// return new ResponseEntity<TimeSheetDTO>(timesheet, HttpStatus.OK);
	// // return ResponseEntity.ok()
	// // .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
	// // accountDTO1.getAccountCode()))
	// // .body(accountDTO1);
	// }

	/**
	 * GET /accounts : get all the accounts.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of accounts in
	 *         body
	 */
	// @GetMapping
	// @Timed
	// public ResponseEntity<List<AccountDTO>> listTimesheets(Pageable pageable) {
	// log.debug("REST request to get a page of Account");
	// // Page<AccountDTO> page = accountsManagementService.findAll(pageable);
	// // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
	// // "/api/accounts");
	// // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	//
	// return null;
	// }

}
