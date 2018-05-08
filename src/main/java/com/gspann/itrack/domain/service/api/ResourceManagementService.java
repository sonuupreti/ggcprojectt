package com.gspann.itrack.domain.service.api;

import java.time.LocalDate;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.common.Toggle;

public interface ResourceManagementService {
	
	public void addResource();

	public void onBoardToBench(final String resourceCode, final LocalDate joiningDate);
	
	public void onBoardToProject(final String resourceCode, final LocalDate joiningDate, final Money hourlyRate, final String projectCode, final LocalDate fromDate,
			final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem);
	
	public void allocateResourceToLeaveProjectsImplicitly(final String resourceCode);

	public void allocateResourceToProject(final String resourceCode);
}
