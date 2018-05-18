package com.gspann.itrack.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gspann.itrack.adapter.persistence.repository.AllocationRepository;
import com.gspann.itrack.adapter.persistence.repository.TimeSheetRepository;
import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.common.dto.ProjectAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.ResourceProjectAllocationSummary;
import com.gspann.itrack.domain.model.common.dto.TimeSheetSubmissionPageVM;
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
//			WeekDTO
			
			// TimeSheetSubmissionPageVM timeSheetSubmissionPageVM =
			// TimeSheetSubmissionPageVM.of(resourceAllocationSummary, weekDTO);
			return null;
		} else {
			return null;
		}
	}

}
