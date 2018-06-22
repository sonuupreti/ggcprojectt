package com.gspann.itrack.domain.model.timesheets.vm;

import java.time.Duration;
import java.time.LocalDate;

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
public class DailyTotalEntry {

	private LocalDate date;

	private Duration totalHours;
	
	private String dailyComment;
}
