package com.gspann.itrack.domain.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gspann.itrack.adapter.persistence.repository.AccountRepository;
import com.gspann.itrack.adapter.persistence.repository.LocationRepository;
import com.gspann.itrack.adapter.persistence.repository.ProjectRepository;
import com.gspann.itrack.adapter.persistence.repository.ResourceRepository;
import com.gspann.itrack.adapter.persistence.repository.SkillsRepository;
import com.gspann.itrack.adapter.rest.util.BeanConverterUtil;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.common.dto.AddAccountPageVM;
import com.gspann.itrack.domain.model.common.dto.AddProjectPageVM;
import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.common.dto.ProjectDTO;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Location;
import com.gspann.itrack.domain.model.org.skills.Technology;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.projects.ProjectStatus;
import com.gspann.itrack.domain.model.projects.ProjectType;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.service.api.ProjectManagementService;

@Service
@Transactional
public class ProjectManagementServiceImpl implements ProjectManagementService {

	private final Logger log = LoggerFactory.getLogger(ProjectManagementServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private SkillsRepository skillsRepository;

	@Override
	public void assignOffShoreManager() {
		// TODO Auto-generated method stub

	}

	@Override
	public void assignOnShoreManager() {
		// TODO Auto-generated method stub

	}

	@Override
	public void allocateResource() {
		// TODO Auto-generated method stub

	}

	@Override
	public void allocateResources() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scheduleAllocation() {
		// TODO Auto-generated method stub

	}

	@Override
	public ProjectDTO addProject(ProjectDTO projectDTO) {

		Resource onShoreManager = resourceRepository.getOne(projectDTO.getOnshoreManagerCode());
		Resource offShoreManager = resourceRepository.getOne(projectDTO.getOffshoreManagerCode());

		City location = locationRepository.loadCity(projectDTO.getCityId());
		Account account = accountRepository.getOne(projectDTO.getAccountCode());
		Optional<ProjectType> type = projectRepository.findProjectTypeByCode(projectDTO.getProjectTypeCode());
		ProjectType projectType = type.get();

		Optional<ProjectStatus> status = projectRepository.findProjectStatusByCode(projectDTO.getProjectStatusCode());
		ProjectStatus projectStatus = status.get();

		List<Pair<String, String>> practiceList = projectDTO.getPracticeList();
		ListIterator<Pair<String, String>> iter = practiceList.listIterator();
		Set<Practice> practices = new HashSet<>();
		while (iter.hasNext()) {

			Optional<Practice> practice = projectRepository.findPracticeByCode(iter.next().getKey());
			Practice practice2 = practice.get();
			practices.add(practice2);

		}
		
		List<Pair<Integer, String>> technologyList = projectDTO.getTechnologyList();
		ListIterator<Pair<Integer, String>> iter1 = technologyList.listIterator();
		Set<Technology> technologies = new HashSet<>();
		
		while (iter1.hasNext()) {
			Pair<Integer, String> keyValue=iter1.next();
			if(keyValue.getKey()!=0) {

		Technology technology = skillsRepository.loadTechnologyById(keyValue.getKey());
			
			technologies.add(technology);
			}
			else {
				technologies.add(Technology.of(keyValue.getValue()));
			}
				
			}
		
		Project project = projectRepository.save(Project.project().withType(projectType).withStatus(projectStatus)
				.namedAs(projectDTO.getProjectName()).locatedAt(location).inAccount(account)
				.startingFrom(projectDTO.getStartDate()).till(projectDTO.getEndDate()).withPractices(practices)
				.withTechnologies(technologies).atOffshoreManagedBy(offShoreManager)
				.atOnshoreManagedBy(onShoreManager).atCustomerEndManagedBy(projectDTO.getCustomerManager())
				.withCustomerProjectId(projectDTO.getCustomerProjectId())
				.withCustomerProjectName(projectDTO.getCustomerProjectName()).build());

		return BeanConverterUtil.projectEntitytoDto(project);
	}

	@Override
	public Page<ProjectDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Accounts");
		return projectRepository.findAll(pageable).map(BeanConverterUtil::projectEntitytoDto);
	}

	@Override
	public ProjectDTO findOne(String id) {
		log.debug("Request to get Project : {}", id);
		Optional<Project> project = projectRepository.findById(id);
		return BeanConverterUtil.projectEntitytoDto(project.get());
	}

	@Override
	public void delete(String id) {
		log.debug("Request to delete Project : {}", id);
		accountRepository.deleteById(id);
	}

	@Override
	public ProjectDTO updateProject(@Valid ProjectDTO projectDTO) {
		
		Optional<Project> project = projectRepository.findById(projectDTO.getProjectCode());

		Project project1 = project.get();
		
		Resource onShoreManager = resourceRepository.getOne(projectDTO.getOnshoreManagerCode());
		Resource offShoreManager = resourceRepository.getOne(projectDTO.getOffshoreManagerCode());

		City location = locationRepository.loadCity(projectDTO.getCityId());
		Account account = accountRepository.getOne(projectDTO.getAccountCode());
		Optional<ProjectType> type = projectRepository.findProjectTypeByCode(projectDTO.getProjectTypeCode());
		ProjectType projectType = type.get();

		Optional<ProjectStatus> status = projectRepository.findProjectStatusByCode(projectDTO.getProjectStatusCode());
		ProjectStatus projectStatus = status.get();

		List<Pair<String, String>> practiceList = projectDTO.getPracticeList();
		ListIterator<Pair<String, String>> iter = practiceList.listIterator();
		Set<Practice> practices = new HashSet<>();
		while (iter.hasNext()) {

			Optional<Practice> practice = projectRepository.findPracticeByCode(iter.next().getKey());
			Practice practice2 = practice.get();
			practices.add(practice2);

		}
		List<Pair<Integer, String>> technologyList = projectDTO.getTechnologyList();
		ListIterator<Pair<Integer, String>> iter1 = technologyList.listIterator();
		Set<Technology> technologies = new HashSet<>();
		while (iter1.hasNext()) {

			Pair<Integer, String> keyValue=iter1.next();
			if(keyValue.getKey()!=0) {

		Technology technology = skillsRepository.loadTechnologyById(keyValue.getKey());
			
			technologies.add(technology);
			}
			else {
				technologies.add(Technology.of(keyValue.getValue()));
			}
				
			

		}
		project1.updatecustomerManager(projectDTO.getCustomerManager());
		project1.updateCustomerProjectId(projectDTO.getCustomerProjectId());
		project1.updateCustomerProjectName(projectDTO.getCustomerProjectName());
		project1.updateEndDate(projectDTO.getEndDate());
		project1.updateStartDate(projectDTO.getStartDate());
		project1.updatePractices(practices);
		project1.updateLocation(location);
		project1.updateTechnologies(technologies);
		project1.updateName(projectDTO.getProjectName());
		project1.updateProjectType(projectType);
		project1.updateProjectStatus(projectStatus);
		project1.assignOnshoreManager(onShoreManager);
		project1.assignOffshoreManager(offShoreManager);
		
		
		return BeanConverterUtil.projectEntitytoDto(project1);
		
	}

	@Override
	public AddProjectPageVM getAddProjectPageVM() {

		List<Location> locations = locationRepository.findAllLocations();
		Collections.sort(locations);
		List<Pair<Integer, String>> locationPairs = new LinkedList<>();
		for (Location location : locations) {
			Pair<Integer, String> loc = new Pair<Integer, String>(location.cityId(), location.format());
			locationPairs.add(loc);
		}

		List <Technology> technologies=skillsRepository.findAllTechnologies();
		
		List<Pair<Integer, String>> technologyPairs = new LinkedList<>();
		for (Technology technology : technologies) {
			Pair<Integer, String> loc = new Pair<Integer, String>(technology.id(), technology.name());
			technologyPairs.add(loc);
		}
		
		
		List<Pair<String, String>> accountList = accountRepository.findAllCodeAndName();
		List<Pair<String, String>> projectTypeList = projectRepository.findAllProjectTypeCodeAndDescription();

		List<Pair<String, String>> projectStatusList = projectRepository.findAllProjectStatusCodeAndDescription();

		List<Pair<String, String>> practiceList = projectRepository.findAllPracticeCodeAndDescription();

		List<Pair<String, String>> resourceList = resourceRepository.findAllCodeAndName();

		return AddProjectPageVM.of(resourceList, locationPairs, projectTypeList, projectStatusList, practiceList,accountList,technologyPairs);

	}

}
