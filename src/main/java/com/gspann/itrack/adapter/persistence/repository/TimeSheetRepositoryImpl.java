package com.gspann.itrack.adapter.persistence.repository;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.common.DateRange_;
import com.gspann.itrack.domain.model.timesheets.Week_;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet_;

@Repository
public class TimeSheetRepositoryImpl implements TimeSheetRepositoryJPA {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<WeeklyTimeSheet> findTimeSheetByWeekStartDate(final LocalDate weekStartDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WeeklyTimeSheet> query = criteriaBuilder.createQuery(WeeklyTimeSheet.class);
		Root<WeeklyTimeSheet> weeklyTimeSheet = query.from(WeeklyTimeSheet.class);
		query.select(weeklyTimeSheet);
		Predicate weekStartDatePredicate = criteriaBuilder.equal(
				weeklyTimeSheet.get(WeeklyTimeSheet_.week.getName()).get(Week_.dateRange.getName()).get(DateRange_.fromDate.getName()),
				weekStartDate);
		query.where(weekStartDatePredicate);
		WeeklyTimeSheet timesheet = null;
		try {
			timesheet = entityManager.createQuery(query)
					.getSingleResult();
		} catch (NoResultException e) {
			// No state with such name exists, so return null
		}
		return Optional.ofNullable(timesheet);
	}

}
