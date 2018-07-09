package com.gspann.itrack.adapter.rest.timesheet;

import static com.gspann.itrack.adapter.rest.timesheet.TimeSheetLinks.*;
import static com.gspann.itrack.common.constants.RestConstant.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import com.gspann.itrack.domain.model.timesheets.vm.MonthVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetActorType;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResource;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceList;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceWeekStatusVMList;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVMList;
import com.gspann.itrack.domain.service.api.TimesheetManagementService;
import com.gspann.itrack.infra.config.ApplicationProperties;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing Time sheets.
 */
@RestController
@RequestMapping(TIMESHEET)
@Slf4j
public class TimeSheetResourceController {

	@NonNull
	private final TimesheetManagementService timesheetManagementService;

	@NonNull
	private final ApplicationProperties applicationProperties;

	@NonNull
	private final TimeSheetLinks timesheetLinks;

	public TimeSheetResourceController(final TimesheetManagementService timesheetManagementService,
			final ApplicationProperties applicationProperties, final TimeSheetLinks timesheetLinks) {
		this.timesheetManagementService = timesheetManagementService;
		this.applicationProperties = applicationProperties;
		this.timesheetLinks = timesheetLinks;
	}

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
	// @formatter:off
	@ApiOperation(nickname = "weekly-view", 
			value = "Gets the timesheet View Model for Weekly View, by input date", response = TimeSheetResource.class, 
				notes = "<p>The <b>input date is optional</b>, if not supplied returns the data for current week otherwise for the week wherein the supplied date falls.</p><br>"
					+ "<br/>	1. If timesheet is PENDING for submission then the provided week's metadata "
					+ "which includes week start and end date and days, week name, week ends, "
					+ "day metadata such as if normal working day or week end or holiday etc."
					+ "resource allocation to project's details, resource details etc."
					+ "<br/>	2. If timesheet is in SAVED state then week metadata, "
					+ "resource and her allocation to project details along with the saved timesheet data is returned."
					+ "<br/>	3. If timesheet is in SUBMITTED state, then the minimal week details with just week name, start and end date, "
					+ "resource details and timesheet data is returned, but not the resource to project allocation, "
					+ "as it is not required by UI for submitted timesheets<br/><br/>"
					+ "Also the link to previous and next week timesheet are return as HATEOAS links in response body"
				)
	// @formatter:on
	public ResponseEntity<TimeSheetResource> weekly(
			@RequestParam(value = "date", required = false) final Optional<LocalDate> weekDate,
			final Principal principal) {
		System.out.println("????? " + principal.getName());
		log.debug("REST request to getTimeSheetSubmissionPageVM() ------>>>");
		String resourceCode = principal.getName();
		Week forWeek = getInputWeek(weekDate);
		Optional<TimeSheetResource> timeSheetResource = timesheetManagementService
				.getTimeSheetVMByResourceAndWeek(resourceCode, forWeek, TimeSheetActorType.RESOURCE);

		if (timeSheetResource.isPresent()) {
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
	// @formatter:off
	@ApiOperation(nickname = "weekly-view", 
			value = "Gets the timesheet View Model for Weekly View, by timesheet ID", response = TimeSheetResource.class, 
				notes = "Takes mandatory timesheet ID as input as path variable"
					+ "<br/>	1. If timesheet is in SAVED state then week metadata, "
					+ "resource and her allocation to project details along with the saved timesheet data is returned."
					+ "<br/>	2. If timesheet is in SUBMITTED state, then the minimal week details with just week name, start and end date, "
					+ "resource details and timesheet data is returned, but not the resource to project allocation, "
					+ "as it is not required by UI for submitted timesheets.<br/><br/>"
					+ "Also the link to previous and next week timesheet are return as HATEOAS links in response body"
				)
	// @formatter:on
	public ResponseEntity<TimeSheetResource> weekly(@PathVariable final long id, final Principal principal) {
		log.debug("REST request to getTimeSheetSubmissionPageVM() ------>>>");
		String resourceCode = principal.getName();

		Optional<TimeSheetResource> timeSheetResource = timesheetManagementService.getTimeSheetVMByIdAndResourceCode(id,
				resourceCode, TimeSheetActorType.RESOURCE);
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

	@GetMapping(TIMESHEET_PENDING_SELF_BY_MONTH)
	@Timed
	// @formatter:off
	@ApiOperation(nickname = "timesheet pending actions", 
			value = "Gets the List of weeks with corresponding timesheet status, pending for any actions, by input month", response = TimeSheetResource.class, 
				notes = "<p>The <b>input month is optional</b>, if not supplied returns the data for current month otherwise for the supplied month.</p><br>"
				)
	// @formatter:on
	public ResponseEntity<TimeSheetWeekStatusVMList> pendingSelfTimesheetActionsByMonth(
			@RequestParam(value = "month", required = false) final Optional<YearMonth> month,
			final Principal principal) {
		System.out.println("month ---->>>>>" + month);
		String resourceCode = principal.getName();
		Set<TimeSheetWeekStatusVM> statuses = timesheetManagementService.getPendingWeeklyActionsForResourceByMonth(
				resourceCode, month.isPresent() ? month.get() : YearMonth.now());
		TimeSheetWeekStatusVMList timeSheetWeekStatusVMList = TimeSheetWeekStatusVMList
				.of(statuses.stream().collect(Collectors.toList()));
		timeSheetWeekStatusVMList.getWeekStatuses().forEach((x) -> x.add(timesheetLinks.getSelfLink(x)));
		return new ResponseEntity<TimeSheetWeekStatusVMList>(timeSheetWeekStatusVMList, HttpStatus.OK);
	}

	@GetMapping(TIMESHEET_PENDING_SELF_SINCE_NO_OF_MONTHS)
	@Timed
	// @formatter:off
	@ApiOperation(nickname = "timesheet pending actions", 
			value = "Gets the List of weeks with corresponding timesheet status, pending for any actions, by input month", response = TimeSheetResource.class, 
				notes = "<p>The <b>input month is optional</b>, if not supplied returns the data for current month otherwise for the supplied month.</p><br>"
				)
	// @formatter:on
	public ResponseEntity<TimeSheetWeekStatusVMList> pendingSelfTimesheetActionsSinceNoOfMonths(
			@RequestParam(value = "monthsCount", required = false) final Optional<Integer> monthsCount,
			final Principal principal) {
		String resourceCode = principal.getName();
		System.out.println("monthsCount ---->>>>>" + monthsCount);
		Set<TimeSheetWeekStatusVM> statuses = timesheetManagementService.getPendingWeeklyActionsForResourceSinceMonths(
				resourceCode, monthsCount.isPresent() ? monthsCount.get() : 1);
		TimeSheetWeekStatusVMList timeSheetWeekStatusVMList = TimeSheetWeekStatusVMList
				.of(statuses.stream().collect(Collectors.toList()));
		timeSheetWeekStatusVMList.getWeekStatuses().forEach((x) -> x.add(timesheetLinks.getSelfLink(x)));
		return new ResponseEntity<TimeSheetWeekStatusVMList>(timeSheetWeekStatusVMList, HttpStatus.OK);
	}

	@GetMapping(TIMESHEET_PENDING_FOR_APPROVER_SINCE_NO_OF_MONTHS)
	@Timed
	// @formatter:off
	@ApiOperation(nickname = "timesheet pending actions", 
			value = "Gets the List of weeks with corresponding timesheet status, pending for any actions, by input month", response = TimeSheetResource.class, 
			notes = "<p>The <b>input month is optional</b>, if not supplied returns the data for current month otherwise for the supplied month.</p><br>"
	)
	// @formatter:on
	public ResponseEntity<TimeSheetResourceWeekStatusVMList> pendingTimesheetActionsForApproverSinceNoOfMonths(
			@RequestParam(value = "monthsCount", required = false) final Optional<Integer> monthsCount,
			final Principal principal) {

		System.out.println("monthCount ---->>>>>" + monthsCount);
		return null;
	}

	@GetMapping(SLASH + PATH_VARIABLE_ID)
	@Timed
	// @formatter:off
	@ApiOperation(nickname = "getTimesheetById", response = TimeSheetResource.class,
			value = "Gets an existing timesheet by ID",
			notes = "Takes mandatory timesheet ID as input as path variable"
					+ "<br/>	1. If timesheet is in SAVED state then week metadata, "
					+ "resource and her allocation to project details along with the saved timesheet data is returned."
					+ "<br/>	2. If timesheet is in SUBMITTED state, then the minimal week details with just week name, start and end date, "
					+ "resource details and timesheet data is returned, but not the resource to project allocation, "
					+ "as it is not required by UI for submitted timesheets<br/><br/>"
					+ "But the link to previous and next week timesheet are not returned in response, "
					+ "if you need next and previous nevigation links, consume the endpoints for <b>weekly-view</b>"
			)
	// @formatter:on
	public ResponseEntity<TimeSheetResource> getTimesheetById(@PathVariable long id, final Principal principal) {
		log.debug("REST request to getTimesheet : {}", id);
		String resourceCode = principal.getName();
		// TODO: Apply role here, only the timesheet owner resource or approver should
		// be able to view
		Optional<TimeSheetResource> timeSheetResource = timesheetManagementService.getTimeSheetVMByIdAndResourceCode(id,
				resourceCode, TimeSheetActorType.RESOURCE);
		if (timeSheetResource.isPresent()) {
			return new ResponseEntity<TimeSheetResource>(timeSheetResource.get(), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@Timed
	// @formatter:off
	@ApiOperation(nickname = "save/submit", 
			value = "Saves or Submits the timesheet", 
				notes = "Saves or Submits the input timesheet depending upon the if <b>action</b> "
						+ "attribute in request body is SAVE or SUBMIT respectively"
						+ "Returns the URI of the newly created timesheet in <b>location</b> header, "
						+ "along with success message as a header value"
				)
	// @formatter:on
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

	private void updateNextAndPreviousMonths(final TimeSheetResourceList timeSheetResourceList) {

		YearMonth month = timeSheetResourceList.getMonthDetails().getMonth();
		if (!month.atEndOfMonth().isAfter(LocalDate.now())
				&& !applicationProperties.timeSheet().SYSTEM_START_DATE().isAfter(month.atDay(1))) {
			timeSheetResourceList.setNextMonth(MonthVM.of(month.plusMonths(1)));
			timeSheetResourceList.setPreviousMonth(MonthVM.of(month.minusMonths(1)));
		} else if (!month.atEndOfMonth().isAfter(LocalDate.now())) {
			timeSheetResourceList.setNextMonth(MonthVM.of(month.plusMonths(1)));
		} else if (!applicationProperties.timeSheet().SYSTEM_START_DATE().isAfter(month.atDay(1))) {
			timeSheetResourceList.setPreviousMonth(MonthVM.of(month.minusMonths(1)));
		}
	}

	@GetMapping(TIMESHEET_MONTHLY)
	@Timed
	// @formatter:off
	@ApiOperation(nickname = "list-self-timesheets-by-month", response = TimeSheetResourceList.class, value = "Gets list of timesheets for input month")
	// @formatter:on
	public ResponseEntity<TimeSheetResourceList> listSelfTimeSheetsByMonth(
			@RequestParam(value = "month", required = false) final Optional<YearMonth> month,
			final Principal principal) {
		log.debug("REST request to listSelfTimeSheetsByMonth ");
		String resourceCode = principal.getName();
		// TODO: Apply role here, only the timesheet owner resource or approver should
		// be able to view
		TimeSheetResourceList timeSheetResourceList = timesheetManagementService.listTimeSheetVMsByResourceAndMonth(
				resourceCode, month.isPresent() ? month.get() : YearMonth.now(), TimeSheetActorType.RESOURCE);
		updateNextAndPreviousMonths(timeSheetResourceList);
		try {
			timeSheetResourceList.add(timesheetLinks.getLinks(timeSheetResourceList));
		} catch (URISyntaxException e) {
			e.printStackTrace();
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
