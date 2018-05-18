package com.example.itracktest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.gspann.itrack.common.constants.ApplicationConstant;
import com.gspann.itrack.domain.model.common.dto.DayVM;
import com.gspann.itrack.domain.model.common.dto.Triple;
import com.gspann.itrack.domain.model.common.dto.WeekVM;

public class Test {
	
	private static final String pattern = "/^[a-zA-Z0-9_]+([-.][a-zA-Z0-9_]+)*$/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
//		System.out.println(Pattern.matches(pattern, "rajveer.singh"));
		

		WeekVM weekDTO = WeekVM.current(ApplicationConstant.WEEKLY_STANDARD_HOURS, ApplicationConstant.DAILY_STANDARD_HOURS);
		
		LocalDate now = LocalDate.now();
	    LocalDate startDate = now.with(TemporalAdjusters.previousOrSame(ApplicationConstant.WEEK_START_DAY));
	    LocalDate endDate = now.with(TemporalAdjusters.nextOrSame(ApplicationConstant.WEEK_START_DAY));

		Set<LocalDate> holidays = new HashSet<LocalDate>();
		holidays.add(startDate.plusDays(3));
		
		while(!startDate.isEqual(endDate)) {
			if(holidays.contains(startDate)) {
				weekDTO.addDayVM(DayVM.ofHoliday(startDate, "Diwali"));
			} else if(startDate.getDayOfWeek() == ApplicationConstant.WEEK_END_FIRST
					|| startDate.getDayOfWeek() == ApplicationConstant.WEEK_END_SECOND) {
				weekDTO.addDayVM(DayVM.ofWeekend(startDate));
			} else {
				weekDTO.addDayVM(DayVM.ofWorkingDay(startDate));
			}
			startDate = startDate.plusDays(1);
		}
		System.out.println(weekDTO);
	}

}
