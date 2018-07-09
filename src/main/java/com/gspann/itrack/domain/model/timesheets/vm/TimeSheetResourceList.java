package com.gspann.itrack.domain.model.timesheets.vm;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
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

	@JsonInclude(NON_NULL)
	private LocalDate systemStartDate;

	@JsonInclude(NON_NULL)
	private MonthVM monthDetails;

	@JsonInclude(NON_NULL)
	private Pair<String, String> resource;

	@JsonInclude(NON_NULL)
	private MonthVM nextMonth;

	@JsonInclude(NON_NULL)
	private MonthVM previousMonth;

	private List<TimeSheetResource> timesheets = new ArrayList<>();

	public static TimeSheetResourceList of(final LocalDate systemStartDate, final YearMonth month,
			final Pair<String, String> resource) {
		TimeSheetResourceList timeSheetResourceList = new TimeSheetResourceList();
		timeSheetResourceList.systemStartDate = systemStartDate;
		timeSheetResourceList.monthDetails = MonthVM.of(month);
		timeSheetResourceList.resource = resource;
		return timeSheetResourceList;
	}

	public void add(final TimeSheetResource timesheet) {
		timesheets.add(timesheet);
	}

	public void addAll(final List<TimeSheetResource> timesheets) {
		timesheets.addAll(timesheets);
	}
}
