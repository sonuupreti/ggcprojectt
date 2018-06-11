package com.gspann.itrack.adapter.persistence.repository;

import java.time.LocalDate;

import javax.money.Monetary;

import org.javamoney.moneta.Money;
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
import com.gspann.itrack.common.enums.standard.CurrencyCode;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.Practice;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class ResourceRepositoryTest {

	@Autowired
	private ResourceRepository systemUnderTest;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private OrganisationRepository organisationRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Test
	public void testAddNewResource() {
		City gurgaon = locationRepository.findCityByName("Gurgaon").get();
		
//		Resource admin = Resource.expectedToJoinOn(LocalDate.now()).at(gurgaon).asFullTimeEmployee()
//				.withJustAnnualSalary(Money.zero(Monetary.getCurrency(CurrencyCode.INR.name()))).withName("Admin")
//				.male().onDesignation(organisationRepository.findDesignationById((short) 37).get())
//				.withEmail("admin@gspann.com").withPrimarySkills("administration").addPractice(Practice.adms())
//				.deputeAtJoiningLocation().build();
//		systemUnderTest.saveAndFlush(admin);
		
//		Project iTrack = projectRepository.getOne("");
		Designation projectManager = organisationRepository.findDesignationById((short) 6).get();
		Designation teamLead = organisationRepository.findDesignationById((short) 5).get();
		Designation srSoftwareEngineer = organisationRepository.findDesignationById((short) 4).get();
		Resource manoj = Resource.expectedToJoinOn(LocalDate.now()).at(gurgaon).asFullTimeEmployee()
				.withJustAnnualSalary(Money.of(2000000, CurrencyCode.INR.name())).withName("Manoj Nautiyal")
				.male().onDesignation(projectManager)
				.withPrimarySkills("Java, J2E").addPractice(Practice.adms())
				.deputeAtJoiningLocation().withEmail("manoj.nautiyal@gspann.com").build();
		systemUnderTest.saveAndFlush(manoj);
//		admin.onboard(LocalDate.now(), iTrack);

		Resource rajveer = Resource.expectedToJoinOn(LocalDate.now()).at(gurgaon).asFullTimeEmployee()
				.withJustAnnualSalary(Money.of(1000000, CurrencyCode.INR.name())).withName("Rajveer Singh")
				.male().onDesignation(teamLead)
				.withPrimarySkills("Java, J2E").addPractice(Practice.adms())
				.deputeAtJoiningLocation().withEmail("rajveer.singh@gspann.com").build();
		systemUnderTest.saveAndFlush(rajveer);

		Resource ankit = Resource.expectedToJoinOn(LocalDate.now()).at(gurgaon).asFullTimeEmployee()
				.withJustAnnualSalary(Money.of(1000000, CurrencyCode.INR.name())).withName("Ankit Bhardwaj")
				.male().onDesignation(srSoftwareEngineer)
				.withPrimarySkills("Java, J2E").addPractice(Practice.adms())
				.deputeAtJoiningLocation().withEmail("ankit.bhardwaj@gspann.com").build();
		systemUnderTest.saveAndFlush(ankit);
	}


	@Test
	public void testAllocateResourceToProject() {
//		Resource manoj = systemUnderTest.getOne(id);
//		Resource rajveer = systemUnderTest.getOne(id);
//		Resource ankit = systemUnderTest.getOne(id);
	}
}
