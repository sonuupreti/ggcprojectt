package com.gspann.itrack.domain.model.timesheets;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.LinkedHashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.docs.Document;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "WEEKLY_TIME_SHEETS")
public class WeeklyTimeSheet extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	private Week week;

	@NotNull
	@Column(name = "STANDARD_HOURS", nullable = false, length = 5)
	private Duration standardHours;

	@NotNull
	@Column(name = "TOTAL_HOURS", nullable = false, length = 5, updatable = false, insertable = false)
	// TODO: Set updatable and insertable as false and
	// apply formula (SQL) to calculate total hours from daily_timesheets
	private Duration totalHours;

	@Column(name = "COMMENTS", nullable = true, length = 255)
	private String comments;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "RESOURCE_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_WEEKLY_TIME_SHEETS_RESOURCE_CODE))
	private Resource resource;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "WEEKLY_TIME_SHEET_ID", nullable = false)
	@org.hibernate.annotations.OrderBy(clause = "DAY_OF_WEEK asc")
	private Set<DailyTimeSheet> dailyTimeSheets = new LinkedHashSet<DailyTimeSheet>();

	@OneToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CLIENT_TIMESHEET_SCREEN_SHOT_ID", unique = false, nullable = true, foreignKey = @ForeignKey(name = FK_WEEKLY_TIME_SHEETS_CLIENT_TIMESHEET_SCREEN_SHOT_ID))
	private Document clientTimeSheetScreenShot;

//	@Column(name = "MATCHING_CUSTOMER_TIME_SHEET", length = 1)
//	private boolean isMatchingCustomerTimeSheet = false;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", nullable = false)
	// TODO: Set updatable and insertable as false and
	// apply formula (SQL) to calculate total hours from time_sheet_Entries
	private TimesheetStatus status;

	@Column(name = "USE_AS_TEMPLATE", length = 1)
	private boolean useAsTemplate = false;

	public Map<DayOfWeek, DailyTimeSheet> dayWiseDailyTimeSheets() {
		return null;
	}
}
