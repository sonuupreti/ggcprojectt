package com.gspann.itrack.domain.model.timesheets.vm;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
@EqualsAndHashCode(of = "week")
@ToString
public class TimeSheetWeekStatusVM {

	private WeekVM week;

	private TimeSheetStatusTypeVM status;

	@JsonInclude(NON_DEFAULT)
	private long timesheetId;

	public TimeSheetWeekStatusVM(final Week week, final TimesheetStatus status, final long timesheetId) {
		week.updateWeekName();
		this.week = WeekVM.of(week.name(), week.startingFrom(), week.endingOn());
		this.status = TimeSheetStatusTypeVM.valueOf(status.name());
		this.timesheetId = timesheetId;
	}

	public static TimeSheetWeekStatusVM ofStartingDatePendingForSubmission(final LocalDate weekStartDate) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		Week inputWeek = Week.of(weekStartDate);
		weekMetaData.week = WeekVM.of(inputWeek.name(), inputWeek.startingFrom(), inputWeek.endingOn());
		weekMetaData.status = TimeSheetStatusTypeVM.PENDING_SUBMISSION;
		return weekMetaData;
	}

	public static TimeSheetWeekStatusVM ofCurrentWeekPendingForSubmission(final DayOfWeek weekStartDay,
			final DayOfWeek weekEndDay) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		Week inputWeek = Week.current(weekStartDay, weekEndDay);
		weekMetaData.week = WeekVM.of(inputWeek.name(), inputWeek.startingFrom(), inputWeek.endingOn());
		weekMetaData.status = TimeSheetStatusTypeVM.PENDING_SUBMISSION;
		return weekMetaData;
	}

	public static TimeSheetWeekStatusVM ofWeekPendingForSubmission(final Week week) {
		TimeSheetWeekStatusVM weekMetaData = new TimeSheetWeekStatusVM();
		weekMetaData.week = WeekVM.of(week.name(), week.startingFrom(), week.endingOn());
		weekMetaData.status = TimeSheetStatusTypeVM.PENDING_SUBMISSION;
		return weekMetaData;
	}
	
	public Week toWeek() {
		return Week.of(this.week.getWeekStartDate());
	}
	
	public TimeSheetWeekStatusVM setWeekName() {
		
		return this;
	}
}
