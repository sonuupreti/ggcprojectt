package com.gspann.itrack.domain.service.api;

public interface TimesheetManagementService {

	public void getTimeSheetVM();
	public void saveTimeSheet();
	public void submitTimesheet();
	public void approveTimesheet();
	public void approveTimesheet(String projectCode, final long timesheetId);
	public void rejectTimesheet();
	public void rejectTimesheet(String projectCode, final long timesheetId);
	public void getTimeSheetsByApprover(final String approverCode);
	public void getTimeSheetsPendingForApproval(final String approverCode);
	public void getTimeSheetsWithPendingActions(final String approverCode);
}
