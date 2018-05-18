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
import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.domain.model.common.dto.DayVM;
import com.gspann.itrack.domain.model.common.dto.ProjectSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceProjectAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.TimeSheetDTO;
import com.gspann.itrack.domain.model.common.dto.TimeSheetSubmissionPageVM;
import com.gspann.itrack.domain.model.common.dto.TimesheetActionType;
import com.gspann.itrack.domain.model.common.dto.WeekVM;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.timesheets.DailyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.DayType;
import com.gspann.itrack.domain.model.timesheets.TimeSheetEntry;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.service.api.TimesheetManagementService;

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

	@Override
	public TimeSheetSubmissionPageVM getTimeSheetSubmissionPageVM(String resourceCode) {
		List<ResourceProjectAllocationSummary> allocationSummaries = allocationRepository
				.findAllAllocationSummaries(resourceCode);
		if (allocationSummaries.size() > 0) {
			ResourceAllocationSummary resourceAllocationSummary = ResourceAllocationSummary
					.of(allocationSummaries.get(0).resourceCode(), allocationSummaries.get(0).resourceName());
			for (var allocationSummary : allocationSummaries) {
				resourceAllocationSummary.addProjectAllocation(ProjectSummary.of(allocationSummary.projectCode(),
						allocationSummary.projectName(), allocationSummary.projectTypeCode(),
						allocationSummary.projectTypeName(), allocationSummary.proportion()));
			}

			WeekVM weekDTO = WeekVM.current(ApplicationConstant.WEEKLY_STANDARD_HOURS,
					ApplicationConstant.DAILY_STANDARD_HOURS);

			LocalDate now = LocalDate.now();
			LocalDate startDate = now.with(TemporalAdjusters.previousOrSame(ApplicationConstant.WEEK_START_DAY));
			LocalDate endDate = now.with(TemporalAdjusters.nextOrSame(ApplicationConstant.WEEK_START_DAY));

			Set<LocalDate> holidays = new HashSet<LocalDate>();
			holidays.add(startDate.plusDays(3));

			while (!startDate.isEqual(endDate)) {
				if (holidays.contains(startDate)) {
					weekDTO.addDayVM(DayVM.ofHoliday(startDate, "Diwali"));
				} else if (isWeekend(startDate.getDayOfWeek())) {
					weekDTO.addDayVM(DayVM.ofWeekend(startDate));
				} else {
					weekDTO.addDayVM(DayVM.ofWorkingDay(startDate));
				}
				startDate = startDate.plusDays(1);
			}

			return TimeSheetSubmissionPageVM.of(resourceAllocationSummary, weekDTO,
					new TimesheetActionType[] { TimesheetActionType.SAVE, TimesheetActionType.SUBMIT });
		} else {
			return null;
		}
	}

	private boolean isWeekend(final DayOfWeek dayOfWeek) {
		return dayOfWeek == ApplicationConstant.WEEK_END_FIRST || dayOfWeek == ApplicationConstant.WEEK_END_SECOND;
	}

	@Override
	@Transactional
	public Optional<WeeklyTimeSheet> createTimeSheet(TimeSheetDTO timesheet) {
		Resource resource = resourceRepository.findById(timesheet.getResourceCode()).get();
		LocalDate weekStartDate = timesheet.getWeek().getWeekStartDate();

		Set<LocalDate> holidays = new HashSet<LocalDate>();
		holidays.add(weekStartDate.plusDays(3));

		LocalDate weekDate = weekStartDate;
		Set<TimeSheetEntry> timesheetEntries = new HashSet<>(5);
		Set<DailyTimeSheet> dailyTimeSheets = new LinkedHashSet<>(7);

		for (var dayDTO : timesheet.getWeek().getDailyEntries()) {
			DayOfWeek dayName = DayOfWeek.valueOf(dayDTO.getDay());

			DayType dayType = null;
			if (isWeekend(dayName)) {
				dayType = DayType.WEEK_END;
			} else if (holidays.contains(weekDate)) {
				dayType = DayType.HOLIDAY;
			} else {
				dayType = DayType.WORKING_DAY;
			}

			DailyTimeSheet dailyTimeSheet = null;
			timesheetEntries.clear();
			for (var entry : dayDTO.getTimeEntries()) {
				TimeSheetEntry timeEntry = null;
				switch (dayType) {
				
				case WEEK_END:
					break;

				case HOLIDAY:

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
			dailyTimeSheet = DailyTimeSheet.withDefaultStandardHours().withDaytype(dayType).forDayOfWeek(dayName)
					.withEntries(timesheetEntries).withDailyComments(dayDTO.getComments()).build();
			dailyTimeSheets.add(dailyTimeSheet);

			weekDate.plusDays(1);
		}

		WeeklyTimeSheet weeklyTimeSheet = WeeklyTimeSheet.of(resource)
				.forWeekOf(Week.of(timesheet.getWeek().getWeekStartDate())).withDefaultStandardHours()
				.withDailyTimeSheets(dailyTimeSheets).build();

		return Optional.ofNullable(timeSheetRepository.save(weeklyTimeSheet));
	}

	@Override
	public Optional<WeeklyTimeSheet> findTimeSheetById(long id) {
		// TODO Auto-generated method stub
		return null;
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
