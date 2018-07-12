package com.gspann.itrack.adapter.persistence.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.Month;

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
import org.springframework.util.ResourceUtils;

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

//	@Test
	public void testReadImage() {
		// String expectedData = "Hello World from fileTest.txt!!!";
		//
		// Path path = Paths.get(getClass().getClassLoader()
		// .getResource("fileTest.txt").toURI());
		//
		// StringBuilder data = new StringBuilder();
		// Stream<String> lines = Files.lines(path);
		// lines.forEach(line -> data.append(line).append("\n"));
		// lines.close();
		//
		// Assert.assertEquals(expectedData, data.toString().trim());

		File file;
		try {
			file = ResourceUtils.getFile("classpath:images/ankit.jpg");
			System.out.println("File Found : " + file.exists());
			String content;
			content = new String(Files.readAllBytes(file.toPath()));
			System.out.println(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 @Test
	public void testAddNewResource() {
		City gurgaon = locationRepository.findCityByName("Gurgaon").get();

		// Resource admin =
		// Resource.expectedToJoinOn(LocalDate.now()).at(gurgaon).asFullTimeEmployee()
		// .withJustAnnualSalary(Money.zero(Monetary.getCurrency(CurrencyCode.INR.name()))).withName("Admin")
		// .male().onDesignation(organisationRepository.findDesignationById((short)
		// 37).get())
		// .withEmail("admin@gspann.com").withPrimarySkills("administration").addPractice(Practice.adms())
		// .deputeAtJoiningLocation().build();
		// systemUnderTest.saveAndFlush(admin);

		// Project iTrack = projectRepository.getOne("");
		Designation projectManager = organisationRepository.findDesignationById((short) 6).get();
		Designation teamLead = organisationRepository.findDesignationById((short) 5).get();
		Designation srSoftwareEngineer = organisationRepository.findDesignationById((short) 4).get();
		Resource manoj = Resource.expectedToJoinOn(LocalDate.of(2018, Month.MAY, 1)).at(gurgaon).asFullTimeEmployee()
				.withJustAnnualSalary(Money.of(2000000, CurrencyCode.INR.name())).withName("Manoj Nautiyal").male()
				.onDesignation(projectManager).withPrimarySkills("Java, J2E").addPractice(Practice.adms())
				.deputeAtJoiningLocation().withEmail("manoj.nautiyal@gspann.com").build();
		// admin.onboard(LocalDate.now(), iTrack);
		try {
			manoj.uploadImage("manoj.jpg", Files.readAllBytes(ResourceUtils.getFile("classpath:images/manoj.jpg").toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		systemUnderTest.saveAndFlush(manoj);
		
		Resource rajveer = Resource.expectedToJoinOn(LocalDate.of(2018, Month.MAY, 1)).at(gurgaon).asFullTimeEmployee()
				.withJustAnnualSalary(Money.of(1000000, CurrencyCode.INR.name())).withName("Rajveer Singh").male()
				.onDesignation(teamLead).withPrimarySkills("Java, J2E").addPractice(Practice.adms())
				.deputeAtJoiningLocation().withEmail("rajveer.singh@gspann.com").build();
		try {
			rajveer.uploadImage("rajveer.jpg", Files.readAllBytes(ResourceUtils.getFile("classpath:images/rajveer.jpg").toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		systemUnderTest.saveAndFlush(rajveer);
		
		Resource ankit = Resource.expectedToJoinOn(LocalDate.of(2018, Month.MAY, 1)).at(gurgaon).asFullTimeEmployee()
				.withJustAnnualSalary(Money.of(1000000, CurrencyCode.INR.name())).withName("Ankit Bhardwaj").male()
				.onDesignation(srSoftwareEngineer).withPrimarySkills("Java, J2E").addPractice(Practice.adms())
				.deputeAtJoiningLocation().withEmail("ankit.bhardwaj@gspann.com").build();
		try {
			ankit.uploadImage("ankit.jpg", Files.readAllBytes(ResourceUtils.getFile("classpath:images/ankit.jpg").toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		systemUnderTest.saveAndFlush(ankit);
		
	}

//	@Test
	public void testAllocateResourceToProject() {
		// Resource manoj = systemUnderTest.getOne(id);
		// Resource rajveer = systemUnderTest.getOne(id);
		// Resource ankit = systemUnderTest.getOne(id);
	}
}
