package com.gspann.itrack.domain.model.timesheets;

import static com.gspann.itrack.adapter.persistence.PersistenceConstant.TableMetaData.*;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gspann.itrack.domain.model.common.type.BaseIdentifiableVersionableEntity;
import com.gspann.itrack.domain.model.common.type.Buildable;
import com.gspann.itrack.domain.model.projects.Project;

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
@EqualsAndHashCode(of = { "project", "dailyTimeSheet" }, callSuper = false)
@ToString(includeFieldNames = true, exclude = "dailyTimeSheet")
@Entity
@Table(name = "TIME_SHEET_ENTRIES")
public class TimeSheetEntry extends BaseIdentifiableVersionableEntity<Long, Long> {

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PROJECT_CODE", nullable = false, foreignKey = @ForeignKey(name = FK_TIME_SHEET_ENTRIES_PROJECT_CODE))
	private Project project;

	@NotNull
	@Column(name = "HOURS", nullable = false, length = 5)
	private Duration hours;

	@Column(name = "COMMENTS", nullable = true, length = 255)
	private String comments;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "DAILY_TIME_SHEET_ID", nullable = false, foreignKey = @ForeignKey(name = FK_TIME_SHEET_ENTRIES_DAILY_TIME_SHEET_ID))
	private DailyTimeSheet dailyTimeSheet;

	public TimeSheetEntry setDailyTimeSheet(final DailyTimeSheet dailyTimeSheet) {
		this.dailyTimeSheet = dailyTimeSheet;
		return this;
	}

	public static TimeSheetEntry forHoliday(final Project project) {
		TimeSheetEntry entry = new TimeSheetEntry();
		entry.project = project;
		entry.hours = Duration.ZERO;
		return entry;
	}

	public static TimeSheetEntry forWeekEnd(final Project project) {
		TimeSheetEntry entry = new TimeSheetEntry();
		entry.project = project;
		entry.hours = Duration.ZERO;
		return entry;
	}

	public static TimeSheetEntryProjectBuilder forWorkingHoliday() {
		return new TimeSheetEntryBuilder();
	}

	public static TimeSheetEntryProjectBuilder forWorkingWeekend() {
		return new TimeSheetEntryBuilder();
	}

	public static TimeSheetEntryProjectBuilder forWorkingDay() {
		return new TimeSheetEntryBuilder();
	}

	public interface TimeSheetEntryProjectBuilder {
		public TimeSheetEntryDurationBuilder workedOn(final Project project);
	}

	public interface TimeSheetEntryDurationBuilder {
		public TimeSheetEntryCommentsBuilder forDuration(final Duration hours);
	}

	public interface TimeSheetEntryCommentsBuilder extends Buildable<TimeSheetEntry> {
		public Buildable<TimeSheetEntry> onTasks(final String comments);
	}

	public static class TimeSheetEntryBuilder
			implements TimeSheetEntryProjectBuilder, TimeSheetEntryDurationBuilder, TimeSheetEntryCommentsBuilder {
		private TimeSheetEntry entry;

		public TimeSheetEntryBuilder() {
			this.entry = new TimeSheetEntry();
		}

		@Override
		public TimeSheetEntryDurationBuilder workedOn(Project project) {
			this.entry.project = project;
			return this;
		}

		@Override
		public TimeSheetEntryCommentsBuilder forDuration(final Duration hours) {
			this.entry.hours = hours;
			return this;
		}

		@Override
		public Buildable<TimeSheetEntry> onTasks(final String comments) {
			this.entry.comments = comments;
			return this;
		}

		@Override
		public TimeSheetEntry build() {
			return this.entry;
		}
	}
}
