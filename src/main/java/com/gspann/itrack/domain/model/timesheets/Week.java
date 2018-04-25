package com.gspann.itrack.domain.model.timesheets;

import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.DateRange;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class Week {

	@NotNull
	private DateRange dateRange;

	@Transient
	private Duration duration;

	private Week(LocalDate startingFrom, int weekLength) {
		this.duration = Duration.ofDays(weekLength);
		this.dateRange = DateRange.dateRange().startingOn(startingFrom).endingOn(startingFrom.plusDays(weekLength));
	}

	public static Week of(LocalDate startingFrom) {
		Week week = new Week(startingFrom, 6);
		return week;
	}

	public static Week of(LocalDate startingFrom, int weekLength) {
		Week week = new Week(startingFrom, weekLength);
		return week;
	}

	public LocalDate startingFrom() {
		return dateRange != null ? dateRange.fromDate() : null;
	}

	public LocalDate endingOn() {
		return dateRange != null ? dateRange.tillDate() : null;
	}

	public Duration duration() {
		return dateRange != null ? dateRange.fromDate() != null && dateRange.tillDate() != null
				? Duration.between(dateRange.fromDate(), dateRange.tillDate())
				: null : null;
	}
}
