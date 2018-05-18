package com.gspann.itrack.domain.service.api;

import java.time.LocalDate;

import org.javamoney.moneta.Money;

import com.gspann.itrack.domain.model.common.Toggle;

public interface ResourceManagementService {

	public void addResource();

	public void onBoardToBench(final String resourceCode, final LocalDate joiningDate);

	public void onBoardToProject(final String resourceCode, final String projectCode, final Money hourlyRate,
			final LocalDate joiningDate, final Toggle onboardedToClientTimeTrackingsystem);
	
	public void onBoardToProjectPartially(final String resourceCode, final String projectCode, final short percentage, final Money hourlyRate,
			final LocalDate joiningDate, final Toggle onboardedToClientTimeTrackingsystem);

	public void allocate(final String resourceCode, final String projectCode, final Money hourlyRate,
			final LocalDate fromDate, final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem);
	
	public void allocatePartially(final String resourceCode, final String projectCode, final short percentage, final Money hourlyRate,
			final LocalDate fromDate, final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem);

}
