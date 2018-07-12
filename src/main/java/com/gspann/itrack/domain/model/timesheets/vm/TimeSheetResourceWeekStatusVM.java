package com.gspann.itrack.domain.model.timesheets.vm;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gspann.itrack.domain.model.staff.ResourceIdentity;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.Week;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = { "week", "resource" }, callSuper = false)
@ToString
public class TimeSheetResourceWeekStatusVM extends ResourceSupport {

	@JsonInclude(NON_NULL)
	private ResourceIdentity resource;

	private WeekVM week;

	private TimeSheetStatusTypeVM status;

	private TimeSheetActionTypeVM[] actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.NONE };

	@JsonInclude(NON_DEFAULT)
	private long timesheetId;

	public TimeSheetResourceWeekStatusVM(final String resourceCode, final String resourceName,
			final byte[] resourceImage, final Week week, final TimesheetStatus status, final long timesheetId) {
		this.resource = new ResourceIdentity(resourceCode, resourceName, resourceImage);
		week.updateWeekName();
		this.week = WeekVM.of(week.name(), week.startingFrom(), week.endingOn());
		this.status = TimeSheetStatusTypeVM.valueOf(status.name());
		this.timesheetId = timesheetId;
		setActions();
	}

	public void setActions() {
		if (this.status == TimeSheetStatusTypeVM.SAVED || status == TimeSheetStatusTypeVM.APPROVED
				|| status == TimeSheetStatusTypeVM.REJECTED
				|| this.status == TimeSheetStatusTypeVM.PENDING_SUBMISSION) {
			actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.NONE };
		} else if (status == TimeSheetStatusTypeVM.SUBMITTED) {
			actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.APPROVE, TimeSheetActionTypeVM.REJECT };
		}
	}

	/*
	 * public static TimeSheetResourceWeekStatusVM ofSubmittedTimeSheet(final
	 * LocalDate weekStartDate, final long timesheetId) {
	 * TimeSheetResourceWeekStatusVM weekMetaData = new
	 * TimeSheetResourceWeekStatusVM(); weekMetaData.timesheetId = timesheetId; Week
	 * inputWeek = Week.of(weekStartDate); weekMetaData.week =
	 * WeekVM.of(inputWeek.name(), inputWeek.startingFrom(), inputWeek.endingOn());
	 * weekMetaData.status = TimeSheetStatusTypeVM.SUBMITTED;
	 * weekMetaData.setActions(); return weekMetaData; }
	 */

	public Week toWeek() {
		return Week.of(this.week.getWeekStartDate());
	}
}
