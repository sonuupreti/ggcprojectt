package com.gspann.itrack.domain.model.timesheets.vm;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;

import com.gspann.itrack.domain.model.timesheets.DayType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode(of = "date")
public class ProjectWiseDailyEntryVM {

	private LocalDate date;

	private DayOfWeek day;

	private DayType type;

	private Duration hours;

	private String comments;
	
}
