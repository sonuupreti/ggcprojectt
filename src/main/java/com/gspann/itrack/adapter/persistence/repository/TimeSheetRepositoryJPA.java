package com.gspann.itrack.adapter.persistence.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceWeekStatusVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;

public interface TimeSheetRepositoryJPA {

//	Optional<WeeklyTimeSheet> findTimeSheetByWeekStartDate(final LocalDate weekStartDate);
	
	List<WeeklyTimeSheet> findSavedTimeSheetsStartingFromDate(final String resourceCode, final LocalDate fromDate);

	Set<TimeSheetWeekStatusVM> findWeeksPendingForSubmissionSinceDate(final String resourceCode, final LocalDate sinceDate);

	Map<Week, TimeSheetWeekStatusVM> findPendingActionWeeksForResourceByWeeks(final String resourceCode, final List<Week> weeks);

//	Map<Week, TimeSheetWeekStatusVM> findPendingActionWeeksForResourceSinceDate(final String resourceCode, final LocalDate sinceDate);

	List<TimeSheetResourceWeekStatusVM> findPendingActionsForApproverByWeek(final String approverCode, final Week week);

//	Set<TimeSheetResourceWeekStatusVM> findPendingActionWeeksForApproverSinceDate(final String resourceCode, final LocalDate sinceDate);
	
	Set<TimeSheetWeekStatusVM> findWeeklyStatusesByWeeks(final String resourceCode, final List<Week> weeks);
	
	List<WeeklyTimeSheet> findAllTimeSheetsByDateRange(final String resourceCode, final DateRange dateRange, final DayOfWeek weekStartDay, final DayOfWeek weekEndDay);
	
	List<WeeklyTimeSheet> findTimeSheetsByWeeks(final String resourceCode, final List<Week> weeks);
}
