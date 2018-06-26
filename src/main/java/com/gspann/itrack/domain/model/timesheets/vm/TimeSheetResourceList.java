package com.gspann.itrack.domain.model.timesheets.vm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.gspann.itrack.domain.model.common.dto.Pair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TimeSheetResourceList extends ResourceSupport {

	private LocalDate systemStartDate;
	
	private Pair<String, String> resource;
	
	public static TimeSheetResourceList of(final LocalDate systemStartDate, final Pair<String, String> resource) {
		TimeSheetResourceList timeSheetResourceList = new TimeSheetResourceList();
		timeSheetResourceList.systemStartDate = systemStartDate;
		timeSheetResourceList.resource = resource;
		return timeSheetResourceList;
	}

	private List<TimeSheetResource> timesheets = new ArrayList<>();
	
	public void add(final TimeSheetResource timesheet) {
		timesheets.add(timesheet);
	}
	
	public void addAll(final List<TimeSheetResource> timesheets) {
		timesheets.addAll(timesheets);
	}
}
