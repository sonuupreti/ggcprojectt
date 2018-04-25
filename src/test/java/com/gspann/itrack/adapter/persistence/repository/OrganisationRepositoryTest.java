package com.gspann.itrack.adapter.persistence.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.assertj.core.util.Sets;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gspann.itrack.ItrackApplication;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.model.org.structure.Company;
import com.gspann.itrack.domain.model.org.structure.EmploymentType;
import com.gspann.itrack.domain.model.org.structure.EmploymentStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganisationRepositoryTest {

	@Autowired
	private OrganisationRepository systemUnderTest;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Test
	public void test01SaveCompany() {
		List<City> india = locationRepository.findAllCitiesByCountryCode("IN");
		systemUnderTest.saveCompany(Company.of("WHISK", "Whisk Software Pvt. Ltd.", Sets.newHashSet(india)));
		
		List<City> USA = locationRepository.findAllCitiesByCountryCode("US");
		systemUnderTest.saveCompany(Company.of("GSPANN_INC", "GSPANN Technologies Inc.", Sets.newHashSet(USA)));

		List<City> UK = locationRepository.findAllCitiesByCountryCode("GB");
		systemUnderTest.saveCompany(Company.of("GSPANN_LTG", "GSPANN Technologies Ltd.", Sets.newHashSet(UK)));
	}

	@Test
	public void test02FindCompanyById() {
//		systemUnderTest.findCompanyById(id);
	}

	@Test
	public void test03FindAllCompanies() {
		List<Company> companies = systemUnderTest.findAllCompanies();
		assertTrue(companies.size() == 3);
	}

	@Test
	public void testSaveDepartment() {
	}

	@Test
	public void testFindDepartmentById() {
	}

	@Test
	public void testFindAllDepartments() {
	}

	@Test
	public void testSaveDesignation() {
	}

	@Test
	public void testFindDesignationById() {
	}

	@Test
	public void testFindAllDesignations() {
	}

	@Test
	public void testSaveEmploymentStatus() {
		systemUnderTest.saveEmploymentStatus(EmploymentType.byStatusCode(EmploymentType.CODE.FULLTIME_EMPLOYEE));
		systemUnderTest.saveEmploymentStatus(EmploymentType.byStatusCode(EmploymentType.CODE.DIRECT_CONTRACTOR));
		systemUnderTest.saveEmploymentStatus(EmploymentType.byStatusCode(EmploymentType.CODE.SUB_CONTRACTOR));
		systemUnderTest.saveEmploymentStatus(EmploymentType.byStatusCode(EmploymentType.CODE.W2_CONSULTANT));
	}

	@Test
	public void testFindEmploymentStatusByCode() {
		assertNotNull(systemUnderTest.findEmploymentStatusByCode(EmploymentType.CODE.FULLTIME_EMPLOYEE.name()));
	}

	@Test
	public void testFindAllEmploymentStatuses() {
		assertTrue(systemUnderTest.findAllEmploymentStatuses().size() == 4);
	}

	@Test
	public void testSaveEngagementStatus() {
		systemUnderTest.saveEngagementStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.PENDING));
		systemUnderTest.saveEngagementStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.ACTIVE));
		systemUnderTest.saveEngagementStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.DID_NOT_JOIN));
		systemUnderTest.saveEngagementStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.ON_BENCH));
		systemUnderTest.saveEngagementStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.ON_LONG_LEAVE));
		systemUnderTest.saveEngagementStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.RESIGNED));
		systemUnderTest.saveEngagementStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.TERMINATED));
		systemUnderTest.saveEngagementStatus(EmploymentStatus.byStatusCode(EmploymentStatus.CODE.ABSCONDED));
	}

	@Test
	public void testFindEngagementStatusByCode() {
	}

	@Test
	public void testFindAllEngagementStatuses() {
	}

}
