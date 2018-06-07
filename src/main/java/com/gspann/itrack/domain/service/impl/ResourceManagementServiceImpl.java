package com.gspann.itrack.domain.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gspann.itrack.adapter.persistence.repository.CompanyRepository;
import com.gspann.itrack.adapter.persistence.repository.LocationRepository;
import com.gspann.itrack.adapter.persistence.repository.OrganisationRepository;
import com.gspann.itrack.adapter.persistence.repository.ProjectRepository;
import com.gspann.itrack.adapter.persistence.repository.ResourceRepository;
import com.gspann.itrack.adapter.rest.util.BeanConverterUtil;
import com.gspann.itrack.common.enums.standard.CurrencyCode;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.common.Toggle;
import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.common.dto.ResourceDTO;
import com.gspann.itrack.domain.model.common.dto.ResourceOnLoadVM;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Location;
import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Department;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.service.api.ResourceManagementService;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResourceManagementServiceImpl implements ResourceManagementService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private OrganisationRepository organizationRepository;
	
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	@javax.transaction.Transactional
	public ResourceDTO addResource(ResourceDTO resourceDTO) {
		
		City baseLocation = locationRepository.loadCity(resourceDTO.getBaseLocationId());
		Optional<Designation> designation=organizationRepository.findDesignationById(resourceDTO.getDesignationId());
			
		Resource resources = null;
		if( null != resourceDTO.getEmploymentTypeCode() && resourceDTO.getEmploymentTypeCode().equalsIgnoreCase("FTE")) {
			resources = Resource.expectedToJoinOn(resourceDTO.getExpectedJoiningDate()).at(baseLocation).asFullTimeEmployee()
					.withJustAnnualSalary(Money.of(2000000, CurrencyCode.INR.name())).withName(resourceDTO.getName()).withGender(resourceDTO.getGender()).onDesignation(designation.get())
					.withEmail(resourceDTO.getEmailId()).withPrimarySkills(resourceDTO.getPrimarySkills()).addPractice(Practice.adms()).deputeAtJoiningLocation().build();
		}
		else if(null != resourceDTO.getEmploymentTypeCode() && resourceDTO.getEmploymentTypeCode().equalsIgnoreCase("Contractor")) {
			resources = Resource.expectedToJoinOn(resourceDTO.getExpectedJoiningDate()).at(baseLocation).asDirectContractor()
					.withName(resourceDTO.getName()).withGender(resourceDTO.getGender()).onDesignation(designation.get())
					.withEmail(resourceDTO.getEmailId()).withPrimarySkills(resourceDTO.getPrimarySkills()).addPractice(Practice.adms()).deputeAtJoiningLocation().build();
		}
		else if(null != resourceDTO.getEmploymentTypeCode() && resourceDTO.getEmploymentTypeCode().equalsIgnoreCase("Sub-Contractor")) {
		resources = Resource.expectedToJoinOn(resourceDTO.getExpectedJoiningDate()).at(baseLocation).asFullTimeEmployee()
				.withJustAnnualSalary(Money.of(2000000, CurrencyCode.INR.name())).withName(resourceDTO.getName()).withGender(resourceDTO.getGender()).onDesignation(designation.get())
				.withEmail(resourceDTO.getEmailId()).withPrimarySkills(resourceDTO.getPrimarySkills()).addPractice(Practice.adms()).deputeAtJoiningLocation().build();
	}
		resourceRepository.saveAndFlush(resources);
		return BeanConverterUtil.resourceEntitytoDto(resources);
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
	
	@Override
	@Transactional(readOnly = true)
	public Page<ResourceDTO> getAllResources(Pageable pageable) {
		log.debug("Request to get all resources");
		return resourceRepository.findAll(pageable).map(BeanConverterUtil::resourceEntitytoDto);
	}
	
	public ResourceOnLoadVM resourceOnLodPage() {
		log.debug("Request to load  ResourceOnLoadVM");
		List<Company> companies = organizationRepository.findAllCompanies();
		List<Department> departments = organizationRepository.findAllDepartments();
		//Collections.sort(companies);
		List<Location> locations = locationRepository.findAllLocations();
		Collections.sort(locations);
		
		List<Pair<Short, String>> companyPairs = new LinkedList<>();
		
		for (Company company : companies) {
			Pair<Short, String> comp = new Pair<Short, String>(company.id(), company.name());
			companyPairs.add(comp);
		}

		
		List<Pair<Integer, String>> locationPairs = new LinkedList<>();
		for (Location location : locations) {
			Pair<Integer, String> loc = new Pair<Integer, String>(location.cityId(), location.format());
			locationPairs.add(loc);
		}
		List<Pair<String, String>> locationsList = resourceRepository.findAllCodeAndName();
		List<Pair<String, String>> companiesList = companyRepository.findAllCodeAndName();
		return ResourceOnLoadVM.of(companiesList,locationsList);
	}
}
