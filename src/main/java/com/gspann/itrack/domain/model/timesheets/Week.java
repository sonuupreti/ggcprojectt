package com.gspann.itrack.domain.model.timesheets;

import static java.time.temporal.ChronoUnit.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.DateRange;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Embeddable
@EqualsAndHashCode(of = "dateRange")
@ToString(includeFieldNames = true)
public class Week implements Comparable<Week> {

	private static final String DATE_PATTERN_DATE_DAY = "d";
	private static final String DATE_PATTERN_DATE_DAY_AND_MONTH = "d MMM";
	private static final String DATE_PATTERN_DATE_DAY_MONTH_AND_YEAR = "d MMM, YY";

	public static final Comparator<Week> DESCENDING_ORDER = (x, y) -> y.startingFrom().compareTo(x.startingFrom());
	public static final Comparator<Week> ASCENDING_ORDER = (x, y) -> x.startingFrom().compareTo(y.startingFrom());

	@NotNull
	private DateRange dateRange;

	// @Transient
	@NotNull
	@Column(name = "WEEK_LENGTH", nullable = false, length = 5)
	private Duration duration;

	@Transient
	private String name;

	private Week(final LocalDate startingFrom, final short weekLength) {
		if (weekLength < 1) {
			throw new IllegalArgumentException("weekLength:" + weekLength + " must be more than 1");
		}
		this.duration = Duration.ofDays(weekLength);
		this.dateRange = DateRange.dateRange().startingOn(startingFrom).endingOn(startingFrom.plusDays(weekLength - 1));
		setWeekName(dateRange);
	}

	public static Week of(final LocalDate startingFrom) {
		Week week = new Week(startingFrom, (short) 7);
		return week;
	}

	public static Week of(final LocalDate weekStartDate, final LocalDate weekEndDate) {
		if (!weekEndDate.isAfter(weekStartDate)) {
			throw new IllegalArgumentException("weekEndDate : " + weekEndDate + " must be after : " + weekStartDate);
		}
		Week week = new Week(weekStartDate, (short) DAYS.between(weekStartDate, weekEndDate.plusDays(1)));
		return week;
	}

	public static Week of(final LocalDate startingFrom, final short weekLength) {
		Week week = new Week(startingFrom, weekLength);
		return week;
	}

	public static Week containingDate(final LocalDate date, final DayOfWeek weekStartDay, final DayOfWeek weekEndDay) {
		if (weekStartDay == weekEndDay) {
			throw new IllegalArgumentException(
					"weekStartDay : " + weekStartDay + " and weekEndDay : " + weekEndDay + " must be different");
		}
		LocalDate weekStartDate = date.with(TemporalAdjusters.previousOrSame(weekStartDay));
		LocalDate weekEndDate = date.with(TemporalAdjusters.nextOrSame(weekEndDay));
		return Week.of(weekStartDate, weekEndDate);
	}

	public static Week current(final DayOfWeek weekStartDay, final DayOfWeek weekEndDay) {
		if (weekStartDay == weekEndDay) {
			throw new IllegalArgumentException(
					"weekStartDay : " + weekStartDay + " and weekEndDay : " + weekEndDay + " must be different");
		}
		LocalDate now = LocalDate.now();
		LocalDate weekStartDate = now.with(TemporalAdjusters.previousOrSame(weekStartDay));
		LocalDate weekEndDate = now.with(TemporalAdjusters.nextOrSame(weekEndDay));
		return Week.of(weekStartDate, weekEndDate);
	}

	public LocalDate startingFrom() {
		return dateRange != null ? dateRange.fromDate() : null;
	}

	public LocalDate endingOn() {
		return dateRange != null ? dateRange.tillDate() : null;
	}

	public Duration duration() {
		return dateRange != null ? dateRange.fromDate() != null && dateRange.tillDate() != null
				? Duration.ofDays(DAYS.between(dateRange.fromDate(), dateRange.tillDate()))
				: null : null;
	}

	public String name() {
		return this.name;
	}

	private void setWeekName(final DateRange weekDateRange) {
		boolean sameYear = weekDateRange.fromDate().getYear() == weekDateRange.tillDate().getYear() ? true : false;
		boolean sameMonth = weekDateRange.fromDate().getMonth() == weekDateRange.tillDate().getMonth() ? true : false;
		if (sameYear) {
			if (sameMonth) {
				this.name = weekDateRange.fromDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN_DATE_DAY)) + " - "
						+ weekDateRange.tillDate()
								.format(DateTimeFormatter.ofPattern(DATE_PATTERN_DATE_DAY_MONTH_AND_YEAR));
			} else {
				this.name = weekDateRange.fromDate()
						.format(DateTimeFormatter.ofPattern(DATE_PATTERN_DATE_DAY_AND_MONTH)) + " - "
						+ weekDateRange.tillDate()
								.format(DateTimeFormatter.ofPattern(DATE_PATTERN_DATE_DAY_MONTH_AND_YEAR));
			}
		} else {
			this.name = weekDateRange.fromDate()
					.format(DateTimeFormatter.ofPattern(DATE_PATTERN_DATE_DAY_MONTH_AND_YEAR)) + " - "
					+ weekDateRange.tillDate()
							.format(DateTimeFormatter.ofPattern(DATE_PATTERN_DATE_DAY_MONTH_AND_YEAR));
		}
	}

	public Week updateWeekName() {
		setWeekName(this.dateRange);
		return this;
	}

	public Week next() {
		return Week.of(this.endingOn().plusDays(1));
	}

	public Week previous() {
		return Week.of(this.startingFrom().minusDays(7));
	}

	public boolean isCurrent() {
		return this.dateRange.contains(LocalDate.now());
	}

	public boolean isNext(final Week week) {
		return this.endingOn().plusDays(1).equals(week.startingFrom());
	}

	public boolean isPrevious(final Week week) {
		return this.startingFrom().minusDays(1).equals(week.endingOn());
	}

	public static List<Week> weeksSince(final DayOfWeek weekStartDay, final DayOfWeek weekEndDay,
			final LocalDate sinceDate,
			final Comparator<Week> comparator) {
		if (weekStartDay == weekEndDay) {
			throw new IllegalArgumentException(
					"weekStartDay : " + weekStartDay + " and weekEndDay : " + weekEndDay + " must be different");
		}
		LocalDate now = LocalDate.now();
//		if (DAYS.between(sinceDate, now.with(TemporalAdjusters.previousOrSame(weekStartDay))) < 0) {
		if (DAYS.between(sinceDate, now) < 0) {
			return Collections.<Week>emptyList();
		}

		LocalDate firstWeekStartDate = sinceDate.with(TemporalAdjusters.previousOrSame(weekStartDay));
		LocalDate lastWeekEndDate = now.with(TemporalAdjusters.nextOrSame(weekEndDay));
		LocalDate current = firstWeekStartDate;
		List<Week> weeks = new ArrayList<>();
		while (current.isBefore(lastWeekEndDate)) {
			weeks.add(Week.of(current));
			current = current.plusDays(7);
		}
		Collections.sort(weeks, comparator);
		return Collections.unmodifiableList(weeks);
	}

	public static List<Week> lastWeeks(final DayOfWeek weekStartDay, final DayOfWeek weekEndDay, final int count,
			final Comparator<Week> comparator) {
		if (weekStartDay == weekEndDay) {
			throw new IllegalArgumentException(
					"weekStartDay : " + weekStartDay + " and weekEndDay : " + weekEndDay + " must be different");
		}
		if (count < 0) {
			throw new IllegalArgumentException(
					"count : " + count + " can be 0 to get only current week otherwise greater than 0");
		}
		Week currentWeek = current(weekStartDay, weekEndDay);
		List<Week> weeks = new ArrayList<>();
		weeks.add(currentWeek);
		for (int i = 1; i < count; i++) {
			currentWeek = currentWeek.previous();
			weeks.add(currentWeek);
		}
		Collections.sort(weeks, comparator);
		return Collections.unmodifiableList(weeks);
	}

	public static List<Week> weeksForDateRange(final DayOfWeek weekStartDay, final DayOfWeek weekEndDay,
			final DateRange dateRange, final Comparator<Week> comparator) {
		if (weekStartDay == weekEndDay) {
			throw new IllegalArgumentException(
					"weekStartDay : " + weekStartDay + " and weekEndDay : " + weekEndDay + " must be different");
		}
		if (DAYS.between(dateRange.fromDate(), dateRange.tillDate()) <= 0) {
			throw new IllegalArgumentException("Number of days between date range: " + dateRange
					+ " start and end date must be more that zero : ");
		}

		Week week = containingDate(dateRange.fromDate(), weekStartDay, weekEndDay);
		List<Week> weeks = new ArrayList<>();
		weeks.add(week);
		while (!week.next().startingFrom().isAfter(dateRange.tillDate())) {
			week = week.next();
			weeks.add(week);
		}

		Collections.sort(weeks, comparator);
		return Collections.unmodifiableList(weeks);
	}

	public static List<Week> weeksForMonth(final DayOfWeek weekStartDay, final DayOfWeek weekEndDay,
			final YearMonth month, final Comparator<Week> comparator) {
		return weeksForDateRange(weekStartDay, weekEndDay,
				DateRange.dateRange().startingOn(month.atDay(1)).endingOn(month.atEndOfMonth()), comparator);
	}

	@Override
	public int compareTo(Week other) {
		return startingFrom().compareTo(other.startingFrom());
	}
}
