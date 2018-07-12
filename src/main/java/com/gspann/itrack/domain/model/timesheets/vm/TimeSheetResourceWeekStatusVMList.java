package com.gspann.itrack.domain.model.timesheets.vm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor(staticName = "of")
@ToString
public class TimeSheetResourceWeekStatusVMList  extends ResourceSupport {

	private List<TimeSheetResourceWeekStatusVM> resourceWeekStatuses = new ArrayList<>();
	
	public TimeSheetResourceWeekStatusVMList of(final List<TimeSheetResourceWeekStatusVM> resourceWeekStatuses) {
		TimeSheetResourceWeekStatusVMList timeSheetResourceWeekStatusVMList = new TimeSheetResourceWeekStatusVMList();
		timeSheetResourceWeekStatusVMList.resourceWeekStatuses = resourceWeekStatuses;
		return timeSheetResourceWeekStatusVMList;
	}

	public void add(final TimeSheetResourceWeekStatusVM resourceWeekStatus) {
		resourceWeekStatuses.add(resourceWeekStatus);
	}
	
	public void addAll(final List<TimeSheetWeekStatusVM> resourceWeekStatuses) {
		resourceWeekStatuses.addAll(resourceWeekStatuses);
	}
}
