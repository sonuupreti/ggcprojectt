package com.gspann.itrack.adapter.persistence.repository;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Sets;
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
import com.gspann.itrack.domain.model.business.payments.BillabilityStatus;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Occasion;
import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.Department;
import com.gspann.itrack.domain.model.org.structure.Designation;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.Practice;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class OrganisationRepositoryTest {

	@Autowired
	private OrganisationRepository systemUnderTest;

	@Autowired
	private LocationRepository locationRepository;

	@Test
	public void test01SaveCompany() {
		List<City> india = locationRepository.findAllCitiesByCountryCode("IN");
		systemUnderTest.saveCompany(Company.of("Whisk Software Pvt. Ltd.", Sets.newHashSet(india)));

		List<City> USA = locationRepository.findAllCitiesByCountryCode("US");
		systemUnderTest.saveCompany(Company.of("GSPANN Technologies Inc.", Sets.newHashSet(USA)));

		List<City> UK = locationRepository.findAllCitiesByCountryCode("GB");
		systemUnderTest.saveCompany(Company.of("GSPANN Technologies Ltd.", Sets.newHashSet(UK)));
	}

	@Test
	public void test02FindCompanyById() {
		// systemUnderTest.findCompanyById(id);
	}

	@Test
	public void test03FindAllCompanies() {
		List<Company> companies = systemUnderTest.findAllCompanies();
		assertTrue(companies.size() == 3);
	}

	@Test
	public void test04SaveDepartment() {
//		List<Company> companies = systemUnderTest.findAllCompanies();
//		for (Company company : companies) {
//			systemUnderTest.saveDepartment(Department.of("Delivery", company));
//			systemUnderTest.saveDepartment(Department.of("SG&A", company));
//			systemUnderTest.saveDepartment(Department.of("Management", company));
//		}
	}

	@Test
	public void test05FindDepartmentById() {
	}

	@Test
	public void test06FindAllDepartments() {
	}

	@Test
	public void test07SaveDesignation() {/*
		List<Company> companies = systemUnderTest.findAllCompanies();
		for (Company company : companies) {
			Department delivery = systemUnderTest.findDepartmentByNameAndCompany("Delivery", company.id()).get();
			Designation designation = Designation.of("Software Engineer Trainee", (short) 1, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Associate Software Engineer", (short) 2, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Software Engineer", (short) 3, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Software Engineer", (short) 4, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Tech Lead", (short) 5, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Project Lead", (short) 6, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Tech Lead", (short) 7, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Project Lead", (short) 8, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Associate Manager", (short) 9, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Associate Architect", (short) 10, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Manager", (short) 11, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Architect", (short) 12, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Manager", (short) 13, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Architect", (short) 14, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Delivery Manager", (short) 15, delivery);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Practice Manager", (short) 16, delivery);
			systemUnderTest.saveDesignation(designation);

			Department SGnA = systemUnderTest.findDepartmentByNameAndCompany("SG&A", company.id()).get();
			designation = Designation.of("Management Trainee", (short) 1, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Associate", (short) 2, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Executive", (short) 3, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Executive", (short) 4, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Team Lead", (short) 5, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Team Lead", (short) 6, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Associate Manager", (short) 7, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Manager", (short) 8, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Manager 1", (short) 9, SGnA);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Manager 2", (short) 10, SGnA);
			systemUnderTest.saveDesignation(designation);

			Department management = systemUnderTest.findDepartmentByNameAndCompany("Management", company.id()).get();
			designation = Designation.of("Practice Associate Director", (short) 1, management);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Associate Director", (short) 2, management);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Principal Consultant", (short) 3, management);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Practice Director", (short) 4, management);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("AVP", (short) 5, management);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Vice President", (short) 6, management);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("Senior Vice President", (short) 7, management);
			systemUnderTest.saveDesignation(designation);
			designation = Designation.of("CEO", (short) 8, management);
			systemUnderTest.saveDesignation(designation);
		}
	*/}

	@Test
	public void test08FindDesignationById() {
	}

	@Test
	public void test09FindAllDesignations() {
	}

	@Test
	public void test10SaveEmploymentStatus() {
		systemUnderTest.saveEmploymentType(EmploymentType.byStatusCode(EmploymentType.CODE.FULLTIME_EMPLOYEE));
		systemUnderTest.saveEmploymentType(EmploymentType.byStatusCode(EmploymentType.CODE.DIRECT_CONTRACTOR));
		systemUnderTest.saveEmploymentType(EmploymentType.byStatusCode(EmploymentType.CODE.SUB_CONTRACTOR));
		systemUnderTest.saveEmploymentType(EmploymentType.byStatusCode(EmploymentType.CODE.W2_CONSULTANT));
	}

	@Test
	public void test11FindEmploymentStatusByCode() {
		assertNotNull(systemUnderTest.findEmploymentStatusByCode(EmploymentType.CODE.FULLTIME_EMPLOYEE.name()));
	}

	@Test
	public void test12FindAllEmploymentStatuses() {
		// assertTrue(systemUnderTest.findAllEmploymentStatuses().size() == 4);
	}

	@Test
	public void test13SaveEngagementStatus() {
		systemUnderTest.saveEmploymentStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.PENDING));
		systemUnderTest.saveEmploymentStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.ACTIVE));
		systemUnderTest.saveEmploymentStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.DID_NOT_JOIN));
		systemUnderTest.saveEmploymentStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.ON_BENCH));
		systemUnderTest.saveEmploymentStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.ON_LONG_LEAVE));
		systemUnderTest.saveEmploymentStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.RESIGNED));
		systemUnderTest.saveEmploymentStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.TERMINATED));
		systemUnderTest.saveEmploymentStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.ABSCONDED));
	}

	@Test
	public void test14FindEngagementStatusByCode() {
	}

	@Test
	public void test15FindAllEngagementStatuses() {
	}

	@Test
	public void test16SavePractice() {
		Practice practice = Practice.of("IA", "IA");
		systemUnderTest.savePractice(practice);
		practice = Practice.of("QA", "QA");
		systemUnderTest.savePractice(practice);
		practice = Practice.of("DevOps", "DevOps");
		systemUnderTest.savePractice(practice);
		practice = Practice.of("Content", "Content");
		systemUnderTest.savePractice(practice);
		practice = Practice.of("eCommerce", "eCommerce");
		systemUnderTest.savePractice(practice);
		practice = Practice.of("Experience", "Experience");
		systemUnderTest.savePractice(practice);
		practice = Practice.of("Mobility", "Mobility");
		systemUnderTest.savePractice(practice);
		practice = Practice.of("ADMS", "ADMS");
		systemUnderTest.savePractice(practice);
	}

	@Test
	public void test17FindPracticeByCode() {
	}

	@Test
	public void test18FindPracticeByName() {
	}

	@Test
	public void test19FindPracticeByName() {
	}

	@Test
	public void test23SaveBillabilityStatus() {
		systemUnderTest.saveBillabilityStatus(BillabilityStatus.ofBillable());
		systemUnderTest.saveBillabilityStatus(BillabilityStatus.ofBuffer());
		systemUnderTest.saveBillabilityStatus(BillabilityStatus.ofNonBillable());

	}
}
