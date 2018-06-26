package com.gspann.itrack.adapter.persistence.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.DateRange_;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.staff.Resource_;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.Week_;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheetStatus;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheetStatus_;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet_;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetStatusTypeVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;
import com.gspann.itrack.infra.config.ApplicationProperties;

@Repository
public class TimeSheetRepositoryImpl implements TimeSheetRepositoryJPA {

	@PersistenceContext
	private EntityManager entityManager;

	private ApplicationProperties.TimeSheet TIMESHEET_PROPERTIES;

	public TimeSheetRepositoryImpl(final ApplicationProperties applicationProperties) {
		TIMESHEET_PROPERTIES = applicationProperties.timeSheet();
	}

	@Override
	public List<WeeklyTimeSheet> findSavedTimeSheetsStartingFromDate(String resourceCode, LocalDate fromDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WeeklyTimeSheet> query = criteriaBuilder.createQuery(WeeklyTimeSheet.class);
		Root<WeeklyTimeSheet> weeklyTimeSheet = query.from(WeeklyTimeSheet.class);
		Join<WeeklyTimeSheet, Resource> resource = weeklyTimeSheet.join(WeeklyTimeSheet_.resource);
		Join<WeeklyTimeSheet, WeeklyTimeSheetStatus> weeklyStatus = weeklyTimeSheet.join(WeeklyTimeSheet_.weeklyStatus);
		query.select(weeklyTimeSheet);
		Predicate weekStartDatePredicate = criteriaBuilder.greaterThanOrEqualTo(weeklyTimeSheet
				.get(WeeklyTimeSheet_.week.getName()).get(Week_.dateRange.getName()).get(DateRange_.fromDate.getName()),
				fromDate);
		Predicate savedSatusPredicate = criteriaBuilder.equal(weeklyStatus.get(WeeklyTimeSheetStatus_.status.getName()),
				TimesheetStatus.SAVED);
		Predicate resourceCodeEqualsPredicate = criteriaBuilder.equal(resource.get(Resource_.code.getName()),
				resourceCode);
		query.where(criteriaBuilder.and(weekStartDatePredicate, savedSatusPredicate, resourceCodeEqualsPredicate));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public Set<TimeSheetWeekStatusVM> findWeeksPendingForSubmissionSinceDate(String resourceCode, LocalDate sinceDate) {
		List<Week> weeks = Week.weeksSince(TIMESHEET_PROPERTIES.WEEK_START_DAY(), TIMESHEET_PROPERTIES.WEEK_END_DAY(),
				sinceDate);
		Set<TimeSheetWeekStatusVM> resultWeeks = new TreeSet<>(
				(x, y) -> x.getWeek().getWeekStartDate().compareTo(y.getWeek().getWeekStartDate()));
		weeks.forEach((week) -> resultWeeks
				.add(TimeSheetWeekStatusVM.ofStartingDatePendingForSubmission(week.startingFrom())));
		Set<TimeSheetWeekStatusVM> timesheetWeeks = findWeeklyStatusesByWeeks(resourceCode, weeks);
		timesheetWeeks.forEach((tw) -> {
			if (tw.getStatus() == TimeSheetStatusTypeVM.SAVED) {
				resultWeeks.remove(tw);
				resultWeeks.add(tw);
			} else if (tw.getStatus() == TimeSheetStatusTypeVM.SUBMITTED
					|| tw.getStatus() == TimeSheetStatusTypeVM.APPROVED
					|| tw.getStatus() == TimeSheetStatusTypeVM.REJECTED) {
				resultWeeks.remove(tw);
			}
		});

		return resultWeeks;
	}

	@Override
	public Set<TimeSheetWeekStatusVM> findWeeklyStatusesByWeeks(String resourceCode, List<Week> weeks) {
		// TODO: Apply sorting at query level and add one more method to filter by
		// status
		Set<TimeSheetWeekStatusVM> resultWeeks = new TreeSet<>(
				(x, y) -> x.getWeek().getWeekStartDate().compareTo(y.getWeek().getWeekStartDate()));

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeSheetWeekStatusVM> query = criteriaBuilder.createQuery(TimeSheetWeekStatusVM.class);
		Root<WeeklyTimeSheet> weeklyTimeSheet = query.from(WeeklyTimeSheet.class);
		Join<WeeklyTimeSheet, Resource> resource = weeklyTimeSheet.join(WeeklyTimeSheet_.resource);
		Join<WeeklyTimeSheet, WeeklyTimeSheetStatus> weeklyStatus = weeklyTimeSheet.join(WeeklyTimeSheet_.weeklyStatus);

		query.select(criteriaBuilder.construct(TimeSheetWeekStatusVM.class,
				weeklyTimeSheet.get(WeeklyTimeSheet_.week.getName()),
				weeklyStatus.get(WeeklyTimeSheetStatus_.status.getName()),
				weeklyTimeSheet.get(WeeklyTimeSheet_.id.getName())));

		Predicate resourceCodeEqualsPredicate = criteriaBuilder.equal(resource.get(Resource_.code.getName()),
				resourceCode);

		query.where(criteriaBuilder.and(resourceCodeEqualsPredicate,
				weeklyTimeSheet.get(WeeklyTimeSheet_.week.getName()).in(weeks)));

		resultWeeks.addAll(entityManager.createQuery(query).getResultList());
		return resultWeeks;
	}

	@Override
	public List<WeeklyTimeSheet> findAllTimeSheetsByDateRange(String resourceCode, DateRange dateRange,
			final DayOfWeek weekStartDay, final DayOfWeek weekEndDay) {

		LocalDate startDate = dateRange.fromDate().with(TemporalAdjusters.previousOrSame(weekStartDay));
		LocalDate endDate = dateRange.tillDate().with(TemporalAdjusters.nextOrSame(weekEndDay));

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WeeklyTimeSheet> query = criteriaBuilder.createQuery(WeeklyTimeSheet.class);
		Root<WeeklyTimeSheet> weeklyTimeSheet = query.from(WeeklyTimeSheet.class);
		Join<WeeklyTimeSheet, Resource> resource = weeklyTimeSheet.join(WeeklyTimeSheet_.resource);

		query.select(weeklyTimeSheet);

		Predicate resourceCodeEqualsPredicate = criteriaBuilder.equal(resource.get(Resource_.code.getName()),
				resourceCode);

		Predicate weekStartDatePredicate = criteriaBuilder.greaterThanOrEqualTo(weeklyTimeSheet
				.get(WeeklyTimeSheet_.week.getName()).get(Week_.dateRange.getName()).get(DateRange_.fromDate.getName()),
				startDate);
		if (!dateRange.isOpenEnded()) {
			Predicate weekEndDatePredicate = criteriaBuilder
					.lessThanOrEqualTo(weeklyTimeSheet.get(WeeklyTimeSheet_.week.getName())
							.get(Week_.dateRange.getName()).get(DateRange_.tillDate.getName()), endDate);
			query.where(criteriaBuilder.and(weekStartDatePredicate, weekEndDatePredicate, resourceCodeEqualsPredicate));
		} else {
			query.where(criteriaBuilder.and(weekStartDatePredicate, resourceCodeEqualsPredicate));
		}

		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<WeeklyTimeSheet> findTimeSheetsByWeeks(String resourceCode, List<Week> weeks) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WeeklyTimeSheet> query = criteriaBuilder.createQuery(WeeklyTimeSheet.class);
		Root<WeeklyTimeSheet> weeklyTimeSheet = query.from(WeeklyTimeSheet.class);
		Join<WeeklyTimeSheet, Resource> resource = weeklyTimeSheet.join(WeeklyTimeSheet_.resource);
		query.select(weeklyTimeSheet);

		Predicate resourceCodeEqualsPredicate = criteriaBuilder.equal(resource.get(Resource_.code.getName()),
				resourceCode);

		query.where(criteriaBuilder.and(resourceCodeEqualsPredicate,
				weeklyTimeSheet.get(WeeklyTimeSheet_.week.getName()).in(weeks)));

		return entityManager.createQuery(query).getResultList();
	}

}
