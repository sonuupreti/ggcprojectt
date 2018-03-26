package com.gspann.itrack.adapter.persistence.repository;

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
import com.gspann.itrack.domain.model.organisation.Company;

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
		systemUnderTest.saveCompany(Company.of("Whisk Software Pvt. Ltd.", Sets.newHashSet(india)));
		
		List<City> USA = locationRepository.findAllCitiesByCountryCode("US");
		systemUnderTest.saveCompany(Company.of("GSPANN Technologies Inc.", Sets.newHashSet(USA)));

		List<City> UK = locationRepository.findAllCitiesByCountryCode("GB");
		systemUnderTest.saveCompany(Company.of("GSPANN Technologies Ltd.", Sets.newHashSet(UK)));
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
	}

	@Test
	public void testFindEmploymentStatusById() {
	}

	@Test
	public void testFindEmploymentStatusByCode() {
	}

	@Test
	public void testFindAllEmploymentStatuses() {
	}

	@Test
	public void testSaveEngagementStatus() {
	}

	@Test
	public void testFindEngagementStatusById() {
	}

	@Test
	public void testFindEngagementStatusByCode() {
	}

	@Test
	public void testFindAllEngagementStatuses() {
	}

}
