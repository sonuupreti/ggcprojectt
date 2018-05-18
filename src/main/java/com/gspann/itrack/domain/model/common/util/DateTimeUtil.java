package com.gspann.itrack.domain.model.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Set;

import com.gspann.itrack.domain.model.timesheets.Week;

public class DateTimeUtil {
	
    private final static ZoneId TZ = ZoneId.of("Pacific/Auckland");

	public static Week currentWeek() {
		
		ZoneId defaultZone = ZoneId.systemDefault();
		Locale locale = Locale.getDefault();
		System.out.println("defaultZone ->" + defaultZone);
		System.out.println("locale ->" + defaultZone);
	    DayOfWeek firstDayOfWeek  = WeekFields.of(locale).getFirstDayOfWeek();
	    System.out.println("firstDayOfWeek ->" + firstDayOfWeek);
	    
	    LocalDate weekStart = LocalDate.now(defaultZone).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	    System.out.println("weekStart ->" + weekStart);
//        lastDayOfWeek = DayOfWeek.of(((this.firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
    
		return null;
	}
	
	public static void main(String[] args) {
		currentWeek();
	}
}
