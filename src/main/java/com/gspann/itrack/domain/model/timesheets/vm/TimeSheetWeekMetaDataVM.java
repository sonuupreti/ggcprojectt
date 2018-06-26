package com.gspann.itrack.domain.model.timesheets.vm;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gspann.itrack.domain.model.timesheets.Week;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class TimeSheetWeekMetaDataVM {
	
	private String weekName;

	private LocalDate weekStartDate;

	private LocalDate weekEndDate;

	@JsonInclude(NON_DEFAULT)
	private int weekLength;

	@JsonInclude(NON_NULL)
	private DayOfWeek weekStartDay;

	@JsonInclude(NON_NULL)
	private DayOfWeek weekEndDay;

	@JsonInclude(NON_DEFAULT)
	private int dailyStandardHours;

	@JsonInclude(NON_DEFAULT)
	private int weeklyStandardHours;

	@JsonInclude(NON_DEFAULT)
	private boolean flexibleWeekends;

	@JsonInclude(NON_EMPTY)
	private Set<TimeSheetDayMetaDataVM> dayDetails;

	public static TimeSheetWeekMetaDataVM current(final Duration weeklyStandardHours, final Duration dailyStandardHours,
			final DayOfWeek weekStartDay, final DayOfWeek weekEndDay) {
		TimeSheetWeekMetaDataVM weekVM = new TimeSheetWeekMetaDataVM();
		Week week = Week.current(weekStartDay, weekEndDay);
		weekVM.weekName = week.name();
		weekVM.weekStartDate = week.startingFrom();
		weekVM.weekEndDate = week.endingOn();
		weekVM.weekLength = (int) week.duration().toDays() + 1;
		weekVM.weekStartDay = weekVM.weekStartDate.getDayOfWeek();
		weekVM.weekEndDay = weekVM.weekEndDate.getDayOfWeek();
		weekVM.weeklyStandardHours = (int) weeklyStandardHours.toHours();
		weekVM.dailyStandardHours = (int) dailyStandardHours.toHours();
		weekVM.dayDetails = new TreeSet<TimeSheetDayMetaDataVM>((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
		return weekVM;
	}

	public static TimeSheetWeekMetaDataVM of(final LocalDate weekStartDate, final LocalDate weekEndDate,
			final Duration weeklyStandardHours, final Duration dailyStandardHours) {
		TimeSheetWeekMetaDataVM weekVM = new TimeSheetWeekMetaDataVM();
		Week week = Week.of(weekStartDate, weekEndDate);
		weekVM.weekName = week.name();
		weekVM.weekStartDate = week.startingFrom();
		weekVM.weekEndDate = week.endingOn();
		weekVM.weekLength = (int) week.duration().toDays() + 1;
		weekVM.weekStartDay = weekStartDate.getDayOfWeek();
		weekVM.weekEndDay = weekEndDate.getDayOfWeek();
		weekVM.weeklyStandardHours = (int) weeklyStandardHours.toHours();
		weekVM.dailyStandardHours = (int) dailyStandardHours.toHours();
		weekVM.dayDetails = new TreeSet<TimeSheetDayMetaDataVM>((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
		return weekVM;
	}

	public static TimeSheetWeekMetaDataVM ofWeek(final Week week, final Duration weeklyStandardHours,
			final Duration dailyStandardHours) {
		return of(week.startingFrom(), week.endingOn(), weeklyStandardHours, dailyStandardHours);
	}
	

	public static TimeSheetWeekMetaDataVM ofWeek(final Week week) {
		TimeSheetWeekMetaDataVM weekVM = new TimeSheetWeekMetaDataVM();
		weekVM.weekStartDate = week.startingFrom();
		weekVM.weekEndDate = week.endingOn();
		weekVM.weekName = week.name();
		return weekVM;
	}

	public TimeSheetWeekMetaDataVM addDayVM(final TimeSheetDayMetaDataVM dayDTO) {
		this.dayDetails.add(dayDTO);
		// TODO: Throw exception if number of entries exceeds week length
		return this;
	}
}
