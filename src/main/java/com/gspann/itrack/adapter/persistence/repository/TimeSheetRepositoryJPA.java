package com.gspann.itrack.adapter.persistence.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;

public interface TimeSheetRepositoryJPA {

	Optional<WeeklyTimeSheet> findTimeSheetByWeekStartDate(final LocalDate weekStartDate);
}
