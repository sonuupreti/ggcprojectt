package com.example.itracktest;

import java.util.regex.Pattern;

import com.gspann.itrack.domain.common.location.Location;
import com.gspann.itrack.domain.common.location.LocationFormat;

public class Test {
	
	private static final String pattern = "/^[a-zA-Z0-9_]+([-.][a-zA-Z0-9_]+)*$/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		System.out.println(Pattern.matches(pattern, "rajveer.singh"));
		
		Location location = new Location("India", "Haryana", "Gurgaon");
		
		System.out.println(location.format());
		System.out.println(location.format(LocationFormat.CITY_STATE_COUNTRY_NAMES_COMMA_SEPERATED));
		System.out.println(location.format(LocationFormat.COUNTRY_STATE_CITY_NAMES_HYPHEN_SEPERATED));
		System.out.println(location.format(LocationFormat.COUNTRY_STATE_CITY_NAMES_HYPHEN_SEPERATED));
	}

}
