package com.gspann.itrack.domain.service.impl;

import java.util.Collections;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.service.api.HolidayService;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Override
	public Set<Holiday> getHolidaysByWeekAndLocation(Week week, final City location) {
		// TODO Update later with repository call to get actual holdys falling in
		// specified week.
		// For the time being hard coded to return a dummy holiday every week.
		return Collections.EMPTY_SET;
	}

}
