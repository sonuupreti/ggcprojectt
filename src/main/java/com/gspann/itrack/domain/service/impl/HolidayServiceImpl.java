package com.gspann.itrack.domain.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gspann.itrack.adapter.persistence.repository.HolidayCalendarRepository;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.service.api.HolidayService;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayCalendarRepository holidayCalendarRepository;
	
	@Override
	public Set<Holiday> getHolidaysByWeekAndLocation(Week week, City location) {
		List<Holiday> holidays = holidayCalendarRepository.findAllHolidaysByWeek(week, location);
		return new HashSet<Holiday>(holidays != null ? holidays : Collections.emptySet());		
	}

}
