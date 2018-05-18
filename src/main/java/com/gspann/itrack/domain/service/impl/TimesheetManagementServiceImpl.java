package com.gspann.itrack.domain.service.impl;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gspann.itrack.adapter.persistence.repository.AllocationRepository;
import com.gspann.itrack.adapter.persistence.repository.TimeSheetRepository;
import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.domain.model.common.dto.DayDTO;
import com.gspann.itrack.domain.model.common.dto.ProjectAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceProjectAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.TimeSheetSubmissionPageVM;
import com.gspann.itrack.domain.model.common.dto.WeekDTO;
import com.gspann.itrack.domain.service.api.TimesheetManagementService;

import lombok.experimental.var;

@Service
public class TimesheetManagementServiceImpl implements TimesheetManagementService {

	@Autowired
	private TimeSheetRepository timeSheetRepository;

	@Autowired
	private AllocationRepository allocationRepository;

	@Override
	public TimeSheetSubmissionPageVM getTimeSheetSubmissionPageVM(String resourceCode) {
		List<ResourceProjectAllocationSummary> allocationSummaries = allocationRepository
				.findAllAllocationSummaries(resourceCode);
		if (allocationSummaries.size() > 0) {
			ResourceAllocationSummary resourceAllocationSummary = ResourceAllocationSummary
					.of(allocationSummaries.get(0).resourceCode(), allocationSummaries.get(0).resourceName());
			for (var allocationSummary : allocationSummaries) {
				resourceAllocationSummary
						.addProjectAllocation(ProjectAllocationSummary.of(allocationSummary.projectCode(),
								allocationSummary.projectName(), allocationSummary.projectTypeCode(),
								allocationSummary.projectTypeName(), allocationSummary.proportion()));
			}
			

			WeekDTO weekDTO = WeekDTO.current(ApplicationConstant.WEEKLY_STANDARD_HOURS, ApplicationConstant.DAILY_STANDARD_HOURS);
			
			LocalDate now = LocalDate.now();
		    LocalDate startDate = now.with(TemporalAdjusters.previousOrSame(ApplicationConstant.WEEK_START_DAY));
		    LocalDate endDate = now.with(TemporalAdjusters.nextOrSame(ApplicationConstant.WEEK_START_DAY));

			Set<LocalDate> holidays = new HashSet<LocalDate>();
			holidays.add(startDate.plusDays(3));
			
			while(!startDate.isEqual(endDate)) {
				if(holidays.contains(startDate)) {
					weekDTO.addDailyDTO(DayDTO.ofHoliday(startDate, "Diwali"));
				} else if(startDate.getDayOfWeek() == ApplicationConstant.WEEK_END_FIRST
						|| startDate.getDayOfWeek() == ApplicationConstant.WEEK_END_SECOND) {
					weekDTO.addDailyDTO(DayDTO.ofWeekend(startDate));
				} else {
					weekDTO.addDailyDTO(DayDTO.ofWorkingDay(startDate));
				}
				startDate = startDate.plusDays(1);
			}
			return TimeSheetSubmissionPageVM.of(resourceAllocationSummary, weekDTO);
		} else {
			return null;
		}
	}

}
