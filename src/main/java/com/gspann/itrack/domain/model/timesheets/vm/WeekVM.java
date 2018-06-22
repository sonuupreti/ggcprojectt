package com.gspann.itrack.domain.model.timesheets.vm;

import java.time.LocalDate;

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

	private String weekName;

	private LocalDate weekStartDate;

	private LocalDate weekEndDate;
}
