package com.gspann.itrack.adapter.persistence.repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gspann.itrack.ItrackApplication;
import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.skills.Technology;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.projects.ProjectStatus;
import com.gspann.itrack.domain.model.projects.ProjectType;
import com.gspann.itrack.domain.model.staff.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class ProjectRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ProjectRepository systemUnderTest;
	
//	@Test
	public void testSaveProjectTypes() {
		systemUnderTest.saveProjectType(ProjectType.fixBid());
		systemUnderTest.saveProjectType(ProjectType.milestone());
		systemUnderTest.saveProjectType(ProjectType.TnM());
		systemUnderTest.saveProjectType(ProjectType.investment());
		systemUnderTest.saveProjectType(ProjectType.bench());
		systemUnderTest.saveProjectType(ProjectType.paidLeave());
		systemUnderTest.saveProjectType(ProjectType.unpaidLeave());
	}

//	@Test
	public void testSaveProjectStatuses() {
		systemUnderTest.saveProjectStatus(ProjectStatus.pending());
		systemUnderTest.saveProjectStatus(ProjectStatus.inProgress());
		systemUnderTest.saveProjectStatus(ProjectStatus.onHold());
		systemUnderTest.saveProjectStatus(ProjectStatus.closed());
	}

	@Test
	public void testFindAllProjectCodeAndNameByOffshoreManagerCode() {
		System.out.println(systemUnderTest.findAllProjectCodeAndNameByOffshoreManagerCode("10001"));
	}

//	@Test
	public void testSave() {

		Set<Practice> practices = new HashSet<>();
		practices.add(Practice.adms());

//		Resource admin = resourceRepository.getOne("10000");
		Account internal = accountRepository.getOne("IN1000");

		City gurgaon = locationRepository.findCityByName("Gurgaon").get();
//		Project paidLeave = Project.project().paidLeave().asInProgress().namedAs("Paid Leave").locatedAt(gurgaon)
//				.inAccount(internal).startingIndefinatelyFrom(LocalDate.now()).withPractices(practices)
//				.withTechnologies("None").atOffshoreManagedBy(admin).atOnshoreManagedBy(admin)
//				.atCustomerEndManagedBy("None").build();
//		systemUnderTest.saveAndFlush(paidLeave);
//
//		Project unpaidLeave = Project.project().unpaidLeave().asInProgress().namedAs("Unpaid Leave").locatedAt(gurgaon)
//				.inAccount(internal).startingIndefinatelyFrom(LocalDate.now()).withPractices(practices)
//				.withTechnologies("None").atOffshoreManagedBy(admin).atOnshoreManagedBy(admin)
//				.atCustomerEndManagedBy("None").build();
//		systemUnderTest.saveAndFlush(unpaidLeave);
//		
//
//		Project bench = Project.project().bench().asInProgress().namedAs("Bench").locatedAt(gurgaon)
//				.inAccount(internal).startingIndefinatelyFrom(LocalDate.now()).withPractices(practices)
//				.withTechnologies("None").atOffshoreManagedBy(admin).atOnshoreManagedBy(admin)
//				.atCustomerEndManagedBy("None").build();
//		systemUnderTest.saveAndFlush(bench);

		Resource manager = resourceRepository.getOne("10001");
		Project iTrack = Project.project().investment().asInProgress().namedAs("iTrack").locatedAt(gurgaon)
				.inAccount(internal).startingIndefinatelyFrom(LocalDate.of(2018, Month.MAY, 1)).withPractices(practices)
				.withTechnologies(Technology.setOf(new String[]{"Java", "J2E", "Spring Boot", "Restfil Web services", "Spring Data", "Hibernate JPA", "Angular 5"})).atOffshoreManagedBy(manager).atOnshoreManagedBy(manager)
				.atCustomerEndManagedBy("None").build();
		systemUnderTest.saveAndFlush(iTrack);
		Project finalSelect = Project.project().investment().asInProgress().namedAs("FinalSelect").locatedAt(gurgaon)
				.inAccount(internal).startingIndefinatelyFrom(LocalDate.of(2018, Month.MAY, 1)).withPractices(practices)
				.withTechnologies(Technology.setOf(new String[]{"Cucumber", "Neo4J", "Air Tables"})).atOffshoreManagedBy(manager).atOnshoreManagedBy(manager)
				.atCustomerEndManagedBy("None").build();
		systemUnderTest.saveAndFlush(finalSelect);
	}

//	@Test
	public void testfindByTypeCode() {
		List<Project> invProjects = systemUnderTest.findByTypeCode(ProjectType.CODE.INVESTMENT.value());
		System.out.println("Investment projects -->" + invProjects);
	}

//	@Test
	public void findAllBenchProjects() {
		List<Project> benchProjects = systemUnderTest.findAllBenchProjects();
		System.out.println("Investment projects -->" + benchProjects);
	}

//	@Test
	public void findAllLeaveProjects() {
		List<Project> leaveProjects = systemUnderTest.findAllLeaveProjects();
		System.out.println("Investment projects -->" + leaveProjects);
	}
}
