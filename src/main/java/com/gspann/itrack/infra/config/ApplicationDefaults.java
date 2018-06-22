package com.gspann.itrack.infra.config;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public interface ApplicationDefaults {

	public interface TimeSheet {

		public Duration DEFAULT_STANDARD_DAILY_WORKING_HOURS = Duration.ofHours(8);
		
		public Duration DEFAULT_STANDARD_WEEKLY_WORKING_HOURS = Duration.ofHours(40);

		public DayOfWeek DEFAULT_WEEK_START_DAY = DayOfWeek.MONDAY;

		public DayOfWeek DEFAULT_WEEK_END_DAY = DayOfWeek.SUNDAY;
	
		public List<DayOfWeek> DEFAULT_WEEK_ENDS = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		
		public int DEFAULT_RECENT_TIMESHEETS_PAGE_SIZE = 4;
	}
}
