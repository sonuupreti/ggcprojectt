package com.gspann.itrack.domain.model.common.dto;

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
	
	private long weeklyTimeSheetId;

	private String resourceCode;
	
	private String action;
	
	private WeekDTO week;
}
