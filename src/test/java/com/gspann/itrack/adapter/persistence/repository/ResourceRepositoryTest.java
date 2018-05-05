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
import com.gspann.itrack.common.ApplicationConstant;
import com.gspann.itrack.common.enums.standard.CurrencyCode;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.model.org.structure.Practice;
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

	@Test
	public void testSave() {
		City gurgaon = locationRepository.findCityByName("Gurgaon").get();
		Resource admin = Resource.expectedToJoinOn(LocalDate.now()).at(gurgaon).asFullTimeEmployee()
				.withJustAnnualSalary(Money.zero(Monetary.getCurrency(CurrencyCode.INR.name()))).withName("Admin")
				.male().onDesignation(organisationRepository.findDesignationById((short) 37).get())
				.withEmail("admin@gspann.com").withPrimarySkills("administration").addPractice(Practice.adms())
				.deputeAtJoiningLocation().build();
		systemUnderTest.saveAndFlush(admin);
	}

}
