package com.gspann.itrack.domain.model.common.dto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;
import java.util.TreeSet;

import com.gspann.itrack.common.constants.ApplicationConstant;

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
public class WeekVM {

	private LocalDate weekStartDate;

	private LocalDate weekEndDate;

	private int weekLength;

	private DayOfWeek weekStartDay;

	private DayOfWeek weekEndDay;

//	private Duration dailyStandardHours;
//
//	private Duration weeklyStandardHours;
	
	private int dailyStandardHours;

	private int weeklyStandardHours;
	
	private boolean flexibleWeekends = false;

	private Set<DayVM> dailyDetails;

	public static WeekVM current(final Duration weeklyStandardHours, final Duration dailyStandardHours) {
		LocalDate now = LocalDate.now();

		WeekVM weekVM = new WeekVM();
		weekVM.weekStartDate = now.with(TemporalAdjusters.previousOrSame(ApplicationConstant.WEEK_START_DAY));
		weekVM.weekEndDate = now.with(TemporalAdjusters.nextOrSame(ApplicationConstant.WEEK_START_DAY)).minusDays(1);
		weekVM.weekLength = 7;
		weekVM.weekStartDay = weekVM.weekStartDate.getDayOfWeek();
		weekVM.weekEndDay = weekVM.weekEndDate.getDayOfWeek();
		weekVM.weeklyStandardHours = (int) weeklyStandardHours.toHours();
		weekVM.dailyStandardHours = (int) dailyStandardHours.toHours();
		weekVM.dailyDetails = new TreeSet<DayVM>((DayVM o1, DayVM o2) -> o1.getDate().compareTo(o2.getDate()));
		return weekVM;
	}

	public static WeekVM of(final LocalDate weekStartDate, final LocalDate weekEndDate,
			final Duration weeklyStandardHours, final Duration dailyStandardHours) {
		WeekVM weekVM = new WeekVM();
		weekVM.weekStartDate = weekStartDate;
		weekVM.weekEndDate = weekEndDate;
		weekVM.weekLength = Long.valueOf(ChronoUnit.DAYS.between(weekStartDate, weekEndDate)).intValue();
		weekVM.weekStartDay = weekStartDate.getDayOfWeek();
		weekVM.weekEndDay = weekVM.weekEndDate.getDayOfWeek();
		weekVM.weeklyStandardHours = (int) weeklyStandardHours.toHours();
		weekVM.dailyStandardHours = (int) dailyStandardHours.toHours();
		weekVM.dailyDetails = new TreeSet<DayVM>((DayVM o1, DayVM o2) -> o1.getDate().compareTo(o2.getDate()));
		return weekVM;
	}

	public WeekVM addDayVM(final DayVM dayDTO) {
		this.dailyDetails.add(dayDTO);
		// TODO: Throw exception of number of entries exceeds week length
		return this;
	}
}
