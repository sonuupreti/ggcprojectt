package com.gspann.itrack.domain.model.timesheets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimeSheetDTO {
	
	private long timesheetId;

//	private String resourceCode;
	
	private String action;
	
	private WeekDTO week;
}
