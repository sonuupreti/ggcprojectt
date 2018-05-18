package com.gspann.itrack.domain.model.common.dto;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

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
public class WeekDTO {
	
	@NotNull
	private LocalDate weekStartDate;
	
	private Set<DayDTO> dailyEntries = new LinkedHashSet<>(7);
}
