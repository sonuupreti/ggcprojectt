package com.gspann.itrack.domain.model.timesheets.dto;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

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
public class DayDTO {
	
	@NotNull
	private LocalDate date;
	
//	private DayType dayType;

	private String comments;
	
	private Set<TimeEntryDTO> timeEntries = new LinkedHashSet<>(5);

}
