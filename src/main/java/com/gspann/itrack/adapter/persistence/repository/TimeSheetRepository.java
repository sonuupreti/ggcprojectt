package com.gspann.itrack.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;

public interface TimeSheetRepository extends JpaRepository<WeeklyTimeSheet, Long>, TimeSheetRepositoryJPA {

}
