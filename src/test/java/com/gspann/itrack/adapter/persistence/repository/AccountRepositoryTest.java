package com.gspann.itrack.adapter.persistence.repository;

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
import com.gspann.itrack.common.enums.CurrencyCode;
import com.gspann.itrack.domain.model.business.Account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@ActiveProfiles(profiles = "dev")
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository systemUnderTest;

	@Autowired
	private LocationRepository locationRepository;

//	static {
//		TimeZone.setDefault(TimeZone.getTimeZone("GMT+2"));
//	}

	@Test
	public void test01Save() {
		// Account account = Account.of("Mecy", "Mecy Inc.", "Angrez Singh", false,
		// locationRepository.findCityByName("Gurgaon").get());
		// systemUnderTest.save(account);

		// Account account1 = Account.of("Koal", "Koal Inc.", "Madam Singh", false,
		// locationRepository.findCityByName("Chicago").get());
		// systemUnderTest.save(account1);

		// Account account = Account.of("Mecy", "Mecy Inc.", "Angrez Singh", false,
		// locationRepository.findCityByName("Gurgaon").get(), Money.of(100, "INR"));
		// systemUnderTest.save(account);

		Account account1 = Account.of("Koal", "Koal Inc.", "Madam Singh", false,
				locationRepository.findCityByName("Chicago").get(), Money.of(75, CurrencyCode.USD.name()));
		systemUnderTest.save(account1);
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
