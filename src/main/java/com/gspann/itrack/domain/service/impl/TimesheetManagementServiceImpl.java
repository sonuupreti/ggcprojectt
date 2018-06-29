package com.gspann.itrack.domain.service.impl;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gspann.itrack.adapter.persistence.repository.AllocationRepository;
import com.gspann.itrack.adapter.persistence.repository.ProjectRepository;
import com.gspann.itrack.adapter.persistence.repository.ResourceRepository;
import com.gspann.itrack.adapter.persistence.repository.TimeSheetRepository;
import com.gspann.itrack.domain.model.allocations.ResourceAllocationSummary;
import com.gspann.itrack.domain.model.allocations.ResourceProjectAllocationProjection;
import com.gspann.itrack.domain.model.allocations.ResourceProjectSummary;
import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.timesheets.DailyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.DayType;
import com.gspann.itrack.domain.model.timesheets.TimeSheetEntry;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.dto.DayDTO;
import com.gspann.itrack.domain.model.timesheets.dto.TimeSheetDTO;
import com.gspann.itrack.domain.model.timesheets.vm.DailyTotalEntry;
import com.gspann.itrack.domain.model.timesheets.vm.ProjectWiseDailyEntryVM;
import com.gspann.itrack.domain.model.timesheets.vm.ProjectWiseTimeSheetVM;
import com.gspann.itrack.domain.model.timesheets.vm.ProjectWiseTimeSheetWeekVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetActionTypeVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetActorType;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetDayMetaDataVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResource;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceList;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetStatusTypeVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekMetaDataVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;
import com.gspann.itrack.domain.service.api.HolidayService;
import com.gspann.itrack.domain.service.api.TimesheetManagementService;
import com.gspann.itrack.infra.config.ApplicationProperties;

import lombok.experimental.var;

@Service
public class TimesheetManagementServiceImpl implements TimesheetManagementService {

	@Autowired
	private TimeSheetRepository timeSheetRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private AllocationRepository allocationRepository;

	@Autowired
	private HolidayService holidayService;

	private ApplicationProperties.TimeSheet TIMESHEET_PROPERTIES;

	public TimesheetManagementServiceImpl(final ApplicationProperties applicationProperties) {
		TIMESHEET_PROPERTIES = applicationProperties.timeSheet();
	}

	public TimeSheetWeekMetaDataVM getTimeSheetWeekMetaDataVM(final City deputedLocation) {
		return getTimeSheetWeekMetaDataVM(
				Week.current(TIMESHEET_PROPERTIES.WEEK_START_DAY(), TIMESHEET_PROPERTIES.WEEK_END_DAY()),
				deputedLocation);
	}

	public TimeSheetWeekMetaDataVM getTimeSheetWeekMetaDataVM(Week week, final City deputedLocation) {
		TimeSheetWeekMetaDataVM timeSheetWeekMetaDataVM = TimeSheetWeekMetaDataVM.ofWeek(week,
				TIMESHEET_PROPERTIES.STANDARD_WEEKLY_HOURS(), TIMESHEET_PROPERTIES.STANDARD_DAILY_HOURS());

		LocalDate weekStartDate = timeSheetWeekMetaDataVM.getWeekStartDate();
		LocalDate weekEndDate = timeSheetWeekMetaDataVM.getWeekEndDate();

		Set<Holiday> holidays = holidayService.getHolidaysByWeekAndLocation(week, deputedLocation);
		LocalDate currentDate = weekStartDate;
		while (!currentDate.isAfter(weekEndDate)) {
			Optional<String> holidayOccassion = isHoliday(holidays, currentDate);
			if (holidayOccassion.isPresent()) {
				timeSheetWeekMetaDataVM.addDayVM(TimeSheetDayMetaDataVM.ofHoliday(currentDate, holidayOccassion.get()));
			} else if (isWeekend(currentDate.getDayOfWeek())) {
				timeSheetWeekMetaDataVM.addDayVM(TimeSheetDayMetaDataVM.ofWeekend(currentDate));
			} else {
				timeSheetWeekMetaDataVM.addDayVM(TimeSheetDayMetaDataVM.ofWorkingDay(currentDate));
			}
			currentDate = currentDate.plusDays(1);
		}
		return timeSheetWeekMetaDataVM;
	}

	@Override
	public Optional<TimeSheetResource> getTimeSheetVMByResource(String resourceCode) {
		return getTimeSheetVMByResourceAndWeek(resourceCode,
				Week.current(TIMESHEET_PROPERTIES.WEEK_START_DAY(), TIMESHEET_PROPERTIES.WEEK_END_DAY()));
	}

	public ProjectWiseTimeSheetVM toProjectWiseTimeSheetVM(final ResourceAllocationSummary resourceAllocationSummary,
			final WeeklyTimeSheet weeklyTimeSheet) {

		Set<DailyTotalEntry> totalEntries = weeklyTimeSheet.dailyTimeSheets().stream()
				.map(dailyEntry -> DailyTotalEntry.of(dailyEntry.date(), dailyEntry.totalHours(),
						dailyEntry.dailyComments()))
				.collect(Collectors.toCollection(() -> new TreeSet<>((x, y) -> x.getDate().compareTo(y.getDate()))));

		Map<Project, Set<TimeSheetEntry>> projectWiseTimeEntriesMap = weeklyTimeSheet.projectWiseTimeEntriesMap();

		ProjectWiseTimeSheetVM projectWiseTimeSheetVM = ProjectWiseTimeSheetVM.of(weeklyTimeSheet.id(), totalEntries);

		Set<ProjectWiseTimeSheetWeekVM> projectWiseWeeklyTimeSheets = new TreeSet<>(
				(x, y) -> x.projectName().compareTo(y.projectName()));

		for (var mapEntry : projectWiseTimeEntriesMap.entrySet()) {
			ProjectWiseTimeSheetWeekVM projectWiseTimeSheetWeekVM = ProjectWiseTimeSheetWeekVM.of(
					resourceAllocationSummary.getProjectSummaryByProjectCode(mapEntry.getKey().code()),
					TimeSheetStatusTypeVM
							.from(weeklyTimeSheet.weeklyStatus(mapEntry.getKey()).get().projectWiseStatus()));
			Set<ProjectWiseDailyEntryVM> projectWiseDailyEntries = new TreeSet<>(
					(x, y) -> x.getDate().compareTo(y.getDate()));
			for (var timeSheetEntry : mapEntry.getValue()) {
				projectWiseDailyEntries.add(ProjectWiseDailyEntryVM.of(timeSheetEntry.dailyTimeSheet().date(),
						timeSheetEntry.dailyTimeSheet().day(), timeSheetEntry.dailyTimeSheet().dayType(),
						timeSheetEntry.hours(), timeSheetEntry.comments()));
			}
			projectWiseTimeSheetWeekVM.addDailyEntries(projectWiseDailyEntries);
			projectWiseWeeklyTimeSheets.add(projectWiseTimeSheetWeekVM);
		}
		projectWiseTimeSheetVM.addProjectWiseWeeklyTimeSheets(projectWiseWeeklyTimeSheets);

		return projectWiseTimeSheetVM;
	}

	// public ProjectWiseTimeSheetVM toProjectWiseTimeSheetVM(final WeeklyTimeSheet
	// weeklyTimeSheet) {
	//
	// Set<DailyTotalEntry> totalEntries =
	// weeklyTimeSheet.dailyTimeSheets().stream()
	// .map(dailyEntry -> DailyTotalEntry.of(dailyEntry.date(),
	// dailyEntry.totalHours(),
	// dailyEntry.dailyComments()))
	// .collect(Collectors.toCollection(() -> new TreeSet<>((x, y) ->
	// x.getDate().compareTo(y.getDate()))));
	//
	// Map<Project, Set<TimeSheetEntry>> projectWiseTimeEntriesMap =
	// weeklyTimeSheet.projectWiseTimeEntriesMap();
	//
	// ProjectWiseTimeSheetVM projectWiseTimeSheetVM =
	// ProjectWiseTimeSheetVM.of(weeklyTimeSheet.id(), totalEntries);
	//
	// Set<ProjectWiseTimeSheetWeekVM> projectWiseWeeklyTimeSheets = new TreeSet<>(
	// (x, y) -> x.projectName().compareTo(y.projectName()));
	//
	// for (var mapEntry : projectWiseTimeEntriesMap.entrySet()) {
	// ProjectWiseTimeSheetWeekVM projectWiseTimeSheetWeekVM =
	// ProjectWiseTimeSheetWeekVM.of(TimeSheetStatusTypeVM
	// .from(weeklyTimeSheet.weeklyStatus(mapEntry.getKey()).get().projectWiseStatus()));
	// Set<ProjectWiseDailyEntryVM> projectWiseDailyEntries = new TreeSet<>(
	// (x, y) -> x.getDate().compareTo(y.getDate()));
	// for (var timeSheetEntry : mapEntry.getValue()) {
	// projectWiseDailyEntries.add(ProjectWiseDailyEntryVM.of(timeSheetEntry.dailyTimeSheet().date(),
	// timeSheetEntry.dailyTimeSheet().day(),
	// timeSheetEntry.dailyTimeSheet().dayType(),
	// timeSheetEntry.hours(), timeSheetEntry.comments()));
	// }
	// projectWiseTimeSheetWeekVM.addDailyEntries(projectWiseDailyEntries);
	// projectWiseWeeklyTimeSheets.add(projectWiseTimeSheetWeekVM);
	// }
	// projectWiseTimeSheetVM.addProjectWiseWeeklyTimeSheets(projectWiseWeeklyTimeSheets);
	//
	// return projectWiseTimeSheetVM;
	// }

	@Override
	public Optional<TimeSheetResource> getTimeSheetVMByResourceAndWeek(String resourceCode, Week week) {
		Optional<ResourceAllocationSummary> resourceAllocationDetails = getResourceAllocationSummary(resourceCode,
				week);
		if (resourceAllocationDetails.isPresent()) {
			ResourceAllocationSummary resourceAllocationSummary = resourceAllocationDetails.get();

			Optional<WeeklyTimeSheet> existingTimesheet = timeSheetRepository.findByResourceCodeAndWeek(resourceCode,
					week);
			if (existingTimesheet.isPresent()) {
				WeeklyTimeSheet weeklyTimeSheet = existingTimesheet.get();
				return getTimeSheetVMForExistingTimeSheet(week, weeklyTimeSheet, resourceAllocationSummary);
			} else {
				// Create meta data for the week requested and return;
				return getTimeSheetVMForNewTimeSheet(week, resourceAllocationSummary);
			}
		} else {
			// TODO: Throw exception that resource is not allocated to any projects
		}
		return Optional.empty();
	}

	private Optional<TimeSheetResource> getTimeSheetVMForExistingTimeSheet(final Week week,
			final WeeklyTimeSheet weeklyTimeSheet, final ResourceAllocationSummary resourceAllocationSummary) {
		if (weeklyTimeSheet.weeklyStatus().status() == TimesheetStatus.SAVED) {
			return Optional.of(TimeSheetResource.ofSaved(TIMESHEET_PROPERTIES.SYSTEM_START_DATE(),
					resourceAllocationSummary.getResource(), resourceAllocationSummary.getProjects(),
					getTimeSheetWeekMetaDataVM(week, resourceAllocationSummary.getDeputedLocation()),
					toProjectWiseTimeSheetVM(resourceAllocationSummary, weeklyTimeSheet),
					weeklyTimeSheet.weeklyStatus().updatedOn()));
		} else if (weeklyTimeSheet.weeklyStatus().status() == TimesheetStatus.SUBMITTED) {
			return Optional.of(TimeSheetResource.ofSubmitted(TIMESHEET_PROPERTIES.SYSTEM_START_DATE(),
					resourceAllocationSummary.getResource(), TimeSheetWeekMetaDataVM.ofWeek(week),
					toProjectWiseTimeSheetVM(resourceAllocationSummary, weeklyTimeSheet), TimeSheetActorType.RESOURCE,
					weeklyTimeSheet.weeklyStatus().updatedOn()));
			// TODO: Get the role from logged in user details and pass the
			// TimeSheetActorType accordingly
		}
		// TODO: Put in the code for other timesheet statuses
		return Optional.empty();
	}

	private Optional<TimeSheetResource> getTimeSheetVMForNewTimeSheet(final Week week,
			final ResourceAllocationSummary resourceAllocationSummary) {
		return Optional.of(TimeSheetResource.ofPendingForSubmission(TIMESHEET_PROPERTIES.SYSTEM_START_DATE(),
				resourceAllocationSummary.getResource(), resourceAllocationSummary.getProjects(),
				getTimeSheetWeekMetaDataVM(week, resourceAllocationSummary.getDeputedLocation())));
	}

	@Override
	public Optional<TimeSheetResource> getTimeSheetVMByIdAndResourceCode(final long timesheetId,
			final String resourceCode) {
		// Optional<WeeklyTimeSheet> existingTimesheet = getTimeSheetById(timesheetId);
		Optional<WeeklyTimeSheet> existingTimesheet = getTimeSheetByIdAndResourceCode(timesheetId, resourceCode);
		if (existingTimesheet.isPresent()) {
			WeeklyTimeSheet weeklyTimeSheet = existingTimesheet.get();
			Optional<ResourceAllocationSummary> resourceAllocationDetails = getResourceAllocationSummary(
					weeklyTimeSheet.resource().code(), weeklyTimeSheet.week());
			return getTimeSheetVMForExistingTimeSheet(weeklyTimeSheet.week().updateWeekName(), weeklyTimeSheet,
					resourceAllocationDetails.get());
		} else {
			// TODO: Throw exception that no timesheet with such ID exists
			return Optional.empty();
		}
	}

	@Override
	public TimeSheetResourceList listRecentTimeSheetVMsByResource(String resourceCode, int pageSize) {
		// TODO Auto-generated method stub
		// Set<TimeSheetWeekStatusVM> timesheets =
		// getWeeklyStatusesByWeeks(resourceCode,
		// Week.lastWeeks(TIMESHEET_PROPERTIES.WEEK_START_DAY(),
		// TIMESHEET_PROPERTIES.WEEK_END_DAY(), pageSize));
		List<WeeklyTimeSheet> existingTimeSheets = listRecentTimeSheetsByResource(resourceCode, pageSize);
		Map<Week, WeeklyTimeSheet> existingTimeSheetsMap = existingTimeSheets.stream()
				.collect(Collectors.toMap(WeeklyTimeSheet::week, timesheet -> timesheet));
		List<Week> forWeeks = Week.lastWeeks(TIMESHEET_PROPERTIES.WEEK_START_DAY(), TIMESHEET_PROPERTIES.WEEK_END_DAY(),
				pageSize);
		TimeSheetResourceList timeSheetResourceList = TimeSheetResourceList.of(TIMESHEET_PROPERTIES.SYSTEM_START_DATE(),
				resourceRepository.findCodeAndName(resourceCode));
		for (var week : forWeeks) {
			if (existingTimeSheetsMap.containsKey(week)) {
				WeeklyTimeSheet weeklyTimesheet = existingTimeSheetsMap.get(week);
				Optional<ResourceAllocationSummary> resourceAllocationDetails = getResourceAllocationSummary(
						resourceCode, week);
				if (resourceAllocationDetails.isPresent()) {
					timeSheetResourceList.add(TimeSheetResource.ofExistingTimeSheet(
							TimeSheetWeekMetaDataVM.ofWeek(weeklyTimesheet.week().updateWeekName()),
							toProjectWiseTimeSheetVM(resourceAllocationDetails.get(), weeklyTimesheet),
							TimeSheetStatusTypeVM.from(weeklyTimesheet.weeklyStatus().status()),
							weeklyTimesheet.weeklyStatus().updatedOn()));
				} else {
					// TODO: Throw exception
				}

			} else {
				timeSheetResourceList
						.add(TimeSheetResource.ofPendingForSubmission(TimeSheetWeekMetaDataVM.ofWeek(week)));
			}
		}
		return timeSheetResourceList;
	}

	private boolean isWeekend(final DayOfWeek dayOfWeek) {
		return TIMESHEET_PROPERTIES.WEEKENDS().contains(dayOfWeek);
	}

	private Optional<String> isHoliday(final Set<Holiday> holidays, final LocalDate date) {
		for (var holiday : holidays) {
			if (date.equals(holiday.date())) {
				return Optional.of(holiday.locationOcassions().stream().map(e -> e.occasion().name()).reduce(", ",
						String::concat));
			}
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<ResourceAllocationSummary> getResourceAllocationSummary(final String resourceCode,
			final Week week) {
		List<ResourceProjectAllocationProjection> allocationSummaries = allocationRepository
				.findAllAllocationSummaries(resourceCode, week);
		if (allocationSummaries.size() > 0) {
			ResourceAllocationSummary resourceAllocationSummary = ResourceAllocationSummary.of(
					allocationSummaries.get(0).resourceCode(), allocationSummaries.get(0).resourceName(),
					allocationSummaries.get(0).deputedLocation());
			for (var allocationSummary : allocationSummaries) {
				resourceAllocationSummary.addProjectAllocation(
						ResourceProjectSummary.of(allocationSummary.projectCode(), allocationSummary.projectName(),
								allocationSummary.projectTypeCode(), allocationSummary.projectTypeName(),
								allocationSummary.fromDate(), allocationSummary.tillDate(),
								allocationSummary.proportion(), allocationSummary.customerTimeTracking()));
			}
			return Optional.of(resourceAllocationSummary);
		} else {
			return Optional.empty();
		}
	}

	@Override
	@Transactional
	public Optional<WeeklyTimeSheet> saveOrSubmitTimeSheet(final String resourceCode, final TimeSheetDTO timesheet) {

		LocalDate weekStartDate = timesheet.getWeek().getWeekStartDate();

		Optional<WeeklyTimeSheet> existingTimeSheet = null;
		if (timesheet.getTimesheetId() != 0) {
			existingTimeSheet = getTimeSheetById(timesheet.getTimesheetId());
		} else {
			existingTimeSheet = getTimeSheetByResourceAndWeek(resourceCode, Week.of(weekStartDate));
		}

		LocalDate weekDate = null;

		Set<DailyTimeSheet> dailyTimeSheets = new LinkedHashSet<>(7);
		Set<DayDTO> dailyEntries = timesheet.getWeek().getDailyEntries();
		Week week = Week.of(timesheet.getWeek().getWeekStartDate());
		Optional<ResourceAllocationSummary> resourceAllocationSummary = getResourceAllocationSummary(resourceCode,
				week);

		Set<Holiday> holidays = holidayService.getHolidaysByWeekAndLocation(week,
				resourceAllocationSummary.get().getDeputedLocation());

		for (var dayDTO : dailyEntries) {
			weekDate = dayDTO.getDate();
			DayOfWeek dayName = weekDate.getDayOfWeek();
			Optional<String> holidayOccassion = isHoliday(holidays, weekDate);

			DayType dayType = null;
			if (isWeekend(dayName)) {
				dayType = DayType.WEEK_END;
			} else if (holidayOccassion.isPresent()) {
				dayType = DayType.HOLIDAY;
			} else {
				dayType = DayType.WORKING_DAY;
			}

			Set<TimeSheetEntry> timesheetEntries = new LinkedHashSet<>(5);
			DailyTimeSheet dailyTimeSheet = null;
			for (var entry : dayDTO.getTimeEntries()) {
				TimeSheetEntry timeEntry = null;
				switch (dayType) {
				case WEEK_END:
					if (entry.getHours() == 0) {
						timeEntry = TimeSheetEntry.forWeekEnd(projectRepository.getOne(entry.getProjectCode()));
					} else {
						timeEntry = TimeSheetEntry.forWorkingWeekend()
								.workedOn(projectRepository.getOne(entry.getProjectCode()))
								.forDuration(Duration.ofHours(entry.getHours())).onTasks(entry.getComments()).build();
					}

					break;
				case HOLIDAY:
					if (entry.getHours() == 0) {
						timeEntry = TimeSheetEntry.forHoliday(projectRepository.getOne(entry.getProjectCode()));
					} else {
						timeEntry = TimeSheetEntry.forWorkingHoliday()
								.workedOn(projectRepository.getOne(entry.getProjectCode()))
								.forDuration(Duration.ofHours(entry.getHours())).onTasks(entry.getComments()).build();
					}

					break;
				case WORKING_DAY:
					timeEntry = TimeSheetEntry.forWorkingDay()
							.workedOn(projectRepository.getOne(entry.getProjectCode()))
							.forDuration(Duration.ofHours(entry.getHours())).onTasks(entry.getComments()).build();

					break;
				default:
					break;
				}
				timesheetEntries.add(timeEntry);
			}
			dailyTimeSheet = DailyTimeSheet.withDefaultStandardHours().forDate(weekDate).withDaytype(dayType)
					.withEntries(timesheetEntries).withDailyComments(dayDTO.getComments()).build();
			dailyTimeSheets.add(dailyTimeSheet);

			weekDate.plusDays(1);
		}

		WeeklyTimeSheet weeklyTimeSheet = null;
		if (existingTimeSheet.isPresent()) {
			// Update existing timesheet
			weeklyTimeSheet = existingTimeSheet.get();
			weeklyTimeSheet.clearAllDailyTimeSheets();
			// weeklyTimeSheet.replaceAllDailyTimeSheets(dailyTimeSheets);
			weeklyTimeSheet.addAllDailyTimeSheets(dailyTimeSheets);
		} else {
			// Create new timesheet in DB
			// Resource resource =
			// resourceRepository.findById(timesheet.getResourceCode()).get();
			Resource resource = resourceRepository.getOne(resourceCode);
			weeklyTimeSheet = WeeklyTimeSheet.of(resource).forWeekOf(Week.of(timesheet.getWeek().getWeekStartDate()))
					.withDefaultStandardHours().withDailyTimeSheets(dailyTimeSheets).build();
		}
		if (TimeSheetActionTypeVM.valueOf(timesheet.getAction()) == TimeSheetActionTypeVM.SAVE) {
			weeklyTimeSheet.save();
		} else if (TimeSheetActionTypeVM.valueOf(timesheet.getAction()) == TimeSheetActionTypeVM.SUBMIT) {
			weeklyTimeSheet.submit();
		}
		return Optional.ofNullable(timeSheetRepository.save(weeklyTimeSheet));
	}

	@Override
	public Optional<WeeklyTimeSheet> getTimeSheetById(long id) {
		return timeSheetRepository.findById(id);
	}

	@Override
	public Optional<WeeklyTimeSheet> getTimeSheetByResourceAndWeek(String resourceCode, Week week) {
		return timeSheetRepository.findByResourceCodeAndWeek(resourceCode, week);
	}

	@Override
	public Optional<WeeklyTimeSheet> getTimeSheetByResourceAndDate(String resourceCode, LocalDate date) {
		return timeSheetRepository.findByResourceCodeAndWeek(resourceCode,
				Week.containingDate(date, TIMESHEET_PROPERTIES.WEEK_START_DAY(), TIMESHEET_PROPERTIES.WEEK_END_DAY()));
	}

	@Override
	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndStatus(String resourceCode, TimesheetStatus status) {
		return timeSheetRepository.findByResourceCodeAndWeeklyStatusStatus(resourceCode, status);
	}

	@Override
	public void approveTimesheet(final long timesheetId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void approveTimesheet(long timesheetId, String projectCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rejectTimesheet(final long timesheetId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rejectTimesheet(long timesheetId, String projectCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndMonth(String resourceCode, YearMonth month) {
		return listTimeSheetsByResourceAndDateRange(resourceCode,
				DateRange.dateRange().startingOn(month.atDay(1)).endingOn(month.atEndOfMonth()));
	}

	@Override
	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndDateRange(final String resourceCode,
			final DateRange dateRange) {
		return timeSheetRepository.findAllTimeSheetsByDateRange(resourceCode, dateRange,
				TIMESHEET_PROPERTIES.WEEK_START_DAY(), TIMESHEET_PROPERTIES.WEEK_END_DAY());
	}

	@Override
	public List<WeeklyTimeSheet> listTimeSheetsByResourceSinceDate(String resourceCode, LocalDate date) {
		return listTimeSheetsByResourceAndDateRange(resourceCode,
				DateRange.dateRange().startingOn(date).endingOn(LocalDate.now()));
	}

	@Override
	public List<WeeklyTimeSheet> listRecentTimeSheetsByResource(String resourceCode, int pageSize) {
		LocalDate endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(TIMESHEET_PROPERTIES.WEEK_END_DAY()));
		LocalDate startDate = endDate.minusDays(6 * pageSize).minusDays(pageSize - 1);
		return listTimeSheetsByResourceAndDateRange(resourceCode,
				DateRange.dateRange().startingOn(startDate).endingOn(endDate));
	}

	@Override
	public Set<TimeSheetWeekStatusVM> getWeeklyStatusesByWeeks(String resourceCode, List<Week> weeks) {
		// TODO Return a Map instead with week as key
		return timeSheetRepository.findWeeklyStatusesByWeeks(resourceCode, weeks);
	}

	@Override
	public Map<Week, TimeSheetWeekStatusVM> getWeeklyStatusesForNextAndPreviousWeeks(String resourceCode, Week week) {
		Week previousWeek = week.previous();
		Week nextWeek = week.next();

		List<Week> forWeeks = new ArrayList<>(2);
		Map<Week, TimeSheetWeekStatusVM> resultMap = new HashMap<>();
		Week currentWeek = Week.current(TIMESHEET_PROPERTIES.WEEK_START_DAY(), TIMESHEET_PROPERTIES.WEEK_END_DAY());
		if (nextWeek.startingFrom().isAfter(currentWeek.endingOn())
				&& TIMESHEET_PROPERTIES.SYSTEM_START_DATE().isAfter(previousWeek.startingFrom())) {
			return Collections.emptyMap();
		} else if (nextWeek.startingFrom().isAfter(currentWeek.endingOn())) {
			forWeeks.add(previousWeek);
			Set<TimeSheetWeekStatusVM> results = getWeeklyStatusesByWeeks(resourceCode, forWeeks);
			if (results.isEmpty()) {
				resultMap.put(previousWeek, TimeSheetWeekStatusVM.ofWeekPendingForSubmission(previousWeek));
			} else {
				resultMap.put(previousWeek, results.iterator().next());
			}
		} else if (TIMESHEET_PROPERTIES.SYSTEM_START_DATE().isAfter(previousWeek.startingFrom())) {
			forWeeks.add(nextWeek);
			Set<TimeSheetWeekStatusVM> results = getWeeklyStatusesByWeeks(resourceCode, forWeeks);
			if (results.isEmpty()) {
				resultMap.put(nextWeek, TimeSheetWeekStatusVM.ofWeekPendingForSubmission(previousWeek));
			} else {
				resultMap.put(nextWeek, results.iterator().next());
			}
		} else {
			forWeeks.add(previousWeek);
			forWeeks.add(nextWeek);
			Set<TimeSheetWeekStatusVM> results = getWeeklyStatusesByWeeks(resourceCode, forWeeks);
			if (results.isEmpty()) {
				resultMap.put(previousWeek, TimeSheetWeekStatusVM.ofWeekPendingForSubmission(previousWeek));
				resultMap.put(nextWeek, TimeSheetWeekStatusVM.ofWeekPendingForSubmission(nextWeek));
			} else if (results.size() == 1) {
				TimeSheetWeekStatusVM timeSheetWeekStatusVM = results.iterator().next();
				if (timeSheetWeekStatusVM.toWeek().equals(previousWeek)) {
					resultMap.put(previousWeek, timeSheetWeekStatusVM);
					resultMap.put(nextWeek, TimeSheetWeekStatusVM.ofWeekPendingForSubmission(nextWeek));
				} else {
					resultMap.put(nextWeek, timeSheetWeekStatusVM);
					resultMap.put(previousWeek, TimeSheetWeekStatusVM.ofWeekPendingForSubmission(previousWeek));
				}
			} else if (results.size() == 2) {
				Iterator<TimeSheetWeekStatusVM> resultItr = results.iterator();
				while (resultItr.hasNext()) {
					TimeSheetWeekStatusVM timeSheetWeekStatusVM = resultItr.next();
					resultMap.put(timeSheetWeekStatusVM.toWeek(), timeSheetWeekStatusVM);
				}
			}
		}
		return Collections.unmodifiableMap(resultMap);
	}

	@Override
	public Optional<WeeklyTimeSheet> getTimeSheetByIdAndResourceCode(long timesheetId, String resourceCode) {
		return timeSheetRepository.findByIdAndResourceCode(timesheetId, resourceCode);
	}
}
