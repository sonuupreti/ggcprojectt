package com.gspann.itrack.domain.service.api;

import java.time.LocalDate;
import java.time.Month;

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
import com.gspann.itrack.domain.model.common.Toggle;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class ResourceManagementServiceTest {

	@Autowired
	private ResourceManagementService systemUnderTest;

	// @Test
	public void testOnBoardToBench() {

	}

	// @Test
	public void testOnBoardToProject() {

	}

	@Test
	public void testAllocateResourcesToProject() {

		String manojResCode = "10001";
		String rajveerResCode = "10002";
		String ankitResCode = "10003";

		String iTrackPrjCode = "INV0000004";
		String finalSelectPrjCode = "INV0000005";

		// systemUnderTest.allocate(rajveerResCode, iTrackPrjCode, Money.of(10,
		// CurrencyCode.USD.name()), LocalDate.now(),
		// null, Toggle.YES);
		// systemUnderTest.allocate(ankitResCode, iTrackPrjCode, Money.of(10,
		// CurrencyCode.USD.name()), LocalDate.now(),
		// null, Toggle.NO);
		//
		// systemUnderTest.allocatePartially(manojResCode, iTrackPrjCode, (short) 50,
		// Money.of(20, CurrencyCode.USD.name()), LocalDate.now(), null, Toggle.NO);
		// systemUnderTest.allocatePartially(manojResCode, finalSelectPrjCode, (short)
		// 50,
		// Money.of(20, CurrencyCode.USD.name()), LocalDate.now(), null, Toggle.YES);

		systemUnderTest.onBoardToProject(rajveerResCode, iTrackPrjCode, Money.of(10, CurrencyCode.USD.name()),
				LocalDate.of(2018, Month.MAY, 1), Toggle.YES);
		
		systemUnderTest.onBoardToProject(ankitResCode, iTrackPrjCode, Money.of(10, CurrencyCode.USD.name()),
				LocalDate.of(2018, Month.MAY, 1), Toggle.NO);
		
		systemUnderTest.onBoardToProjectPartially(manojResCode, iTrackPrjCode, (short) 50,
				Money.of(20, CurrencyCode.USD.name()), LocalDate.of(2018, Month.MAY, 1), Toggle.NO);
		systemUnderTest.allocatePartially(manojResCode, finalSelectPrjCode, (short) 50,
				Money.of(10, CurrencyCode.USD.name()), LocalDate.of(2018, Month.MAY, 1), null, Toggle.YES);
	}
}
