package com.gspann.itrack.domain.model.timesheets;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.gspann.itrack.domain.common.type.Buildable;
import com.gspann.itrack.domain.model.docs.Document;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;

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
@EqualsAndHashCode(of = { "week", "resource" }, callSuper = false)
@Entity
@Table(name = "WEEKLY_TIME_SHEETS")
public class WeeklyTimeSheet extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	private Week week;

	@NotNull
	@Column(name = "STANDARD_HOURS", nullable = false, length = 5)
	private Duration standardHours;

	@NotNull
	@Column(name = "TOTAL_HOURS", nullable = false, length = 5)
	// @Column(name = "TOTAL_HOURS", nullable = false, length = 5, updatable =
	// false, insertable = false)
	// TODO: Set updatable and insertable as false and
	// apply formula (SQL) to calculate total hours from daily_timesheets
	private Duration totalHours;

//	@Column(name = "COMMENTS", nullable = true, length = 255)
//	private String comments;

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

	// @Column(name = "MATCHING_CUSTOMER_TIME_SHEET", length = 1)
	// private boolean isMatchingCustomerTimeSheet = false;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", nullable = false)
	// TODO: Set updatable and insertable as false and
	// apply formula (SQL) to calculate total hours from time_sheet_Entries
	private TimesheetStatus status;

	@Column(name = "USE_AS_TEMPLATE", length = 1)
	private boolean useAsTemplate = false;

	public Map<DayOfWeek, DailyTimeSheet> dayWiseDailyTimeSheets() {
		return dailyTimeSheets.stream()
				.collect(Collectors.toMap(x -> x.day(), x -> x));
	}
	
	public void save() {
		
	}
	
	public void submit() {
		
	}
	
	public void saveAsTemplate() {
		
	}

	public void approve() {

	}

	public void approve(final Project project) {

	}

	public void reject() {

	}

	public void reject(final Project project) {

	}

	public static WeeklyTimeSheetWeekBuilder of(final Resource resource) {
		return new WeeklyTimeSheetBuilder(resource);
	}

	public interface WeeklyTimeSheetWeekBuilder {
		public WeeklyTimeSheetStandardHoursBuilder forWeekOf(final Week week);
	}

	public interface WeeklyTimeSheetStandardHoursBuilder {
		public WeeklyTimeSheetDailyTimeEntriesBuilder withStandardHours(final Duration weeklyStandardHours);

		public WeeklyTimeSheetDailyTimeEntriesBuilder withDefaultStandardHours();

	}

	public interface WeeklyTimeSheetDailyTimeEntriesBuilder extends Buildable<WeeklyTimeSheet> {
		public ScreenShotBuilder withDailyTimeSheets(final Set<DailyTimeSheet> dailyTimeSheets);
		public TuesdayTimeEntriesBuilder forMonday(final DailyTimeSheet dailyTimesheet);
	}

	public interface TuesdayTimeEntriesBuilder extends Buildable<WeeklyTimeSheet> {
		public WednesdayTimeEntriesBuilder forTuesday(final DailyTimeSheet dailyTimesheet);
	}
	
	public interface WednesdayTimeEntriesBuilder extends Buildable<WeeklyTimeSheet> {
		public ThursdayTimeEntriesBuilder forWednesday(final DailyTimeSheet dailyTimesheet);
	}

	public interface ThursdayTimeEntriesBuilder extends Buildable<WeeklyTimeSheet> {
		public FridayTimeEntriesBuilder forThursday(final DailyTimeSheet dailyTimesheet);
	}

	public interface FridayTimeEntriesBuilder extends Buildable<WeeklyTimeSheet> {
		public SaturdayTimeEntriesBuilder forFriday(final DailyTimeSheet dailyTimesheet);
	}

	public interface SaturdayTimeEntriesBuilder extends Buildable<WeeklyTimeSheet> {
		public SundayTimeEntriesBuilder forSaturday(final DailyTimeSheet dailyTimesheet);
	}

	public interface SundayTimeEntriesBuilder extends Buildable<WeeklyTimeSheet> {
		public ScreenShotBuilder forSunday(final DailyTimeSheet dailyTimesheet);
	}

	public interface ScreenShotBuilder extends Buildable<WeeklyTimeSheet> {
		public Buildable<WeeklyTimeSheet> withScreenShot(final Document clientTimeSheetScreenShot);
	}

	public static class WeeklyTimeSheetBuilder implements WeeklyTimeSheetWeekBuilder,
			WeeklyTimeSheetStandardHoursBuilder, WeeklyTimeSheetDailyTimeEntriesBuilder, TuesdayTimeEntriesBuilder,
			WednesdayTimeEntriesBuilder, ThursdayTimeEntriesBuilder, FridayTimeEntriesBuilder,
			SaturdayTimeEntriesBuilder, SundayTimeEntriesBuilder, ScreenShotBuilder {

		private WeeklyTimeSheet weeklyTimeSheet;

		public WeeklyTimeSheetBuilder(final Resource resource) {
			this.weeklyTimeSheet = new WeeklyTimeSheet();
			this.weeklyTimeSheet.resource = resource;
			this.weeklyTimeSheet.status = TimesheetStatus.SAVED;
		}

		@Override
		public WeeklyTimeSheetStandardHoursBuilder forWeekOf(Week week) {
			this.weeklyTimeSheet.week = week;
			return this;
		}

		@Override
		public WeeklyTimeSheetDailyTimeEntriesBuilder withStandardHours(Duration weeklyStandardHours) {
			this.weeklyTimeSheet.standardHours = weeklyStandardHours;
			return this;
		}

		@Override
		public WeeklyTimeSheetDailyTimeEntriesBuilder withDefaultStandardHours() {
			this.weeklyTimeSheet.standardHours = Duration.ofHours(40);
			return this;
		}

		@Override
		public ScreenShotBuilder withDailyTimeSheets(Set<DailyTimeSheet> dailyTimeSheets) {
			this.weeklyTimeSheet.dailyTimeSheets = dailyTimeSheets;
			return this;
		}

		@Override
		public TuesdayTimeEntriesBuilder forMonday(DailyTimeSheet dailyTimesheet) {
			this.weeklyTimeSheet.dailyTimeSheets.add(dailyTimesheet.setWeeklyTimeSheet(this.weeklyTimeSheet));
			return this;
		}

		@Override
		public WednesdayTimeEntriesBuilder forTuesday(DailyTimeSheet dailyTimesheet) {
			this.weeklyTimeSheet.dailyTimeSheets.add(dailyTimesheet.setWeeklyTimeSheet(this.weeklyTimeSheet));
			return this;
		}

		@Override
		public ThursdayTimeEntriesBuilder forWednesday(DailyTimeSheet dailyTimesheet) {
			this.weeklyTimeSheet.dailyTimeSheets.add(dailyTimesheet.setWeeklyTimeSheet(this.weeklyTimeSheet));
			return this;
		}

		@Override
		public FridayTimeEntriesBuilder forThursday(DailyTimeSheet dailyTimesheet) {
			this.weeklyTimeSheet.dailyTimeSheets.add(dailyTimesheet.setWeeklyTimeSheet(this.weeklyTimeSheet));
			return this;
		}

		@Override
		public SaturdayTimeEntriesBuilder forFriday(DailyTimeSheet dailyTimesheet) {
			this.weeklyTimeSheet.dailyTimeSheets.add(dailyTimesheet.setWeeklyTimeSheet(this.weeklyTimeSheet));
			return this;
		}

		@Override
		public SundayTimeEntriesBuilder forSaturday(DailyTimeSheet dailyTimesheet) {
			this.weeklyTimeSheet.dailyTimeSheets.add(dailyTimesheet.setWeeklyTimeSheet(this.weeklyTimeSheet));
			return this;
		}

		@Override
		public ScreenShotBuilder forSunday(DailyTimeSheet dailyTimesheet) {
			this.weeklyTimeSheet.dailyTimeSheets.add(dailyTimesheet.setWeeklyTimeSheet(this.weeklyTimeSheet));
			return this;
		}

		@Override
		public Buildable<WeeklyTimeSheet> withScreenShot(Document clientTimeSheetScreenShot) {
			this.weeklyTimeSheet.clientTimeSheetScreenShot = clientTimeSheetScreenShot;
			return this;
		}

		@Override
		public WeeklyTimeSheet build() {
			this.weeklyTimeSheet.totalHours = Duration.ZERO;
			for (var dailyTimeSheet : this.weeklyTimeSheet.dailyTimeSheets) {
				this.weeklyTimeSheet.totalHours = this.weeklyTimeSheet.totalHours.plus(dailyTimeSheet.totalHours());
			}
			return this.weeklyTimeSheet;
		}
	}
}
