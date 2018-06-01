package com.gspann.itrack.domain.model.org.holidays;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.location.City;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
@Table(name = "HOLIDAY_LOCATION_MAP")
@Access(AccessType.FIELD)
public class HolidayLocation {

	@EmbeddedId
	private HolidayLocationId id = new HolidayLocationId();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("holidayId")
	private Holiday holiday;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("locationId")
	private City location;

	@NotNull
	// @formatter:off
	@ManyToOne(targetEntity = com.gspann.itrack.domain.model.org.holidays.Occasion.class, 
			fetch = FetchType.EAGER, optional = false)
	// @formatter:on
	private Occasion occasion;
	
	public static HolidayLocation of(final City location, final Occasion occasion) {
		HolidayLocation holidayLocation = new HolidayLocation();
		holidayLocation.location = location;
		holidayLocation.occasion = occasion;
		return holidayLocation;
	}
	
	public HolidayLocation setHoliday(final Holiday holiday) {
		this.holiday = holiday;
		return this;
	}
}
