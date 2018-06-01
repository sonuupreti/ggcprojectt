package com.gspann.itrack.domain.service.impl;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gspann.itrack.adapter.persistence.repository.AllocationRepository;
import com.gspann.itrack.adapter.persistence.repository.ProjectRepository;
import com.gspann.itrack.adapter.persistence.repository.ResourceRepository;
import com.gspann.itrack.adapter.persistence.repository.TimeSheetRepository;
import com.gspann.itrack.domain.model.common.dto.DayDTO;
import com.gspann.itrack.domain.model.common.dto.DayVM;
import com.gspann.itrack.domain.model.common.dto.ProjectSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceProjectAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.TimeSheetActionType;
import com.gspann.itrack.domain.model.common.dto.TimeSheetDTO;
import com.gspann.itrack.domain.model.common.dto.TimeSheetMetaDataVM;
import com.gspann.itrack.domain.model.common.dto.WeekVM;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.timesheets.DailyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.DayType;
import com.gspann.itrack.domain.model.timesheets.TimeSheetEntry;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
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

	@Override
	public TimeSheetMetaDataVM getTimeSheetMetaData(String resourceCode) {
		List<ResourceProjectAllocationSummary> allocationSummaries = allocationRepository
				.findAllAllocationSummaries(resourceCode);
		City deputedLocation = null;
		if (allocationSummaries.size() > 0) {
			ResourceAllocationSummary resourceAllocationSummary = ResourceAllocationSummary
					.of(allocationSummaries.get(0).resourceCode(), allocationSummaries.get(0).resourceName());
			deputedLocation = allocationSummaries.get(0).deputedLocation();
			for (var allocationSummary : allocationSummaries) {
				resourceAllocationSummary.addProjectAllocation(
						ProjectSummary.of(allocationSummary.projectCode(), allocationSummary.projectName(),
								allocationSummary.projectTypeCode(), allocationSummary.projectTypeName(),
								allocationSummary.proportion(), allocationSummary.customerTimeTracking()));
			}

			WeekVM weekDTO = WeekVM.current(TIMESHEET_PROPERTIES.STANDARD_WEEKLY_HOURS(),
					TIMESHEET_PROPERTIES.STANDARD_WEEKLY_HOURS(), TIMESHEET_PROPERTIES.WEEK_START_DAY());

			LocalDate currentDate = LocalDate.now();
			LocalDate weekStartDate = currentDate
					.with(TemporalAdjusters.previousOrSame(TIMESHEET_PROPERTIES.WEEK_START_DAY()));
			LocalDate weekEndDate = currentDate
					.with(TemporalAdjusters.nextOrSame(TIMESHEET_PROPERTIES.WEEK_START_DAY()));
			Week week = Week.of(weekStartDate);

			Set<Holiday> holidays = holidayService.getHolidaysByWeekAndLocation(week, deputedLocation);
			currentDate = weekEndDate;
			boolean isHoliday = false;
			String holidayOccassions = null;
			while (!currentDate.isEqual(weekEndDate)) {
				for (var holiday : holidays) {
					if (currentDate.equals(holiday.date())) {
						isHoliday = true;
						holidayOccassions = holiday.locationOcassions().stream().map(e -> e.occasion().name())
								.reduce(", ", String::concat);
					}
				}
				if (isHoliday) {
					weekDTO.addDayVM(DayVM.ofHoliday(currentDate, holidayOccassions));
				} else if (isWeekend(weekStartDate.getDayOfWeek())) {
					weekDTO.addDayVM(DayVM.ofWeekend(currentDate));
				} else {
					weekDTO.addDayVM(DayVM.ofWorkingDay(currentDate));
				}
				currentDate = currentDate.plusDays(1);
			}

			// Find any saved timesheet, pending for submission or create new if none is
			// there for current week.

			return TimeSheetMetaDataVM.of(resourceAllocationSummary, weekDTO,
					new TimeSheetActionType[] { TimeSheetActionType.SAVE, TimeSheetActionType.SUBMIT });
		} else {
			return null;
		}
	}

	private boolean isWeekend(final DayOfWeek dayOfWeek) {
		return TIMESHEET_PROPERTIES.WEEKENDS().contains(dayOfWeek);
	}

	@Override
	@Transactional
	public Optional<WeeklyTimeSheet> saveOrSubmitTimeSheet(final TimeSheetDTO timesheet,
			final Set<ProjectSummary> allocatedProjects) {
		Resource resource = resourceRepository.findById(timesheet.getResourceCode()).get();

		Optional<WeeklyTimeSheet> existingTimeSheet = null;
		if (timesheet.getWeeklyTimeSheetId() != 0) {
			existingTimeSheet = timeSheetRepository.findById(timesheet.getWeeklyTimeSheetId());
		} else {
			existingTimeSheet = timeSheetRepository
					.findTimeSheetByWeekStartDate(timesheet.getWeek().getWeekStartDate());
		}

		LocalDate weekStartDate = timesheet.getWeek().getWeekStartDate();

		Set<LocalDate> holidays = new HashSet<LocalDate>();
		holidays.add(weekStartDate.plusDays(3));

		LocalDate weekDate = null;
		// Map<LocalDate, DayDTO> dailyEntriesMap =
		// timesheet.getWeek().dailyEntriesMap();
		// for (LocalDate date : dailyEntriesMap.keySet())
		// System.out.println("key: " + date);

		Set<DailyTimeSheet> dailyTimeSheets = new LinkedHashSet<>(7);
		Set<DayDTO> dailyEntries = timesheet.getWeek().getDailyEntries();

		for (var dayDTO : dailyEntries) {
			weekDate = dayDTO.getDate();
			DayOfWeek dayName = weekDate.getDayOfWeek();

			DayType dayType = null;
			if (isWeekend(dayName)) {
				dayType = DayType.WEEK_END;
			} else if (holidays.contains(weekDate)) {
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
			// weeklyTimeSheet.clearAllDailyTimeSheets();
			weeklyTimeSheet.addAllDailyTimeSheets(dailyTimeSheets);
		} else {
			// Create new timesheet in DB
			weeklyTimeSheet = WeeklyTimeSheet.of(resource).forWeekOf(Week.of(timesheet.getWeek().getWeekStartDate()))
					.withDefaultStandardHours().withDailyTimeSheets(dailyTimeSheets).build();
		}
		if (TimeSheetActionType.valueOf(timesheet.getAction()) == TimeSheetActionType.SAVE) {
			weeklyTimeSheet.save();
		} else if (TimeSheetActionType.valueOf(timesheet.getAction()) == TimeSheetActionType.SUBMIT) {
			weeklyTimeSheet.submit();
		}
		return Optional.ofNullable(timeSheetRepository.save(weeklyTimeSheet));
	}

	@Override
	public Optional<WeeklyTimeSheet> findTimeSheetById(long id) {
		// TODO Auto-generated method stub
		return timeSheetRepository.findById(id);
	}

	@Override
	public Optional<WeeklyTimeSheet> findTimeSheetByResourceCodeAndDate(String resourceCode, LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WeeklyTimeSheet> findTimeSheetByResourceCodeAndStatus(String resourceCode, TimesheetStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

}
