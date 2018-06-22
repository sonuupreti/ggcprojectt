package com.gspann.itrack.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;

public interface TimeSheetRepository extends JpaRepository<WeeklyTimeSheet, Long>, TimeSheetRepositoryJPA {

	public Optional<WeeklyTimeSheet> findByIdAndResourceCode(final long id, final String resourceCode);
	
	public Optional<WeeklyTimeSheet> findByResourceCodeAndWeek(final String resourceCode, final Week week);

	public List<WeeklyTimeSheet> findByResourceCodeAndWeeklyStatusStatus(final String resourceCode, final TimesheetStatus status);
}
