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
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResource;
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

	public Optional<TimeSheetResource> getTimeSheetVMByResource(final String resourceCode);

	public Optional<TimeSheetResource> getTimeSheetVMByResourceAndWeek(final String resourceCode, final Week week);

	public Optional<TimeSheetResource> getTimeSheetVMByIdAndResourceCode(final long timesheetId, final String resourceCode);

	public Optional<WeeklyTimeSheet> saveOrSubmitTimeSheet(final String resourceCode, final TimeSheetDTO timesheet);

	public Optional<WeeklyTimeSheet> getTimeSheetById(final long id);

	public Optional<WeeklyTimeSheet> getTimeSheetByResourceAndWeek(final String resourceCode, final Week week);

	public Optional<WeeklyTimeSheet> getTimeSheetByResourceAndDate(final String resourceCode, final LocalDate date);

	public Optional<WeeklyTimeSheet> getTimeSheetByIdAndResourceCode(final long timesheetId, final String resourceCode);
	
	public Set<TimeSheetWeekStatusVM> getWeeklyStatusesByWeeks(String resourceCode, List<Week> weeks);
	
	public Map<Week, TimeSheetWeekStatusVM> getWeeklyStatusesForNextAndPreviousWeeks(String resourceCode, Week week);

	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndMonth(final String resourceCode, final YearMonth month);

	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndDateRange(final String resourceCode, final DateRange dateRange);

	public List<WeeklyTimeSheet> listTimeSheetsByResourceSinceDate(final String resourceCode, final LocalDate date);

	public List<WeeklyTimeSheet> listTimeSheetsByResourceAndStatus(final String resourceCode, final TimesheetStatus status);

	public List<WeeklyTimeSheet> listRecentTimeSheetsByResource(final String resourceCode, final int pageSize);

	public void approveTimesheet(final long timesheetId);

	public void approveTimesheet(final long timesheetId, final String projectCode);

	public void rejectTimesheet(final long timesheetId);

	public void rejectTimesheet(final long timesheetId, final String projectCode);
}
