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
@ToString
public class TimeSheetWeekStatusVMList extends ResourceSupport {

	private int pendingCount;

	private List<TimeSheetWeekStatusVM> weekStatuses = new ArrayList<>();

	public static TimeSheetWeekStatusVMList of(List<TimeSheetWeekStatusVM> statuses) {
		TimeSheetWeekStatusVMList statusList = new TimeSheetWeekStatusVMList();
		statusList.weekStatuses = statuses;
		statusList.pendingCount = statusList.pendingCount + statuses.size();
		return statusList;
	}

	public void add(final TimeSheetWeekStatusVM weekStatus) {
		weekStatuses.add(weekStatus);
		this.pendingCount++;
	}

	public void addAll(final List<TimeSheetWeekStatusVM> weekStatuses) {
		weekStatuses.addAll(weekStatuses);
		this.pendingCount = this.pendingCount + weekStatuses.size();
	}
}
