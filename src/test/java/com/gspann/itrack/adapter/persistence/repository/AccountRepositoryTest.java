package com.gspann.itrack.adapter.persistence.repository;

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
import com.gspann.itrack.common.enums.standard.CountryCode;
import com.gspann.itrack.domain.common.location.City;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.staff.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository systemUnderTest;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	// static {
	// TimeZone.setDefault(TimeZone.getTimeZone("GMT+2"));
	// }

	public static void main(String[] args) {

		System.out.println(CountryCode.US.name());
		System.out.println(CountryCode.US.name());
		System.out.println(CountryCode.US.name());
		System.out.println(CountryCode.US.name());
		System.out.println(CountryCode.US.name());
	}

	@Test
	public void test01Save() {
		// Resource admin = resourceRepository.findById("20000").get();
		// City gurgaon = locationRepository.findCityByName("Gurgaon").get();
		Resource admin = resourceRepository.getOne("20000");
		City gurgaon = locationRepository.findCityByName("Gurgaon").get();
		Account account = Account.ofCustomer("Internal").reportingTo("Internal").withNoEntity()
				.customerTimeTrackingNotAvailable().managedBy(admin).locatedAt(gurgaon).build();
		systemUnderTest.saveAndFlush(account);
	}

	// @Test
	// public void testFindAll() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testFindAllSort() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testFindAllIterableOfID() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSaveIterableOfS() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetOne() {
	// fail("Not yet implemented");
	// }

}
