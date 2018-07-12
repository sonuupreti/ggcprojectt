package com.gspann.itrack.adapter.persistence.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gspann.itrack.domain.model.common.DateRange;
import com.gspann.itrack.domain.model.common.DateRange_;
import com.gspann.itrack.domain.model.common.dto.Pair;
import com.gspann.itrack.domain.model.docs.Document_;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;
import com.gspann.itrack.domain.model.staff.Resource_;
import com.gspann.itrack.domain.model.timesheets.ProjectTimeSheetStatus;
import com.gspann.itrack.domain.model.timesheets.ProjectTimeSheetStatus_;
import com.gspann.itrack.domain.model.timesheets.TimesheetStatus;
import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.Week_;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheetStatus;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheetStatus_;
import com.gspann.itrack.domain.model.timesheets.WeeklyTimeSheet_;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetResourceWeekStatusVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetStatusTypeVM;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;
import com.gspann.itrack.infra.config.ApplicationProperties;

@Repository
public class TimeSheetRepositoryImpl implements TimeSheetRepositoryJPA {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ProjectRepository projectRepository;

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
				sinceDate, Week.DESCENDING_ORDER);
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
				// TODO: Needs to filter in query itself rather than in application layer
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

		query.orderBy(criteriaBuilder.asc(weeklyTimeSheet.get(WeeklyTimeSheet_.week.getName())
				.get(Week_.dateRange.getName()).get(DateRange_.fromDate.getName())));
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

	@Override
	public Map<Week, TimeSheetWeekStatusVM> findPendingActionWeeksForResourceByWeeks(String resourceCode,
			List<Week> weeks) {

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
				weeklyTimeSheet.get(WeeklyTimeSheet_.week.getName()).in(weeks),
				weeklyTimeSheet.get(WeeklyTimeSheet_.weeklyStatus).get(WeeklyTimeSheetStatus_.status)
						.in(Arrays.asList(TimesheetStatus.SAVED, TimesheetStatus.REJECTED,
								TimesheetStatus.PARTIALLY_APPROVED_REJECTED,
								TimesheetStatus.PARTIALLY_REJECTED_PENDING))));

		List<TimeSheetWeekStatusVM> resultWeeks = entityManager.createQuery(query).getResultList();
		if (resultWeeks != null && !resultWeeks.isEmpty()) {
			return resultWeeks.stream().collect(Collectors.toMap(TimeSheetWeekStatusVM::toWeek, x -> x));
		} else {
			return Collections.emptyMap();
		}
	}

	// @Override
	// public Map<Week, TimeSheetWeekStatusVM>
	// findPendingActionWeeksForResourceSinceDate(String resourceCode,
	// LocalDate sinceDate) {
	// List<Week> weeks = Week.weeksSince(TIMESHEET_PROPERTIES.WEEK_START_DAY(),
	// TIMESHEET_PROPERTIES.WEEK_END_DAY(),
	// sinceDate);
	// return findPendingActionWeeksForResourceByWeeks(resourceCode, weeks);
	// }

	@Override
	public List<TimeSheetResourceWeekStatusVM> findPendingActionsForApproverByWeek(final String approverCode,
			final Week week) {

		// List<ProjectSummary> approverProjects =
		// projectRepository.findProjectSummariesByApproverInAWeek(week, approverCode);

		List<Pair<String, String>> projectDetails = projectRepository
				.findAllProjectCodeAndNameByOffshoreManagerCode(approverCode);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeSheetResourceWeekStatusVM> query = criteriaBuilder
				.createQuery(TimeSheetResourceWeekStatusVM.class);
		Root<WeeklyTimeSheet> weeklyTimeSheet = query.from(WeeklyTimeSheet.class);
		Join<WeeklyTimeSheet, Resource> resource = weeklyTimeSheet.join(WeeklyTimeSheet_.resource);
		Join<WeeklyTimeSheet, WeeklyTimeSheetStatus> weeklyStatus = weeklyTimeSheet.join(WeeklyTimeSheet_.weeklyStatus);
		MapJoin<WeeklyTimeSheetStatus, Project, ProjectTimeSheetStatus> projectTimeSheetStatuses = weeklyStatus
				.join(WeeklyTimeSheetStatus_.projectTimeSheetStatuses);

		query.select(criteriaBuilder.construct(TimeSheetResourceWeekStatusVM.class,
				resource.get(Resource_.code.getName()), resource.get(Resource_.name.getName()),
				resource.get(Resource_.image.getName()).get(Document_.data.getName()),
				weeklyTimeSheet.get(WeeklyTimeSheet_.week.getName()),
				weeklyStatus.get(WeeklyTimeSheetStatus_.status.getName()),
				weeklyTimeSheet.get(WeeklyTimeSheet_.id.getName())));

		List<Project> projects = projectDetails.stream().map((p) -> projectRepository.getOne(p.getKey()))
				.collect(Collectors.toList());

		Predicate weekEqualsPredicate = criteriaBuilder.equal(weeklyTimeSheet.get(WeeklyTimeSheet_.week), week);
		Predicate approverProjectsInPredicate = projectTimeSheetStatuses.key().in(projects);
		// TODO: Add further statuses for which action is pending on Approver such as
		// ADJUSTMENT_REQUESTED
		Predicate projectTimeSheetStatusnPredicate = projectTimeSheetStatuses.value()
				.get(ProjectTimeSheetStatus_.projectWiseStatus).in(Arrays.asList(TimesheetStatus.SUBMITTED));

		query.where(criteriaBuilder.and(weekEqualsPredicate, approverProjectsInPredicate,
				projectTimeSheetStatusnPredicate));
		query.orderBy(criteriaBuilder.asc(resource.get(Resource_.name.getName())));

		List<TimeSheetResourceWeekStatusVM> pendingActions = entityManager.createQuery(query).getResultList();
		if (pendingActions != null && !pendingActions.isEmpty()) {
			return Collections.unmodifiableList(pendingActions);
		} else {
			return Collections.emptyList();
		}
	}
}
