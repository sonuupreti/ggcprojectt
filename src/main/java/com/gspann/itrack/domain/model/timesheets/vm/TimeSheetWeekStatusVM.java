package com.gspann.itrack.domain.model.timesheets.vm;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@EqualsAndHashCode(of = "week", callSuper = false)
@ToString
public class TimeSheetWeekStatusVM extends ResourceSupport {

	private WeekVM week;

	private TimeSheetStatusTypeVM status;

	private TimeSheetActionTypeVM[] actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.NONE };

	@JsonInclude(NON_DEFAULT)
	private long timesheetId;

	public TimeSheetWeekStatusVM(final Week week, final TimesheetStatus status, final long timesheetId) {
		week.updateWeekName();
		this.week = WeekVM.of(week.name(), week.startingFrom(), week.endingOn());
		this.status = TimeSheetStatusTypeVM.valueOf(status.name());
		this.timesheetId = timesheetId;
		setActions();
	}

	public void setActions() {
		if (this.status == TimeSheetStatusTypeVM.SAVED || this.status == TimeSheetStatusTypeVM.PENDING_SUBMISSION) {
			actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.SAVE, TimeSheetActionTypeVM.SUBMIT };
		} else if (status == TimeSheetStatusTypeVM.SUBMITTED || status == TimeSheetStatusTypeVM.APPROVED) {
			actions = new TimeSheetActionTypeVM[] { TimeSheetActionTypeVM.NONE };
		}
	}

	public static TimeSheetWeekStatusVM ofStartingDatePendingForSubmission(final LocalDate weekStartDate) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		Week inputWeek = Week.of(weekStartDate);
		weekMetaData.week = WeekVM.of(inputWeek.name(), inputWeek.startingFrom(), inputWeek.endingOn());
		weekMetaData.status = TimeSheetStatusTypeVM.PENDING_SUBMISSION;
		weekMetaData.setActions();
		return weekMetaData;
	}

	public static TimeSheetWeekStatusVM ofCurrentWeekPendingForSubmission(final DayOfWeek weekStartDay,
			final DayOfWeek weekEndDay) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		Week inputWeek = Week.current(weekStartDay, weekEndDay);
		weekMetaData.week = WeekVM.of(inputWeek.name(), inputWeek.startingFrom(), inputWeek.endingOn());
		weekMetaData.status = TimeSheetStatusTypeVM.PENDING_SUBMISSION;
		weekMetaData.setActions();
		return weekMetaData;
	}

	public static TimeSheetWeekStatusVM ofWeekPendingForSubmission(final Week week) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		weekMetaData.week = WeekVM.of(week.name(), week.startingFrom(), week.endingOn());
		weekMetaData.status = TimeSheetStatusTypeVM.PENDING_SUBMISSION;
		weekMetaData.setActions();
		return weekMetaData;
	}

	public static TimeSheetWeekStatusVM ofSavedTimeSheet(final LocalDate weekStartDate, final long timesheetId) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		weekMetaData.timesheetId = timesheetId;
		Week inputWeek = Week.of(weekStartDate);
		weekMetaData.week = WeekVM.of(inputWeek.name(), inputWeek.startingFrom(), inputWeek.endingOn());
		weekMetaData.status = TimeSheetStatusTypeVM.SAVED;
		weekMetaData.setActions();
		return weekMetaData;
	}

	public static TimeSheetWeekStatusVM ofSubmittedTimeSheet(final LocalDate weekStartDate, final long timesheetId) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		weekMetaData.timesheetId = timesheetId;
		Week inputWeek = Week.of(weekStartDate);
		weekMetaData.week = WeekVM.of(inputWeek.name(), inputWeek.startingFrom(), inputWeek.endingOn());
		weekMetaData.status = TimeSheetStatusTypeVM.SUBMITTED;
		weekMetaData.setActions();
		return weekMetaData;
	}

	public static TimeSheetWeekStatusVM ofExistingTimeSheet(final LocalDate weekStartDate, final long timesheetId,
			final TimeSheetStatusTypeVM status) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		weekMetaData.timesheetId = timesheetId;
		Week inputWeek = Week.of(weekStartDate);
		weekMetaData.week = WeekVM.of(inputWeek.name(), inputWeek.startingFrom(), inputWeek.endingOn());
		weekMetaData.status = status;
		weekMetaData.setActions();
		return weekMetaData;
	}

	public Week toWeek() {
		return Week.of(this.week.getWeekStartDate());
	}
}
