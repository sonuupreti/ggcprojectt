package com.gspann.itrack.domain.model.common.dto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class WeekDTO {

	private LocalDate weekStartDate;

	private DayOfWeek weekStartDay;

	private Duration dailyStandardHours;

	private Duration weeklyStandardHours;

	private List<DayDTO> dailyDTOs;

	public WeekDTO of(final LocalDate weekStartDate, final Duration dailyStandardHours,
			final Duration weeklyStandardHours) {
		WeekDTO weekDTO = new WeekDTO();
		weekDTO.weekStartDate = weekStartDate;
		weekDTO.weekStartDay = weekStartDate.getDayOfWeek();
		weekDTO.dailyStandardHours = dailyStandardHours;
		weekDTO.weeklyStandardHours = weeklyStandardHours;
		weekDTO.dailyDTOs = new ArrayList<>(7);
		return weekDTO;
	}
	
	public WeekDTO addDailyDTO(final DayDTO dayDTO) {
		this.dailyDTOs.add(dayDTO);
		return this;
	}
}
