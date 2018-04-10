package com.gspann.itrack.domain.model.org.holidays;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.type.Assignable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "HOLIDAY_CALENDARS")
public class HolidayCalendar implements Assignable<Year> {

	@NotNull
	@Id
	@Column(name = "YEAR", nullable = false)
	private Year year;

	@OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	public List<Holiday> holidays = new ArrayList<>();

	@Override
	public Year code() {
		return this.year;
	}
}
