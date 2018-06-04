package com.gspann.itrack.domain.model.common.dto;

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
public class TimeSheetMetaDataVM {
	
	private LocalDate systemStartDate;

	private ResourceAllocationSummary resourceAllocationSummary;
	
	private WeekVM weekDetails;
	
	private TimeSheetActionType[] actions;
}
