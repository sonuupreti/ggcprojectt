package com.gspann.itrack.domain.service.impl;

import java.time.LocalDate;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.common.Toggle;
import com.gspann.itrack.domain.service.api.ResourceManagementService;

public class ResourceManagementServiceImpl implements ResourceManagementService {

	@Override
	public void addResource() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBoardToBench(String resourceCode, LocalDate joiningDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBoardToProject(String resourceCode, LocalDate joiningDate, Money hourlyRate, String projectCode,
			LocalDate fromDate, LocalDate tillDate, Toggle onboardedToClientTimeTrackingsystem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void allocateResourceToLeaveProjectsImplicitly(String resourceCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void allocateResourceToProject(String resourceCode) {
		// TODO Auto-generated method stub

	}

}
