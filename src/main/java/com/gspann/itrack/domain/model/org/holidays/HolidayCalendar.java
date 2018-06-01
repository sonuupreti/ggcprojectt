package com.gspann.itrack.domain.model.org.holidays;

import java.time.Year;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.type.Assignable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.var;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = "year")
@Entity
@Table(name = "HOLIDAY_CALENDARS")
public class HolidayCalendar implements Assignable<Year> {

	@NotNull
	@Id
	@Column(name = "YEAR", nullable = false)
	private Year year;

	@OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Holiday> holidays = new LinkedHashSet<>();

	@Override
	public Year code() {
		return this.year;
	}

	public static HolidayCalendar forYear(final Year year) {
		HolidayCalendar holidayCalendar = new HolidayCalendar();
		holidayCalendar.year = year;
		return holidayCalendar;
	}

	public HolidayCalendar addHolidays(final Set<Holiday> holidays) {
		for (var hd: holidays) {
			hd.setHolidayCalendar(this);
		}
		this.holidays.addAll(holidays);
		return this;
	}

	public HolidayCalendar addHoliday(final Holiday holiday) {
		this.holidays.add(holiday.setHolidayCalendar(this));
		return this;
	}
}
