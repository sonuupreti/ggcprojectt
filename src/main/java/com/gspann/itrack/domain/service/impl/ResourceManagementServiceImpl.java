package com.gspann.itrack.domain.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gspann.itrack.adapter.persistence.repository.ProjectRepository;
import com.gspann.itrack.adapter.persistence.repository.ResourceRepository;
import com.gspann.itrack.domain.model.common.Toggle;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.service.api.ResourceManagementService;

import lombok.experimental.var;

@Service
public class ResourceManagementServiceImpl implements ResourceManagementService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public void addResource() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBoardToBench(final String resourceCode, final LocalDate joiningDate) {
		List<Project> benches = projectRepository.findAllBenchProjects();

		Resource resource = resourceRepository.findById(resourceCode).get();
		resource.allocate().in(benches.get(0)).fully().onboardedToClientTimeTrackingSystem(Toggle.NO)
				.startingIndefinatelyFrom(joiningDate).asNonBillable();
		
		resource.onBoarded(joiningDate, EmploymentStatus.onBench());

		allocateResourceToLeaveProjectsImplicitly(resourceCode, joiningDate);
	}

	@Override
	public void onBoardToProject(final String resourceCode, final String projectCode, final Money hourlyRate,
			final LocalDate joiningDate, final Toggle onboardedToClientTimeTrackingsystem) {

		Resource resource = resourceRepository.findById(resourceCode).get();
		resource.allocate().in(projectRepository.getOne(projectCode)).fully()
				.onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem)
				.startingIndefinatelyFrom(joiningDate).asNonBillable();
		
		resource.onBoarded(joiningDate, EmploymentStatus.active());

		allocateResourceToLeaveProjectsImplicitly(resourceCode, joiningDate);
	}

	@Override
	public void onBoardToProjectPartially(final String resourceCode, final String projectCode, final short percentage, final Money hourlyRate,
			final LocalDate joiningDate, final Toggle onboardedToClientTimeTrackingsystem) {

		Resource resource = resourceRepository.findById(resourceCode).get();
		
		resource.allocate().in(projectRepository.getOne(projectCode)).partially(percentage)
				.onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem)
				.startingIndefinatelyFrom(joiningDate).asNonBillable();
		
		resource.onBoarded(joiningDate, EmploymentStatus.active());

		allocateResourceToLeaveProjectsImplicitly(resourceCode, joiningDate);
	}

	@Override
	public void allocate(final String resourceCode, final String projectCode, final Money hourlyRate,
			final LocalDate fromDate, final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem) {

		Resource resource = resourceRepository.findById(resourceCode).get();
		
		resource.allocate().in(projectRepository.getOne(projectCode)).fully()
				.onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem)
				.startingFrom(fromDate).till(tillDate).atHourlyRateOf(hourlyRate);
		
//		resource.markActive();
	}
	
	@Override
	public void allocatePartially(final String resourceCode, final String projectCode, final short percentage, final Money hourlyRate,
			final LocalDate fromDate, final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem) {

		Resource resource = resourceRepository.findById(resourceCode).get();
		resource.allocate().in(projectRepository.getOne(projectCode)).partially(percentage)
				.onboardedToClientTimeTrackingSystem(onboardedToClientTimeTrackingsystem)
				.startingFrom(fromDate).till(tillDate).atHourlyRateOf(hourlyRate);
		
//		resource.markActive();

	}
	
	private void allocateResourceToLeaveProjectsImplicitly(final String resourceCode, final LocalDate joiningDate) {
		List<Project> leaveProjects = projectRepository.findAllLeaveProjects();

		Resource resource = resourceRepository.findById(resourceCode).get();
		
		for (var leave : leaveProjects) {
			resource.allocate().in(leave).fully().onboardedToClientTimeTrackingSystem(Toggle.NO)
					.startingIndefinatelyFrom(joiningDate).asNonBillable();
		}
	}
}
