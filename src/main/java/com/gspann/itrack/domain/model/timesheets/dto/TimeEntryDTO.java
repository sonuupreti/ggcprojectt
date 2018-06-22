package com.gspann.itrack.domain.model.timesheets.dto;

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
@EqualsAndHashCode(of = "projectCode")
public class TimeEntryDTO {
	
	@NotNull
	private String projectCode;
	
	@NotNull
	private int hours;
	
	private String comments;
}
