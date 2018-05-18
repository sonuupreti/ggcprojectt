package com.example.itracktest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Pattern;

import com.gspann.itrack.domain.model.common.dto.Triple;

public class Test {
	
	private static final String pattern = "/^[a-zA-Z0-9_]+([-.][a-zA-Z0-9_]+)*$/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
//		System.out.println(Pattern.matches(pattern, "rajveer.singh"));
		
		LocalDate today = LocalDate.now();

	    
	    DayOfWeek weekStart = DayOfWeek.WEDNESDAY;
	    
	    LocalDate weekStartDate = today.with(TemporalAdjusters.previousOrSame(weekStart));
	    LocalDate weekEndDate = today.with(TemporalAdjusters.nextOrSame(weekStart)).minusDays(1);
	    
	    System.out.println("Week Start >>> date ->>" + weekStartDate + ", day ->>" + weekStartDate.getDayOfWeek());
	    System.out.println("Week Etart >>> date ->>" + weekEndDate + ", day ->>" + weekEndDate.getDayOfWeek());
	}

}
