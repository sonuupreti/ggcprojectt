package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.org.holidays.Holiday;
import com.gspann.itrack.domain.model.org.holidays.Occasion;
import com.gspann.itrack.domain.model.timesheets.Week;

public interface HolidayCalendarRepositoryJPA {

	Occasion saveOccasion(final Occasion occasion);
	
	Optional<Occasion> findOccasionByName(final String name);

	Optional<Occasion> findOccasionById(final short id);

	List<Occasion> findAllOccasions();
	
	List<Holiday> findAllHolidaysByWeek(final Week week, final City location);
}
