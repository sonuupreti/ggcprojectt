package com.gspann.itrack.infra.config;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.convert.DurationUnit;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Properties specific to iTrack.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
//@Getter
@Setter
@NoArgsConstructor
@ToString
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	@NestedConfigurationProperty
	private TimeSheet timeSheet = new TimeSheet();
	
	public TimeSheet timeSheet() {
		return this.timeSheet;
	}
	
//	@Getter
	@Setter
	@NoArgsConstructor
	@ToString
//	@Component
	public static class TimeSheet {
		
		private LocalDate systemStartDate;
		
		@DurationUnit(ChronoUnit.HOURS)
		private Duration standardDailyHours = ApplicationDefaults.TimeSheet.DEFAULT_STANDARD_DAILY_WORKING_HOURS;

		@DurationUnit(ChronoUnit.HOURS)
		private Duration standardWeeklyHours = ApplicationDefaults.TimeSheet.DEFAULT_STANDARD_WEEKLY_WORKING_HOURS;

		private DayOfWeek weekStartDay = ApplicationDefaults.TimeSheet.DEFAULT_WEEK_START_DAY;

		private DayOfWeek weekEndDay = ApplicationDefaults.TimeSheet.DEFAULT_WEEK_END_DAY;
		
		private List<DayOfWeek> weekends = ApplicationDefaults.TimeSheet.DEFAULT_WEEK_ENDS;
		
		private int recentTimesheetsPageSize = ApplicationDefaults.TimeSheet.DEFAULT_RECENT_TIMESHEETS_PAGE_SIZE;
		
		public Duration STANDARD_DAILY_HOURS() {
			return this.standardDailyHours;
		}
		
		public Duration STANDARD_WEEKLY_HOURS() {
			return this.standardWeeklyHours;
		}
		
		public LocalDate SYSTEM_START_DATE() {
			return this.systemStartDate;
		}
		
		public DayOfWeek WEEK_START_DAY() {
			return this.weekStartDay;
		}
		
		public DayOfWeek WEEK_END_DAY() {
			return this.weekEndDay;
		}
		
		public List<DayOfWeek> WEEKENDS() {
			return this.weekends;
		}
		
		public int RECENT_TIMESHEETS_PAGE_SIZE() {
			return this.recentTimesheetsPageSize;
		}
	}
}
