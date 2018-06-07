package com.gspann.itrack.adapter.persistence.repository;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

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
import com.gspann.itrack.common.enums.standard.CountryCode;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Country;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.org.holidays.HolidayCalendar;
import com.gspann.itrack.domain.model.org.holidays.Occasion;
import com.gspann.itrack.domain.model.timesheets.Week;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class HolidayCalendarRepositoryTest {

	@Autowired
	private HolidayCalendarRepository systemUnderTest;
	
	@Autowired
	private LocationRepository locationRepository;

	@Test
	public void testSave() {
		Country india = locationRepository.findCountryByCode(CountryCode.IN.alpha2()).get();
		
		
		/*HolidayCalendar calendar = HolidayCalendar.forYear(Year.of(2017));
		Occasion republicDay = systemUnderTest.findOccasionById((short)8).get();
		calendar.addHoliday(Holiday.on(LocalDate.of(2017, 1, 26)).national(india).withOccassion(republicDay).build());
		systemUnderTest.save(calendar);*/
		
		/*HolidayCalendar calendar1 = HolidayCalendar.forYear(Year.of(2018));
		Occasion ramzan = systemUnderTest.findOccasionById((short)15).get();
		calendar1.addHoliday(Holiday.on(LocalDate.of(2018, 6, 15)).national(india).withOccassion(ramzan).build());
		systemUnderTest.saveAndFlush(calendar1);*/
		
		HolidayCalendar calendar3 = HolidayCalendar.forYear(Year.of(2018));
		Occasion holi = systemUnderTest.findOccasionById((short)12).get();
		City city = locationRepository.findCityById(new Integer(1)).get();
		calendar3.addHoliday(Holiday.on(LocalDate.of(2018, 3, 2)).regional(city).withOccassion(holi).build());
		systemUnderTest.saveAndFlush(calendar3);

	}

//	@Test
	public void testSaveHolidayOccasions() {
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.GLOBAL_DAY_NEW_YEAR));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.GLOBAL_EVE_CHRISTMAS));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.US_NH_MEMORIAL_DAY));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.US_NH_INDEPENDENCE_DAY));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.US_NH_LABOR_DAY));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_THANKS_GIVING_DAY_1));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_THANKS_GIVING_DAY_2));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.IN_NH_REPUBLIC_DAY));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.IN_NH_INDEPENDENCE_DAY));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.IN_NH_GANDHI_JAYANTI));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_PONGAL));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_HOLI));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_DIWALI));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_GANESH_CHATURTHI));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_ID_UL_FITR_RAMZAN));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.UK_NH_BOXING_DAY));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_EASTER_MONDAY));
		systemUnderTest.saveOccasion(Occasion.byReasonCode(Occasion.REASON.EVE_GOOD_FRIDAY));

	}
	
	@Test
	public void testFindAllHolidaysByWeek() {
		LocalDate weekStartDate = LocalDate.of(2017, 1, 24);
		Week week = Week.of(weekStartDate);
		City city = locationRepository.findCityById(new Integer(1)).get();
		List<Holiday> holidays = systemUnderTest.findAllHolidaysByWeek(week, city);
		System.out.println("Holidays are ---->"+holidays);
	}
}
