package com.gspann.itrack.adapter.persistence.repository;

import java.time.Year;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gspann.itrack.domain.model.org.holidays.HolidayCalendar;

public interface HolidayCalendarRepository extends JpaRepository<HolidayCalendar, Year>, HolidayCalendarRepositoryJPA {

}
