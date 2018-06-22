package com.gspann.itrack.domain.model.org.holidays;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import com.gspann.itrack.domain.model.common.type.AbstractIdentifiable;
import com.gspann.itrack.domain.model.common.type.Buildable;
import com.gspann.itrack.domain.model.location.City;
import com.gspann.itrack.domain.model.location.Country;

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
@EqualsAndHashCode(of = { "calendar", "date" }, callSuper = false)
@Entity
@Table(name = "HOLIDAYS")
@Immutable
public class Holiday extends AbstractIdentifiable<Integer> {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "year", foreignKey = @ForeignKey(name = FK_HOLIDAYS_HOLIDAY_CALENDARS_YEAR))
	private HolidayCalendar calendar;

	@NotNull
	@Column(name = "DATE", nullable = false)
	private LocalDate date;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "DAY", nullable = false)
	private DayOfWeek day;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TYPE", nullable = false)
	private HolidayType type;

	@OneToMany(mappedBy = "holiday", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HolidayLocation> locationOcassions = new HashSet<>();

	public Holiday addHolidayLocations(final Set<HolidayLocation> locationOcassions) {
		this.locationOcassions.addAll(locationOcassions);
		return this;
	}

	public Holiday addHolidayLocation(final HolidayLocation locationOcassion) {
		this.locationOcassions.add(locationOcassion);
		return this;
	}

	public Holiday setHolidayCalendar(final HolidayCalendar calendar) {
		this.calendar = calendar;
		return this;
	}

	public static HolidayTypeBuilder on(final LocalDate date) {
		return new HolidayBuilder(date);
	}

	public interface HolidayTypeBuilder {
		public HolidayOccasionBuilder national(final Country country);

		public HolidayOccasionBuilder regional(final City city);
	}

	public interface HolidayOccasionBuilder {
		public AddMoreBuilder withOccassion(Occasion occasion);
	}

	public interface AddMoreBuilder extends Buildable<Holiday> {
		public HolidayTypeBuilder and();
	}

	public static class HolidayBuilder implements HolidayTypeBuilder, HolidayOccasionBuilder, AddMoreBuilder {
		private Holiday holiday;
		private Set<City> cities;
		private City city;
		private Occasion occasion;

		public HolidayBuilder(final LocalDate date) {
			this.holiday = new Holiday();
			this.holiday.date = date;
			this.holiday.day = date.getDayOfWeek();
			
			this.cities = new HashSet<>();
		}

		@Override
		public HolidayOccasionBuilder national(Country country) {
			this.holiday.type = HolidayType.NATIONAL;
			for (var state : country.states()) {
				cities.addAll(state.cities());
			}
			return this;
		}

		@Override
		public HolidayOccasionBuilder regional(City city) {
			this.holiday.type = HolidayType.REGIONAL;
			this.city = city;
			return this;
		}

		@Override
		public AddMoreBuilder withOccassion(Occasion occasion) {
			this.occasion = occasion;
			return this;
		}

		@Override
		public HolidayTypeBuilder and() {
			if (this.holiday.type == HolidayType.REGIONAL) {
				this.holiday.locationOcassions
						.add(HolidayLocation.of(this.city, this.occasion).setHoliday(this.holiday));
			} else {
				for (var ct : cities) {
					this.holiday.locationOcassions.add(HolidayLocation.of(ct, this.occasion).setHoliday(this.holiday));
				}
			}
			return this;
		}

		@Override
		public Holiday build() {
			if (this.holiday.type == HolidayType.REGIONAL) {
				this.holiday.locationOcassions
						.add(HolidayLocation.of(this.city, this.occasion).setHoliday(this.holiday));
			} else {
				for (var ct : cities) {
					this.holiday.locationOcassions.add(HolidayLocation.of(ct, this.occasion).setHoliday(this.holiday));
				}
			}
			return this.holiday;
		}
	}
}
