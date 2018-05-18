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
import lombok.experimental.Accessors;

@Getter
@Setter
//@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class WeekDTO {

	private LocalDate weekStartDate;

	private LocalDate weekEndDate;

	private int weekLength;

	private DayOfWeek weekStartDay;

	private DayOfWeek weekEndDay;

	private Duration dailyStandardHours;

	private Duration weeklyStandardHours;
	
	private boolean flexibleWeekends = false;

	private Set<DayDTO> dailyDetails;

	public static WeekDTO current(final Duration weeklyStandardHours, final Duration dailyStandardHours) {
		LocalDate now = LocalDate.now();

		WeekDTO weekDTO = new WeekDTO();
		weekDTO.weekStartDate = now.with(TemporalAdjusters.previousOrSame(ApplicationConstant.WEEK_START_DAY));
		weekDTO.weekEndDate = now.with(TemporalAdjusters.nextOrSame(ApplicationConstant.WEEK_START_DAY)).minusDays(1);
		weekDTO.weekLength = 7;
		weekDTO.weekStartDay = weekDTO.weekStartDate.getDayOfWeek();
		weekDTO.weekEndDay = weekDTO.weekEndDate.getDayOfWeek();
		weekDTO.weeklyStandardHours = weeklyStandardHours;
		weekDTO.dailyStandardHours = dailyStandardHours;
		weekDTO.dailyDetails = new TreeSet<DayDTO>((DayDTO o1, DayDTO o2) -> o1.getDate().compareTo(o2.getDate()));
		return weekDTO;
	}

	public static WeekDTO of(final LocalDate weekStartDate, final LocalDate weekEndDate,
			final Duration weeklyStandardHours, final Duration dailyStandardHours) {
		WeekDTO weekDTO = new WeekDTO();
		weekDTO.weekStartDate = weekStartDate;
		weekDTO.weekEndDate = weekEndDate;
		weekDTO.weekLength = Long.valueOf(ChronoUnit.DAYS.between(weekStartDate, weekEndDate)).intValue();
		weekDTO.weekStartDay = weekStartDate.getDayOfWeek();
		weekDTO.weekEndDay = weekDTO.weekEndDate.getDayOfWeek();
		weekDTO.weeklyStandardHours = weeklyStandardHours;
		weekDTO.dailyStandardHours = dailyStandardHours;
		weekDTO.dailyDetails = new TreeSet<DayDTO>((DayDTO o1, DayDTO o2) -> o1.getDate().compareTo(o2.getDate()));
		return weekDTO;
	}

	public WeekDTO addDailyDTO(final DayDTO dayDTO) {
		this.dailyDetails.add(dayDTO);
		// TODO: Throw exception of number of entries exceeds week length
		return this;
	}
}
