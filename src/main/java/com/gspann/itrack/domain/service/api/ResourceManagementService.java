package com.gspann.itrack.domain.service.api;

import java.time.LocalDate;

import org.javamoney.moneta.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gspann.itrack.domain.model.common.Toggle;
import com.gspann.itrack.domain.model.common.dto.ResourceDTO;
import com.gspann.itrack.domain.model.common.dto.ResourceOnLoadVM;

public interface ResourceManagementService {

	public ResourceDTO addResource(ResourceDTO resourceDTO);
	public ResourceOnLoadVM resourceOnLodPage();
	public Page<ResourceDTO> getAllResources(Pageable pageable);
	public void onBoardToBench(final String resourceCode, final LocalDate joiningDate);
	public ResourceDTO findById(String id);

	public void onBoardToProject(final String resourceCode, final String projectCode, final Money hourlyRate,
			final LocalDate joiningDate, final Toggle onboardedToClientTimeTrackingsystem);
	
	public void onBoardToProjectPartially(final String resourceCode, final String projectCode, final short percentage, final Money hourlyRate,
			final LocalDate joiningDate, final Toggle onboardedToClientTimeTrackingsystem);

	public void allocate(final String resourceCode, final String projectCode, final Money hourlyRate,
			final LocalDate fromDate, final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem);
	
	public void allocatePartially(final String resourceCode, final String projectCode, final short percentage, final Money hourlyRate,
			final LocalDate fromDate, final LocalDate tillDate, final Toggle onboardedToClientTimeTrackingsystem);

}
