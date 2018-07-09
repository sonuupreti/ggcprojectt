package com.gspann.itrack.domain.model.timesheets;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.common.type.Buildable;
import com.gspann.itrack.domain.model.docs.Document;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = { "week", "resource" }, callSuper = false)
@ToString(includeFieldNames = true, exclude = { "resource", "clientTimeSheetScreenShot" })
@Entity
//@formatter:off
@Table(name = "WEEKLY_TIME_SHEETS", 
		uniqueConstraints = {
				@UniqueConstraint(
						name = UNQ_WEEKLY_TIME_SHEETS_STATUS_ID, 
						columnNames = { "WEEKLY_TIME_SHEET_STATUS_ID" 
						}
				) 
		}, 
		indexes = {
				@Index(name = IDX_WEEKLY_TIME_SHEETS_RESOURCE_CODE, columnList = "RESOURCE_CODE"),
				@Index(name = IDX_WEEKLY_TIME_SHEETS_WEEK_START_DATE, columnList = "WEEK_START_DATE"),
				@Index(name = IDX_WEEKLY_TIME_SHEETS_WEEK_END_DATE, columnList = "WEEK_END_DATE")  
		}
)
//@formatter:on
public class WeeklyTimeSheet extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@AttributeOverrides({ @AttributeOverride(name = "dateRange.fromDate", column = @Column(name = "WEEK_START_DATE")),
			@AttributeOverride(name = "dateRange.tillDate", column = @Column(name = "WEEK_END_DATE")) })
	private Week week;

	@NotNull
	@Column(name = "STANDARD_HOURS", nullable = false, length = 5)
	private Duration standardHours;

	@NotNull
	@Column(name = "TOTAL_HOURS", nullable = false, length = 5)
	private Duration totalHours;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "RESOURCE_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_WEEKLY_TIME_SHEETS_RESOURCE_CODE))
	private Resource resource;

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "weeklyTimeSheet", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	// @JoinColumn(name = "WEEKLY_TIME_SHEET_ID", nullable = false)
	@org.hibernate.annotations.OrderBy(clause = "DATE asc")
	@Getter(value = AccessLevel.NONE)
	private Set<DailyTimeSheet> dailyTimeSheets = new LinkedHashSet<>();

	@OneToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.PERSIST)
	// @formatter:off
	@JoinColumn(name = "CLIENT_TIMESHEET_SCREEN_SHOT_ID", unique = false, nullable = true, 
			foreignKey = @ForeignKey(name = FK_WEEKLY_TIME_SHEETS_CLIENT_TIMESHEET_SCREEN_SHOT_ID))
	// @formatter:on
	private Document clientTimeSheetScreenShot;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
	// @formatter:off
	@JoinColumn(name = "WEEKLY_TIME_SHEET_STATUS_ID", nullable = false, 
			foreignKey = @ForeignKey(name = FK_WEEKLY_TIME_SHEETS_STATUS_ID))
	// @formatter:on
	private WeeklyTimeSheetStatus weeklyStatus;

	@Column(name = "USE_AS_TEMPLATE", length = 1)
	private boolean useAsTemplate = false;

	public Set<DailyTimeSheet> dailyTimeSheets() {
		return Collections.unmodifiableSet(this.dailyTimeSheets);
	}

	public Optional<ProjectTimeSheetStatus> weeklyStatus(final Project project) {
		return Optional.ofNullable(this.weeklyStatus.projectTimeSheetStatuses().get(project));
	}

	public void clearAllDailyTimeSheets() {
		if(this.dailyTimeSheets != null && !this.dailyTimeSheets.isEmpty()) {
			this.dailyTimeSheets.forEach((dailyTimeSheet) -> dailyTimeSheet.clearAllTimeEntries());
			this.dailyTimeSheets.clear();
		}
	}

	public boolean replaceDailyTimeSheet(final DailyTimeSheet dailyTimeSheet) {
		this.dailyTimeSheets.remove(dailyTimeSheet);
		return this.dailyTimeSheets.add(dailyTimeSheet);
	}

	public boolean replaceAllDailyTimeSheets(final Set<DailyTimeSheet> dailyTimeSheets) {
		this.dailyTimeSheets.removeAll(dailyTimeSheets);
		return addAllDailyTimeSheets(dailyTimeSheets);
	}

	public boolean addDailyTimeSheet(final DailyTimeSheet dailyTimeSheet) {
		return this.dailyTimeSheets.add(dailyTimeSheet);
	}

	public boolean addAllDailyTimeSheets(final Set<DailyTimeSheet> dailyTimeSheets) {
		dailyTimeSheets.forEach((dailyTimesheet) -> dailyTimesheet.setWeeklyTimeSheet(this));
		return this.dailyTimeSheets.addAll(dailyTimeSheets);
	}

	public Map<DayOfWeek, DailyTimeSheet> dayWiseDailyTimeSheets() {
		return dailyTimeSheets.stream().collect(Collectors.toMap(x -> x.day(), x -> x));
	}

	public Map<Project, Set<TimeSheetEntry>> projectWiseTimeEntriesMap() {
		return this.dailyTimeSheets().stream().flatMap(dailyEntry -> dailyEntry.entries().stream())
				.collect(Collectors.groupingBy(TimeSheetEntry::project, Collectors.toCollection(() -> new TreeSet<>(
						(x, y) -> x.dailyTimeSheet().date().compareTo(y.dailyTimeSheet().date())))));
	}

	public Set<Project> allProjects() {
		if (this.dailyTimeSheets.isEmpty()) {
			throw new IllegalStateException(
					"A timesheet must have atleat one project, hence time entries for the same");
		} else {
			Set<Project> projects = new HashSet<>();
			this.dailyTimeSheets.iterator()
					.forEachRemaining((dailyTimeSheet) -> projects.addAll(dailyTimeSheet.projects()));
			return projects;
		}
	}

	public Set<Project> leaveProjects() {
		if (this.dailyTimeSheets.isEmpty()) {
			throw new IllegalStateException(
					"A timesheet must have atleat one project, hence time entries for the same");
		} else {
			Set<Project> leaveProjects = allProjects();
			leaveProjects.removeIf((project) -> !project.isLeaveType());
			return leaveProjects;
		}
	}

	public Set<Project> nonLeaveProjects() {
		if (this.dailyTimeSheets.isEmpty()) {
			throw new IllegalStateException(
					"A timesheet must have atleat one project, hence time entries for the same");
		} else {
			Set<Project> nonLeaveProjects = allProjects();
			nonLeaveProjects.removeIf((project) -> project.isLeaveType());
			return nonLeaveProjects;
		}
	}

	public void save() {
		if (this.weeklyStatus != null) {
			this.weeklyStatus.clearProjectStatuses();
		}
		this.weeklyStatus = WeeklyTimeSheetStatus.forSave(allProjects());
	}

	public void submit() {
		if (this.weeklyStatus != null && this.weeklyStatus.projectTimeSheetStatuses() != null
				&& !this.weeklyStatus.projectTimeSheetStatuses().isEmpty()) {
			this.weeklyStatus.projectTimeSheetStatuses().clear();
		}
		this.weeklyStatus = WeeklyTimeSheetStatus.forSubmit(allProjects());
	}

	public void saveAsTemplate() {
		this.useAsTemplate = true;
	}

	// public void approve() {
	// }

	public void approve(final Resource approver, final Project project, final String comments) {
		this.weeklyStatus.approve(approver, project, comments);
	}

	// public void reject() {
	// }

	public void reject(final Resource approver, final Project project, final String comments) {
		this.weeklyStatus.reject(approver, project, comments);
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
			dailyTimeSheets.forEach((dailyTimesheet) -> dailyTimesheet.setWeeklyTimeSheet(this.weeklyTimeSheet));
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
			this.weeklyTimeSheet.dailyTimeSheets
					.forEach((dailyTimeSheet) -> this.weeklyTimeSheet.totalHours = this.weeklyTimeSheet.totalHours
							.plus(dailyTimeSheet.totalHours()));
			return this.weeklyTimeSheet;
		}
	}
}
