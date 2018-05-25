package com.gspann.itrack.domain.model.timesheets;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.common.type.Buildable;
import com.gspann.itrack.domain.model.projects.Project;

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
@EqualsAndHashCode(of = { "day", "weeklyTimeSheet" }, callSuper = false)
@Entity
@Table(name = "DAILY_TIME_SHEETS")
public class DailyTimeSheet extends BaseIdentifiableVersionableEntity<Long, Long> {
	
	@NotNull
	@Column(name = "DATE", nullable = false)
	private LocalDate date;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "DAY_OF_WEEK", nullable = false)
	private DayOfWeek day;

	@NotNull
	@Column(name = "STANDARD_HOURS", nullable = false, length = 5)
	private Duration standardHours;

	@NotNull
	@Column(name = "TOTAL_HOURS", nullable = false, length = 5)
	// @Column(name = "TOTAL_HOURS", nullable = false, length = 5, updatable =
	// false, insertable = false)
	// TODO: Set updatable and insertable as false and
	// apply formula (SQL) to calculate total hours from time_sheet_Entries
	private Duration totalHours;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "DAY_TYPE", nullable = false)
	private DayType dayType;

	@Column(name = "COMMENTS", nullable = true, length = 255)
	private String dailyComments;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "DAILY_TIME_SHEET_ID", nullable = false)
	private Set<TimeSheetEntry> entries = new LinkedHashSet<>();

	@NotNull
	@ManyToOne
	@JoinColumn(name = "WEEKLY_TIME_SHEET_ID", updatable = false, insertable = false, foreignKey = @ForeignKey(name = FK_DAILY_TIME_SHEETS_WEEKLY_TIME_SHEET_ID))
	private WeeklyTimeSheet weeklyTimeSheet;

	public DailyTimeSheet setWeeklyTimeSheet(final WeeklyTimeSheet weeklyTimeSheet) {
		this.weeklyTimeSheet = weeklyTimeSheet;
		return this;
	}

	public Map<String, TimeSheetEntry> projectWiseEntries() {
		return null;
	}

	public static DailyTimeSheetDateBuilder withStandardHours(final Duration dailyStandardHours) {
		return new DailyTimeSheetBuilder(dailyStandardHours);
	}

	public static DailyTimeSheetDateBuilder withDefaultStandardHours() {
		return new DailyTimeSheetBuilder(Duration.ofHours(8));
	}

	public interface DailyTimeSheetDateBuilder {
		public DailyTimeSheetDayTypeBuilder forDate(final LocalDate date);
	}
	
	public interface DailyTimeSheetDayTypeBuilder {
		public Buildable<DailyTimeSheet> forHoliday(final DayOfWeek dow, final Project... projects);

		public Buildable<DailyTimeSheet> forWeekend(final DayOfWeek dow, final Project... projects);

		public Buildable<DailyTimeSheet> forWeekendSaturday(final Project... projects);

		public Buildable<DailyTimeSheet> forWeekendSunday(final Project... projects);

		public DailyTimeSheetEntryBuilder forWorkingDay();

		public DailyTimeSheetEntryBuilder withDaytype(final DayType dayType);
	}

	public interface DailyTimeSheetEntryBuilder {
		public DailyTimeSheetDailyEntryCommentsBuilder withEntry(final TimeSheetEntry timeEntry);

		public DailyTimeSheetDailyEntryCommentsBuilder withEntries(final Set<TimeSheetEntry> timeEntries);

		public DailyTimeSheetEntryDurationBuilder workedOn(final Project project);
	}

	public interface DailyTimeSheetDailyEntryCommentsBuilder extends Buildable<DailyTimeSheet> {
		public Buildable<DailyTimeSheet> withDailyComments(final String dailyComments);
	}

	public interface DailyTimeSheetEntryDurationBuilder {
		public DailyTimeSheetEntryCommentsBuilder forDuration(final Duration hours);
	}

	public interface DailyTimeSheetEntryCommentsBuilder
			extends DailyTimeSheetDailyCommentsBuilder, Buildable<DailyTimeSheet> {
		public DailyTimeSheetDailyCommentsBuilder onTasks(final String comments);
	}

	public interface DailyTimeSheetDailyCommentsBuilder
			extends DailyTimeSheetMoreEntryBuilder, Buildable<DailyTimeSheet> {
		public DailyTimeSheetMoreEntryBuilder dailyComments(final String dailyComments);
	}

	public interface DailyTimeSheetMoreEntryBuilder extends Buildable<DailyTimeSheet> {
		public DailyTimeSheetEntryBuilder and();
	}

	public static class DailyTimeSheetBuilder implements DailyTimeSheetDateBuilder, DailyTimeSheetDayTypeBuilder,
			DailyTimeSheetEntryBuilder, DailyTimeSheetEntryCommentsBuilder, 
			DailyTimeSheetEntryDurationBuilder, DailyTimeSheetDailyCommentsBuilder, DailyTimeSheetMoreEntryBuilder,
			DailyTimeSheetDailyEntryCommentsBuilder, Buildable<DailyTimeSheet> {

		private DailyTimeSheet dailyTimeSheet;
		private Project project;
		private Duration hours;
		private String comments;

		private boolean entryAlreadyAdded = false;

		public DailyTimeSheetBuilder(final Duration dailyStandardHours) {
			this.dailyTimeSheet = new DailyTimeSheet();
			this.dailyTimeSheet.standardHours = dailyStandardHours;
		}

		@Override
		public DailyTimeSheetDayTypeBuilder forDate(final LocalDate date) {
			this.dailyTimeSheet.date = date;
			this.dailyTimeSheet.day = date.getDayOfWeek();
			return this;
		}
		
		@Override
		public Buildable<DailyTimeSheet> forHoliday(final DayOfWeek dow, final Project... projects) {
			this.dailyTimeSheet.day = dow;
			this.dailyTimeSheet.dayType = DayType.HOLIDAY;
			for (var project : projects) {
				this.dailyTimeSheet.entries
						.add(TimeSheetEntry.forHoliday(project).setDailyTimeSheet(this.dailyTimeSheet));
			}
			entryAlreadyAdded = true;
			return this;
		}

		@Override
		public Buildable<DailyTimeSheet> forWeekend(final DayOfWeek dow, final Project... projects) {
			this.dailyTimeSheet.day = dow;
			this.dailyTimeSheet.dayType = DayType.WEEK_END;
			for (var project : projects) {
				this.dailyTimeSheet.entries
						.add(TimeSheetEntry.forWeekEnd(project).setDailyTimeSheet(this.dailyTimeSheet));
			}
			entryAlreadyAdded = true;
			return this;
		}

		@Override
		public Buildable<DailyTimeSheet> forWeekendSaturday(final Project... projects) {
			this.dailyTimeSheet.day = DayOfWeek.SATURDAY;
			this.dailyTimeSheet.dayType = DayType.WEEK_END;
			for (var project : projects) {
				this.dailyTimeSheet.entries
						.add(TimeSheetEntry.forWeekEnd(project).setDailyTimeSheet(this.dailyTimeSheet));
			}
			entryAlreadyAdded = true;
			return this;
		}

		@Override
		public Buildable<DailyTimeSheet> forWeekendSunday(final Project... projects) {
			this.dailyTimeSheet.day = DayOfWeek.SUNDAY;
			this.dailyTimeSheet.dayType = DayType.WEEK_END;
			for (var project : projects) {
				this.dailyTimeSheet.entries
						.add(TimeSheetEntry.forWeekEnd(project).setDailyTimeSheet(this.dailyTimeSheet));
			}
			entryAlreadyAdded = true;
			return this;
		}

		@Override
		public DailyTimeSheetEntryBuilder withDaytype(DayType dayType) {
			this.dailyTimeSheet.dayType = dayType;
			return this;
		}

		@Override
		public DailyTimeSheetEntryBuilder forWorkingDay() {
			this.dailyTimeSheet.dayType = DayType.WORKING_DAY;
			return this;
		}

		@Override
		public DailyTimeSheetDailyEntryCommentsBuilder withEntry(TimeSheetEntry timeEntry) {
			this.dailyTimeSheet.entries.add(timeEntry.setDailyTimeSheet(this.dailyTimeSheet));
			entryAlreadyAdded = true;
			return this;
		}

		@Override
		public DailyTimeSheetDailyEntryCommentsBuilder withEntries(Set<TimeSheetEntry> timeEntries) {
			for (var entry : timeEntries) {
				entry.setDailyTimeSheet(this.dailyTimeSheet);
			}
			this.dailyTimeSheet.entries = timeEntries;
			entryAlreadyAdded = true;
			return this;
		}

		@Override
		public Buildable<DailyTimeSheet> withDailyComments(String dailyComments) {
			this.dailyTimeSheet.dailyComments = dailyComments;
			return this;
		}

		@Override
		public DailyTimeSheetEntryDurationBuilder workedOn(Project project) {
			this.project = project;
			return this;
		}

		@Override
		public DailyTimeSheetEntryCommentsBuilder forDuration(Duration hours) {
			this.hours = hours;
			return this;
		}

		@Override
		public DailyTimeSheetDailyCommentsBuilder onTasks(String comments) {
			this.comments = comments;
			return this;
		}

		@Override
		public DailyTimeSheetMoreEntryBuilder dailyComments(String dailyComments) {
			this.dailyTimeSheet.dailyComments = dailyComments;
			return this;
		}

		@Override
		public DailyTimeSheetEntryBuilder and() {
			this.dailyTimeSheet.entries.add(TimeSheetEntry.forWorkingDay().workedOn(this.project)
					.forDuration(this.hours).onTasks(this.comments).build().setDailyTimeSheet(this.dailyTimeSheet));
			return this;
		}

		@Override
		public DailyTimeSheet build() {
			if (!entryAlreadyAdded) {
				this.dailyTimeSheet.entries.add(TimeSheetEntry.forWorkingDay().workedOn(this.project)
						.forDuration(this.hours).onTasks(this.comments).build().setDailyTimeSheet(this.dailyTimeSheet));
			}
			dailyTimeSheet.totalHours = Duration.ZERO;
			for (var entry : dailyTimeSheet.entries) {
				dailyTimeSheet.totalHours = dailyTimeSheet.totalHours.plus(entry.hours());
			}
			return this.dailyTimeSheet;
		}
	}
}
