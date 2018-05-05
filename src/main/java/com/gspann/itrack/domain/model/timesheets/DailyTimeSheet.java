package com.gspann.itrack.domain.model.timesheets;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
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

import com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "DAILY_TIME_SHEETS")
public class DailyTimeSheet extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "DAY_OF_WEEK", nullable = false)
	private DayOfWeek dow;

	@NotNull
	@Column(name = "STANDARD_HOURS", nullable = false, length = 5)
	private Duration standardHours;

	@NotNull
	@Column(name = "TOTAL_HOURS", nullable = false, length = 5, updatable = false, insertable = false)
	private Duration totalHours;

	@NotNull
	@ManyToOne
    @JoinColumn(name = "WEEKLY_TIME_SHEET_ID", updatable = false, insertable = false, foreignKey = @ForeignKey(name = FK_DAILY_TIME_SHEETS_WEEKLY_TIME_SHEET_ID))
	private WeeklyTimeSheet weeklyTimeSheet;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "DAILY_TIME_SHEET_ID", nullable = false)
	private Set<TimeSheetEntry> entries = new HashSet<TimeSheetEntry>();

	public Map<String, TimeSheetEntry> projectWiseEntries() {
		return null;
	}
}
