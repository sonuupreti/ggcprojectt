package com.gspann.itrack.domain.service.api;

import java.time.LocalDate;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.gspann.itrack.ItrackApplication;
import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.adapter.persistence.repository.LocationRepository;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.timesheets.Week;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrackApplication.class)
@Transactional
@Commit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { ApplicationConstant.SPRING_PROFILE_DEVELOPMENT })
public class HolidayServiceTest {

	@Autowired
	private HolidayService holidaySreviceTest;

	@Autowired
	private LocationRepository locationRepository;

	@Test
	public void testGetHolidaysByWeekAndLocation() {
		LocalDate weekStartDate = LocalDate.of(2018, 2, 27);
		Week week = Week.of(weekStartDate);
		City location = locationRepository.findCityById(new Integer(1)).get();
		
		Set<Holiday> holidaysByLocation = holidaySreviceTest.getHolidaysByWeekAndLocation(week, location);
		System.out.println("Holidays by Location are " +holidaysByLocation);
	}
}
