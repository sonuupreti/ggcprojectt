package com.gspann.itrack.domain.service.api;

import java.util.Set;

import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.timesheets.Week;

public interface HolidayService {

	public Set<Holiday> getHolidaysByWeekAndLocation(final Week week, final City location);
}
