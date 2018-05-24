package com.gspann.itrack.domain.service.api;

import java.time.LocalDate;
import java.util.Optional;

import com.gspann.itrack.domain.model.common.dto.TimeSheetDTO;
import com.gspann.itrack.domain.model.common.dto.TimeSheetMetaDataVM;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;

public interface TimesheetManagementService {

//	public void getTimeSheetVM();
//	public void saveTimeSheet();
//	public void submitTimesheet();
//	public void approveTimesheet();
//	public void approveTimesheet(final long timesheetId, String... projectCode);
//	public void rejectTimesheet();
//	public void rejectTimesheet(final long timesheetId, String... projectCode);
//	public void getTimeSheetsByApprover(final String approverCode, final Week week);
//	public void getTimeSheetsPendingForApproval(final String approverCode, final Week week);
//	public void getTimeSheetsWithPendingActions(final String approverCode, final Week week);
	
	public TimeSheetMetaDataVM getTimeSheetMetaData(final String resourceCode);
	
	public Optional<WeeklyTimeSheet> saveOrSubmitTimeSheet(final TimeSheetDTO timesheet);

	public Optional<WeeklyTimeSheet> findTimeSheetById(final long id);
	
	public Optional<WeeklyTimeSheet> findTimeSheetByResourceCodeAndDate(final String resourceCode, final LocalDate date);

	public Optional<WeeklyTimeSheet> findTimeSheetByResourceCodeAndStatus(final String resourceCode, final TimesheetStatus status);
}
