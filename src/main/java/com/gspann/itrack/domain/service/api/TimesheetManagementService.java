package com.gspann.itrack.domain.service.api;

import com.gspann.itrack.domain.model.common.dto.TimeSheetSubmissionPageVM;
import com.gspann.itrack.domain.model.timesheets.Week;

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
	
	public TimeSheetSubmissionPageVM getTimeSheetSubmissionPageVM(final String resourceCode);
}
