package com.gspann.itrack.domain.model.org.holidays;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import com.gspann.itrack.domain.common.type.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "HOLIDAYS")
@Immutable
public class Holiday extends AbstractIdentifiable<Long> {

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
	private List<HolidayLocation> locationOcassions = new ArrayList<>();
}
