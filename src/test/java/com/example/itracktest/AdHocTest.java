package com.example.itracktest;

import static java.time.temporal.ChronoUnit.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import com.gspann.itrack.domain.model.timesheets.Week;
import com.gspann.itrack.domain.model.timesheets.vm.TimeSheetWeekStatusVM;

public class AdHocTest {

	public static void main(String[] args) {
		Duration duration = Duration.ofHours(4).plusMinutes(30).plusSeconds(50);

		LocalDate now = LocalDate.now();
//		System.out.println(LocalDate.now());
		
		DayOfWeek weekStartDay = DayOfWeek.MONDAY;
		DayOfWeek weekEndDay = DayOfWeek.SUNDAY;

	    LocalDate weekStartDate = now.with(TemporalAdjusters.previousOrSame(weekStartDay));
	    LocalDate weekEndDate = now.with(TemporalAdjusters.nextOrSame(weekEndDay));
	    

	    LocalDate date = LocalDate.of(2018, 6, 29);
	    LocalDate endDate = date.with(TemporalAdjusters.nextOrSame(weekEndDay));
	    int n = 5;
//	    System.out.println(endDate.minusDays(6*n).minusDays(n-1) + " - " + endDate);
	    
	    String x = "2018-06-11"; 
//	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	    DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE; 
	    LocalDate t1 = LocalDate.parse(x, dtf); 
//	    System.out.println(LocalDate.now().format(dtf));
//	    System.out.println(Week.containingDate(t1, DayOfWeek.MONDAY, DayOfWeek.SUNDAY));
	    
//	    Week current = Week.current(weekStartDay, weekEndDay);
//	    System.out.println("current -->" + current);
//	    System.out.println("next -->" + current.next());
//	    System.out.println("previous -->" + current.previous());
//	    System.out.println("--------------------------------------");
//	    System.out.println("current.isCurrent()-->" + current.isCurrent());
//	    Week newWeek = Week.of(LocalDate.parse("2018-06-05", dtf), LocalDate.parse("2018-06-12", dtf));
//	    System.out.println(newWeek + "----" + newWeek.isCurrent());
//	    System.out.println("--------------------------------------");
//	    System.out.println("--------------------------------------");
	    
	    YearMonth month = YearMonth.now();
//	    System.out.println(month);
//	    System.out.println(month.atDay(1) + " - " + month.atEndOfMonth());
	    
//	    LocalDate firstWeekStartDate = sinceDate
//				.with(TemporalAdjusters.nextOrSame(weekStartDay));
//	    Week.lastWeeks(weekStartDay, weekEndDay, 3, Week.DESCENDING_ORDER).forEach(System.out::println);
	    
	    System.out.println(DAYS.between(now, now));
	}

}
