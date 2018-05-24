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
public class TimeSheetVM {

	private long weeklyTimeSheetId;
	
	private WeekDTO weekDetails;

	private TimeSheetStatusType status;
	
	private TimeSheetMetaDataVM timesheetMetaData;
}
