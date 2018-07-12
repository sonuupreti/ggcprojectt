package com.gspann.itrack.domain.service.api;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.gspann.itrack.domain.model.allocations.ResourceAllocationSummary;
import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.dto.TimeSheetDTO;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetActorType;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResource;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceList;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceWeekStatusVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;

public interface TimesheetManagementService {

	// public void getTimeSheetVM();
	// public void saveTimeSheet();
	// public void submitTimesheet();
	// public void getTimeSheetsByApprover(final String approverCode, final Week
	// week);
	// public void getTimeSheetsPendingForApproval(final String approverCode, final
	// Week week);
	// public void getTimeSheetsWithPendingActions(final String approverCode, final
	// Week week);

	public Optional<ResourceAllocationSummary> getResourceAllocationSummary(final String resourceCode, final Week week);

	public Optional<TimeSheetResource> getTimeSheetVMByResource(final String resourceCode,
			final TimeSheetActorType actor);

	public Optional<TimeSheetResource> getTimeSheetVMByResourceAndWeek(final String resourceCode, final Week week,
			final TimeSheetActorType actor);

	public Optional<TimeSheetResource> getTimeSheetVMByIdAndResourceCode(final long timesheetId,
			final String resourceCode, final TimeSheetActorType actor);

	public TimeSheetResourceList listTimeSheetVMsByResourceAndMonth(final String resourceCode, final YearMonth month,
			final TimeSheetActorType actor);

	public Optional<WeeklyTimeSheet> saveOrSubmitTimeSheet(final String resourceCode, final TimeSheetDTO timesheet);

	public Optional<WeeklyTimeSheet> getTimeSheetById(final long id);

	public Optional<WeeklyTimeSheet> getTimeSheetByResourceAndWeek(final String resourceCode, final Week week);

	public Optional<WeeklyTimeSheet> getTimeSheetByResourceAndDate(final String resourceCode, final LocalDate date);

	public Optional<WeeklyTimeSheet> getTimeSheetByIdAndResourceCode(final long timesheetId, final String resourceCode);

	public Set<TimeSheetWeekStatusVM> getWeeklyStatusesByWeeks(final String resourceCode, final List<Week> weeks);

	public Set<TimeSheetWeekStatusVM> getWeeklyStatusesSinceMonths(final String resourceCode, final int months);

	public Set<TimeSheetWeekStatusVM> getPendingWeeklyActionsForResourceSinceMonths(final String resourceCode, final int months);

	public Set<TimeSheetWeekStatusVM> getPendingWeeklyActionsForResourceByMonth(final String resourceCode, final YearMonth month);

	public Set<TimeSheetResourceWeekStatusVM> getPendingWeeklyActionsForApproverSinceMonths(final String approverCode, final int months);

	public Set<TimeSheetResourceWeekStatusVM> getPendingWeeklyActionsForApproverByMonth(final String approverCode, final YearMonth month);

	public Map<Week, TimeSheetWeekStatusVM> getWeeklyStatusesForNextAndPreviousWeeks(final String resourceCode,
			final Week week);

	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndMonth(final String resourceCode, final YearMonth month);

	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndDateRange(final String resourceCode,
			final DateRange dateRange);

	public List<WeeklyTimeSheet> listTimeSheetsByResourceSinceDate(final String resourceCode, final LocalDate date);

	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndStatus(final String resourceCode,
			final TimesheetStatus status);

	public List<WeeklyTimeSheet> listRecentTimeSheetsByResource(final String resourceCode, final int pageSize);

	public void approveTimesheet(final long timesheetId, final String approverCode);

	public void approveTimesheet(final long timesheetId, final String projectCode, final String approverCode);

	public void rejectTimesheet(final long timesheetId, final String approverCode);

	public void rejectTimesheet(final long timesheetId, final String projectCode, final String approverCode);
}
