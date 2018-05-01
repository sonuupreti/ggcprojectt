package com.gspann.itrack.domain.common;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class DateRange {

	@NotNull
	@Column(name = "FROM_DATE", nullable = false)
	private LocalDate fromDate;

	@Column(name = "TILL_DATE", nullable = true)
	private LocalDate tillDate;
	
	public void endOn(final LocalDate endDate) {
		this.tillDate = endDate;
	}
	
	public void startOn(final LocalDate endDate) {
		this.tillDate = endDate;
	}

	public static DateRangeBuilder dateRange() {
		return new DateRangeBuilder();
	}

	public interface StartDateBuilder {
		EndDateBuilder startingOn(final LocalDate fromDate);

		DateRange startIndefinitelyOn(final LocalDate fromDate);
	}

	public interface EndDateBuilder {
		DateRange endingOn(final LocalDate fromDate);
	}

	public static class DateRangeBuilder implements StartDateBuilder, EndDateBuilder {
		private DateRange dateRange;

		DateRangeBuilder() {
			dateRange = new DateRange();
		}

		@Override
		public EndDateBuilder startingOn(LocalDate fromDate) {
			dateRange.fromDate = fromDate;
			return this;
		}

		@Override
		public DateRange startIndefinitelyOn(LocalDate fromDate) {
			dateRange.fromDate = fromDate;
			return this.dateRange;
		}

		@Override
		public DateRange endingOn(LocalDate tillDate) {
			dateRange.tillDate = tillDate;
			return this.dateRange;
		}
	}

	public static void main(String[] args) {
		dateRange().startingOn(LocalDate.now()).endingOn(LocalDate.now().plusDays(7));
		dateRange().startIndefinitelyOn(LocalDate.now());
	}
}
